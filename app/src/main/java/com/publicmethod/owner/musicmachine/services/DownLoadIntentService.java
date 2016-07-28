package com.publicmethod.owner.musicmachine.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.publicmethod.owner.musicmachine.viewmodels.MainActivityViewModel;

public class DownLoadIntentService extends IntentService {
    private static final String TAG = DownLoadIntentService.class.getSimpleName();

    public DownLoadIntentService() {
        super("DownLoadService");
        setIntentRedelivery(true);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        String song = intent.getStringExtra(MainActivityViewModel.KEY_SONG);
        downLoadSong(song);

    }

    private void downLoadSong(String songName) {
        long endTime = System.currentTimeMillis() + 10 * 1000;

        while (System.currentTimeMillis() < endTime) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, String.format("Song %s Downloaded!", songName));
    }

}
