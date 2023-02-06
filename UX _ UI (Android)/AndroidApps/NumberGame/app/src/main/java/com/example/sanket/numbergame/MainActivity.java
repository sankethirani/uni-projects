package com.example.sanket.numbergame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    int num1,num2;
    Button button1, button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        num1 = (int)(Math.random()*100);
        num2 = (int)(Math.random()*100);
        button1 = (Button)findViewById(R.id.button1);
        button1.setText(num1);
        button2 = (Button)findViewById(R.id.button2);
        button2.setText(num2);
    }

    public void button1Click(View view) {
    }
}
