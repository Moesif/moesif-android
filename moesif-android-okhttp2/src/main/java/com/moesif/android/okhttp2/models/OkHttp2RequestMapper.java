package com.moesif.android.okhttp2.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moesif.android.GlobalConfig;
import com.moesif.android.api.models.EventRequestModel;
import com.moesif.android.inspector.RequestBodyHelper;
import com.moesif.android.utils.CollectionUtils;
import com.moesif.android.utils.NetUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import javax.annotation.Nullable;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import okio.BufferedSink;
import okio.Okio;

/*
 * MoesifAndroid
 *
 *
 */
public class OkHttp2RequestMapper extends EventRequestModel {

    public static EventRequestModel createOkHttp2Request(String requestId,
                                                         Request request,
                                                         RequestBodyHelper requestBodyHelper)
            throws java.io.IOException {

        EventRequestModel eventRequest = new EventRequestModel();
        eventRequest.setTime(new Date());
        eventRequest.setUri(request.urlString());
        eventRequest.setVerb(request.method());
        eventRequest.setHeaders(CollectionUtils.flattenMultiMap(request.headers().toMultimap()));
        eventRequest.setApiVersion(GlobalConfig.getApiVersion());
        eventRequest.setIpAddress(NetUtils.getIPAddress(true));

        try {
            if (request.header("Content-Type").toLowerCase().contains("application/json")) {
                eventRequest.setBody(OkHttp2RequestMapper.bodyAsJson(request, requestBodyHelper));
            } else {
                eventRequest.setBody(OkHttp2RequestMapper.bodyAsBase64(request, requestBodyHelper));
                eventRequest.setTransferEncoding("base64");
            }
        } catch (Exception e) {
            eventRequest.setBody(OkHttp2RequestMapper.bodyAsBase64(request, requestBodyHelper));
            eventRequest.setTransferEncoding("base64");
        }
        return eventRequest;
    }

    @Nullable
    private static Object bodyAsJson(Request request, RequestBodyHelper requestBodyHelper) throws IOException {
        RequestBody body = request.body();
        if (body == null || body.contentLength() == 0) {
            return null;
        }

        OutputStream out = requestBodyHelper.createBodySink(request.header("Content-Encoding"));
        BufferedSink bufferedSink = Okio.buffer(Okio.sink(out));
        try {
            body.writeTo(bufferedSink);
        } finally {
            bufferedSink.close();
        }
        ObjectMapper mapper = new ObjectMapper();
        Object json = mapper.readValue(requestBodyHelper.getDisplayBody(), Object.class);
        return json;
    }

    @Nullable
    private static Object bodyAsBase64(Request request, RequestBodyHelper requestBodyHelper) throws IOException {
        RequestBody body = request.body();
        if (body == null || body.contentLength() == 0) {
            return null;
        }

        OutputStream out = requestBodyHelper.createBodySink(request.header("Content-Encoding"));
        BufferedSink bufferedSink = Okio.buffer(Okio.sink(out));
        try {
            body.writeTo(bufferedSink);
        } finally {
            bufferedSink.close();
        }
        return android.util.Base64.encodeToString(requestBodyHelper.getDisplayBody(), 0);
    }
}