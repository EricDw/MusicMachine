package com.publicmethod.owner.musicmachine.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;

import com.publicmethod.owner.musicmachine.handlers.DownLoadHandler;
import com.publicmethod.owner.musicmachine.threads.DownLoadThread;
import com.publicmethod.owner.musicmachine.viewmodels.MainActivityViewModel;

public class DownLoadService extends Service {
    private static final String TAG = DownLoadService.class.getSimpleName();
    private DownLoadHandler mDownLoadHandler;

    public DownLoadService() {
    }

    @Override
    public void onCreate() {
        // TODO: 2016-07-19 Create new thread and start it.

        DownLoadThread downLoadThread = new DownLoadThread();
        downLoadThread.setName("DownLoadThread");
        downLoadThread.start();

        while (downLoadThread.mDownLoadHandler == null) {

        }
        mDownLoadHandler = downLoadThread.mDownLoadHandler;
        mDownLoadHandler.setService(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String song = intent.getStringExtra(MainActivityViewModel.KEY_SONG);

        // TODO: 2016-07-19 Create and send messages to the handler.

        Message message = Message.obtain();
        message.obj = song;
        message.arg1 = startId;
        mDownLoadHandler.sendMessage(message);

        return Service.START_REDELIVER_INTENT;
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


}
