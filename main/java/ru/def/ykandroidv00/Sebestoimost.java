package ru.def.ykandroidv00;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.CookieHandler;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Sebestoimost extends AppCompatActivity {
    private static final String FILE_NAME_ZATRATI = "zatrati.def";
    public String[][] arr2d;
    public String[][] zatrti2d;

    private static final String FILE_NAME_ZAKAZI = "base_zak.txt";
    public String[][] zakazi2d;

    public double zatratiY;
    private long koldneySrGod;
    private double suMetr;
    private Calendar dateAndTime=Calendar.getInstance();
    private String dateS;
    private String datePo;

    TextView etDayS;
    TextView etDayPo;
    TextView tvSPo;
    private Calendar dateAndTimePo=Calendar.getInstance();
    private long ddS=0;
    private long ddP=0;
    private double zatratiSPo=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sebestoimost);
        TextView tvSrGodSSt=findViewById(R.id.sebestiomostSrednegodovaya);
        TextView tvSstSegodnya=findViewById(R.id.sebestiomostNaSegodnya);
        tvSPo=findViewById(R.id.sebestiomostZaPeriod);
        etDayS=findViewById(R.id.textView26);
        etDayPo=findViewById(R.id.textView27);

        mas2dfromfile(FILE_NAME_ZATRATI);
            zatrti2d=new String[arr2d.length][arr2d[1].length];

        for (int i = 0; i <arr2d.length ; i++) {
            for (int j = 0; j <arr2d[1].length ; j++) {
                zatrti2d[i][j]=arr2d[i][j];
            }
        }
        System.out.println("zat2d-"+zatrti2d.length+"x"+zatrti2d[0].length);
        for (int i = 0; i <zatrti2d.length ; i++) {
            if (!zatrti2d[i][3].equals("Халва")||!zatrti2d[i][4].equals("Халва")||
                    !zatrti2d[i][3].equals("халва")||!zatrti2d[i][4].equals("халва"))
            zatratiY+=Double.parseDouble(zatrti2d[i][2])*1.10;
        }
        Date date = new Date();
        long datetoday=date.getTime();
            koldneySrGod=1+(datetoday-timeCoding("01-01-2020").getTime())/86400000;
        System.out.println("zat-"+zatratiY+" day="+koldneySrGod);

int a=0;
            mas2dfromfile(FILE_NAME_ZAKAZI);
        System.out.println("arr2d[1][1]"+arr2d[1][1]);
