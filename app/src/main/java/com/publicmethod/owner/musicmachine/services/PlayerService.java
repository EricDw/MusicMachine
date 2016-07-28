package com.publicmethod.owner.musicmachine.services;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Messenger;
import android.util.Log;

import com.publicmethod.owner.musicmachine.R;
import com.publicmethod.owner.musicmachine.handlers.PlayerHandler;

public class PlayerService extends Service {
    private static final String TAG = PlayerService.class.getSimpleName();
    private MediaPlayer mMediaPlayer = new MediaPlayer();
    // Messenger takes a Handler which in turn takes a Service
    public Messenger mMessenger = new Messenger(new PlayerHandler(this));


    @Override
    public void onCreate() {
        mMediaPlayer = MediaPlayer.create(this, R.raw.jingle);
        Log.d(TAG, "onCreate: " + mMediaPlayer.isPlaying());

    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return mMessenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: ");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        // It is best practice to release the player when finished with it.
        // This should actually be viewed as necessary.
        mMediaPlayer.release();
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Notification.Builder notificationBuilder = new Notification.Builder(this);
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        Notification notification = notificationBuilder.build();
        startForeground(11, notification);

        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                stopSelf();
                stopForeground(true);
            }
        });

        return Service.START_NOT_STICKY;
    }


    // Client Methods, for this case they are methods that control the MediaPlayer
    // but can be anything else based on the needs of the service.

    public boolean isPlaying() {
        Log.d(TAG, "isPlaying: " + mMediaPlayer.isPlaying());
        return mMediaPlayer.isPlaying();
    }

    public void play() {
        mMediaPlayer.start();
        Log.d(TAG, "play: ");
    }

    public void pause() {
        mMediaPlayer.pause();
    }
}
