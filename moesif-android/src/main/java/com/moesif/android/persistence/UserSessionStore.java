package com.moesif.android.persistence;

import android.content.Context;
import android.content.SharedPreferences;

import com.moesif.android.GlobalConfig;
import com.moesif.android.common.MoesifLog;

/*
 * MoesifAndroid
 *
 *
 */
public final class UserSessionStore {

    private static final String LOGTAG = UserSessionStore.class.getCanonicalName();

    //private static variables for the singleton pattern
    private static final Object syncObject = new Object();
    private static UserSessionStore instance = null;

    /**
     * No Access private constructor
     */
    private UserSessionStore() {
    }

    /**
     * Default constructor
     */
    private UserSessionStore (Context context) {

        final String prefsName =  PersistenceConstants.SHARED_PREFERENCES_NAME + "_" + GlobalConfig.getApplicationId();
        preferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE);
        readPrefs();
    }

    /**
     * Initialization of configuration by reading from App Context
     * @param  context Android Application Context
     */
    public static void initialize(Context context) {
        synchronized (syncObject) {
            if (null == instance) {
                instance = new UserSessionStore(context.getApplicationContext());
            } else {
                MoesifLog.getLogger().w(LOGTAG, "UserSessionStore already initialized, skipping initialization");
            }
        }
    }

    /**
     * Singleton pattern implementation
     * @return The singleton instance of the ApiController class
     */
    public static UserSessionStore getInstance() throws IllegalAccessException {
        if (instance == null) {
            throw new IllegalAccessException("Call UserSessionStore.initialize() first before getting an instance");
        }
        return instance;
    }

    public synchronized String getSessionToken() {
        if (!loaded) {
            readPrefs();
        }
        return sessionToken;
    }

    public synchronized void setSessionToken(String sessionToken) {
        if (!loaded) {
            readPrefs();
        }
        this.sessionToken = sessionToken;
        writePrefs();
    }

    public synchronized String getUserIdentity() {
        if (!loaded) {
            readPrefs();
        }
        return userId;
    }

    public synchronized void setUserIdentity(String userId) {
        if (!loaded) {
            readPrefs();
        }
        this.userId = userId;
        writePrefs();
    }

    private void readPrefs() {
        if (null == preferences) {
            return;
        }

        sessionToken = preferences.getString("session_token", null);
        userId = preferences.getString("user_id", null);
        loaded = true;
    }


    private void writePrefs() {
        final SharedPreferences.Editor prefsEditor = preferences.edit();

        prefsEditor.putString("session_token", sessionToken);
        prefsEditor.putString("user_id", userId);

        prefsEditor.apply();
    }

    private SharedPreferences preferences;
    private boolean loaded;
    private String sessionToken;
    private String userId;
}
