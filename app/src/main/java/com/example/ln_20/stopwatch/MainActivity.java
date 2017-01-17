package com.example.ln_20.stopwatch;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.InterpolatorRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static Chronometer chronometer;
    Button btn_start,btn_stop,btn_reset,btn_unBind,btn_resume;
    MyService myService;
    boolean isBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chronometer = (Chronometer) findViewById(R.id.chronometer);
        btn_start = (Button) findViewById(R.id.start);
        btn_stop = (Button) findViewById(R.id.stop);
        btn_reset = (Button) findViewById(R.id.reset);
        btn_unBind = (Button) findViewById(R.id.unBind);
        btn_resume = (Button) findViewById(R.id.resume);

        //chronometer.setBase(SystemClock.elapsedRealtime());

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chronometer.start();
                myService.doStartTime();
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myService.doStopTime();
            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myService.doResetTime();
            }
        });

        btn_resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myService.doResumeTime();
            }
        });



        final Intent intent = new Intent(this,MyService.class);
        startService(intent);
        if(!isBound){
            bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
        }


        btn_unBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBound == true){
                    unbindService(serviceConnection);
                }

            }
        });

    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.LocalService localService = (MyService.LocalService) service;
            Toast.makeText(MainActivity.this,"In serviceConnected",Toast.LENGTH_LONG).show();
            myService = localService.getService();
            isBound = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(MainActivity.this,"In serviceDisconnected",Toast.LENGTH_LONG).show();
            isBound = false;
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(MainActivity.this,"In serviceDisonnected onStop",Toast.LENGTH_LONG).show();
        isBound = false;
    }
}
