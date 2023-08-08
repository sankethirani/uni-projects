package com.sanket.basketball_150016252;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText teamA;
    EditText teamB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        teamA = findViewById(R.id.edit_text_team_a);
        teamB = findViewById(R.id.edit_text_team_b);

    }

    public void StartGameActivity(View view) {
        String nameTeamA = teamA.getText().toString();
        String nameTeamB = teamB.getText().toString();
        String errorMessage = "Please enter the team names";

        if (nameTeamA.equals("") || nameTeamB.equals("")){
            Toast.makeText(getBaseContext(),errorMessage,Toast.LENGTH_LONG).show();
        }

        else{
            Intent displayGameActivity = new Intent(this, GameActivity.class);
            displayGameActivity.putExtra("teamA",nameTeamA);
            displayGameActivity.putExtra("teamB", nameTeamB);
            startActivity(displayGameActivity);
        }
    }
}
