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

public class EventResponseModel
        extends java.util.Observable {
    private static final long serialVersionUID = 4852928021807078251L;
    private Date time;
    private int status;
    private Object headers;
    private Object body;
    private String ipAddress;
    /** GETTER
     * Time when response received
     */
    @JsonProperty("time")
    @JsonFormat(pattern = ApiConstants.DATETIME_FORMAT)
    public Date getTime ( ) { 
        return this.time;
    }
    
    /** SETTER
     * Time when response received
     */
    @JsonProperty("time")
    public void setTime (Date value) { 
        this.time = value;
        notifyObservers(this.time);
    }
 
    /** GETTER
     * HTTP Status code such as 200
     */
    @JsonProperty("status")
    public int getStatus ( ) { 
        return this.status;
    }
    
    /** SETTER
     * HTTP Status code such as 200
     */
    @JsonProperty("status")
    public void setStatus (int value) { 
        this.status = value;
        notifyObservers(this.status);
    }
 
    /** GETTER
     * Key/Value map of response headers
     */
    @JsonProperty("headers")
    public Object getHeaders ( ) { 
        return this.headers;
    }
    
    /** SETTER
     * Key/Value map of response headers
     */
    @JsonProperty("headers")
    public void setHeaders (Object value) { 
        this.headers = value;
        notifyObservers(this.headers);
    }
 
    /** GETTER
     * Response body
     */
    @JsonProperty("body")
    public Object getBody ( ) { 
        return this.body;
    }
    
    /** SETTER
     * Response body
     */
    @JsonProperty("body")
    public void setBody (Object value) { 
        this.body = value;
        notifyObservers(this.body);
    }
 
    /** GETTER
     * IP Address from the response, such as the server IP Address
     */
    @JsonProperty("ip_address")
    public String getIpAddress ( ) { 
        return this.ipAddress;
    }
    
    /** SETTER
     * IP Address from the response, such as the server IP Address
     */
    @JsonProperty("ip_address")
    public void setIpAddress (String value) { 
        this.ipAddress = value;
        notifyObservers(this.ipAddress);
    }
 
}
 