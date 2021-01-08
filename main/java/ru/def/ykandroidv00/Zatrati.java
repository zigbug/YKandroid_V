package ru.def.ykandroidv00;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Zatrati extends AppCompatActivity {
    private static final String FILE_NAME_ZATRATI = "zatrati.def";
    public String[] zatrati;
    public String[][] zatrti2d;
    public String bignewString = "Дата /На что /Сколько /Откуда /Коммент \n";
    public String arraytofile="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zatrati);

        EditText zatTV=findViewById(R.id.zatratiTV);

        mas2dfromfile(FILE_NAME_ZATRATI);

        for (int i = 0; i <zatrti2d.length ; i++) {
            arraytofile+=zatrti2d[i][0]+" "+zatrti2d[i][1]+" "+zatrti2d[i][2]+" "+zatrti2d[i][3]+" "+zatrti2d[i][4]+"\n";
            bignewString+=dateConvert(zatrti2d[i][0])+" "+zatrti2d[i][1]+"   "+zatrti2d[i][2]+"   "+zatrti2d[i][3]+"   "+zatrti2d[i][4]+"\n";
        }
        zatTV.setText(bignewString);

        FileOutputStream fos= null;
        try {
            fos = openFileOutput(FILE_NAME_ZATRATI, MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            arraytofile=new String(arraytofile.getBytes("UTF-8"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            fos.write(arraytofile.getBytes("Cp1251"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override protected void onResume() {
        super.onResume();

        Button plus=findViewById(R.id.button2);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), VvodZatrat.class);
                intent.putExtra("biS",bignewString);
                startActivity(intent);
            }
        });
    }


    public String dateConvert (String date){

        BigDecimal bd = new BigDecimal(date);
        long val = bd.longValue();
        String newdate;
        DateFormat TIMESTAMP = new SimpleDateFormat("dd-MM-YY");
        newdate=TIMESTAMP.format(val*1000);
        return newdate;

    }

    public void mas2dfromfile(String FILE_NAME){
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
                zatrati=textUTF.split("\n");
                zatrti2d=new String[zatrati.length][];
                for (int i = 0; i < zatrati.length; i++) {
                    zatrti2d[i]=zatrati[i].split(" ");
                    }

                String bouble;


                    for (int i = 0; i <zatrti2d.length-1; i++) {
                        if (Double.parseDouble(zatrti2d[i][0])>Double.parseDouble(zatrti2d[i+1][0])){
                            for (int j = 0; j <zatrti2d[0].length ; j++) {
                                bouble= zatrti2d[i][j];
                                zatrti2d[i][j]=zatrti2d[i+1][j];
                                zatrti2d[i+1][j]=bouble;
                            }
                           i=0;
                        }
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
            case R.id.m_menu:
        Intent intent2= new Intent(this, MainActivity.class);
        startActivity(intent2);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}


