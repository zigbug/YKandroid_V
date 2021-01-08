package ru.def.ykandroidv00;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Zalivki extends AppCompatActivity implements View.OnClickListener {

    CalendarView calender;
    long timesec;
    EditText eTxt;
    String zalivshik;
    String date;
    String[] zalivshiki = {"-Выбери заливщика", "Киря", "Деня", "Оба", "Ввести имя(не работает сейчас)"};
    //TextView selection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zalivki);
        Date today=new Date();
        date=String.valueOf(today.getTime());

        Button kvvouzal = findViewById(R.id.butVvodzal);
        kvvouzal.setOnClickListener(this);
        Button tabZalb= findViewById(R.id.tabZalbutton);
        tabZalb.setOnClickListener(this);
        Button clickZal= findViewById(R.id.zalivliclick);
        clickZal.setOnClickListener(this);

        calender = (CalendarView) findViewById(R.id.calendarView1);
        eTxt = (EditText) findViewById(R.id.edittext);

        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                // TODO Auto-generated method stub
                date = dayOfMonth + "/" + (month+1) + "/" + year;
                eTxt.setText(getString(R.string.datezal) + date);
timeCoding();

            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, zalivshiki);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinner.setAdapter(adapter);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Получаем выбранный объект
                zalivshik = (String) parent.getItemAtPosition(position);
                //selection.setText(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);

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
                break;
            case R.id.m_menu:
                Intent intent2 = new Intent(this, MainActivity.class);
                startActivity(intent2);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    protected Date timeCoding(){
        Date date1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC")); "yyyy-MM-dd"
        try {
            date1 =  simpleDateFormat.parse(date);
            timesec=date1.getTime();
            System.out.println("timesec"+timesec);

            Log.i("parse date :", date1.getTime() + " ");

        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }

    @Override
    public void onClick(View view) {
        timesec=timesec/1000;
            switch (view.getId()){
            case R.id.butVvodzal:
                        Intent intent = new Intent(this, VvodZalivki.class);
            intent.putExtra("zalivshik",zalivshik);
            intent.putExtra("time",String.valueOf(timesec));
            startActivity(intent);
            break;
            case R.id.tabZalbutton:
                Intent intent2 = new Intent(this, TablitsaZalivok.class);

                startActivity(intent2);
                break;

                case R.id.zalivliclick:
                    Intent intent3 = new Intent(this, ZalivkiClicker.class);

                    startActivity(intent3);
                    break;

        }

    }


}