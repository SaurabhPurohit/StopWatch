package com.example.ln_20.stopwatch;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Chronometer;

/**
 * Created by ln-20 on 17/1/17.
 */

public class MyService extends Service {


    private static final String TAG = "Service";
    //MainActivity mainActivity;
    IBinder iBinder = new LocalService();
    long lastPause;
    //Chronometer chronometerService = mainActivity.chronometer;


    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"in onBind()");
        return iBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"in onCreate()");
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.d(TAG,"in onRebind()");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG,"in onUnBind()");
        return super.onUnbind(intent);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"in onUnBind()");
    }

    public class LocalService extends Binder{
        MyService getService(){ return MyService.this; }
    }

    public void doStartTime(){
        MainActivity.chronometer.setBase(SystemClock.elapsedRealtime());
        MainActivity.chronometer.start();
    }

    public void doStopTime(){
        lastPause = SystemClock.elapsedRealtime();
        MainActivity.chronometer.stop();
    }

    public void doResetTime(){
        MainActivity.chronometer.setBase(SystemClock.elapsedRealtime());
    }

    public void doResumeTime(){
        MainActivity.chronometer.setBase(MainActivity.chronometer.getBase() + SystemClock.elapsedRealtime() - lastPause);
        MainActivity.chronometer.start();
    }

}
