/*
 * MoesifAndroid
 *
 *
 */
package com.moesif.android.api.models;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.moesif.android.api.ApiConstants;

public class EventRequestModel
        extends java.util.Observable {
    private static final long serialVersionUID = 5088558222649538649L;
    private Date time;
    private String uri;
    private String verb;
    private Object headers;
    private String apiVersion;
    private String ipAddress;
    private Object body;
    /** GETTER
     * Time when request was made
     */
    @JsonProperty("time")
    @JsonFormat(pattern = ApiConstants.DATETIME_FORMAT)
    public Date getTime ( ) { 
        return this.time;
    }
    
    /** SETTER
     * Time when request was made
     */
    @JsonProperty("time")
    public void setTime (Date value) { 
        this.time = value;
        notifyObservers(this.time);
    }
 
    /** GETTER
     * full uri of request such as https://www.example.com/my_path?param=1
     */
    @JsonProperty("uri")
    public String getUri ( ) { 
        return this.uri;
    }
    
    /** SETTER
     * full uri of request such as https://www.example.com/my_path?param=1
     */
    @JsonProperty("uri")
    public void setUri (String value) { 
        this.uri = value;
        notifyObservers(this.uri);
    }
 
    /** GETTER
     * verb of the API request such as GET or POST
     */
    @JsonProperty("verb")
    public String getVerb ( ) { 
        return this.verb;
    }
    
    /** SETTER
     * verb of the API request such as GET or POST
     */
    @JsonProperty("verb")
    public void setVerb (String value) { 
        this.verb = value;
        notifyObservers(this.verb);
    }
 
    /** GETTER
     * Key/Value map of request headers
     */
    @JsonProperty("headers")
    public Object getHeaders ( ) { 
        return this.headers;
    }
    
    /** SETTER
     * Key/Value map of request headers
     */
    @JsonProperty("headers")
    public void setHeaders (Object value) { 
        this.headers = value;
        notifyObservers(this.headers);
    }
 
    /** GETTER
     * Optionally tag the call with your API or App version
     */
    @JsonProperty("api_version")
    public String getApiVersion ( ) { 
        return this.apiVersion;
    }
    
    /** SETTER
     * Optionally tag the call with your API or App version
     */
    @JsonProperty("api_version")
    public void setApiVersion (String value) { 
        this.apiVersion = value;
        notifyObservers(this.apiVersion);
    }
 
    /** GETTER
     * IP Address of the client if known.
     */
    @JsonProperty("ip_address")
    public String getIpAddress ( ) { 
        return this.ipAddress;
    }
    
    /** SETTER
     * IP Address of the client if known.
     */
    @JsonProperty("ip_address")
    public void setIpAddress (String value) { 
        this.ipAddress = value;
        notifyObservers(this.ipAddress);
    }
 
    /** GETTER
     * Request body
     */
    @JsonProperty("body")
    public Object getBody ( ) { 
        return this.body;
    }
    
    /** SETTER
     * Request body
     */
    @JsonProperty("body")
    public void setBody (Object value) { 
        this.body = value;
        notifyObservers(this.body);
    }
 
}
 