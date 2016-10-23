/*
 * MoesifAndroid
 *
 *
 */
package com.moesif.android.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EventModel
        extends java.util.Observable {
    private static final long serialVersionUID = 4870784894453872193L;
    private EventRequestModel request;
    private EventResponseModel response;
    private String sessionToken;
    private String tags;
    private String userId;
    /** GETTER
     * API request object
     */
    @JsonProperty("request")
    public EventRequestModel getRequest ( ) {
        return this.request;
    }
    
    /** SETTER
     * API request object
     */
    @JsonProperty("request")
    public void setRequest (EventRequestModel value) {
        this.request = value;
        notifyObservers(this.request);
    }
 
    /** GETTER
     * API response Object
     */
    @JsonProperty("response")
    public EventResponseModel getResponse ( ) {
        return this.response;
    }
    
    /** SETTER
     * API response Object
     */
    @JsonProperty("response")
    public void setResponse (EventResponseModel value) {
        this.response = value;
        notifyObservers(this.response);
    }
 
    /** GETTER
     * End user's auth/session token
     */
    @JsonProperty("session_token")
    public String getSessionToken ( ) { 
        return this.sessionToken;
    }
    
    /** SETTER
     * End user's auth/session token
     */
    @JsonProperty("session_token")
    public void setSessionToken (String value) { 
        this.sessionToken = value;
        notifyObservers(this.sessionToken);
    }
 
    /** GETTER
     * comma separated list of tags, see documentation
     */
    @JsonProperty("tags")
    public String getTags ( ) { 
        return this.tags;
    }
    
    /** SETTER
     * comma separated list of tags, see documentation
     */
    @JsonProperty("tags")
    public void setTags (String value) { 
        this.tags = value;
        notifyObservers(this.tags);
    }
 
    /** GETTER
     * End user's user_id string from your app
     */
    @JsonProperty("user_id")
    public String getUserId ( ) { 
        return this.userId;
    }
    
    /** SETTER
     * End user's user_id string from your app
     */
    @JsonProperty("user_id")
    public void setUserId (String value) { 
        this.userId = value;
        notifyObservers(this.userId);
    }
 
}
 