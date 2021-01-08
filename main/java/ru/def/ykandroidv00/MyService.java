package ru.def.ykandroidv00;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {

    private MediaPlayer mPlayer;
    private Timer mtimer;
    private int progress;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mPlayer = MediaPlayer.create(this, R.raw.horn);
        mPlayer.setLooping(false);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Таймера Пашёль!!!",
                Toast.LENGTH_LONG).show();
        progress=0;
        mtimer=new Timer();
        mtimer.schedule(new TimerTask() {
            @Override
            public void run() {
                progress++;
                System.out.println("progress"+ progress);
                if(progress==1200){
                    mPlayer.start();
                    mtimer.cancel();
                }
            }
        },0,100);


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Служба остановлена",
                Toast.LENGTH_SHORT).show();
        mPlayer.stop();
        progress=0;
    }
}
