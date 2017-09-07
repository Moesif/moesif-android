package com.moesif.android.okhttp3.models;

import com.moesif.android.api.models.EventResponseModel;
import com.moesif.android.utils.CollectionUtils;

import java.io.InputStream;
import java.util.Date;

import okhttp3.*;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;


/*
 * MoesifAndroid
 *
 *
 */
public class OkHttp3ResponseMapper extends EventResponseModel {

    public static EventResponseModel createOkHttp3Response(Response response, Connection connection) {

        String ipAddress = null;
        if (connection != null) {
            ipAddress = connection.route().socketAddress().getAddress().getHostAddress();
        }

        EventResponseModel responseModel = new EventResponseModel();
        responseModel.setTime(new Date());
        responseModel.setStatus(response.code());
        responseModel.setHeaders(CollectionUtils.flattenMultiMap(response.headers().toMultimap()));
        responseModel.setIpAddress(ipAddress);
        return responseModel;
    }
}
