package com.publicmethod.owner.musicmachine.handlers;

import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import com.publicmethod.owner.musicmachine.R;
import com.publicmethod.owner.musicmachine.ui.MainActivity;

/**
 * Created by Eric De Wildt on 2016-07-24.
 */

public class ActivityHandler extends Handler {

    private static final String TAG = ActivityHandler.class.getSimpleName();
    private MainActivity mMainActivity;

    public ActivityHandler(MainActivity mainActivity) {
        mMainActivity = mainActivity;
    }

    @Override
    public void handleMessage(Message msg) {
        if (msg.arg1 == 0) {
            // Music is NOT playing.
            Log.d(TAG, "handleMessage: " + msg.arg1);
            if (msg.arg2 == 1) {
                mMainActivity.getPlayPauseButton().setText(R.string.play_button_text);
                Log.d(TAG, "handleMessage: arg2 = " + msg.arg2
                        + " button with ID "
                        + mMainActivity.getPlayPauseButton().toString() + " text set to " + mMainActivity.getPlayPauseButton().getText().toString());
            } else {
                // Play the music
                Message message = Message.obtain();
                message.arg1 = 0;
                try {
                    msg.replyTo.send(message);
                    Log.d(TAG, "handleMessage: " + msg.replyTo.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                // Change the play/pause button to say "Pause"
                mMainActivity.getPlayPauseButton().setText(R.string.pause_button_text);
            }
        } else if (msg.arg1 == 1) {
            // Music IS playing
            if (msg.arg2 == 1) {
                mMainActivity.getPlayPauseButton().setText(R.string.pause_button_text);
            } else {
                // Pause the music
                Message message = Message.obtain();
                message.arg1 = 1;
                try {
                    msg.replyTo.send(message);
                    Log.d(TAG, "handleMessage: " + msg.replyTo.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                // Change the play/pause button to say "Play"
                mMainActivity.getPlayPauseButton().setText(R.string.play_button_text);
            }
        }
    }
}
