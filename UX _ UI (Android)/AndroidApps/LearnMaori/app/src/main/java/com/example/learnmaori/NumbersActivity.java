package com.example.learnmaori;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class NumbersActivity extends AppCompatActivity {
    private Map<Integer, String> GetMaoriDigits(){
        Map<Integer, String> words = new LinkedHashMap<Integer, String>();
        words.put(1,"Tahi");
        words.put(2,"Rua");
        words.put(3,"Toru");
        words.put(4,"Wha");
        words.put(5,"Rima");
        words.put(6,"Ono");
        words.put(7,"Whitu");
        words.put(8,"Waru");
        words.put(9,"Iwa");
        return words;
    }

    ArrayList<Number> GetNumbers(){
        ArrayList<Number> numbersArayList = new ArrayList<Number>();
        Map<Integer, String> words = GetMaoriDigits();
        for (Integer key : words.keySet()){
            int id = key;
            String maoriTranslation = words.get(key);
            String icon = "icon"+id;
            String audio = "audio"+id;
            Number n = new Number(id, icon, maoriTranslation, audio);
            numbersArayList.add(n);
        }
        return numbersArayList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

//        Intent intent = getIntent();
//        String dataFromAnotherActivity = intent.getStringExtra(Intent.EXTRA_TEXT);
//        Toast.makeText(getBaseContext(),dataFromAnotherActivity, Toast.LENGTH_LONG).show();

        ArrayList<Number> numbers = GetNumbers();

        NumberAdaptor itemsAdapter = new NumberAdaptor(this,
                R.layout.list_view_number_item,
                numbers);

        ListView listView = findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);
    }
}
