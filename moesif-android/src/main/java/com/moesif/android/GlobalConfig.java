/*
 * MoesifAndroid
 *
 *
 */
package com.moesif.android;

public class GlobalConfig {
    //The base Uri for API calls
    public static String baseUri = "https://api.moesif.net";

    //private backing field for applicationId
    private static String applicationId;

    //private backing field for your API Version
    private static String apiVersion;
    
    //This token authenticates your API Calls
    /**
     * getValue value for applicationId
     * @return  applicationId Your Application Id for authentication/authorization
     */
    public static String getApplicationId() {
        if(applicationId == null)
            throw new IllegalStateException("Must initialize configuration before use");

        return applicationId;
    }

    /**
     * Set value for applicationId
     * @param applicationId Your Application Id for authentication/authorization
     */
    public static void setApplicationId(String applicationId) {
        GlobalConfig.applicationId = applicationId;
    }


    //Optionally set a your API Version
    /**
     * getValue value for apiVersion
     * @return  apiVersion Your API Version
     */
    public static String getApiVersion() {
        if(apiVersion == null)
            throw new IllegalStateException("Must initialize configuration before use");

        return apiVersion;
    }

    /**
     * Set value for apiVersion
     * @param apiVersion Your apiVersion string if your API is versioned
     */
    public static void setApiVersion(String apiVersion) {
        GlobalConfig.apiVersion = apiVersion;
    }
}
