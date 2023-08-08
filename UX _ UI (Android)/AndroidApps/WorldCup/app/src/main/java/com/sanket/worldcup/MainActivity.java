package com.sanket.worldcup;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickTeams(View view) {
        Intent categoryListActivity = new Intent(this, CategoryListActivity.class);
        categoryListActivity.putExtra("category", "teams");
        View startView = findViewById(R.id.teams_card_text_view);
        //transition
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, startView, getString(R.string.transition_string));
        startActivity(categoryListActivity, options.toBundle());
    }

    public void onClickStadiums(View view) {
        Intent categoryListActivity = new Intent(this, CategoryListActivity.class);
        categoryListActivity.putExtra("category", "stadiums");
        View startView = findViewById(R.id.stadiums_card_text_view);
        //transition
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, startView, getString(R.string.transition_string));
        startActivity(categoryListActivity, options.toBundle());
    }

    public void onClickGreats(View view) {
        Intent categoryListActivity = new Intent(this, CategoryListActivity.class);
        categoryListActivity.putExtra("category", "greats");
        View startView = findViewById(R.id.greats_card_text_view);
        //transition
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, startView, getString(R.string.transition_string));
        startActivity(categoryListActivity, options.toBundle());
    }
}
