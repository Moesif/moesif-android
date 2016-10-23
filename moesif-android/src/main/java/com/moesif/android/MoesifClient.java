/*
 * MoesifAndroid
 *
 *
 */
package com.moesif.android;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.moesif.android.api.ApiClient;
import com.moesif.android.api.controllers.*;
import com.moesif.android.common.MoesifLog;
import com.moesif.android.persistence.UserSessionStore;

import javax.annotation.CheckForNull;

public final class MoesifClient {
    private static final String LOGTAG = MoesifClient.class.getCanonicalName();

    //private static variables for the singleton pattern
    private static final Object syncObject = new Object();
    private static MoesifClient instance = null;

    /**
     * No Access private constructor
     */
    private MoesifClient() {
    }

    /**
     * Default constructor
     */
    private MoesifClient(Context context) {
        Context appContext = context.getApplicationContext();

        String packageName = appContext.getPackageName();
        PackageManager packageManager = context.getPackageManager();
        try {
            ApplicationInfo app = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
            Bundle bundle = app.metaData;

            if(bundle != null) {
                GlobalConfig.setApplicationId(bundle.getString(BuildConfig.APPLICATION_ID + ".ApplicationId"));
            }
            try {
                GlobalConfig.setApiVersion(packageManager.getPackageInfo(packageName, 0).versionName);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        catch (Exception ex){
            throw new IllegalStateException("Unable to initialize", ex);
        }

        ApiClient.initialize(context);
        UserSessionStore.initialize(context);
    }

    /**
     * Initialization of configuration by reading from App Context
     * @param  context Android Application Context
     */
    public static void initialize(Context context) {
        synchronized (syncObject) {
            if (null == instance) {
                instance = new MoesifClient(context.getApplicationContext());
            } else {
                MoesifLog.getLogger().w(LOGTAG, "MoesifClient already initialized, skipping initialization");
            }
        }
    }

    /**
     * Singleton pattern implementation
     * @return The singleton instance of the ApiController class
     */
    public static MoesifClient getInstance() throws IllegalAccessException {
        if (instance == null) {
            throw new IllegalAccessException("Call MoesifClient.initialize() first before getting an instance");
        }
        return instance;
    }

    /**
     * Set value for sessionToken
     * @param sessionToken Your End User's Session Token
     */
    public static void setSessionToken(@CheckForNull String sessionToken) throws IllegalAccessException {
        UserSessionStore.getInstance().setSessionToken(sessionToken);
    }

    /**
     * Set value for userId
     * @param userId Your End User's Id
     */
    public static void identifyUser(@CheckForNull String userId) throws IllegalAccessException {
        UserSessionStore.getInstance().setUserIdentity(userId);
    }

    /**
     * Access to Api controller
     * @return	Returns the ApiController instance
     */
    public static ApiController getApi() {
        return ApiController.getInstance();
    }

    /**
     * Access to Health controller
     * @return	Returns the HealthController instance 
     */
    public static HealthController getHealth() {
        return HealthController.getInstance();
    }
}