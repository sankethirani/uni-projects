package com.example.mithub;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private CircleImageView tabHeaderCircleImage;
    private ImageView tabHeaderImage;

    private FirebaseAuth mAuth;
    private DatabaseReference currentUserRef;

    private FragmentPersonalDetails personalInfoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        getSupportActionBar().hide();


        //creating object of all fragment so that their getters can be used in the main activity;
        personalInfoFragment = new FragmentPersonalDetails();

        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tablayout_id);
        viewPager = findViewById(R.id.viewpager_id);
        tabHeaderCircleImage = findViewById(R.id.tab_header_circle_image);
        tabHeaderImage = findViewById(R.id.tab_header_image);

        String currentUserId = mAuth.getUid();
        currentUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        //Adding Fragments
        adapter.AddFragment(new FragmentCategories(), "Subject");
        adapter.AddFragment(new FragmentDiscussions(), "Explore" );
        adapter.AddFragment(new FragmentEvents(), "Events");
        adapter.AddFragment(personalInfoFragment, "Profile");

        //Adapter Setup
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()){
                    case 0:
                        tabHeaderCircleImage.setVisibility(View.GONE);
                        tabHeaderImage.setImageResource(R.drawable.categories);
                        tabHeaderImage.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        tabHeaderCircleImage.setVisibility(View.GONE);
                        tabHeaderImage.setImageResource(R.drawable.discussion);
                        tabHeaderImage.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        tabHeaderCircleImage.setVisibility(View.GONE);
                        tabHeaderImage.setImageResource(R.drawable.events_icon);
                        tabHeaderImage.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        tabHeaderImage.setVisibility(View.GONE);
                        getUserProfileImage();
                        tabHeaderCircleImage.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void getUserProfileImage() {
        currentUserRef.child("profile_image_url").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String image = dataSnapshot.getValue().toString();
                    Picasso.get().load(image).into(tabHeaderCircleImage);
                }
                else{
                    tabHeaderCircleImage.setImageResource(R.drawable.avatar);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
