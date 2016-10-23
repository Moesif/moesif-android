/*
 * MoesifAndroid
 *
 *
 */
package com.moesif.android.api.controllers;

import java.io.*;
import java.util.*;

import com.fasterxml.jackson.core.type.TypeReference;

import com.moesif.android.api.ApiHelper;
import com.moesif.android.GlobalConfig;
import com.moesif.android.api.models.*;
import com.moesif.android.api.exceptions.*;
import com.moesif.android.api.http.client.HttpContext;
import com.moesif.android.api.http.request.HttpRequest;
import com.moesif.android.api.http.response.HttpResponse;
import com.moesif.android.api.http.response.HttpStringResponse;
import com.moesif.android.api.http.client.APICallBack;

public class HealthController extends BaseController {    
    //private static variables for the singleton pattern
    private static final Object syncObject = new Object();
    private static HealthController instance = null;

    /**
     * Singleton pattern implementation 
     * @return The singleton instance of the HealthController class 
     */
    public static HealthController getInstance() {
        synchronized (syncObject) {
            if (null == instance) {
                instance = new HealthController();
            }
        }
        return instance;
    }

    /**
     * Health Probe
     * @return    Returns the void response from the API call 
     */
    public void getHealthProbeAsync(
                final APICallBack<StatusModel> callBack
    ) {
        //the base uri for api requests
        String _baseUri = GlobalConfig.baseUri;

        //prepare query string for API call
        StringBuilder _queryBuilder = new StringBuilder(_baseUri);
        _queryBuilder.append("/health/probe");
        //validate and preprocess url
        String _queryUrl = ApiHelper.cleanUrl(_queryBuilder);

        //load all headers for the outgoing API request
        Map<String, String> _headers = new HashMap<String, String>() {
            private static final long serialVersionUID = 4862438463231798618L;
            {
                    put( "user-agent", System.getProperty("http.agent") );
                    put( "accept", "application/json" );
                    put( "X-Moesif-Application-Id", GlobalConfig.getApplicationId() );
            }
        };

        //prepare and invoke the API call request to fetch the response
        final HttpRequest _request = getClientInstance().get(_queryUrl, _headers, null);

        //invoke the callback before request if its not null
        if (getHttpCallBack() != null)
        {
            getHttpCallBack().OnBeforeRequest(_request);
        }

        //invoke request and getValue response
        Runnable _responseTask = new Runnable() {
            public void run() {
                //make the API call
                getClientInstance().executeAsStringAsync(_request, new APICallBack<HttpResponse>() {
                    public void onSuccess(HttpContext _context, HttpResponse _response) {
                        try {

                            //invoke the callback after response if its not null
                            if (getHttpCallBack() != null)	
                            {
                                getHttpCallBack().OnAfterResponse(_context);
                            }

                            //handle errors defined at the API level
                            validateResponse(_response, _context);

                            //extract result from the http response
                            String _responseBody = ((HttpStringResponse)_response).getBody();
                            StatusModel _result = ApiHelper.deserialize(_responseBody,
                                                        new TypeReference<StatusModel>(){});

                            //let the caller know of the success
                            callBack.onSuccess(_context, _result);
                        } catch (APIException error) {
                            //let the caller know of the error
                            callBack.onFailure(_context, error);
                        } catch (IOException ioException) {
                            //let the caller know of the caught IO Exception
                            callBack.onFailure(_context, ioException);
                        } catch (Exception exception) {
                            //let the caller know of the caught Exception
                            callBack.onFailure(_context, exception);
                        }
                    }
                    public void onFailure(HttpContext _context, Throwable _error) {
                        //invoke the callback after response if its not null
                        if (getHttpCallBack() != null)	
                            {
                            getHttpCallBack().OnAfterResponse(_context);
                        }

                        //let the caller know of the failure
                        callBack.onFailure(_context, _error);
                    }
                });
            }
        };

        //execute async using thread pool
        ApiHelper.getScheduler().execute(_responseTask);
    }

}