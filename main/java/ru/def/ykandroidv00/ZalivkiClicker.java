package ru.def.ykandroidv00;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class ZalivkiClicker extends AppCompatActivity {

    // Идентификатор уведомления
    private static final int NOTIFY_ID = 101;

    // Идентификатор канала
    private static String CHANNEL_ID = "Kamenshik channel";

    TextView numZal;
    TextView uzeNal;
    TextView minutes;

    ProgressBar pb;
    EditText kolvoformSt1;
    EditText kolvoformSt2;
    EditText kolvoformSt3;
    EditText kolvoformSt4;

    Spinner sp1;
    Spinner sp2;
    Spinner sp3;
    Spinner sp4;


    Button klatz;
    private int num=0;
    private String nalito;
    private int stol1;
    private int stol2;
    private int stol3;
    private int stol4;
    private int formiNaStole1;
    private int formiNaStole2;
    private int formiNaStole3;
    private int formiNaStole4;
    private int a=0;
    private Timer mTimer;
    private int progress;
    private String selFormNameSt1;
    private String selFormNameSt2;
    private String selFormNameSt3;
    private String selFormNameSt4;
    private String[][] zalivkiArr;
    private String[] zalivki;
    private String[][] zalivki2d;
    String dateText;

    private static final String FILE_ZALTMP = "zal_tmp.def";

    MediaPlayer mPlayer;
    private String nalitoF;
    private String stArr;


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zalivki_clicker);

      String [] nazvanirform= {"Не выбрано","Сланец Фигурный","Сланец Фигурный Жёлтый","Сланец Фигурный Коричневый",
              "Сланец Тонкослойный длиные","Сланец Тонкослойный короткие",
                "Сланец Тонкослойный Жёлтый длиные", "Сланец Тонкослойный Жёлтый короткие",
              "Сланец Тонкослойный Коричневый длиные","Сланец Тонкослойный Коричневый короткие",
              "Старый кирпич","Кирпич классика",
                "Кирпич Вертикаль", "Кирпич мини","Старинный кирпич","Каменный Скол","Памирский сланец"};

        pb=findViewById(R.id.progressBar2);
        numZal= findViewById(R.id.numZalivki);
        uzeNal= findViewById(R.id.uzeNalito);
        minutes= findViewById(R.id.minutesTV);

        kolvoformSt1= findViewById(R.id.kolformSt1);
        kolvoformSt2= findViewById(R.id.kolformSt2);
        kolvoformSt3= findViewById(R.id.kolformSt3);
        kolvoformSt4= findViewById(R.id.kolformSt4);

        sp1= findViewById(R.id.spinnerStol1);
        sp2= findViewById(R.id.spinnerStol2);
        sp3= findViewById(R.id.spinnerStol3);
        sp4= findViewById(R.id.spinnerStol4);


        klatz= findViewById(R.id.clickerButton);
zalivkiArr=new String[nazvanirform.length-1][2];
        for (int i = 0; i <zalivkiArr.length ; i++) {
            zalivkiArr[i][0]=
                    nazvanirform[i+1];
            zalivkiArr[i][1]="0";
        }
        Date currentDate = new Date();

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        dateText = dateFormat.format(currentDate);

        mas2dfromfile(FILE_ZALTMP);
