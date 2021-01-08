package ru.def.ykandroidv00;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class SpisokOplat extends AppCompatActivity {
    private static final String FILE_NAME_OPLATI = "base_opl.def";
    public String[] oplati;
    public String[][] oplati2d;
    public String bignewString = "Дата / Кто / Сколько / Куда \n";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spisokoplat);

        TextView oplTV=findViewById(R.id.oplatiTV);

        mas2dfromfile(FILE_NAME_OPLATI);

        for (int i = 0; i <oplati.length ; i++) {
            bignewString+=dateConvert(oplati2d[i][0])+" "+oplati2d[i][1]+"   "+oplati2d[i][2]+"   "+nalBn(oplati2d[i][3])+"\n";
        }
        oplTV.setText(bignewString);

    }

    @Override protected void onResume() {
        super.onResume();

        Button plus=findViewById(R.id.buttonOk);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), Debitors.class);
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
    public  String nalBn(String arrVal){
        String nBn="непонятно";
        if (arrVal.equals("1")){
            nBn="Безнал";
        } else if (arrVal.equals("0")) nBn="Наличкой";
    return nBn;
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
                oplati=textUTF.split("\n");
                oplati2d=new String[oplati.length][];
                for (int i = 0; i < oplati.length; i++) {
                    oplati2d[i]=oplati[i].split(" ");
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


