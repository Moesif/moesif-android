/*
 * MoesifAndroid
 *
 *
 */
package com.moesif.android.api.controllers;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import static org.junit.Assert.*;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.moesif.android.GlobalConfig;
import com.moesif.android.MoesifClient;
import com.moesif.android.api.models.*;
import com.moesif.android.api.ApiHelper;
import com.moesif.android.api.controllers.syncwrapper.APICallBackCatcher;

import com.fasterxml.jackson.core.type.TypeReference;

@RunWith(AndroidJUnit4.class)
//@LargeTest
public class ApiControllerTest extends ControllerTestBase {
    
    /**
     * Controller instance (for all tests)
     */
    private static ApiController apiController;
    
    /**
     * Setup test class
     */
    @BeforeClass
    public static void setUpClass() {
        MoesifClient.initialize(InstrumentationRegistry.getContext());
        GlobalConfig.setApplicationId("eyJhcHAiOiIzNjU6NiIsInZlciI6IjIuMCIsIm9yZyI6IjM1OTo0IiwiaWF0IjoxNDczMzc5MjAwfQ.9WOx3D357PGMxrXzFm3pV3IzJSYNsO4oRudiMI8mQ3Q");
        apiController = MoesifClient.getApi();
    }

    /**
     * Tear down test class
     * @throws IOException
     */
    @AfterClass
    public static void tearDownClass() throws IOException {
        apiController = null;
    }

    /**
     * Add Single Event via Injestion API
     * @throws Throwable
     */
    @Test
    public void testAddEvent() throws Throwable {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

        // Parameters for the API call
        Object reqHeaders = ApiHelper.deserialize("{" +
                    "\"Host\": \"api.acmeinc.com\"," +
                    "\"Accept\": \"*/*\"," +
                    "\"Connection\": \"Keep-Alive\"," +
                    "\"User-Agent\": \"Dalvik/2.1.0 (Linux; U; Android 5.0.2; C6906 Build/14.5.A.0.242)\"," +
                    "\"Content-Type\": \"application/json\"," +
                    "\"Content-Length\": \"126\"," +
                    "\"Accept-Encoding\": \"gzip\"" +
                "}");

        Object reqBody = ApiHelper.deserialize("{" +
                    "\"items\": [" +
                        "{" +
                            "\"type\": 1," +
                            "\"id\": \"fwfrf\"" +
                        "}," +
                        "{" +
                            "\"type\": 2," +
                            "\"id\": \"d43d3f\"" +
                        "}" +
                    "]" +
                "}");

        Object rspHeaders = ApiHelper.deserialize("{" +
                    "\"Date\": \"Tue, 23 Aug 2016 23:46:49 GMT\"," +
                    "\"Vary\": \"Accept-Encoding\"," +
                    "\"Pragma\": \"no-cache\"," +
                    "\"Expires\": \"-1\"," +
                    "\"Content-Type\": \"application/json; charset=utf-8\"," +
                    "\"Cache-Control\": \"no-cache\"" +
                "}");

        Object rspBody = ApiHelper.deserialize("{" +
                    "\"Error\": \"InvalidArgumentException\"," +
                    "\"Message\": \"Missing field field_a\"" +
                "}");


        EventRequestModel eventReq = new EventRequestModel();

        eventReq.setTime(dateFormat.parse("2016-09-09T04:45:42.914"));
        eventReq.setUri("https://api.acmeinc.com/items/reviews/");
        eventReq.setVerb("PATCH");
        eventReq.setApiVersion("1.1.0");
        eventReq.setIpAddress("61.48.220.123");
        eventReq.setHeaders(reqHeaders);
        eventReq.setBody(reqBody);


        EventResponseModel eventRsp = new EventResponseModel();

        eventRsp.setTime(dateFormat.parse("2016-09-09T04:45:42.914"));
        eventRsp.setStatus(500);
        eventRsp.setHeaders(rspHeaders);
        eventRsp.setBody(rspBody);

        EventModel eventModel = new EventModel();
        eventModel.setRequest(eventReq);
        eventModel.setResponse(eventRsp);
        eventModel.setUserId("my_user_id");
        eventModel.setSessionToken("23jdf0owekfmcn4u3qypxg09w4d8ayrcdx8nu2ng]s98y18cx98q3yhwmnhcfx43f");

        // Set callback and perform API call
        APICallBackCatcher<Object> response = new APICallBackCatcher<Object>();
        apiController.setHttpCallBack(httpResponse);
        apiController.createEventAsync(eventModel, response);
        response.getResult();

        // Test response code
        assertEquals("Status is not 201",
                201, httpResponse.getResponse().getStatusCode());

    }

