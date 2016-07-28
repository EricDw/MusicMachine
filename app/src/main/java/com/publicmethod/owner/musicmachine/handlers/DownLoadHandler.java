package com.publicmethod.owner.musicmachine.handlers;

import android.os.Message;
import android.util.Log;

import com.publicmethod.owner.musicmachine.services.DownLoadService;

/**
 * Created by Eric De Wildt on 2016-07-13.
 */

public class DownLoadHandler extends android.os.Handler{

    private static final String TAG = DownLoadHandler.class.getSimpleName();
    private DownLoadService mDownLoadService;

    @Override
    public void handleMessage(Message msg) {
        downLoadSong(msg.obj.toString());
        mDownLoadService.stopSelf(msg.arg1);

    }

    private void downLoadSong(String songName) {
        long endTime = System.currentTimeMillis() + 10 * 1000;

        while (System.currentTimeMillis() < endTime) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException  e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, String.format("Song %s Downloaded!", songName));
    }

    public void setService(DownLoadService downLoadService) {
        mDownLoadService = downLoadService;
    }
}
