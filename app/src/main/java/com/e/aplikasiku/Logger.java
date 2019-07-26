package com.e.aplikasiku;

import android.util.Log;

public class Logger {

    public static void v(final String msg) {
        if (BuildConfig.DEBUG) {
            final StackTraceElement stackTrace = new Exception().getStackTrace()[1];
            String fileName = stackTrace.getFileName();
            if (fileName == null)
                fileName = "";
            final String info = ".(" + fileName + ":"
                    + stackTrace.getLineNumber() + ")->" + stackTrace.getMethodName() + "()";
            Log.v("V>>", info + ": " + msg);
        }
    }

    public static void d(final String msg) {
        if (BuildConfig.DEBUG) {
            final StackTraceElement stackTrace = new Exception().getStackTrace()[1];
            String fileName = stackTrace.getFileName();
            if (fileName == null)
                fileName = "";
            final String info = ".(" + fileName + ":"
                    + stackTrace.getLineNumber() + ")->" + stackTrace.getMethodName() + "()";
            Log.d("D>>", info + ": " + msg);
        }
    }

    public static void i(final String msg) {
        if (BuildConfig.DEBUG) {
            final StackTraceElement stackTrace = new Exception().getStackTrace()[1];
            String fileName = stackTrace.getFileName();
            if (fileName == null)
                fileName = "";
            final String info = ".(" + fileName + ":"
                    + stackTrace.getLineNumber() + ")->" + stackTrace.getMethodName() + "()";
            Log.i("I>>", info + ": " + msg);
        }
    }

    public static void w(final String msg) {
        if (BuildConfig.DEBUG) {
            final StackTraceElement stackTrace = new Exception().getStackTrace()[1];
            String fileName = stackTrace.getFileName();
            if (fileName == null)
                fileName = "";
            final String info = ".(" + fileName + ":"
                    + stackTrace.getLineNumber() + ")->" + stackTrace.getMethodName() + "()";
            Log.w("W>>", info + ": " + msg);
        }
    }

    public static void wtf(final String msg) {
        if (BuildConfig.DEBUG) {
            final StackTraceElement stackTrace = new Exception().getStackTrace()[1];
            String fileName = stackTrace.getFileName();
            if (fileName == null)
                fileName = "";
            final String info = ".(" + fileName + ":"
                    + stackTrace.getLineNumber() + ")->" + stackTrace.getMethodName() + "()";
            Log.wtf("WTF>>", info + ": " + msg);
        }
    }

    public static void t(Throwable tr) {
        if (BuildConfig.DEBUG) {
            final StackTraceElement stackTrace = new Exception().getStackTrace()[1];
            String fileName = stackTrace.getFileName();
            if (fileName == null)
                fileName = "";
            final String info = ".(" + fileName + ":"
                    + stackTrace.getLineNumber() + ")->" + stackTrace.getMethodName() + "()";
            Log.e("Throwable>>", info, tr);
        }
    }

    public static void e(final String msg) {
        if (BuildConfig.DEBUG) {
            final StackTraceElement stackTrace = new Exception().getStackTrace()[1];
            String fileName = stackTrace.getFileName();
            if (fileName == null)
                fileName = "";
            final String info = ".(" + fileName + ":"
                    + stackTrace.getLineNumber() + ")->" + stackTrace.getMethodName() + "()";
            Log.e("E>>", info + ": " + msg);
        }
    }

}