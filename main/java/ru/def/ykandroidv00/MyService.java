package ru.def.ykandroidv00;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Switch;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {

    private MediaPlayer mPlayer;
    private Timer mtimer;
    private static int progress=0;
    MyBinder binder = new MyBinder();
    DateFormat timeFormat;
    int startTime,testTime;
    static boolean timerstoped=false;

    @Override
    public IBinder onBind(Intent intent) {

        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mPlayer=new MediaPlayer();


        timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        //Long timeText = timeFormat.format(currentDate).getTime();

        progress=0;

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Таймера Пашёль!!!",
                Toast.LENGTH_LONG).show();
        timerstoped=false;
        startTime=(int)new Date().getTime()/1000;
        progress=0;
        mtimer=new Timer();
        mtimer.schedule(new TimerTask() {
            @Override
            public void run() {
                testTime=(int) new Date().getTime()/1000;
                progress=(testTime-startTime); // для теста *100
                System.out.println("progress"+ progress);
                if(progress>=1200){
                    mtimer.cancel();
                    horn();
                    timerstoped=true;

                }
            }
        },0,1000);

mPlayer.stop();
        return super.onStartCommand(intent, flags, startId);
    }

    public void horn(){
        Random r=new Random();
switch (r.nextInt(8)){
    case 0:{
        mPlayer = MediaPlayer.create(this, R.raw.horn);
        break;
    }
    case 1:{
        mPlayer = MediaPlayer.create(this, R.raw.horn2);
        break;
    }
    case 2:{
        mPlayer = MediaPlayer.create(this, R.raw.horn3);
        break;
    }
    case 3:{
        mPlayer = MediaPlayer.create(this, R.raw.horn4);
        break;
    }
    case 4:{
        mPlayer = MediaPlayer.create(this, R.raw.horn5);
        break;
    }
    case 5:{
        mPlayer = MediaPlayer.create(this, R.raw.horn6);
        break;
    }
    case 6:{
        mPlayer = MediaPlayer.create(this, R.raw.horn7);
        break;
    }
    case 7:{
        mPlayer = MediaPlayer.create(this, R.raw.horn8);
        break;
    }
}

        mPlayer.setLooping(false);
mPlayer.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Служба остановлена",
                Toast.LENGTH_SHORT).show();
        mPlayer.stop();
        progress=0;
    }

    public static int progressRet(){

        return progress;
    }


    public static boolean timerstop(){

        return timerstoped;
    }

    class MyBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }
}
