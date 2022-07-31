package com.ericcode.wanandroiddemo;

import android.os.Handler;
import android.os.Looper;

public class MainHandlerManager {

    private final Handler handler;

    private MainHandlerManager() {
        handler = new Handler(Looper.getMainLooper());
    }

    public Handler getHandler() {
        return handler;
    }

    public static MainHandlerManager getIns() {
        return HOLDER.ins;
    }

    private static class HOLDER {
        static final MainHandlerManager ins = new MainHandlerManager();
    }
}
