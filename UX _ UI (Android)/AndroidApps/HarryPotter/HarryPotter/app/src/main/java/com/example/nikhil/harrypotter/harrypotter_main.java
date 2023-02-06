package com.example.nikhil.harrypotter;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class harrypotter_main extends Activity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harrypotter_main);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            ImageView logo = findViewById(R.id.main_logo);
            logo.setVisibility(ImageView.GONE);
        }

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("Hogwarts");

//        textView = findViewById(R.id.houses_text);
//        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/HARRYP__.TTF");
//        textView.setTypeface(typeface);

        CardView gryffindor = findViewById(R.id.gryffindor_cardView);
        CardView slytherin = findViewById(R.id.slytherin_cardView);
        CardView hufflepuff = findViewById(R.id.hufflepuff_cardView);
        CardView ravenclaw = findViewById(R.id.ravenclaw_cardView);



        gryffindor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), gryffindor.class);
                startActivity(i);
            }
        });
        slytherin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), slytherin.class);
                startActivity(i);
            }
        });
        hufflepuff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), hufflepuff.class);
                startActivity(i);
            }
        });
        ravenclaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ravenclaw.class);
                startActivity(i);
            }
        });



    }
}
