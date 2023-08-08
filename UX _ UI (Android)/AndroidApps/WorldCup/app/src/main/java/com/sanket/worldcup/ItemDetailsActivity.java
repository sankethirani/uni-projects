package com.sanket.worldcup;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemDetailsActivity extends AppCompatActivity {
    ImageView itemImageView;
    TextView itemTitleTextView;
    TextView itemSubtitleTextView;
    TextView desc1TitleTextView;
    TextView desc1DetailsTextView;
    TextView desc2TitleTextView;
    TextView desc2DetailsTextView;
    TextView desc3TitleTextView;
    TextView desc3DetailsTextView;
    View desc1LinearLayout;
    View desc2LinearLayout;
    View desc3LinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        itemImageView = findViewById(R.id.item_image_view);
        itemTitleTextView = findViewById(R.id.item_title_text_view);
        itemSubtitleTextView = findViewById(R.id.item_subtitle_text_view);
        desc1TitleTextView = findViewById(R.id.desc1_title_text_view);
        desc1DetailsTextView = findViewById(R.id.desc1_details_text_view);
        desc2TitleTextView = findViewById(R.id.desc2_title_text_view);
        desc2DetailsTextView = findViewById(R.id.desc2_details_text_view);
        desc3TitleTextView = findViewById(R.id.desc3_title_text_view);
        desc3DetailsTextView = findViewById(R.id.desc3_details_text_view);
        desc1LinearLayout = findViewById(R.id.desc1_linear_layout);
        desc2LinearLayout = findViewById(R.id.desc2_linear_layout);
        desc3LinearLayout = findViewById(R.id.desc3_linear_layout);

        //get data from previous activity and put it in the relevant views using intent
        itemImageView.setImageResource(getIntent().getIntExtra("imageResource",0));
        itemTitleTextView.setText(getIntent().getStringExtra("title"));
        itemSubtitleTextView.setText(getIntent().getStringExtra("subtitle"));
        desc1DetailsTextView.setText(getIntent().getStringExtra("desc1"));
        desc2DetailsTextView.setText(getIntent().getStringExtra("desc2"));
        desc3DetailsTextView.setText(getIntent().getStringExtra("desc3"));

        //display the right views based on what item is clicked
        String category = getIntent().getStringExtra("category");
        if(category.equals("teams")){
            showTeamItemDetails();
        }

        if(category.equals("stadiums")){
            showStadiumItemDetails();
        }

        if(category.equals("greats")){
            showGreatItemDetails();
        }
    }

    private void showTeamItemDetails() {
        desc1TitleTextView.setText("Best Performance");
        desc2TitleTextView.setText("Key Player");
        desc1LinearLayout.setVisibility(View.VISIBLE);
        desc2LinearLayout.setVisibility(View.VISIBLE);
    }

    private void showStadiumItemDetails() {
        desc1TitleTextView.setText("Stadium Capacity");
        desc1DetailsTextView.setText(getIntent().getStringExtra("desc1") + " seats");
        desc2TitleTextView.setText("World Cup Matches");
        desc1LinearLayout.setVisibility(View.VISIBLE);
        desc2LinearLayout.setVisibility(View.VISIBLE);

    }

    private void showGreatItemDetails() {
        desc1TitleTextView.setText("Birth Place");
        desc2TitleTextView.setText("Position");
        desc3TitleTextView.setText("Clubs Played For");
        desc1LinearLayout.setVisibility(View.VISIBLE);
        desc2LinearLayout.setVisibility(View.VISIBLE);
        desc3LinearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