    /**
     * Add Batched Events via Ingestion API
     * @throws Throwable
     */
    @Test
    public void testAddBatchedEvents() throws Throwable {
        // Parameters for the API call
        List<EventModel> body = ApiHelper.deserialize("[{ 					\"request\": { 						\"time\": \"2016-09-09T04:45:42.914\", 						\"uri\": \"https://api.acmeinc.com/items/reviews/\", 						\"verb\": \"PATCH\", 						\"api_version\": \"1.1.0\", 						\"ip_address\": \"61.48.220.123\", 						\"headers\": { 							\"Host\": \"api.acmeinc.com\", 							\"Accept\": \"*/*\", 							\"Connection\": \"Keep-Alive\", 							\"User-Agent\": \"Dalvik/2.1.0 (Linux; U; Android 5.0.2; C6906 Build/14.5.A.0.242)\", 							\"Content-Type\": \"application/json\", 							\"Content-Length\": \"126\", 							\"Accept-Encoding\": \"gzip\" 						}, 						\"body\": { 							\"items\": [ 								{ 									\"direction_type\": 1, 									\"discovery_id\": \"fwfrf\", 									\"liked\": false 								}, 								{ 									\"direction_type\": 2, 									\"discovery_id\": \"d43d3f\", 									\"liked\": true 								} 							] 						} 					}, 					\"response\": { 						\"time\": \"2016-09-09T04:45:42.914\", 						\"status\": 500, 						\"headers\": { 							\"Date\": \"Tue, 23 Aug 2016 23:46:49 GMT\", 							\"Vary\": \"Accept-Encoding\", 							\"Pragma\": \"no-cache\", 							\"Expires\": \"-1\", 							\"Content-Type\": \"application/json; charset=utf-8\", 							\"X-Powered-By\": \"ARR/3.0\", 							\"Cache-Control\": \"no-cache\", 							\"Arr-Disable-Session-Affinity\": \"true\" 						}, 						\"body\": { 							\"Error\": \"InvalidArgumentException\", 							\"Message\": \"Missing field field_a\" 						} 					}, 					\"user_id\": \"mndug437f43\", 					\"session_token\": \"23jdf0owekfmcn4u3qypxg09w4d8ayrcdx8nu2ng]s98y18cx98q3yhwmnhcfx43f\" 					 }, { 					\"request\": { 						\"time\": \"2016-09-09T04:46:42.914\", 						\"uri\": \"https://api.acmeinc.com/items/reviews/\", 						\"verb\": \"PATCH\", 						\"api_version\": \"1.1.0\", 						\"ip_address\": \"61.48.220.123\", 						\"headers\": { 							\"Host\": \"api.acmeinc.com\", 							\"Accept\": \"*/*\", 							\"Connection\": \"Keep-Alive\", 							\"User-Agent\": \"Dalvik/2.1.0 (Linux; U; Android 5.0.2; C6906 Build/14.5.A.0.242)\", 							\"Content-Type\": \"application/json\", 							\"Content-Length\": \"126\", 							\"Accept-Encoding\": \"gzip\" 						}, 						\"body\": { 							\"items\": [ 								{ 									\"direction_type\": 1, 									\"discovery_id\": \"fwfrf\", 									\"liked\": false 								}, 								{ 									\"direction_type\": 2, 									\"discovery_id\": \"d43d3f\", 									\"liked\": true 								} 							] 						} 					}, 					\"response\": { 						\"time\": \"2016-09-09T04:46:42.914\", 						\"status\": 500, 						\"headers\": { 							\"Date\": \"Tue, 23 Aug 2016 23:46:49 GMT\", 							\"Vary\": \"Accept-Encoding\", 							\"Pragma\": \"no-cache\", 							\"Expires\": \"-1\", 							\"Content-Type\": \"application/json; charset=utf-8\", 							\"X-Powered-By\": \"ARR/3.0\", 							\"Cache-Control\": \"no-cache\", 							\"Arr-Disable-Session-Affinity\": \"true\" 						}, 						\"body\": { 							\"Error\": \"InvalidArgumentException\", 							\"Message\": \"Missing field field_a\" 						} 					}, 					\"user_id\": \"mndug437f43\", 					\"session_token\": \"23jdf0owekfmcn4u3qypxg09w4d8ayrcdx8nu2ng]s98y18cx98q3yhwmnhcfx43f\" 					 }, { 					\"request\": { 						\"time\": \"2016-09-09T04:47:42.914\", 						\"uri\": \"https://api.acmeinc.com/items/reviews/\", 						\"verb\": \"PATCH\", 						\"api_version\": \"1.1.0\", 						\"ip_address\": \"61.48.220.123\", 						\"headers\": { 							\"Host\": \"api.acmeinc.com\", 							\"Accept\": \"*/*\", 							\"Connection\": \"Keep-Alive\", 							\"User-Agent\": \"Dalvik/2.1.0 (Linux; U; Android 5.0.2; C6906 Build/14.5.A.0.242)\", 							\"Content-Type\": \"application/json\", 							\"Content-Length\": \"126\", 							\"Accept-Encoding\": \"gzip\" 						}, 						\"body\": { 							\"items\": [ 								{ 									\"direction_type\": 1, 									\"discovery_id\": \"fwfrf\", 									\"liked\": false 								}, 								{ 									\"direction_type\": 2, 									\"discovery_id\": \"d43d3f\", 									\"liked\": true 								} 							] 						} 					}, 					\"response\": { 						\"time\": \"2016-09-09T04:47:42.914\", 						\"status\": 500, 						\"headers\": { 							\"Date\": \"Tue, 23 Aug 2016 23:46:49 GMT\", 							\"Vary\": \"Accept-Encoding\", 							\"Pragma\": \"no-cache\", 							\"Expires\": \"-1\", 							\"Content-Type\": \"application/json; charset=utf-8\", 							\"X-Powered-By\": \"ARR/3.0\", 							\"Cache-Control\": \"no-cache\", 							\"Arr-Disable-Session-Affinity\": \"true\" 						}, 						\"body\": { 							\"Error\": \"InvalidArgumentException\", 							\"Message\": \"Missing field field_a\" 						} 					}, 					\"user_id\": \"mndug437f43\", 					\"session_token\": \"23jdf0owekfmcn4u3qypxg09w4d8ayrcdx8nu2ng]s98y18cx98q3yhwmnhcfx43f\" 					 }, { 					\"request\": { 						\"time\": \"2016-09-09T04:48:42.914\", 						\"uri\": \"https://api.acmeinc.com/items/reviews/\", 						\"verb\": \"PATCH\", 						\"api_version\": \"1.1.0\", 						\"ip_address\": \"61.48.220.123\", 						\"headers\": { 							\"Host\": \"api.acmeinc.com\", 							\"Accept\": \"*/*\", 							\"Connection\": \"Keep-Alive\", 							\"User-Agent\": \"Dalvik/2.1.0 (Linux; U; Android 5.0.2; C6906 Build/14.5.A.0.242)\", 							\"Content-Type\": \"application/json\", 							\"Content-Length\": \"126\", 							\"Accept-Encoding\": \"gzip\" 						}, 						\"body\": { 							\"items\": [ 								{ 									\"direction_type\": 1, 									\"discovery_id\": \"fwfrf\", 									\"liked\": false 								}, 								{ 									\"direction_type\": 2, 									\"discovery_id\": \"d43d3f\", 									\"liked\": true 								} 							] 						} 					}, 					\"response\": { 						\"time\": \"2016-09-09T04:48:42.914\", 						\"status\": 500, 						\"headers\": { 							\"Date\": \"Tue, 23 Aug 2016 23:46:49 GMT\", 							\"Vary\": \"Accept-Encoding\", 							\"Pragma\": \"no-cache\", 							\"Expires\": \"-1\", 							\"Content-Type\": \"application/json; charset=utf-8\", 							\"X-Powered-By\": \"ARR/3.0\", 							\"Cache-Control\": \"no-cache\", 							\"Arr-Disable-Session-Affinity\": \"true\" 						}, 						\"body\": { 							\"Error\": \"InvalidArgumentException\", 							\"Message\": \"Missing field field_a\" 						} 					}, 					\"user_id\": \"mndug437f43\", 					\"session_token\": \"exfzweachxjgznvKUYrxFcxv]s98y18cx98q3yhwmnhcfx43f\" 					 }, { 					\"request\": { 						\"time\": \"2016-09-09T04:49:42.914\", 						\"uri\": \"https://api.acmeinc.com/items/reviews/\", 						\"verb\": \"PATCH\", 						\"api_version\": \"1.1.0\", 						\"ip_address\": \"61.48.220.123\", 						\"headers\": { 							\"Host\": \"api.acmeinc.com\", 							\"Accept\": \"*/*\", 							\"Connection\": \"Keep-Alive\", 							\"User-Agent\": \"Dalvik/2.1.0 (Linux; U; Android 5.0.2; C6906 Build/14.5.A.0.242)\", 							\"Content-Type\": \"application/json\", 							\"Content-Length\": \"126\", 							\"Accept-Encoding\": \"gzip\" 						}, 						\"body\": { 							\"items\": [ 								{ 									\"direction_type\": 1, 									\"discovery_id\": \"fwfrf\", 									\"liked\": false 								}, 								{ 									\"direction_type\": 2, 									\"discovery_id\": \"d43d3f\", 									\"liked\": true 								} 							] 						} 					}, 					\"response\": { 						\"time\": \"2016-09-09T04:49:42.914\", 						\"status\": 500, 						\"headers\": { 							\"Date\": \"Tue, 23 Aug 2016 23:46:49 GMT\", 							\"Vary\": \"Accept-Encoding\", 							\"Pragma\": \"no-cache\", 							\"Expires\": \"-1\", 							\"Content-Type\": \"application/json; charset=utf-8\", 							\"X-Powered-By\": \"ARR/3.0\", 							\"Cache-Control\": \"no-cache\", 							\"Arr-Disable-Session-Affinity\": \"true\" 						}, 						\"body\": { 							\"Error\": \"InvalidArgumentException\", 							\"Message\": \"Missing field field_a\" 						} 					}, 					\"user_id\": \"mndug437f43\", 					\"session_token\": \"23jdf0owekfmcn4u3qypxg09w4d8ayrcdx8nu2ng]s98y18cx98q3yhwmnhcfx43f\" 					 }, { 					\"request\": { 						\"time\": \"2016-09-09T04:50:42.914\", 						\"uri\": \"https://api.acmeinc.com/items/reviews/\", 						\"verb\": \"PATCH\", 						\"api_version\": \"1.1.0\", 						\"ip_address\": \"61.48.220.123\", 						\"headers\": { 							\"Host\": \"api.acmeinc.com\", 							\"Accept\": \"*/*\", 							\"Connection\": \"Keep-Alive\", 							\"User-Agent\": \"Dalvik/2.1.0 (Linux; U; Android 5.0.2; C6906 Build/14.5.A.0.242)\", 							\"Content-Type\": \"application/json\", 							\"Content-Length\": \"126\", 							\"Accept-Encoding\": \"gzip\" 						}, 						\"body\": { 							\"items\": [ 								{ 									\"direction_type\": 1, 									\"discovery_id\": \"fwfrf\", 									\"liked\": false 								}, 								{ 									\"direction_type\": 2, 									\"discovery_id\": \"d43d3f\", 									\"liked\": true 								} 							] 						} 					}, 					\"response\": { 						\"time\": \"2016-09-09T04:50:42.914\", 						\"status\": 500, 						\"headers\": { 							\"Date\": \"Tue, 23 Aug 2016 23:46:49 GMT\", 							\"Vary\": \"Accept-Encoding\", 							\"Pragma\": \"no-cache\", 							\"Expires\": \"-1\", 							\"Content-Type\": \"application/json; charset=utf-8\", 							\"X-Powered-By\": \"ARR/3.0\", 							\"Cache-Control\": \"no-cache\", 							\"Arr-Disable-Session-Affinity\": \"true\" 						}, 						\"body\": { 							\"Error\": \"InvalidArgumentException\", 							\"Message\": \"Missing field field_a\" 						} 					}, 					\"user_id\": \"recvreedfef\", 					\"session_token\": \"xcvkrjmcfghwuignrmcmhxdhaaezse4w]s98y18cx98q3yhwmnhcfx43f\" 					 } ]", new TypeReference<List<EventModel>>(){});

        // Set callback and perform API call
        APICallBackCatcher<Object> response = new APICallBackCatcher<Object>();
        apiController.setHttpCallBack(httpResponse);
        apiController.createEventsBatchAsync(body, response);
        response.getResult();

        // Test response code
        assertEquals("Status is not 201",
                201, httpResponse.getResponse().getStatusCode());
    }

}
