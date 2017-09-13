package com.moesif.android.okhttp3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moesif.android.api.controllers.ApiController;
import com.moesif.android.api.http.client.APICallBack;
import com.moesif.android.api.http.client.HttpContext;
import com.moesif.android.api.models.EventModel;
import com.moesif.android.api.models.EventRequestModel;
import com.moesif.android.api.models.EventResponseModel;
import com.moesif.android.common.MoesifLog;
import com.moesif.android.inspector.INetworkEventReporter;
import com.moesif.android.inspector.NetworkEventReporterImpl;
import com.moesif.android.inspector.RequestBodyHelper;
import com.moesif.android.inspector.ResponseHandler;
import com.moesif.android.okhttp3.models.OkHttp3EventMapper;
import com.moesif.android.okhttp3.models.OkHttp3RequestMapper;
import com.moesif.android.okhttp3.models.OkHttp3ResponseMapper;

import okhttp3.*;
import okio.BufferedSource;
import okio.Okio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class MoesifOkHttp3Interceptor implements Interceptor {

    private static final String LOGTAG = MoesifOkHttp3Interceptor.class.getCanonicalName();

    private final INetworkEventReporter mEventReporter = NetworkEventReporterImpl.get();
    private final AtomicInteger mNextRequestId = new AtomicInteger(0);

    @Override
    public Response intercept(Chain chain) throws IOException {
        final String requestId = String.valueOf(mNextRequestId.getAndIncrement());
        final Request request = chain.request();

        RequestBodyHelper requestBodyHelper;
        requestBodyHelper = new RequestBodyHelper();
        final EventRequestModel loggedRequest =
                OkHttp3RequestMapper.createOkHttp3Request(requestId, request, requestBodyHelper);

        Response response;
        try {
            response = chain.proceed(request);
        } catch (IOException e) {
            MoesifLog.getLogger().e(LOGTAG, "[NetworkEventReporter] cannot proceed", e);
            throw e;
        }

        final Connection connection = chain.connection();

        final ResponseBody responseBody = response.body();
        final EventResponseModel loggedResponse =
                OkHttp3ResponseMapper.createOkHttp3Response(response, connection);

        final boolean isJsonHeader = response.header("Content-Type").toLowerCase().contains("application/json");

        if (responseBody != null) {
            try {
                final ByteArrayOutputStream outputStream = new ByteArrayOutputStream((int) response.body().contentLength()*2);

                InputStream responseStream = mEventReporter.interpretResponseStream(requestId,
                        response.header("Content-Encoding"),
                        responseBody.byteStream(),
                        outputStream,
                        new ResponseHandler() {
                            @Override
                            public void onRead(int numBytes) {
                            }

                            @Override
                            public void onReadDecoded(int numBytes) {
                            }

                            @Override
                            public void onEOF() {
                                sendEvent(loggedRequest, loggedResponse, outputStream, isJsonHeader);
                            }

                            @Override
                            public void onError(IOException e) {
                                MoesifLog.getLogger().e(LOGTAG, "Error Decompressing stream", e);
                            }
                        });

                if (responseStream != null) {
                    response = response.newBuilder()
                        .body(new ForwardingResponseBody(responseBody, responseStream))
                        .build();
                }
            } catch (Exception e) {
                MoesifLog.getLogger().e(LOGTAG, "Cannot Response Body as JSON", e);
            }
        }

        return response;
    }

    private void sendEvent(EventRequestModel loggedRequest, EventResponseModel loggedResponse,
                           ByteArrayOutputStream bodyStream, boolean isJsonHeader) {
        try {
            if (isJsonHeader) {
                ObjectMapper mapper = new ObjectMapper();
                Object json = mapper.readValue(bodyStream.toByteArray(), Object.class);
                loggedResponse.setBody(json);
            } else {
                if (bodyStream.size() < 100000) {
                    loggedResponse.setBody(android.util.Base64.encodeToString(bodyStream.toByteArray(), 0));
                    loggedResponse.setTransferEncoding("base64");
                }
            }

            final EventModel loggedEvent =
                    OkHttp3EventMapper.createOkHttp3Event(loggedRequest, loggedResponse);

            final ApiController apiController = ApiController.getInstance();

            apiController.createEventAsync(loggedEvent, new APICallBack<Object>() {


                public void onSuccess(HttpContext context, final Object response) {
                    MoesifLog.getLogger().i(LOGTAG, "API Call Saved to Moesif");
                }

                public void onFailure(HttpContext context, Throwable error) {
                    MoesifLog.getLogger().e(LOGTAG, "Error saving API Call to Moesif", error);
                }
            });
        } catch (Exception e) {
            MoesifLog.getLogger().e(LOGTAG, "Error deserializing body", e);
        }
    }


    private static class ForwardingResponseBody extends ResponseBody {
        private final ResponseBody mBody;
        private final BufferedSource mInterceptedSource;

        public ForwardingResponseBody(ResponseBody body, InputStream interceptedStream) {
            mBody = body;
            mInterceptedSource = Okio.buffer(Okio.source(interceptedStream));
        }

        @Override
        public MediaType contentType() {
            return mBody.contentType();
        }

        @Override
        public long contentLength() {
            return mBody.contentLength();
        }

        @Override
        public BufferedSource source() {
            // close on the delegating body will actually close this intercepted source, but it
            // was derived from mBody.byteStream() therefore the close will be forwarded all the
            // way to the original.
            return mInterceptedSource;
        }
    }
}