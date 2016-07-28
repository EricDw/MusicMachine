package com.publicmethod.owner.musicmachine.threads;

import android.os.Looper;

import com.publicmethod.owner.musicmachine.handlers.DownLoadHandler;

/**
 * Created by Eric De Wildt on 2016-07-13.
 */

public class DownLoadThread extends Thread {

    private static final String TAG = DownLoadThread.class.getSimpleName();
    public DownLoadHandler mDownLoadHandler;

    @Override
    public void run() {
        Looper.prepare();
        mDownLoadHandler = new DownLoadHandler();
        Looper.loop();
    }
}
