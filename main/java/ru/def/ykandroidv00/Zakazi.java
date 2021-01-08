package ru.def.ykandroidv00;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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

public class Zakazi extends AppCompatActivity implements View.OnClickListener {
    private static final String FILE_NAME_BASE_ZAK = "base_zak.txt";
    public String[] zakazi;
    public String[][] zakazi2d;
    public int previusid=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zakazi);
        LinearLayout linearLayout=new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.setGravity(Gravity.CENTER);

        mas2dfromfile(FILE_NAME_BASE_ZAK);


        for (int i = 0; i < zakazi.length; i++) {
            System.out.println("-"+zakazi2d[i][8]+"-");

            if (Integer.parseInt(zakazi2d[i][8]) != 4 && Integer.parseInt(zakazi2d[i][8])!=5) {
                makeButton(i, linearLayout);
            }

        }
        makeNenZakButton(zakazi.length,linearLayout);
        ScrollView sv= new ScrollView(this);
        sv.addView(linearLayout);
        setContentView(sv);
    }

    public void mas2dfromfile(String FILE_NAME){
        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_NAME);
            if (fis != null) {
                InputStreamReader isr = new InputStreamReader(fis,"windows-1251");
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String textWin;

                while ((textWin = br.readLine()) != null) {
                    sb.append(textWin).append("\n");
                }
                String textWinend=sb.toString();
                String textUTF= new String(textWinend.getBytes("UTF-8"), "UTF-8");
                zakazi=textUTF.split("\n");
                zakazi2d=new String[zakazi.length][];
                for (int i = 0; i < zakazi.length; i++) {
                    zakazi2d[i]=zakazi[i].split(" ");
                }
                String bouble;
                for (int i = 0; i <zakazi2d.length-1; i++) {
                    if (Double.parseDouble(zakazi2d[i][0])>Double.parseDouble(zakazi2d[i+1][0])){
                        for (int j = 0; j <zakazi2d[0].length ; j++) {
                            bouble= zakazi2d[i][j];
                            zakazi2d[i][j]=zakazi2d[i+1][j];
                            zakazi2d[i+1][j]=bouble;
                        }
                        if (i>=3)
                        i=i-3;
                        else i=0;
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

    public String dateConvert (String date){
        BigDecimal bd = new BigDecimal(date);
        long val = bd.longValue();
        System.out.println(val);
        String newdate;
        DateFormat TIMESTAMP = new SimpleDateFormat("dd-MM");
        newdate=TIMESTAMP.format(val*1000);
        System.out.println(newdate);
        return newdate;
    }


    @SuppressLint("ResourceAsColor")
    public void makeButton(int i, LinearLayout layout) {
        String sTV=new String();
        sTV=dateConvert(zakazi2d[i][0])+" "+zakazi2d[i][1]+" "+zakazi2d[i][2]+" "+zakazi2d[i][3]+" "+zakazi2d[i][6];
        Button b = new Button(this);
        switch (Integer.parseInt(zakazi2d[i][8])){
            case 1:
                b.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                break;
            case 2:
                b.setTextColor(getResources().getColor(R.color.colorGreen));
                break;
            case 3:
                b.setTextColor(getResources().getColor(R.color.colorRead));
                break;
            case 7:
                b.setTextColor(getResources().getColor(R.color.maxGreen));
                break;
        }
        b.setText("№"+ i + " от "+sTV);
        b.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        previusid=i+1000;
        b.setId(previusid);
        b.setOnClickListener(this);
        layout.addView(b);
    }

    public void makeNenZakButton(int i, LinearLayout layout) {
        Button b = new Button(this);
        b.setText("Добавить новый заказ");
        b.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        int NewZakButtonid=i+2000;
        b.setId(NewZakButtonid);
        b.setOnClickListener(this);
        layout.addView(b);
    }

    @Override
    public void onClick(View v) {
int id=v.getId();
        System.out.println("id-"+id);
      if(id<2000){

          Intent intent=new Intent(this, ZakazPodrobnosti.class);
        intent.putExtra("get_i", id-1000);
          Bundle mBundle = new Bundle();
          mBundle.putSerializable("zakazi2d",zakazi2d);
          intent.putExtras(mBundle);
          startActivity(intent);
      }
      else {
          Intent intent=new Intent(this, VvodZakaza.class);
          startActivity(intent);
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
