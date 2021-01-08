package ru.def.ykandroidv00;

import android.os.Bundle;
import android.widget.EditText;
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

public class TablitsaZalivok extends AppCompatActivity {
    private static final String FILE_NAME_BASE = "base.txt";
    private String[][] zalivki2d;
    private String[] zalivki;
    EditText zalTV;
    private String bigString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablitsa_zalivok);

        zalTV=findViewById(R.id.zalivkiTV);

        mas2dfromfile(FILE_NAME_BASE);


//        FileInputStream fis = null;
//
//        try {
//            fis = openFileInput(FILE_NAME_BASE);
//
//            if (fis != null) {
//                InputStreamReader isr = new InputStreamReader(fis,"windows-1251");
//                BufferedReader br = new BufferedReader(isr);
//                StringBuilder sb = new StringBuilder();
//                String text;
//
//                while ((text = br.readLine()) != null) {
//                    sb.append(text).append("\n");
//                }
//
//                zalTV.setText(sb.toString());
//            } else zalTV.setText("");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            Toast.makeText(this, "Try to open " + getFilesDir() + "/" + FILE_NAME_BASE,
//                    Toast.LENGTH_LONG).show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (fis != null) {
//                try {
//                    fis.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (int i = 0; i <zalivki.length ; i++){
            for (int j = 0; j <zalivki2d[2].length ; j++)
 if(j==0)
     zalTV.append(dateConvert(zalivki2d[i][j]) + " ");
                        else zalTV.append(zalivki2d[i][j] + " ");
                    zalTV.append("\n");}
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
}