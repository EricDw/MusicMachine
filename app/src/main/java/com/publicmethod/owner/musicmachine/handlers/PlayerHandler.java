package com.publicmethod.owner.musicmachine.handlers;

import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import com.publicmethod.owner.musicmachine.services.PlayerService;

/**
 * Created by Eric De Wildt on 2016-07-24.
 */

public class PlayerHandler extends Handler {

    private static final String TAG = PlayerHandler.class.getSimpleName();
    private PlayerService mPlayerService;

    public PlayerHandler(PlayerService playerService) {
        mPlayerService = playerService;
        Log.d(TAG, "PlayerHandler: playerService = " + playerService.toString());
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.arg1) {
            case 0: // Play
                mPlayerService.play();
                Log.d(TAG, "handleMessage: case 0");
                break;

            case 1: // Pause
                mPlayerService.pause();
                Log.d(TAG, "handleMessage: case 1");
                break;
            case 2: // isPlaying
                Log.d(TAG, "handleMessage: case 2");
                int isPlaying = mPlayerService.isPlaying() ? 1 : 0;
                Message message = Message.obtain();
                message.arg1 = isPlaying;
                if (msg.arg2 == 1) {
                    message.arg2 = 1;
                    Log.d(TAG, "handleMessage:  arg2 = " + msg.arg2);
                }
                message.replyTo = mPlayerService.mMessenger;
                try {
                    msg.replyTo.send(message);
                    Log.d(TAG, "handleMessage: sending Message to " + msg.replyTo);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
