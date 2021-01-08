package ru.def.ykandroidv00;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ZakazPodrobnosti extends AppCompatActivity {
    String[][] zakazi2d;
    String[] articles;
    String[] kagent;
    String[] statusArr={"в ожидании","в работе","готов","отправлен","оплачен","отменён","докрасить","предоплата"};

    TextView zakDate;
    Spinner kAgent;
    Spinner articl;
    EditText prise;
    EditText summ;
    Spinner status;
    EditText comment;
    EditText kolvo;
    Button back;
    Button save;
    private static final String FILE_AG = "articlesgoods.def";
    private static final String FILE_CAG = "cagents.def";
    private static final String FILE_ZAK = "base_zak.txt";
    private static final String FILE_OPL = "base_opl.def";
    private static final String FILE_RES = "base_res.def";

 String selkagent;
 String selArticl;
 String selStatus;
 String saveChangesZak="";
 String prs;
 String sum;
 String klv;
    Object[] objectArray;
    short nalBez=-1;
    private boolean dialogclosed;
    private String startStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zakaz_podrobnosti);
        Intent intent=getIntent();
        final int i=intent.getExtras().getInt("get_i");
        objectArray = (Object[]) intent.getExtras().getSerializable("zakazi2d");
        if(objectArray!=null){
            zakazi2d = new String[objectArray.length][];
            for(int j=0;j<objectArray.length;j++){
                zakazi2d[j]=(String[]) objectArray[j];
            }
        }
        for (int j = 0; j <objectArray.length ; j++) {
            for (int k = 0; k < zakazi2d[1].length; k++) {
                System.out.println(zakazi2d[j][k]);
            }
            System.out.println(objectArray.length +"<-objectArray.length  zakazi2d[1].length->"+zakazi2d[1].length +"i->"+i);
        }

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

        zakDate=findViewById(R.id.zakazDateatPodrobnosti);
        zakDate.setText("Дата: "+ dateConvert(zakazi2d[i][0]));
        prise=findViewById(R.id.zakazPriseAtPodrobnosti);
        prise.setText(zakazi2d[i][4]);
        prise.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
            zakazi2d[i][4]=prise.getText().toString();
            summ.setText(String.valueOf
                    (Float.parseFloat(prise.getText().toString())*Float.parseFloat(kolvo.getText().toString())));
            zakazi2d[i][6]=summ.getText().toString();
            }
        });
        summ=findViewById(R.id.zakazSummAtPodrodnosti);
        summ.setText(zakazi2d[i][6]);
        summ.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                zakazi2d[i][6]=summ.getText().toString();
                prise.setText(String.valueOf(Float.parseFloat(summ.getText().toString())/Float.parseFloat(kolvo.getText().toString())));
                zakazi2d[i][4]=prise.getText().toString();
            }
        });
        comment=findViewById(R.id.zakazCommentAtPodrodnosti);
        comment.setText(zakazi2d[i][7]);
        comment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                zakazi2d[i][7]=comment.getText().toString().replace(" ","_");
            }
        });
        kolvo=findViewById(R.id.zakazKolvoAtPodrobnosti);
        kolvo.setText(zakazi2d[i][3]);
        kolvo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                zakazi2d[i][3]=kolvo.getText().toString();
                summ.setText(String.valueOf(Float.parseFloat(zakazi2d[i][3])*Float.parseFloat(zakazi2d[i][4])));
                zakazi2d[i][6]=summ.getText().toString();
            }
        });
        kAgent=findViewById(R.id.spinner2);
        articl=findViewById(R.id.spinner3);
        status=findViewById(R.id.spinner4);

        ArrayAdapter arrayAdapterCagent= new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item, kagent);
        arrayAdapterCagent.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kAgent.setAdapter(arrayAdapterCagent);

        ArrayAdapter arrayAdapterArticles= new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item, articles);
        arrayAdapterArticles.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        articl.setAdapter(arrayAdapterArticles);

        ArrayAdapter arrayAdapterStatus= new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item, statusArr);
        arrayAdapterArticles.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status.setAdapter(arrayAdapterStatus);

        AdapterView.OnItemSelectedListener itemSelectedListener1 = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Получаем выбранный объект
                selkagent = (String) parent.getItemAtPosition(position);
                System.out.println("selkagent-"+selkagent);
                zakazi2d[i][1]=selkagent;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
