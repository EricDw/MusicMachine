package com.publicmethod.owner.musicmachine.viewmodels;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.publicmethod.owner.musicmachine.R;
import com.publicmethod.owner.musicmachine.models.Playlist;
import com.publicmethod.owner.musicmachine.services.DownLoadIntentService;
import com.publicmethod.owner.musicmachine.services.PlayerService;
import com.publicmethod.owner.musicmachine.ui.MainActivity;

/**
 * Created by Eric De Wildt on 2016-07-13.
 */

public class MainActivityViewModel {

    private static final String TAG = MainActivityViewModel.class.getSimpleName();
    public static final String KEY_SONG = "song";
    private Context mContext;
    private boolean mBound = false;
    private MainActivity mMainActivity;

    public MainActivityViewModel(Context context, MainActivity mainActivity) {
        mContext = context;
        mMainActivity = mainActivity;
    }


    public void downloadSong(View view) {
        Toast.makeText(mContext, R.string.downloading_toast_text, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "downloadSong: ");

        // Send Messages to Handler for processing
        for (String song : Playlist.songs) {
            Intent intent = new Intent(mContext, DownLoadIntentService.class);
            intent.putExtra(KEY_SONG, song);
            mContext.startService(intent);
        }
    }

    public void setBound(boolean bound) {
        mBound = bound;
    }



    public void playPauseSong(View view) {
        if (mBound) {
            Intent intent = new Intent(mContext, PlayerService.class);
            mContext.startService(intent);
            Message message = Message.obtain();
            message.arg1 = 2; // Asks messenger to check if it is playing.
            message.replyTo = mMainActivity.getActivityMessenger(); // To hear back from the messenger we supply our own
            try {
                mMainActivity.getServiceMessenger().send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "onServiceConnected: service is bound = " + mBound);
        }



    }
}








