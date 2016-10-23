/*
    BSD License

    For Stetho software

    Copyright (c) 2015, Facebook, Inc. All rights reserved.

    Redistribution and use in source and binary forms, with or without modification,
    are permitted provided that the following conditions are met:

     * Redistributions of source code must retain the above copyright notice, this
       list of conditions and the following disclaimer.

     * Redistributions in binary form must reproduce the above copyright notice,
       this list of conditions and the following disclaimer in the documentation
       and/or other materials provided with the distribution.

     * Neither the name Facebook nor the names of its contributors may be used to
       endorse or promote products derived from this software without specific
       prior written permission.

    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
    ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
    WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
    DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
    ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
    (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
    LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
    ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
    (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package com.moesif.android.inspector;

import com.moesif.android.utils.ExceptionUtils;
import com.moesif.android.utils.IOUtils;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.zip.GZIPInputStream;

/**
 * An {@link OutputStream} filter which decompresses gzip data before it is written to the
 * specified destination output stream.  This is functionally equivalent to
 * {@link java.util.zip.InflaterOutputStream} but provides gzip header awareness.  The
 * implementation however is very different to avoid actually interpreting the gzip header.
 */
class GunzippingOutputStream extends FilterOutputStream {
    private final Future<Void> mCopyFuture;

    private static final ExecutorService sExecutor = Executors.newCachedThreadPool();

    public static GunzippingOutputStream create(OutputStream finalOut) throws IOException {
        PipedInputStream pipeIn = new PipedInputStream();
        PipedOutputStream pipeOut = new PipedOutputStream(pipeIn);

        Future<Void> copyFuture = sExecutor.submit(
                new GunzippingCallable(pipeIn, finalOut));

        return new GunzippingOutputStream(pipeOut, copyFuture);
    }

    private GunzippingOutputStream(OutputStream out, Future<Void> copyFuture) throws IOException {
        super(out);
        mCopyFuture = copyFuture;
    }

    @Override
    public void close() throws IOException {
        boolean success = false;
        try {
            super.close();
            success = true;
        } finally {
            try {
                getAndRethrow(mCopyFuture);
            } catch (IOException e) {
                if (success) {
                    throw e;
                }
            }
        }
    }

    private static <T> T getAndRethrow(Future<T> future) throws IOException {
        while (true) {
            try {
                return future.get();
            } catch (InterruptedException e) {
                // Continue...
            } catch (ExecutionException e) {
                Throwable cause = e.getCause();
                ExceptionUtils.propagateIfInstanceOf(cause, IOException.class);
                ExceptionUtils.propagate(cause);
            }
        }
    }

    private static class GunzippingCallable implements Callable<Void> {
        private final InputStream mIn;
        private final OutputStream mOut;

        public GunzippingCallable(InputStream in, OutputStream out) {
            mIn = in;
            mOut = out;
        }

        @Override
        public Void call() throws IOException {
            GZIPInputStream in = new GZIPInputStream(mIn);
            try {
                IOUtils.copy(in, mOut, new byte[1024]);
            } finally {
                in.close();
                mOut.close();
            }
            return null;
        }
    }
}