package com.sanket.basketball_150016252;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {
    int scoreTeamA = 0;
    int scoreTeamB = 0;
    TextView scoreTextViewTeamA;
    TextView scoreTextViewTeamB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        TextView teamA = findViewById(R.id.team_a_text_view);
        teamA.setText(intent.getStringExtra("teamA"));
        TextView teamB = findViewById(R.id.team_b_text_view);
        teamB.setText(intent.getStringExtra("teamB"));
        scoreTextViewTeamA = findViewById(R.id.team_a_score_text_view);
        scoreTextViewTeamB = findViewById(R.id.team_b_score_text_view);
    }

    public void Plus3TeamA(View view) {
        scoreTeamA = Integer.parseInt(scoreTextViewTeamA.getText().toString());
        scoreTextViewTeamA.setText(Integer.toString(scoreTeamA + 3));

    }

    public void Plus2TeamA(View view) {
        scoreTeamA = Integer.parseInt(scoreTextViewTeamA.getText().toString());
        scoreTextViewTeamA.setText(Integer.toString(scoreTeamA + 2));
    }

    public void Plus1TeamA(View view) {
        scoreTeamA = Integer.parseInt(scoreTextViewTeamA.getText().toString());
        scoreTextViewTeamA.setText(Integer.toString(scoreTeamA + 1));
    }

    public void Plus3TeamB(View view) {
        scoreTeamB = Integer.parseInt(scoreTextViewTeamB.getText().toString());
        scoreTextViewTeamB.setText(Integer.toString(scoreTeamB + 3));
    }

    public void Plus2TeamB(View view) {
        scoreTeamB = Integer.parseInt(scoreTextViewTeamB.getText().toString());
        scoreTextViewTeamB.setText(Integer.toString(scoreTeamB + 2));
    }

    public void Plus1TeamB(View view) {
        scoreTeamB = Integer.parseInt(scoreTextViewTeamB.getText().toString());
        scoreTextViewTeamB.setText(Integer.toString(scoreTeamB + 1));
    }

    public void ResetScores(View view) {
        scoreTextViewTeamA.setText("0");
        scoreTextViewTeamB.setText("0");
    }
}
