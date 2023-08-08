package com.example.nikhil.harrypotter;

import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class ravenclaw extends Activity {

    ListView listView;
    ListViewAdapter adapter;
    String [] name;
    String [] description;
    String [] desc;
    String [] quote;
    String [] skill;
    String [] patronus;
    int [] wicon;
    int [] icon;
    ArrayList<Model> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ravenclaw);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("Character list");

        name = new String[]{"Rowena Ravenclaw", "Filius Flitwick", "Gilderoy Lockhart", "Sibill Trelawney", "Garrick Olivander", "Cho Chang", "Luna Lovegood","Moaning Myrtle", "Padma Patil", "Quirinus Quirell" };
        description = new String[]{"Hello","Hello","Hello","Hello","Hello","Hello","Hello","Hello","Hello","Hello"};
        desc = new String[]{"Hello","Hello","Hello","Hello","Hello","Hello","Hello","Hello","Hello","Hello"};
        quote = new String[]{"Hello","Hello","Hello","Hello","Hello","Hello","Hello","Hello","Hello","Hello"};
        patronus = new String[]{"Unknown","Phoenix","Unknown","Stag","Jack Russel terrier","Otter","Unknown","Unknown","Unknown","Horse" };
        skill = new String[]{"Duelist","Wizard","Wizard, Bowman, Strong","Animagus, Wizard","Parseltongue,Seeker, Wizard","Wizard Chess, Quiditch Goalkeeping","Almost Everything","Herbology, Warrior","Animagus, Witch","Chaser, Bat-Bogey Hex"};
        wicon = new int[]{R.drawable.gryffindor, R.drawable.slytherin, R.drawable.ravenclaw,R.drawable.gryffindor, R.drawable.slytherin, R.drawable.ravenclaw,R.drawable.gryffindor, R.drawable.slytherin, R.drawable.ravenclaw, R.drawable.gryffindor};
        icon = new int[]{R.drawable.gryffindor, R.drawable.slytherin, R.drawable.ravenclaw,R.drawable.gryffindor, R.drawable.slytherin, R.drawable.ravenclaw,R.drawable.gryffindor, R.drawable.slytherin, R.drawable.ravenclaw, R.drawable.gryffindor};
        listView = findViewById(R.id.ravenclaw_list);

        for (int i = 0; i<name.length; i++){
            Model model = new Model(name[i],desc[i],description[i],quote[i],patronus[i],skill[i],icon[i],wicon[i]);
            //bind all strings in an array
            arrayList.add(model);
        }
        //pass results to listViewAdapter Class
        adapter = new ListViewAdapter(this, arrayList);

        //bind the adapter to the listview
        listView.setAdapter(adapter);

    }
}

