package ru.def.ykandroidv00;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dropbox.core.v2.teamlog.SmartSyncOptOutType;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;

public class Debitors extends AppCompatActivity implements View.OnClickListener {

    TextView debitorkaTV;
//    TextView[][] tvarray=
//            {{findViewById(R.id.textView15), findViewById(R.id.textView16), findViewById(R.id.textView17),
//            findViewById(R.id.textView18), findViewById(R.id.textView19), findViewById(R.id.textView20),
//            findViewById(R.id.textView21)},
//            {findViewById(R.id.debitorka2),findViewById(R.id.debitorka3),findViewById(R.id.debitorka4),
//                    findViewById(R.id.debitorka5),findViewById(R.id.debitorka6),findViewById(R.id.debitorka7),
//                    findViewById(R.id.debitorka8)}};
//


    public String[] zakazi;
    public String[] debitors;
    public String[][] zakazi2d;
    public String[][] debitors2d;
    public String[][] debtcl;
    private String[] debtNames;

    private static final String FILE_DBTR = "base_dbtr.txt";
    private static final String FILE_ZAK = "base_zak.txt";
    private static final String FILE_CAG = "cagents.def";
    private double debitorka = 0;
    private int itoe;
    private Object id;

//
//    public class masFromFile {
//
//
//        private String convertStreamToString(InputStream is) throws Exception {
//            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//            StringBuilder sb = new StringBuilder();
//            String line;
//            while ((line = reader.readLine()) != null) {
//                sb.append(line).append("\n");
//            }
//            reader.close();
//            return sb.toString();
//        }
//
//        String getStringFromFile(Context context, String filePath) throws Exception {
//            final InputStream stream = context.getResources().getAssets().open(filePath);
//            String ret = convertStreamToString(stream);
//            stream.close();
//            return ret;
//        }
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debitors);
        debitorkaTV = findViewById(R.id.debitorka);

        FileInputStream cagents = null;
        try {
            cagents = openFileInput(FILE_CAG);
            if (cagents != null) {
                InputStreamReader isr = new InputStreamReader(cagents);
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String textWin;

                while ((textWin = br.readLine()) != null) {
                    sb.append(textWin).append("\n");
                }
                String textWinend = sb.toString();
                String textUTF = new String(textWinend.getBytes("UTF-8"), "UTF-8");
                // System.out.println("textUTF" + "\n" + textUTF);

                debtNames = textUTF.split(" ");
                // System.out.println(zakazi);
                for (int i = 0; i < debtNames.length; i++) {
                    System.out.println(debtNames[i] + " ");
                }
                debtcl = new String[debtNames.length][3];
                for (int i = 0; i < debtcl.length; i++) {
                    Arrays.fill(debtcl[i], "0");
                }

                for (int i = 0; i < debtNames.length; i++) {
                    if (!debtNames.equals("МСК"))
                        debtcl[i][0] = debtNames[i];
                    System.out.println(debtcl[i][0] + " -");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "Try to open " + getFilesDir() + "/" + FILE_CAG,
                    Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (cagents != null) {
                try {
                    cagents.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


//
//            debtS = txtUTF.split(" ");
//            for (int i = 0; i <debtS.length ; i++) {
//                debtcl[i][0]=debtS[i];
//                debtcl[i][1]="0";
//            }


        FileInputStream fis = null;

        try {
            fis = openFileInput(FILE_ZAK);
            if (fis != null) {
                InputStreamReader isr = new InputStreamReader(fis, "windows-1251");
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String textWin;

                while ((textWin = br.readLine()) != null) {
                    sb.append(textWin).append("\n");
                }
                String textWinend = sb.toString();
                String textUTF = new String(textWinend.getBytes("UTF-8"), "UTF-8");
                // System.out.println("textUTF" + "\n" + textUTF);

                zakazi = textUTF.split("\n");
                // System.out.println(zakazi);
                zakazi2d = new String[zakazi.length][];
                for (int i = 0; i < zakazi.length; i++) {
                    zakazi2d[i] = zakazi[i].split(" ");
                    //System.out.println(zakazi2d[i][8]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "Try to open " + getFilesDir() + "/" + FILE_ZAK,
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

        for (int i = 0; i < zakazi2d.length; i++) {
            if (zakazi2d[i][8].equals("3")) {
                debitorka += Double.parseDouble(zakazi2d[i][6]);
                System.out.println("debitorka++\n");
            }
        }
        System.out.println("debit-" + debitorka);
        debitorkaTV.setText(String.valueOf((int) debitorka));

        FileInputStream fis2 = null;

        try {
            fis2 = openFileInput(FILE_DBTR);
            if (fis2 != null) {
                InputStreamReader isr = new InputStreamReader(fis2, "windows-1251");
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String textWin;

                while ((textWin = br.readLine()) != null) {
                    sb.append(textWin).append("\n");
                }
                String textWinend = sb.toString();
                String textUTF = new String(textWinend.getBytes("UTF-8"), "UTF-8");
                // System.out.println("textUTF" + "\n" + textUTF);

                debitors = textUTF.split("\n");
                // System.out.println(zakazi);
                debitors2d = new String[debitors.length][];
                for (int i = 0; i < debitors.length; i++)
                    debitors2d[i] = debitors[i].split(" ");

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "Try to open " + getFilesDir() + "/" + FILE_DBTR,
                    Toast.LENGTH_LONG).show();
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

        for (int i = 0; i < zakazi2d.length; i++) {
            if (zakazi2d[i][8].equals("3"))
                for (int j = 0; j < debtcl.length; j++)
                    if (!zakazi2d[i][1].equals("МСК")) {
                        if (zakazi2d[i][1].equals(debtcl[j][0]))
                            if (debtcl[j][1] != null)
                                debtcl[j][1] = String.valueOf(Double.parseDouble(debtcl[j][1])
                                        + Double.parseDouble(zakazi2d[i][6]));
                            else debtcl[j][1] = String.valueOf(zakazi2d[i][6]);
                    } else if ("Мск".equals(debtcl[j][0]))
                        if (debtcl[j][1] != null)
                            debtcl[j][1] = String.valueOf(Double.parseDouble(debtcl[j][1])
                                    + Double.parseDouble(zakazi2d[i][6]));
                        else debtcl[j][1] = String.valueOf(zakazi2d[i][6]);
        }

        for (int i = 0; i < debtcl.length; i++) {
            for (int j = 0; j < debitors2d.length; j++) {
                if (debtcl[i][0].equals(debitors2d[j][0]))
                    debtcl[i][2] = debitors2d[j][2];
            }

        }

        for (int i = 0; i < debtcl.length; i++) {
            System.out.println("\n");
            for (int j = 0; j < debtcl[1].length; j++) {
                System.out.print(debtcl[i][j] + " ");
            }
        }
        for (int i = 0; i < debtcl.length; i++) {
            System.out.println(debtcl.length + "length");
        }

        TextView tv15 = findViewById(R.id.textView15);
        tv15.setText(debtcl[0][0]);
        TextView tv16 = findViewById(R.id.textView16);
        tv16.setText(debtcl[1][0]);
        TextView tv17 = findViewById(R.id.textView17);
        tv17.setText(debtcl[2][0]);
        TextView tv18 = findViewById(R.id.textView18);
        tv18.setText(debtcl[3][0]);
        TextView tv19 = findViewById(R.id.textView19);
        tv19.setText(debtcl[4][0]);
        TextView tv20 = findViewById(R.id.textView20);
        tv20.setText(debtcl[5][0]);
        TextView tv21 = findViewById(R.id.textView21);
        tv21.setText(debtcl[6][0]);
        TextView tvdeb2 = findViewById(R.id.debitorka2);
        TextView tvdeb3 = findViewById(R.id.debitorka3);
        TextView tvdeb4 = findViewById(R.id.debitorka4);
        TextView tvdeb5 = findViewById(R.id.debitorka5);
        TextView tvdeb6 = findViewById(R.id.debitorka6);
        TextView tvdeb7 = findViewById(R.id.debitorka7);
        TextView tvdeb8 = findViewById(R.id.debitorka8);
        tvdeb2.setText(String.valueOf((int) (Double.parseDouble(debtcl[0][1]) - Double.parseDouble(debtcl[0][2]))));
        tvdeb3.setText(String.valueOf((int) (Double.parseDouble(debtcl[1][1]) - Double.parseDouble(debtcl[1][2]))));
        tvdeb4.setText(String.valueOf((int) (Double.parseDouble(debtcl[2][1]) - Double.parseDouble(debtcl[2][2]))));
        tvdeb5.setText(String.valueOf((int) (Double.parseDouble(debtcl[3][1]) - Double.parseDouble(debtcl[3][2]))));
        tvdeb6.setText(String.valueOf((int) (Double.parseDouble(debtcl[4][1]) - Double.parseDouble(debtcl[4][2]))));
        tvdeb7.setText(String.valueOf((int) (Double.parseDouble(debtcl[5][1]) - Double.parseDouble(debtcl[5][2]))));
        tvdeb8.setText(String.valueOf((int) (Double.parseDouble(debtcl[6][1]) - Double.parseDouble(debtcl[6][2]))));
        Button button1 = findViewById(R.id.button5);
        Button button2 = findViewById(R.id.button6);
        Button button3 = findViewById(R.id.button7);
        Button button4 = findViewById(R.id.button8);
        Button button5 = findViewById(R.id.button9);
        Button button6 = findViewById(R.id.button10);
        Button button7 = findViewById(R.id.button11);
        Button button8 = findViewById(R.id.button12);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);

    }

public void oplati(){
        debtcl[itoe][2]=String.valueOf(Double.parseDouble(debtcl[itoe][2])+Double.parseDouble(VvodOplatiDialog.getOplati()));
    System.out.println(debtcl[itoe][2]);
    for (int i = 0; i <zakazi2d.length ; i++) {
        if (zakazi2d[i][1].equals(debtcl[itoe][0]) && zakazi2d[i][8].equals("3")){
              if (Double.parseDouble(zakazi2d[i][6])<=Double.parseDouble(debtcl[itoe][2])){
                zakazi2d[i][8]="4"; debtcl[itoe][2]=String.valueOf(
                        Double.parseDouble(debtcl[itoe][2])-Double.parseDouble(zakazi2d[i][6]));
            debtcl[itoe][1]=String.valueOf(Double.parseDouble(debtcl[itoe][1])-Double.parseDouble(zakazi2d[i][6]));
        }
        }

        TextView tv=findViewById((Integer) id);
            tv.setText(String.valueOf(Double.parseDouble(debtcl[itoe][1]) - Double.parseDouble(debtcl[itoe][2])));
    }
    saveOplataToFile();
    saveZakaziToFile();
    saveDebitorToFile();
    NewTechInfo();
    }

public void saveOplataToFile (){
    short nalbez=VvodOplatiDialog.getNalbez();
    String summa=VvodOplatiDialog.getOplati();
    String nameKagent=debtcl[itoe][0];
    Date date=new Date();
    String savestring= date.getTime()/1000 + " " + nameKagent +" "+ summa +" "+ nalbez + "\n";
    try {
        FileOutputStream fos=openFileOutput("base_opl.def",MODE_APPEND);
        fos.write(savestring.getBytes("windows-1251"));
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    public void saveDebitorToFile (){
        String savestring="";
        for (int i = 0; i <debtcl.length ; i++) {
            savestring+=debtcl[i][0]+" 0 "+debtcl[i][2]+" "+String.valueOf(Double.parseDouble(debtcl[i][1])-Double.parseDouble(debtcl[i][2]))+"\n";
        }

        try {
            FileOutputStream fos=openFileOutput("base_dbtr.txt",MODE_PRIVATE);
            fos.write(savestring.getBytes("Cp1251"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void saveZakaziToFile (){
        String savestring="";

        for (int i = 0; i <zakazi2d.length ; i++) {
            for (int j = 0; j <zakazi2d[1].length ; j++) {
                if (j!=zakazi2d[1].length-1)
                savestring+=zakazi2d[i][j]+" ";
                else savestring+=zakazi2d[i][j]+"\n";
            }
        }
        try {
            FileOutputStream fos=openFileOutput("base_zak.txt",MODE_PRIVATE);
            fos.write(savestring.getBytes("Cp1251"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button5:
VvodOplatiDialog dialog=new VvodOplatiDialog();
            Bundle kagent=new Bundle();
            kagent.putString("kagent", debtcl[0][0]);
                itoe=0;
                id=R.id.debitorka2;
                dialog.setArguments(kagent);
                dialog.show(getSupportFragmentManager(), "custom");
                break;
            case R.id.button7:
                dialog = new VvodOplatiDialog();
                kagent=new Bundle();
                kagent.putString("kagent", debtcl[1][0]);
                itoe=1;
                id=R.id.debitorka3;
                dialog.setArguments(kagent);
                dialog.show(getSupportFragmentManager(), "custom");
                break;
            case R.id.button6:
                dialog = new VvodOplatiDialog();
                kagent=new Bundle();
                kagent.putString("kagent", debtcl[2][0]);
                itoe=2;
                id=R.id.debitorka4;
                dialog.setArguments(kagent);
                dialog.show(getSupportFragmentManager(), "custom");
                break;
            case R.id.button10:
                dialog = new VvodOplatiDialog();
                kagent=new Bundle();
                kagent.putString("kagent", debtcl[3][0]);
                itoe=3;
                id=R.id.debitorka5;
                dialog.setArguments(kagent);
                dialog.show(getSupportFragmentManager(), "custom");
                break;
            case R.id.button9:
                dialog = new VvodOplatiDialog();
                kagent=new Bundle();
                kagent.putString("kagent", debtcl[4][0]);
                itoe=4;
                id=R.id.debitorka6;
                dialog.setArguments(kagent);
                dialog.show(getSupportFragmentManager(), "custom");
                break;
            case R.id.button8:
                dialog = new VvodOplatiDialog();
                kagent=new Bundle();
                kagent.putString("kagent", debtcl[5][0]);
                itoe=5;
                id=R.id.debitorka7;
                dialog.setArguments(kagent);
                dialog.show(getSupportFragmentManager(), "custom");
                break;
            case R.id.button11:
                dialog = new VvodOplatiDialog();
                kagent=new Bundle();
                kagent.putString("kagent", debtcl[6][0]);
                itoe=6;
                id=R.id.debitorka8;
                dialog.setArguments(kagent);
                dialog.show(getSupportFragmentManager(), "custom");
                break;
            case R.id.button12:
                Intent intent=new Intent(getApplicationContext(),SpisokOplat.class);
                startActivity(intent);
                break;
        }
    }
}


//debtcl[i].nazvanie+
//        " "
//        +debtcl[i].debet+" \n"
//private static String convertStreamToString(InputStream is) throws Exception {
//    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//    StringBuilder sb = new StringBuilder();
//    String line;
//    while ((line = reader.readLine()) != null) {
//        sb.append(line).append("\n");
//    }
//    reader.close();
//    return sb.toString();
//}
//
//    static String getStringFromFile(Context context, String filePath) throws Exception {
//        final InputStream stream = context.getResources().getAssets().open(filePath);
//        String ret = convertStreamToString(stream);
//        stream.close();
//        return ret;
//    }