//
        kAgent.setSelection(getPos(zakazi2d[i][1],kagent,"контрагент"));
        kAgent.setOnItemSelectedListener(itemSelectedListener1);


        AdapterView.OnItemSelectedListener itemSelectedListener2 = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Получаем выбранный объект
                selArticl = (String) parent.getItemAtPosition(position);
                System.out.println("selAArticl-"+selArticl);
                zakazi2d[i][2]=selArticl;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        articl.setSelection(getPos(zakazi2d[i][2],articles, "артикул"));
        articl.setOnItemSelectedListener(itemSelectedListener2);

        AdapterView.OnItemSelectedListener itemSelectedListener3 = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Получаем выбранный объект
                if (!zakazi2d[i][8].equals("7")){
                    selStatus = (String) parent.getItemAtPosition(position);
                    System.out.println("selStatus-"+selStatus);
                    if (selStatus.equals("оплачен")) {
                        OplataDialog dialog = new OplataDialog();
                        dialog.show(getSupportFragmentManager(),"context");}
                    else if (selStatus.equals("предоплата")){
                        OplataDialog dialog = new OplataDialog();
                        dialog.show(getSupportFragmentManager(),"context");
                    }
                    startStatus=zakazi2d[i][8];
                    zakazi2d[i][8]=String.valueOf(position);

                } else {
                    selStatus = (String) parent.getItemAtPosition(position);
                    System.out.println("selStatus-"+selStatus);
                    startStatus=zakazi2d[i][8];
                    zakazi2d[i][8]=String.valueOf(position);

                }
                            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        status.setSelection(Integer.parseInt(zakazi2d[i][8]));
        status.setOnItemSelectedListener(itemSelectedListener3);
        back=findViewById(R.id.button4);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), Zakazi.class);
                startActivity(intent);
            }
        });
        save=findViewById(R.id.button3);
        save.setOnClickListener(new View.OnClickListener() {


    @Override
    public void onClick(View v) {
        if(selStatus.equals("оплачен") && Integer.parseInt(startStatus)!=3){
            fileReschange(String.valueOf(Calendar.getInstance().getTimeInMillis()/1000),
                    zakazi2d[i][2],zakazi2d[i][1],zakazi2d[i][3]);
            String saveOpl;
            saveOpl= Calendar.getInstance().getTimeInMillis()/1000+" "+zakazi2d[i][1]+" "+zakazi2d[i][6]+" "+nalBez+"\n";
            FileOutputStream fos3 = null;
            try {
                fos3 = openFileOutput(FILE_OPL,MODE_APPEND);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                saveOpl=new String(saveOpl.getBytes("UTF-8"),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {
                fos3.write(saveOpl.getBytes("Cp1251"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fos3.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if(selStatus.equals("отправлен") && Integer.parseInt(startStatus)!=7){
            fileReschange(String.valueOf(Calendar.getInstance().getTimeInMillis()/1000),
                    zakazi2d[i][2],zakazi2d[i][1],zakazi2d[i][3]);

        } else
            if (selStatus.equals("отправлен") && Integer.parseInt(startStatus)==7){
            fileReschange(String.valueOf(Calendar.getInstance().getTimeInMillis()/1000),
                    zakazi2d[i][2],zakazi2d[i][1],zakazi2d[i][3]);
            zakazi2d[i][8]="4";
        } else
            if (selStatus.equals("предоплата")||(selStatus.equals("оплачен") && Integer.parseInt(startStatus)==3)){
                String saveOpl;
                saveOpl= Calendar.getInstance().getTimeInMillis()/1000+" "+zakazi2d[i][1]+" "+zakazi2d[i][6]+" "+nalBez+"\n";
                FileOutputStream fos3 = null;
                try {
                    fos3 = openFileOutput(FILE_OPL,MODE_APPEND);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    saveOpl=new String(saveOpl.getBytes("UTF-8"),"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                try {
                    fos3.write(saveOpl.getBytes("Cp1251"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    fos3.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        filesSave();
        Intent intent=new Intent(getApplicationContext(), Zakazi.class);
        startActivity(intent);
  }

});

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
        saveRes=date+" -"+String.valueOf((int)v_kor(art,kagnt,klvo))+" -"+String.valueOf((int)v_kor(art,kagnt,klvo))+" 0"+" заказ_исполнен\n";
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

    public void filesSave(){
        for (int j = 0; j <zakazi2d.length ; j++) {
            for (int k = 0; k <zakazi2d[1].length ; k++) {
                if(k!=zakazi2d[1].length-1)
                    saveChangesZak+=zakazi2d[j][k]+" ";
                else saveChangesZak+=zakazi2d[j][k]+" \n";
            }
        }
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILE_ZAK, MODE_PRIVATE);
            saveChangesZak=new String(saveChangesZak.getBytes("UTF-8"),"UTF-8");
            fos.write(saveChangesZak.getBytes("Cp1251"));
            fos.close();
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

        NewTechInfo();

    }

    public void dialogButPressed(){
        nalBez=OplataDialog.getNalbez();
    }


    public void dialogClosed(){
        dialogclosed=OplataDialog.getDialogclosed();
    }

    int getPos(String a, String[] b,String pole){
        int pos=-1;
        boolean dialogstart=false;
        System.out.println("a>"+a);
        for (int i = 0; i <b.length ; i++) {
            System.out.println("b[i]>"+b[i]);
            if (a.equals(b[i])){
                pos=i;
                System.out.println("a.equals(b[i]) pos="+pos);}
            else if (a.equals("МСК")){ pos=1;
                System.out.println("a.equals(\"МСК\") pos="+pos);
            }
        }
        if (pos==-1){
            System.out.println("позиция не найдена включаю диалог");
            dialogstart=true;}
    if (dialogstart==true){
    AddCagentDialog dialog=new AddCagentDialog();
    Bundle bndl=new Bundle();
    System.out.println(pole+"-pole");
    bndl.putString("izpolya",pole);
    dialog.setArguments(bndl);
        dialog.show(getSupportFragmentManager(), "custom");
}
        System.out.println("pos"+pos);
    return pos;}

    public String dateConvert (String date){

        BigDecimal bd = new BigDecimal(date);
        long val = bd.longValue();
        System.out.println(val);
        String newdate;
        DateFormat TIMESTAMP = new SimpleDateFormat("dd-MM-YYYY");
        newdate=TIMESTAMP.format(val*1000);
        System.out.println(newdate);
        return newdate;

    }

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

