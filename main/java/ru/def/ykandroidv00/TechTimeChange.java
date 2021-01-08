package ru.def.ykandroidv00;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class TechTimeChange extends AppCompatActivity {
    FileOutputStream fos2;
    String text;
    private static final String FILE_NAME_Tech = "techinfo.def";
    public void NewTechInfo(){
        Date date = new Date();
        long sec = date.getTime()/1000;

        text = Long.toString(sec);


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



}
