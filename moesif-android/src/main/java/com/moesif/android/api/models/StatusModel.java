/*
 * MoesifAndroid
 *
 *
 */
package com.moesif.android.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StatusModel
        extends java.util.Observable {
    private static final long serialVersionUID = 5538233462953943768L;
    private boolean status;
    private String region;
    /** GETTER
     * Status of Call
     */
    @JsonProperty("status")
    public boolean getStatus ( ) { 
        return this.status;
    }
    
    /** SETTER
     * Status of Call
     */
    @JsonProperty("status")
    public void setStatus (boolean value) { 
        this.status = value;
        notifyObservers(this.status);
    }
 
    /** GETTER
     * Location
     */
    @JsonProperty("region")
    public String getRegion ( ) { 
        return this.region;
    }
    
    /** SETTER
     * Location
     */
    @JsonProperty("region")
    public void setRegion (String value) { 
        this.region = value;
        notifyObservers(this.region);
    }
 
}
 