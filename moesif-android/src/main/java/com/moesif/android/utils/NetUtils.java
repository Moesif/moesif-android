package com.moesif.android.utils;

/*
 * MoesifAndroid
 *
 *
 */
import com.moesif.android.common.MoesifLog;

import java.io.*;
import java.net.*;
import java.util.*;

public class NetUtils {

    private static final String LOGTAG = NetUtils.class.getCanonicalName();
    /**
     * Get IP address from first non-localhost interface
     * @param useIPv4  true=return ipv4, false=return ipv6
     * @return  address or empty string
     */
    public static String getIPAddress(boolean useIPv4) {
        String ipAddress = null;
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        boolean isIPv4 = sAddr.indexOf(':')<0;

                        if (useIPv4) {
                            if (isIPv4) {
                                ipAddress = sAddr;
                                if (!addr.isSiteLocalAddress()) {
                                    return ipAddress;
                                }
                            }
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                ipAddress = delim < 0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                                if (!addr.isSiteLocalAddress()) {
                                    return ipAddress;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            MoesifLog.getLogger().e(LOGTAG, "Cannot get IP Address", e);
        }
        return ipAddress;
    }

}