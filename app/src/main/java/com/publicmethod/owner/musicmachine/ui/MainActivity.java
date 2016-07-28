package com.publicmethod.owner.musicmachine.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.publicmethod.owner.musicmachine.R;
import com.publicmethod.owner.musicmachine.databinding.ActivityMainBinding;
import com.publicmethod.owner.musicmachine.handlers.ActivityHandler;
import com.publicmethod.owner.musicmachine.services.PlayerService;
import com.publicmethod.owner.musicmachine.viewmodels.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private boolean mBound = false;
    private Button mPlayPauseButton;
    private Messenger mServiceMessenger;
    private Messenger mActivityMessenger = new Messenger(new ActivityHandler(this));
    private MainActivityViewModel mMainActivityViewModel = new MainActivityViewModel(this, this);
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mBound = true;
            mServiceMessenger = new Messenger(iBinder);
            mMainActivityViewModel.setBound(mBound);
            Message message = Message.obtain();
            message.arg1 = 2; // Asks messenger to check if it is playing.
            message.arg2 = 1; // Updates the text of the playPause button.
            message.replyTo = mActivityMessenger; // To hear back from the messenger we supply our own
            try {
                mServiceMessenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "onServiceConnected: service is bound = " + mBound);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBound = false;
            Log.d(TAG, "onServiceDisconnected: " + mBound);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.setViewModel(mMainActivityViewModel);
        mPlayPauseButton = activityMainBinding.playPauseSongButton;

    }


    public Messenger getActivityMessenger() {
        return mActivityMessenger;
    }

    public Messenger getServiceMessenger() {
        return mServiceMessenger;
    }

    public Button getPlayPauseButton() {
        Log.d(TAG, "getPlayPauseButton: ");
        return mPlayPauseButton;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, PlayerService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        Log.d(TAG, "onStart: Service is now bound");
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mServiceConnection);
            mBound = false;
            Log.d(TAG, "onStop: ");
        }
    }
}
