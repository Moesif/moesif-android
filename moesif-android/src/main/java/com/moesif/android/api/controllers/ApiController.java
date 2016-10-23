/*
 * MoesifAndroid
 *
 *
 */
package com.moesif.android.api.controllers;

import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.moesif.android.api.ApiHelper;
import com.moesif.android.GlobalConfig;
import com.moesif.android.api.models.*;
import com.moesif.android.api.exceptions.*;
import com.moesif.android.api.http.client.HttpContext;
import com.moesif.android.api.http.request.HttpRequest;
import com.moesif.android.api.http.response.HttpResponse;
import com.moesif.android.api.http.client.APICallBack;

public class ApiController extends BaseController {    
    //private static variables for the singleton pattern
    private static final Object syncObject = new Object();
    private static ApiController instance = null;

    /**
     * Singleton pattern implementation 
     * @return The singleton instance of the ApiController class 
     */
    public static ApiController getInstance() {
        synchronized (syncObject) {
            if (null == instance) {
                instance = new ApiController();
            }
        }
        return instance;
    }

    /**
     * Add Single API Event Call
     * @param    body    Required parameter: Example: 
     * @return    Returns the void response from the API call 
     */
    public void createEventAsync(
                final EventModel body,
                final APICallBack<Object> callBack
    ) throws JsonProcessingException {
        //the base uri for api requests
        String _baseUri = GlobalConfig.baseUri;

        //prepare query string for API call
        StringBuilder _queryBuilder = new StringBuilder(_baseUri);
        _queryBuilder.append("/v1/events");
        //validate and preprocess url
        String _queryUrl = ApiHelper.cleanUrl(_queryBuilder);

        //load all headers for the outgoing API request
        Map<String, String> _headers = new HashMap<String, String>() {
            private static final long serialVersionUID = 4954891755887909753L;
            {
                    put( "user-agent", System.getProperty("http.agent") );
                    put( "X-Moesif-Application-Id", GlobalConfig.getApplicationId() );
            }
        };

        //prepare and invoke the API call request to fetch the response
        final HttpRequest _request = getClientInstance().postBody(_queryUrl, _headers, ApiHelper.serialize(body));

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

                            //let the caller know of the success
                            callBack.onSuccess(_context, _context);
                        } catch (APIException error) {
                            //let the caller know of the error
                            callBack.onFailure(_context, error);
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

    /**
     * Add multiple API Events in a single batch (batch size must be less than 250kb)
     * @param    body    Required parameter: Example: 
     * @return    Returns the void response from the API call 
     */
    public void createEventsBatchAsync(
                final List<EventModel> body,
                final APICallBack<Object> callBack
    ) throws JsonProcessingException {
        //the base uri for api requests
        String _baseUri = GlobalConfig.baseUri;

        //prepare query string for API call
        StringBuilder _queryBuilder = new StringBuilder(_baseUri);
        _queryBuilder.append("/v1/events/batch");
        //validate and preprocess url
        String _queryUrl = ApiHelper.cleanUrl(_queryBuilder);

        //load all headers for the outgoing API request
        Map<String, String> _headers = new HashMap<String, String>() {
            private static final long serialVersionUID = 5716520205381280331L;
            {
                    put( "user-agent", System.getProperty("http.agent") );
                    put( "X-Moesif-Application-Id", GlobalConfig.getApplicationId() );
            }
        };

        //prepare and invoke the API call request to fetch the response
        final HttpRequest _request = getClientInstance().postBody(_queryUrl, _headers, ApiHelper.serialize(body));

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

                            //let the caller know of the success
                            callBack.onSuccess(_context, _context);
                        } catch (APIException error) {
                            //let the caller know of the error
                            callBack.onFailure(_context, error);
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