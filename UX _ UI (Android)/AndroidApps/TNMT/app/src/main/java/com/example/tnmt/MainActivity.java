package com.example.tnmt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    RadioButton rbLeo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.tmntImage);
        rbLeo = findViewById(R.id.rbLeo);
        rbLeo.setChecked(true);
    }

    public void onClickDon(View view) {
        imageView.setImageResource(R.drawable.donatello);
    }

    public void onClickLeo(View view) {
        imageView.setImageResource(R.drawable.leonardo);
    }

    public void onClickMic(View view) {
        imageView.setImageResource(R.drawable.michelangelo);
    }

    public void onClickRap(View view) {
        imageView.setImageResource(R.drawable.raphael);
    }
}
