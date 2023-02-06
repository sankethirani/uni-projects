package com.example.learnmaori;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CardView cardNumbers = (CardView) findViewById(R.id.card_numbers);
        cardNumbers.setOnClickListener(CardNumbersHandler);
    }

    View.OnClickListener CardNumbersHandler = new View.OnClickListener(){
        public void onClick (View View){
            Intent numbersActivity = new Intent(getBaseContext(),NumbersActivity.class).
                putExtra(Intent.EXTRA_TEXT,"This message was sent from MainActivity");
            startActivity(numbersActivity);
        }
    };

    public void ShowFamilyActivty(View view) {
        Intent familyActivity = new Intent(this,FamilyActivity.class);
        startActivity(familyActivity);
    }

    public void ShowColorsActivity(View view) {
        Intent colorsActivity = new Intent (this, ColorsActivity.class);
        startActivity(colorsActivity);
    }

//    public void ShowNumbersActivity(View view){
//        Intent numbersActivity = new Intent(this, NumbersActivity.class);
//        startActivity(numbersActivity);
//    }
}
