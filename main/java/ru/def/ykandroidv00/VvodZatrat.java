package ru.def.ykandroidv00;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VvodZatrat extends AppCompatActivity {

    private static final String FILE_NAME_ZATRATI = "zatrati.def";
    Date datetmp=new Date();
    long timesec=datetmp.getTime()/1000;;
    String date;
    private String nachto;
    private String skolko;
    private String comm;
    private String bsourse;
    public String bigString;
    EditText etNaChto;
    EditText etSkolko;
    EditText etComment;
    ImageView photo;
    CalendarView cv;
    Spinner sp;
    String picName;
    String what;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_vvod_zatrat);
        Intent intent=getIntent();



        etNaChto=findViewById(R.id.textView3);
        if( intent != null && intent.getStringExtra("what")!=null){
           what=intent.getStringExtra("what");
            etNaChto.setText(what);
        }
        etSkolko=findViewById(R.id.textView4);
        if( intent != null && intent.getStringExtra("h-much")!=null){
            etSkolko.setText(intent.getStringExtra("h-much"));
        }
        etComment=findViewById(R.id.textView5);
        cv=findViewById(R.id.calendarView1);
        sp=findViewById(R.id.balosourse);
        photo=findViewById(R.id.photoBill);
        if (intent != null && intent.getStringExtra("picName") != null ){

            picName=intent.getStringExtra("picName");
            photo.setVisibility(View.INVISIBLE);
            etComment.setText(intent.getStringExtra("comment")+" фотка");
        }
        Button save=findViewById(R.id.save_zatrati);

        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                date = dayOfMonth + "-" + (month+1) + "-" + year;
                System.out.println("date-"+date);
                timesec=timeCoding().getTime()/1000;
                System.out.println(timesec+"-timesec");

            }
        });

        photo.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent= new Intent(getApplicationContext(), TakePhoto.class);
        what=etNaChto.getText().toString();
        intent.putExtra("what1", what);
        intent.putExtra("h-much1", etSkolko.getText().toString());
        intent.putExtra("comment1", etComment.getText().toString());
        startActivity(intent);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileOutputStream fos = null;
                nachto=etNaChto.getText().toString();
                nachto=nachto.replace(" ","_");
                skolko=etSkolko.getText().toString();
                comm=etComment.getText().toString();
                comm=comm.replace(" ","_");
                comm=comm.replace("фотка", "pic:="+picName);
                bsourse=sp.getSelectedItem().toString();
                bigString=timesec+" "+nachto+" "+skolko+" "+bsourse+" "+comm+"\n";
                try {
                    fos = openFileOutput(FILE_NAME_ZATRATI, MODE_APPEND);
                    //bigString=new String(bigString.getBytes("UTF-8"),"UTF-8");
                    fos.write(bigString.getBytes("Cp1251"));
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
                        FILE_NAME_ZATRATI, Toast.LENGTH_LONG).show();


                NewTechInfo();
                Intent intent= new Intent(getApplication() , Zatrati.class);
                startActivity(intent);
            }
        });
    }

    protected Date timeCoding(){
        Date date1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
//        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            date1 =  simpleDateFormat.parse(date);

            Log.i("parse date :", date1.getTime() + " ");

        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }

    public void NewTechInfo(){
        String FILE_NAME_Tech = "techinfo.def";
        long sec = datetmp.getTime()/1000;

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

//    @Override
//    public void onSaveInstanceState(Bundle savedInstanceState) {
//
//        savedInstanceState.putString("what", etNaChto.getText().toString());
//        savedInstanceState.putString("h-much", etSkolko.getText().toString());
//        //savedInstanceState.put
//        // всегда вызывайте суперкласс для сохранения состояний видов
//        super.onSaveInstanceState(savedInstanceState);
//    }


}