if (zalivki2d.length>0){
    if (!zalivki2d[0][0].equals(dateText)){
        FileOutputStream fos3 = null;
        try {
            fos3 = openFileOutput(FILE_ZALTMP,0);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fos3.write("".getBytes("Cp1251"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fos3.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    else {
        num=Integer.parseInt(zalivki2d[0][1]);
        for (int i = 1; i <zalivki2d.length ; i++) {
            zalivkiArr[i-1][0]=zalivki2d[i][0];
            zalivkiArr[i-1][1]=zalivki2d[i][1];
        }
        numZal.setText(String.valueOf(num));
        StringBuilder sb= new StringBuilder();
        for (int i = 0; i <zalivkiArr.length ; i++) {
            if(!zalivkiArr[i][1].equals("0")){
                sb.append(zalivkiArr[i][0]).append(" ").append(zalivkiArr[i][1]).append("\n");
                System.out.println("sb "+sb);
            }

        }
        nalito=sb.toString();
        uzeNal.setText(nalito);
    }

}



        ArrayAdapter arrayAdapterSt1= new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item, nazvanirform);
        arrayAdapterSt1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        AdapterView.OnItemSelectedListener itemSelectedListener1=new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selFormNameSt1=adapterView.getItemAtPosition(i).toString().replace(" ","_");
            }
           @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };

        AdapterView.OnItemSelectedListener itemSelectedListener2=new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selFormNameSt2=(String) adapterView.getItemAtPosition(i).toString().replace(" ","_");}
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
        AdapterView.OnItemSelectedListener itemSelectedListener3=new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selFormNameSt3=(String) adapterView.getItemAtPosition(i).toString().replace(" ","_");}
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
        AdapterView.OnItemSelectedListener itemSelectedListener4=new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selFormNameSt4=(String) adapterView.getItemAtPosition(i).toString().replace(" ","_");}
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };

        sp1.setAdapter(arrayAdapterSt1);
    sp1.setOnItemSelectedListener(itemSelectedListener1);
    sp2.setAdapter(arrayAdapterSt1);
    sp2.setOnItemSelectedListener(itemSelectedListener2);
    sp3.setAdapter(arrayAdapterSt1);
    sp3.setOnItemSelectedListener(itemSelectedListener3);
    sp4.setAdapter(arrayAdapterSt1);
    sp4.setOnItemSelectedListener(itemSelectedListener4);

    pb.setProgress(progress);
    minutes.setText(String.valueOf((int)progress/60)+"мин");
    klatz.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startService(new Intent(ZalivkiClicker.this, MyService.class));
            setTVnumzal();
            for (int i = 0; i <zalivkiArr.length ; i++) {
                stArr+=zalivkiArr[i][0]+" "+zalivkiArr[i][1] + "\n";
            }

            nalitoF = dateText + " " + num +"\n"+ stArr;
            FileOutputStream fos3 = null;
            try {
                fos3 = openFileOutput(FILE_ZALTMP,0);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                nalitoF=new String(nalitoF.getBytes("UTF-8"),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {
                fos3.write(nalitoF.getBytes("Cp1251"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fos3.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Intent notificationIntent = new Intent(ZalivkiClicker.this, ZalivkiClicker.class);
            PendingIntent contentIntent = PendingIntent.getActivity(ZalivkiClicker.this,
                    0, notificationIntent,
                    PendingIntent.FLAG_CANCEL_CURRENT);

            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(ZalivkiClicker.this, CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_launcher_foreground)
                            .setContentTitle("Напоминание")
                            .setContentText("Таймер работает")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setProgress(100,progress,false)
                            .setContentIntent(contentIntent);

            NotificationManagerCompat notificationManager =
                    NotificationManagerCompat.from(ZalivkiClicker.this);
            notificationManager.notify(NOTIFY_ID, builder.build());

        }
    });

    }

    private void setTVnumzal() {
    num++;
    numZal.setText(String.valueOf(num));
    formiNaStole1=Integer.parseInt(String.valueOf(kolvoformSt1.getText()));
    formiNaStole2=Integer.parseInt(String.valueOf(kolvoformSt2.getText()));
    formiNaStole3=Integer.parseInt(String.valueOf(kolvoformSt3.getText()));
    formiNaStole4=Integer.parseInt(String.valueOf(kolvoformSt4.getText()));
    stol1 = formiNaStole1;
    stol2 = formiNaStole2;
    stol3 = formiNaStole3;
    stol4 = formiNaStole4;

        changeArrZalivok(selFormNameSt1,stol1);
        changeArrZalivok(selFormNameSt2,stol2);
        changeArrZalivok(selFormNameSt3,stol3);
        changeArrZalivok(selFormNameSt4,stol4);

StringBuilder sb= new StringBuilder();
        for (int i = 0; i <zalivkiArr.length ; i++) {
        if(!zalivkiArr[i][1].equals("0")){
            sb.append(zalivkiArr[i][0]).append(" ").append(zalivkiArr[i][1]).append("\n");
            System.out.println("sb "+sb);
        }

        }
        nalito=sb.toString();
        uzeNal.setText(nalito);
//
//progress=0;
//        mTimer = new Timer();
//            mTimer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//
//                progress++;
//                System.out.println("prog"+progress);
//                pb.setProgress(progress);
//                minutes.setText(String.valueOf((int)progress/60)+"мин");
//
//            if(progress==1200) { mTimer.cancel();
//                mPlayer=MediaPlayer.create(getApplicationContext(), R.raw.horn);
//            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                @Override
//                public void onCompletion(MediaPlayer mediaPlayer) {
//                    mediaPlayer.stop();
//                }
//            });
//            mPlayer.start();
//            }
//
//                }
//            },0 ,1000);


    }

    private void changeArrZalivok(String selFormNameSt, int stol) {
        System.out.println(" *"+selFormNameSt + stol+"\n");
        for (int i = 0; i <zalivkiArr.length ; i++) {
            System.out.println("zal0"+zalivkiArr.length+" "+zalivkiArr[i][0]+" "+zalivkiArr[i][1]);
            if (zalivkiArr[i][0]!=null && zalivkiArr[i][0].equals(selFormNameSt)){
                zalivkiArr[i][1]=String.valueOf(Integer.parseInt(zalivkiArr[i][1])+stol);
            }
        }
    }

    public void mas2dfromfile(String FILE_NAME){
        FileInputStream fis = null;
        byte [] buffer;

        try {
            fis = openFileInput(FILE_NAME);

            if (fis != null) {
                //
                InputStreamReader isr = new InputStreamReader(fis,"windows-1251");
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String textWin;

                while ((textWin = br.readLine()) != null) {
                    sb.append(textWin).append("\n");
                }
                String textWinend=sb.toString();
                String textUTF= new String(textWinend.getBytes("UTF-8"), "UTF-8");
                zalivki=textUTF.split("\n");
                zalivki2d=new String[zalivki.length][];
                for (int i = 0; i < zalivki.length; i++) {
                    zalivki2d[i]=zalivki[i].split(" ");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "Try to open " + getFilesDir() + "/" + FILE_NAME,
                    Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.zakM:
                Intent intent = new Intent(this, Zakazi.class);
                startActivity(intent);
                break;
            case R.id.zalM:
                Intent intent1 = new Intent(this, Zalivki.class);
                startActivity(intent1);
                break;
            case R.id.zatM:
                Intent intent3 = new Intent(this, Zatrati.class);
                startActivity(intent3);
                break;
            case R.id.m_menu:
                Intent intent2= new Intent(this, MainActivity.class);
                startActivity(intent2);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}