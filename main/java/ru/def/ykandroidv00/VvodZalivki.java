package ru.def.ykandroidv00;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.Date;

import static android.widget.Toast.LENGTH_LONG;

public class VvodZalivki extends AppCompatActivity implements View.OnClickListener {


    public Button[] buttonsArray;
    public TextView[] textViewsArray;
    int len = 0;
    private String[] textVmin;
    public String[] articles;
    String text;
    private RelativeLayout layout;
    int textId;
    String [] nal;
    String [][]nalito;
    private static final String FILE_NAME_ARTICLES = "articles.def";
    private static final String FILE_NAME_BASE = "base.txt";
    private int [] in_m={60,60,64,67,48,60,36};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        FileInputStream fis = null;

        try {
            fis = openFileInput(FILE_NAME_ARTICLES);
            if (fis != null) {
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String text;

                while ((text = br.readLine()) != null) {
                    sb.append(text).append("\n");
                }

                articles = sb.toString().split(" ");
                len = articles.length;
                textVmin = new String[len];
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(this, "файл не найден", LENGTH_LONG);
            toast.show();
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

        int a = 0;
        final RelativeLayout layout = new RelativeLayout(this);
        Intent intent=getIntent();
        text=intent.getStringExtra("time") + " " + intent.getStringExtra("zalivshik");
        System.out.println("time zal"+text);
        if (intent.getStringExtra("nalito")!=null)
        nal=intent.getStringExtra("nalito").split("\n");
        for (int i = 0; i <nal.length ; i++) {
            System.out.println(nal[i]);
        }

        if (nal!=null){
            System.out.println("ну да не нулл");
            nalito=new String[nal.length][];
            for (int i = 0; i <nal.length ; i++) {
                nalito[i]=nal[i].split(" ");
            }
            for (int i = 0; i <nalito.length ; i++) {
                for (int j = 0; j <nalito[1].length ; j++) {
                    System.out.println(nalito[i][j]);
                }
            }

            for (int i = 0; i <textVmin.length ; i++) {
                if (i>=0   &&  i<=2){ //считаем фигурку
                    textVmin[i]=String.format("%.2f",(Double.parseDouble(nalito[i+1][1])/30)).replace(',','.');///bjkbjbkjjk
                    System.out.println("textV"+textVmin[i]);
                } else if (i==3){// сланец тонкослойный
                    textVmin[i]= String.format("%.2f",( Double.parseDouble(nalito[i+1][1])/30 +
                            Double.parseDouble(nalito[i+2][1])/40)).replace(',','.');
                    System.out.println("textV"+textVmin[i]);
                }
                else if (i==4){// ст жёлтый
                    textVmin[i]=String.format("%.2f", (Double.parseDouble(nalito[i+2][1])/30 +
                            Double.parseDouble(nalito[i+3][1])/40) ).replace(',','.');
                    System.out.println("textV"+textVmin[i]);
                }
                else if (i==5){//ст коричневый
                    textVmin[i]=String.format("%.2f", (Double.parseDouble(nalito[i+3][1])/30+
                            Double.parseDouble(nalito[i+4][1])/40)).replace(',','.') ;
                    System.out.println("textV "+textVmin[i]);
                }
                else if (i>5){
                    if (i+4<nalito.length){
                        textVmin[i]=String.format("%.2f",Double.parseDouble(nalito[i+4][1])/in_m[i-6]).replace(',','.');
                        System.out.println("textV"+textVmin[i]);
                    }else textVmin[i]="0";
                }
            }
        }

        buttonsArray = new Button[len];
        textViewsArray = new TextView[len];
        for (int i = 0; i < len; i++) {
            makeButton(i, layout);
            makeText(i, layout);
            a++;
        }

        Button savebut = new Button(this);
        savebut.setText("Сохранить заливки");
        RelativeLayout.LayoutParams par = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        par.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        par.addRule(RelativeLayout.CENTER_VERTICAL);
        savebut.setId(a + 100);



        savebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i <len ; i++) {
                    if (i!=len-1){
                        if (textVmin[i]!=null)
                        text+=" "+textVmin[i];
                    else text+=" "+0;
                    }else if (textVmin[i]!=null)
                        text+=" "+textVmin[i]+"\n";
                    else text+=" "+0+"\n";

                }

                FileOutputStream fos = null;

                try {
                    fos = openFileOutput(FILE_NAME_BASE, MODE_APPEND);
                    text=new String(text.getBytes("UTF-8"),"UTF-8");
                    fos.write(text.getBytes("Cp1251"));
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
                        FILE_NAME_BASE, Toast.LENGTH_LONG).show();


                NewTechInfo();

                Intent intent= new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("sinch",false);
                startActivity(intent);

            }
        });
        layout.addView(savebut, par);
        setContentView(layout);


    }

    public void makeButton(int i, RelativeLayout layout) {
        this.layout = layout;
        Button b = new Button(this);
        b.setText(articles[i]);
        RelativeLayout.LayoutParams par = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
//
        if (i != 0)
            par.addRule(RelativeLayout.BELOW, i + 99);
        else par.addRule(RelativeLayout.BELOW);
//
        b.setId(i + 100);
        b.setOnClickListener(this);
        layout.addView(b, par);
        setContentView(layout);

    }

    public void makeText(int i, RelativeLayout layout) {
        this.layout = layout;
        TextView t = new TextView(this);

        RelativeLayout.LayoutParams par = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        par.addRule(RelativeLayout.ALIGN_BOTTOM, i + 100);
        par.addRule(RelativeLayout.RIGHT_OF, i + 100);
        t.setTextSize(36);
        t.setId(i);
        if (textVmin[i] != null)
            t.setText(textVmin[i]);
        else
            t.setText("0");
        layout.addView(t, par);
        setContentView(layout);

    }

    public void makeText2(int i, RelativeLayout layout) {
        this.layout = layout;
        TextView tv = new TextView(this);
        String str = new String();
        for (int a = 0; a < textVmin.length; a++) {
            str += " " + textVmin[a];
        }

        tv.setText(str);
        RelativeLayout.LayoutParams par = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        par.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        par.addRule(RelativeLayout.CENTER_HORIZONTAL);
        tv.setTextSize(32);
        tv.setId(i + 200);
        layout.addView(tv, par);
        setContentView(layout);

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
        Toast.makeText(getApplicationContext(), "Saved to " + getFilesDir() + "/" + "techinfo.def", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onClick(View view) {
        //.setBackground(GREEN);

        VvodZalivkiDialog dialog = new VvodZalivkiDialog();
        Bundle args = new Bundle();
        args.putString("text", (String) ((TextView) view).getText());
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), "custom");

        textId = view.getId();
        textId -= 100;

        Toast toast = Toast.makeText(this, "id кнопки" + view.getId(), LENGTH_LONG);
        toast.show();
    }

    public void okClicked() {
        TextView text = findViewById(textId);
        textVmin[textId] = VvodZalivkiDialog.getNalil();
        text.setText(textVmin[textId]);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        if (textVmin != null) {
            savedInstanceState.putStringArray("TextVmin", textVmin);

        }
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            textVmin = savedInstanceState.getStringArray("TextVmin");

        }
        super.onRestoreInstanceState(savedInstanceState);
    }


}