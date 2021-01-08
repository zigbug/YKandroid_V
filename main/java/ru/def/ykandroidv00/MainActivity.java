package ru.def.ykandroidv00;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
   // private static final String TAG = null;
   // private static final String FILE_SINCH = "techinfo.def";
    public boolean sinchTF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=getIntent();
        sinchTF=intent.getBooleanExtra("sinch", false);
    if(sinchTF!=true){
    SynchDialog dialog = new SynchDialog();
    dialog.show(getSupportFragmentManager(), "custom");

}

        Button zakaziB = findViewById(R.id.zakaziBut);
        Button zalivkiB = findViewById(R.id.zalivkiBut);
        Button nastroikiB = findViewById(R.id.nastroiki);
        Button zatratiB = findViewById(R.id.zatrati);
        Button debitorkaB = findViewById(R.id.debitorkaB);
        Button sebestoimostB = findViewById(R.id.sebestoimostB);
        nastroikiB.setOnClickListener(this);
        zakaziB.setOnClickListener(this);
        zalivkiB.setOnClickListener(this);
        zatratiB.setOnClickListener(this);
        debitorkaB.setOnClickListener(this);
        sebestoimostB.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zakaziBut:
                Intent intent = new Intent(this, Zakazi.class);
                startActivity(intent);
                break;
            case R.id.zalivkiBut:
                Intent intent1 = new Intent(this, Zalivki.class);
                startActivity(intent1);
                break;
            case R.id.nastroiki:
                Intent intent2 = new Intent(this, Nastroiki.class);
                startActivity(intent2);
                break;
            case R.id.zatrati:
                Intent intent3 = new Intent(this, Zatrati.class);
                startActivity(intent3);
                break;
            case R.id.debitorkaB:
                Intent intent4 = new Intent(this, Debitors.class);
                startActivity(intent4);
                break;
            case R.id.sebestoimostB:
                Intent intent5 = new Intent(this, Sebestoimost.class);
                startActivity(intent5);
                break;
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
      /*  Intent intent2= new Intent(this, MainActivity.class);
        startActivity(intent2);*/
                break;
        }

        return super.onOptionsItemSelected(item);
    }



    }