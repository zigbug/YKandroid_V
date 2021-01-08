package ru.def.ykandroidv00;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

public class VvodZakaza extends AppCompatActivity {
String[] kagent;
String[] articles;
String selkagent="Галерея";
String selArticl="1000";
Date today;
String datelong;
String date;
TextView datezak;
Calendar dateAndTime=Calendar.getInstance();
EditText kolvoEt;
EditText priseEt;
EditText commentEt;
String prise;
String kolvo;
String comment;
String saveNewZak;
String bDdate;
TextView summ;
String summZak;
Button saveBut;



    private static final String FILE_AG = "articlesgoods.def";
    private static final String FILE_CAG = "cagents.def";
    private static final String FILE_ZAK = "base_zak.txt";
    private static final String FILE_RES = "base_res.def";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vvod_zakaza);
        datezak=findViewById(R.id.zakazDate);
        kolvoEt=findViewById(R.id.skolkoZakaz);
        priseEt=findViewById(R.id.priceZakaz);
        commentEt=findViewById(R.id.commentZak);
        summ=findViewById(R.id.sumZak);
        saveBut=findViewById(R.id.saveZakaz);
        setInitialDateTime();

        FileInputStream fis1 = null;


        try {
            fis1 = openFileInput(FILE_AG);
            if (fis1 != null) {
                InputStreamReader isr = new InputStreamReader(fis1);
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String text;
                while ((text = br.readLine()) != null) {
                    sb.append(text).append("\n");
                }
                articles=sb.toString().split(" ");
            } else articles[0]="что-то не так с файлом артикулов";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis1 != null) {
                try {
                    fis1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        FileInputStream fis2 = null;
        try {
            fis2 = openFileInput(FILE_CAG);
            if (fis2 != null) {
                InputStreamReader isr = new InputStreamReader(fis2);
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb2 = new StringBuilder();
                String text;

                while ((text = br.readLine()) != null) {
                    sb2.append(text).append("\n");
                }

                kagent=sb2.toString().split(" ");
            } else kagent[0]="что-то не так с файлом контрагентов";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis2 != null) {
                try {
                    fis2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

kolvoEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
            kolvo=kolvoEt.getText().toString();
                System.out.println("kolvo"+kolvo);
    setSumm();
    }

});


    setSumm();

    }


    public void setDate(View v) {
        new DatePickerDialog(this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    void setSumm(){
        double tv1 = 0.0;
        double tv2 = 0.0;
        if (prise!=null && prise.length()>0) {
            tv1=Double.parseDouble(prise);
            System.out.println("tv1 "+tv1);
        }
        if (kolvo!=null && kolvo.length()>0){
            tv2=Double.parseDouble(kolvo);
            System.out.println("tv2 "+tv2);}

        String tv=String.valueOf(tv1*tv2);
        System.out.println(tv+" "+tv1*tv2);
        summZak=tv;
        summ.setText(tv);
    }

    String setPrise(String kagent, String articl){
        double [][] priseArr ={
                {800,870,700,690,670,550,650,850,950,660,750,850,980,0,0,0},
                {432.4,479.4,385.4,385.4,385.4,0,0,0,0,0,0,0,0,0,0,0},
                {570,620,535,490,480,410,480,600,750,510,580,650,750,0,0,0}};
        int i=0;
        int j=0;
        System.out.println("kagent"+kagent);
if (kagent.equals("Людина"))
{j=0;}
    else if (kagent.equals("МСК")||kagent.equals("Мск"))
    {
        System.out.println("kagent на j=1");
        j=1;}
    else{ j=2;}

        System.out.println("j="+j);
int art=Integer.parseInt(articl);
int vib=(int)art/100;
        System.out.println("vib="+vib);
        switch(vib)
        { case 10:{
            i=0;
            break;}
            case 20:{
                i=1;
                break;}
            case 30:{
                i=2;
                break;}
            case 31:{
                i=3;
                break;}
            case 32:{
                i=4;
                break;}
            case 33:{
                if ((art-vib*100)!=0)
                    i=6;
                else i=5;
                break;}
            case 34:{
                if ((art-vib*100)!=0)
                    i=8;
                else i=7;
                break;}
            case 35:{
                if ((art-vib*100)!=0)
                    i=10;
                else i=9;
                break;}
            case 50:{
                if ((art-vib*100)!=0)
                    i=12;
                else i=11;
                break;}
            case 60:{
                if ((art-vib*100)!=0)
                    i=13;
                else i=12;
                break;}
            case 61:{
                if ((art-vib*100)!=0)
                    i=14;
                else i=13;
                break;}
            case 70:{
                if ((art-vib*100)!=0)
                    i=15;
                else i=14;
                break;}}
    prise=String.valueOf(priseArr[j][i]);
        return prise;
    }

    float v_kor(String art, String kagnt, String klvo){
        double [] K_kor = {1.6,1.7,1.4,1.5,2,2.2,2.5,2.5,3,1.2,1.8,1.5,0,0,0};//проверить по БК
        int k_kor;
        int m=0;
        int i;
        if (kagnt=="Мск")
            m=1;
        int sw=Integer.parseInt(art)/100;
        switch(sw){
            case 10:{
                i=1-m;
                break;}
            case 20:{
                i=3-m;
                break;}
            case 30:{
                i=5-m;
                break;}
            case 31:{
                i=6;
                break;}
            case 32:{
                i=7;
                break;}
            case 33:{
                i=8; break;}
            case 34:{
                i=9; break;}
            case 35:{
                i=10; break;}
            case 50:{
                i=11; break;}
            case 60:{
                i=12; break;}
            case 61:{
                i=13; break;}
            case 70:{
                i=14; break;}
            default:
                throw new IllegalStateException("Unexpected value: " + sw);
        }
        if (K_kor[i]>0){
            if (((Double.parseDouble(klvo)/K_kor[i])-(int)(Double.parseDouble(klvo)/K_kor[i]))>0.65)
                k_kor=(int)(Double.parseDouble(klvo)/K_kor[i])+1;
            else k_kor=(int)(Double.parseDouble(klvo)/K_kor[i]);
        } else k_kor=0;
        return k_kor;
    }

    public void fileReschange(String date,String art, String kagnt, String klvo){
        String saveRes ="";
        saveRes=date+" 0 "+String.valueOf((int)v_kor(art,kagnt,klvo))+" 0 зарезервировано_для_"+art+"_"+klvo+"_кв.м\n";
        FileOutputStream fos4 = null;
        try {
            fos4 = openFileOutput(FILE_RES,MODE_APPEND);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            saveRes=new String(saveRes.getBytes("UTF-8"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ;
        try {
            fos4.write(saveRes.getBytes("Cp1251"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fos4.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart()
    {   super.onStart();
        priseEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                prise=priseEt.getText().toString();
                summ.setText(String.valueOf(Double.parseDouble(prise)*Double.parseDouble(kolvo)));
            }
        });
        final Spinner spinnerCagent=findViewById(R.id.kAgentspinner);
        Spinner spinnerArticles=findViewById(R.id.articlesSpinner);
        ArrayAdapter arrayAdapterCagent= new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item, kagent);
        arrayAdapterCagent.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCagent.setAdapter(arrayAdapterCagent);

        ArrayAdapter arrayAdapterArticles= new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item, articles);
        arrayAdapterArticles.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerArticles.setAdapter(arrayAdapterArticles);

        AdapterView.OnItemSelectedListener itemSelectedListener1 = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Получаем выбранный объект
                selkagent = (String) parent.getItemAtPosition(position);
                System.out.println("selkagent-"+selkagent);
                priseEt.setText(setPrise(selkagent,selArticl));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
//
        spinnerCagent.setOnItemSelectedListener(itemSelectedListener1);

        AdapterView.OnItemSelectedListener itemSelectedListener2 = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Получаем выбранный объект
                selArticl = (String) parent.getItemAtPosition(position);
                System.out.println("selAArticl-"+selArticl);
                priseEt.setText(setPrise(selkagent,selArticl));
            setSumm();}

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        spinnerArticles.setOnItemSelectedListener(itemSelectedListener2);
        setSumm();

        saveBut.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        comment=commentEt.getText().toString();
        comment=comment.replace(" ","_");
        saveNewZak=bDdate+" "+selkagent+" "+selArticl+" "+kolvo+" "+prise+" "+summZak+" "+summZak+" "+comment+" 1 1 \n";

        System.out.println(saveNewZak);
        fileReschange(datelong,selArticl,selkagent,kolvo);
        FileOutputStream fos = null;
            try {
            fos = openFileOutput(FILE_ZAK, MODE_APPEND);
            saveNewZak=new String(saveNewZak.getBytes("UTF-8"),"UTF-8");
            fos.write(saveNewZak.getBytes("Cp1251"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Toast.makeText(getApplication(), "Saved to " + getFilesDir() + "/" +
                FILE_ZAK, Toast.LENGTH_LONG).show();

//                TechTimeChange timeChange2=new TechTimeChange();
//                timeChange2.NewTechInfo();
        NewTechInfo();

Intent intent=new Intent(getApplicationContext(), Zakazi.class);
startActivity(intent);


    }
});
    }

    @Override
    protected void onResume() {
        super.onResume();
        setSumm();
    }

    // установка начальных даты и времени
    private void setInitialDateTime() {
 date=(DateUtils.formatDateTime(this,
        dateAndTime.getTimeInMillis(),
        DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                | DateUtils.FORMAT_SHOW_TIME));

 datelong=String.valueOf(dateAndTime.getTimeInMillis()/1000);
 long dd=dateAndTime.getTimeInMillis()/1000;
      //  BigDecimal bdd=new BigDecimal(String.valueOf(dateAndTime.getTimeInMillis()));
        bDdate=String.valueOf(dd);

        System.out.println("datelong "+datelong);

        datezak.setText(date);
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

    public void NewTechInfo(){
        String FILE_NAME_Tech = "techinfo.def";
        Date date = new Date();
        long sec = date.getTime()/1000;

        String text = Long.toString(sec);
        FileOutputStream fos2=null;
        try {
            fos2 = openFileOutput(FILE_NAME_Tech, MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fos2.write(text.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("тут еще норм 2");
        Toast.makeText(getApplicationContext(), "Saved to " + getFilesDir() + "/" + "techinfo.def", Toast.LENGTH_LONG).show();

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
