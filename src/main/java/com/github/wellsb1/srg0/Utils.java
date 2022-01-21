/**
 * Copyright (c) 2021 Wells Burke
 * SPDX short identifier: MIT
 */
package com.github.wellsb1.srg0;

import java.io.*;

public class Utils {

    public static InputStream find(String filePath){
        InputStream in = Utils.class.getResourceAsStream(filePath);
        try {
            if (in == null) {
                File f = new File(filePath);
                if (f.exists())
                    in = new FileInputStream(filePath);
            }
        }
        catch(IOException ex){
            rethrow(ex);
        }
        if (in == null)
            throw new RuntimeException("Unable to find input file: " + filePath);

        return in;
    }

    public static String read(String filePath){
        return read(find(filePath));
    }

    public static String read(InputStream in){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        pipe(in, out);
        return new String(out.toByteArray());
    }

    public static void pipe(InputStream in, OutputStream out){
        try {
            int    read;
            byte[] buffer = new byte[1024];
            while ((read = in.read(buffer)) > -1) {
                out.write(buffer, 0, read);   // Don't allow any extra bytes to creep in, final write
            }
            out.close();
        }
        catch(Exception ex){
            rethrow(ex);
        }
    }

    public static void rethrow(Throwable error) throws RuntimeException {
        rethrow(null, error);
    }

    /**
     * Throws the root cause of e as a RuntimeException
     *
     * @param message the optional message to include in the RuntimeException
     * @param error   the error to rethrow
     * @throws RuntimeException always
     */
    public static void rethrow(String message, Throwable error) throws RuntimeException {
        Throwable cause = error;

        while (cause.getCause() != null && cause.getCause() != error)
            cause = cause.getCause();

        if (cause instanceof RuntimeException) {
            throw (RuntimeException) cause;
        }

        if (error instanceof RuntimeException)
            throw (RuntimeException) error;

        if (!empty(message)) {
            throw new RuntimeException(message, error);
        } else {
            throw new RuntimeException(error);
        }
    }

    public static boolean empty(Object... arr) {
        boolean empty = true;
        for (int i = 0; empty && arr != null && i < arr.length; i++) {
            Object obj = arr[i];
            if (obj != null && obj.toString().length() > 0)
                empty = false;
        }
        return empty;
    }
}