zakazi2d=new String[arr2d.length][arr2d[1].length];

                    for (int i = 0; i <arr2d.length ; i++) {
            for (int j = 0; j <arr2d[0].length ; j++) {
                zakazi2d[i][j] = arr2d[i][j]; }
                    }
        System.out.println("zak2d-"+zakazi2d.length+"x"+zakazi2d[0].length);
        for (int i = 0; i <zakazi2d.length ; i++) {
            suMetr+=Double.parseDouble(zakazi2d[i][3].replace(',', '.'));
        }
        System.out.println("sumM"+suMetr);
        tvSrGodSSt.setText(String.valueOf((int)(zatratiY/suMetr))+"руб/кв.м");
    DateFormat mounthFRMT=new SimpleDateFormat("MM-YY");
        DateFormat daymounthFRMT=new SimpleDateFormat("dd-MM-yy");
    String mouth=mounthFRMT.format(datetoday);
        System.out.println("месяц"+mouth);
    mouth="01-"+mouth;
        System.out.println("месяц+01"+mouth);
        date=null;
    try {
            date=daymounthFRMT.parse(mouth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(date+"datems"+date.getTime());
        suMetr=0;
        for (int i = 0; i <zakazi2d.length ; i++) {
            if (Double.parseDouble(zakazi2d[i][0])>=date.getTime()/1000){
                suMetr+=Double.parseDouble(zakazi2d[i][3].replace(',', '.'));
            }
        }
        double zatratiM=0;
        for (int i = 0; i <zatrti2d.length ; i++) {
            if (Double.parseDouble(zatrti2d[i][0])>=date.getTime()/1000)
            if (!zatrti2d[i][3].equals("Халва")||!zatrti2d[i][4].equals("Халва")||
                    !zatrti2d[i][3].equals("халва")||!zatrti2d[i][4].equals("халва"))
                zatratiM+=Double.parseDouble(zatrti2d[i][2]);
        }
        System.out.println("suM zat"+suMetr+" "+zatratiM);
    tvSstSegodnya.setText((int)(zatratiM/suMetr)+"руб/кв.м");

    }

    public void setSsSPo(){

        zatratiSPo=0;
        for (int i = 0; i <zatrti2d.length ; i++) {
            if (Double.parseDouble(zatrti2d[i][0])>=ddS &&
                    Double.parseDouble(zatrti2d[i][0])<=ddP){
                zatratiSPo+=Double.parseDouble(zatrti2d[i][2]);
            }
        }
        suMetr=0;
        for (int i = 0; i <zakazi2d.length ; i++) {
            if (Double.parseDouble(zakazi2d[i][0])>=ddS &&
                    Double.parseDouble(zakazi2d[i][0])<=ddP){
                suMetr+=Double.parseDouble(zakazi2d[i][3].replace(",","."));
            }
        }
        if (ddS!=0||ddP!=0)
            tvSPo.setText((int)(zatratiSPo/suMetr)+"руб/кв.м");
    }

    public void setDateS(View v) {
        new DatePickerDialog(this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    public void setDatePo(View v) {
        new DatePickerDialog(this, dp,
                dateAndTimePo.get(Calendar.YEAR),
                dateAndTimePo.get(Calendar.MONTH),
                dateAndTimePo.get(Calendar.DAY_OF_MONTH))
                .show();
    }


    // установка начальных даты и времени
    private void setInitialDateTime() {
        dateS=(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                        ));
//| DateUtils.FORMAT_SHOW_TIME
    //    datelong=String.valueOf(dateAndTime.getTimeInMillis()/1000);
         ddS=dateAndTime.getTimeInMillis()/1000;
        //  BigDecimal bdd=new BigDecimal(String.valueOf(dateAndTime.getTimeInMillis()));
      //  bDdate=String.valueOf(dd);

        //System.out.println("datelong "+datelong);

        etDayS.setText(dateS);
    }
    private void setInitialDateTime2() {
        datePo=(DateUtils.formatDateTime(this,
                dateAndTimePo.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
        ));
//| DateUtils.FORMAT_SHOW_TIME
        //    datelong=String.valueOf(dateAndTime.getTimeInMillis()/1000);
        ddP=dateAndTimePo.getTimeInMillis()/1000;
        //  BigDecimal bdd=new BigDecimal(String.valueOf(dateAndTime.getTimeInMillis()));
        //  bDdate=String.valueOf(dd);
        //System.out.println("datelong "+datelong);
        etDayPo.setText(datePo);
        setSsSPo();
    }
    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };

    DatePickerDialog.OnDateSetListener dp=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTimePo.set(Calendar.YEAR, year);
            dateAndTimePo.set(Calendar.MONTH, monthOfYear);
            dateAndTimePo.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime2();
        }
    };


    public String dateConvert (String date){

        BigDecimal bd = new BigDecimal(date);
        long val = bd.longValue();
        String newdate;
        DateFormat TIMESTAMP = new SimpleDateFormat("dd-MM-YY");
        newdate=TIMESTAMP.format(val*1000);
        return newdate;

    }

    protected Date timeCoding(String date){
        Date date1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            date1 =  simpleDateFormat.parse(date);

            Log.i("parse date :", date1.getTime() + " ");

        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }

    public void mas2dfromfile(String FILE_NAME) {
        String[] arr;
        String bouble;
        FileInputStream fis = null;
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
                arr=textUTF.split("\n");
                arr2d=new String[arr.length][];
                for (int i = 0; i < arr.length; i++) {
                    arr2d[i]=arr[i].split(" ");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "Try to open " + getFilesDir() + "/" + FILE_NAME,
                    Toast.LENGTH_LONG).show();
        }
        catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } }
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
            case R.id.m_menu:
        Intent intent2= new Intent(this, MainActivity.class);
        startActivity(intent2);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}