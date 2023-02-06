package com.example.nikhil.harrypotter;

import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

public class hufflepuff extends Activity {

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
    ArrayList<Model> arrayList = new ArrayList<Model>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hufflepuff);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("Character list");

        name = new String[]{"Helga Hufflepuff", "Bridget Wenlock", "Newt Scamander", "Cedric Diggory", "Pomona Sprout", "Nymphadora Tonks", "Teddy Lupin","Hannah Abbot", "The Fat Friar", "Justin Finch-Fletchley" };
        description = new String[]{"One of the four Hogwarts founders. Her portrait can be seen in the Hufflepuff common room toasting her students with her golden cup","She defied the stereotype of intelligent wizards only coming from Ravenclaw by being sorted into Hufflepuff.","Magizoologist and writer of the textbook ‘Fantastic Beasts and Where to Find Them","Popular Hufflepuff Seeker and competitor in the Triwizard Tournament","Herbology Professor and expert in dangerous plants. Head of Hufflepuff house","A Metamorphmagus and Auror with distinctive hair. Member of the Order of the Phoenix","Hello","Hufflepuff prefect and a loyal member of Dumbledore's Army","A cheerful Hufflepuff ghost who still resents the fact he was never made a cardinal in life","Muggle-born Hufflepuff student who was also accepted to Eton"};
        quote = new String[]{"'I’ll teach the lot, and treat them just the same'","'So we're just playing each other again!'","'Just a Smidge...'","'Be Careful of the Venomous Tentacula... Its Teething'","'Wotcher...Harry!'","Hello","Hello","'The Fat Friar...'",""};
        desc = new String[]{"Unknown","Unknown","Unknown","Unknown","Unknown","Wolf - Jack Rabbit","Unknown","Unknown","Unknown","Unknown"};
        patronus = new String[]{"Unknown","Unknown","Unknown","Unknown","Unknown","Wolf - Jack Rabbit","Unknown","Unknown","Unknown","Unknowns" };
        skill = new String[]{"Charms, Witch","Arithmancy","Magizoology, Wizard","Seeker","Herbology","Auror, Metamorphmagus","Doctor","Animagus","Defender, Witch","Ghost"," Bat-Bogey Hex"};
        wicon = new int[]{R.drawable.hufflepuff, R.drawable.hufflepuff, R.drawable.newt,R.drawable.cedric, R.drawable.hufflepuff, R.drawable.tonks,R.drawable.hufflepuff, R.drawable.hufflepuff, R.drawable.hufflepuff, R.drawable.hufflepuff};
        icon = new int[]{R.drawable.hufflepuff, R.drawable.hufflepuff, R.drawable.hufflepuff,R.drawable.hufflepuff, R.drawable.hufflepuff, R.drawable.hufflepuff,R.drawable.hufflepuff, R.drawable.hufflepuff, R.drawable.hufflepuff, R.drawable.hufflepuff};
        listView = findViewById(R.id.hufflepuff_list);

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


