package com.moesif.android.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/*
 * MoesifAndroid
 *
 *
 */
public class IOUtils {

    public static void copy(InputStream input, OutputStream output, byte[] buffer)
            throws IOException {
        int n;
        while ((n = input.read(buffer)) != -1) {
            output.write(buffer, 0, n);
        }
    }

}
