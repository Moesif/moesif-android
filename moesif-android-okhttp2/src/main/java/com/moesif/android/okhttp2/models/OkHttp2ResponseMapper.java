package com.moesif.android.okhttp2.models;

import com.moesif.android.api.models.EventResponseModel;
import com.moesif.android.utils.CollectionUtils;

import java.util.Date;
import com.squareup.okhttp.Connection;
import com.squareup.okhttp.Response;


/*
 * MoesifAndroid
 *
 *
 */
public class OkHttp2ResponseMapper extends EventResponseModel {

    public static EventResponseModel createOkHttp2Response(Response response, Connection connection) {

        EventResponseModel responseModel = new EventResponseModel();
        responseModel.setTime(new Date());
        responseModel.setStatus(response.code());
        responseModel.setHeaders(CollectionUtils.flattenMultiMap(response.headers().toMultimap()));
        responseModel.setIpAddress(connection.getRoute().getSocketAddress().getAddress().getHostAddress());
        return responseModel;
    }
}
