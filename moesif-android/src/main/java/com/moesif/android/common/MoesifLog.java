package com.moesif.android.common;

import android.util.Log;

public class MoesifLog {

    private volatile boolean logingEnabled = true;
    private volatile int logLevel = Log.INFO;

    private static final MoesifLog instance = new MoesifLog();

    public static MoesifLog getLogger() {
        return instance;
    }

    private MoesifLog() {}

    public MoesifLog setLogLevel(int logLevel) {
        this.logLevel = logLevel;
        return instance;
    }

    public MoesifLog setLogingEnabled(boolean logingEnabled) {
        this.logingEnabled = logingEnabled;
        return instance;
    }

    public int d(String tag, String msg) {
        if (logingEnabled && logLevel <= Log.DEBUG) return Log.d(tag, msg);
        return 0;
    }

    public int d(String tag, String msg, Throwable tr) {
        if (logingEnabled && logLevel <= Log.DEBUG) return Log.d(tag, msg, tr);
        return 0;
    }

    public int e(String tag, String msg) {
        if (logingEnabled && logLevel <= Log.ERROR) return Log.e(tag, msg);
        return 0;
    }

    public int e(String tag, String msg, Throwable tr) {
        if (logingEnabled && logLevel <= Log.ERROR) return Log.e(tag, msg, tr);
        return 0;
    }

    public String getStackTraceString(Throwable tr) {
        return Log.getStackTraceString(tr);
    }

    public int i(String tag, String msg) {
        if (logingEnabled && logLevel <= Log.INFO) return Log.i(tag, msg);
        return 0;
    }

    public int i(String tag, String msg, Throwable tr) {
        if (logingEnabled && logLevel <= Log.INFO) return Log.i(tag, msg, tr);
        return 0;
    }

    public boolean isLoggable(String tag, int level) {
        return Log.isLoggable(tag, level);
    }

    public int println(int priority, String tag, String msg) {
        return Log.println(priority, tag, msg);
    }

    public int v(String tag, String msg) {
        if (logingEnabled && logLevel <= Log.VERBOSE) return Log.v(tag, msg);
        return 0;
    }

    public int v(String tag, String msg, Throwable tr) {
        if (logingEnabled && logLevel <= Log.VERBOSE) return Log.v(tag, msg, tr);
        return 0;
    }

    public int w(String tag, String msg) {
        if (logingEnabled && logLevel <= Log.WARN) return Log.w(tag, msg);
        return 0;
    }

    public int w(String tag, Throwable tr) {
        if (logingEnabled && logLevel <= Log.WARN) return Log.w(tag, tr);
        return 0;
    }

    public int w(String tag, String msg, Throwable tr) {
        if (logingEnabled && logLevel <= Log.WARN) return Log.w(tag, msg, tr);
        return 0;
    }

    public int wtf(String tag, String msg) {
        if (logingEnabled && logLevel <= Log.ASSERT) return Log.wtf(tag, msg);
        return 0;
    }

    public int wtf(String tag, Throwable tr) {
        if (logingEnabled && logLevel <= Log.ASSERT) return Log.wtf(tag, tr);
        return 0;
    }

    public int wtf(String tag, String msg, Throwable tr) {
        if (logingEnabled && logLevel <= Log.ASSERT) return Log.wtf(tag, msg, tr);
        return 0;
    }
}