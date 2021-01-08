package ru.def.ykandroidv00;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Nastroiki extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nastroiki);

        Button articulsBut = findViewById(R.id.articuls_change_but);
        articulsBut.setOnClickListener(this);
        Button cagentBut = findViewById(R.id.addelCagent);
        cagentBut.setOnClickListener(this);
        Button artGoos = findViewById(R.id.addelatrgoods);
        artGoos.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.articuls_change_but:
                Intent intent = new Intent(this, ArticlesDialog.class);
                startActivity(intent);
                break;
            case R.id.addelCagent:
                Intent intent2 = new Intent(this, CagentDialog.class);
                startActivity(intent2);
                break;
            case R.id.addelatrgoods:
                Intent intent3 = new Intent(this, ArtGoodsDialog.class);
                startActivity(intent3);
                break;

        }
    }
}