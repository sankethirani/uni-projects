package com.sanket.worldcup;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class CategoryListActivity extends AppCompatActivity {
    String category;
    TextView actionbarTitleTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        category = intent.getStringExtra("category");
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        setContentView(R.layout.activity_category_list);
        actionbarTitleTextView = findViewById(R.id.actionbar_title_text_view);

        if(category.equals("teams")){
            showTeamsList();
            actionbarTitleTextView.setText("Teams");
        }

        if(category.equals("stadiums")){
            showStadiumsList();
            actionbarTitleTextView.setText("Stadiums");
        }

        if(category.equals("greats")){
            showGreatsList();
            actionbarTitleTextView.setText("Greats");
        }


    }

    private void showTeamsList(){
        final ArrayList<Team> teams = new ArrayList<Team>();
        //all country logos retreived from iconarchive.com
        teams.add(new Team(R.drawable.germany,"Germany","Joachim Löw","Toni Kroos", "Winners 2014 (West Germany were champions in 1954, 1974 and 1990)"));
        teams.add(new Team(R.drawable.brazil,"Brazil","Titte","Neymar", "Winners 1958, 1962, 1970, 1994, 2002"));
        teams.add(new Team(R.drawable.belgium,"Belgium","Roberto Martinez","Eden Hazard", "Fourth place 1986"));
        teams.add(new Team(R.drawable.portugal,"Portugal","Fernando Santos","Cristiano Ronaldo", "Third place 1966"));
        teams.add(new Team(R.drawable.argentina,"Argentina","Jorge Sampaoli","Lionel Messi", "Winners 1978, 1986"));
        teams.add(new Team(R.drawable.france,"France","Didier Deschamps","Antoine Griezmann", "Winners 1998"));
        teams.add(new Team(R.drawable.spain,"Spain","Fernando Hierro","David De Gea", "Winners 2010"));
        teams.add(new Team(R.drawable.england,"England","Gareth Southgate","Harry Kane", "Winners 1966"));
        teams.add(new Team(R.drawable.switzerland,"Switzerland","Vladimir Petkovic","Xherdan Shaqiri", "Quarter-finals 1934, 1938, 1954"));
        teams.add(new Team(R.drawable.poland,"Poland","Adam Nawalka","Robert Lewandowski", "Third place 1974, 1982"));

        //Type cast all objects in the teams list to ListItem and add them to the ArrayList listItems, so that they can be added to the list view using the adapter.
        ArrayList<ListItem> listItems = new ArrayList<ListItem>();
        for (int i = 0; i<teams.size(); i++){
            listItems.add((ListItem) teams.get(i));
        }

        ListItemAdapter adapter = new ListItemAdapter(this, listItems);
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent showItemDetailsActivity = new Intent(getBaseContext(), ItemDetailsActivity.class);
                Team selectedTeam = teams.get(position);
                showItemDetailsActivity.putExtra("category","teams");
                showItemDetailsActivity.putExtra("imageResource",selectedTeam.getItemImageResourceId());
                showItemDetailsActivity.putExtra("title", selectedTeam.getItemTitle());
                showItemDetailsActivity.putExtra("subtitle", selectedTeam.getItemSubtitle());
                showItemDetailsActivity.putExtra("desc1", selectedTeam.getHistory());
                showItemDetailsActivity.putExtra("desc2", selectedTeam.getPlayers());
                startActivity(showItemDetailsActivity);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    private void showStadiumsList(){
        final ArrayList<Stadium> stadiums = new ArrayList<Stadium>();
        //image resources wikipedia, google
        stadiums.add(new Stadium(R.drawable.luzhniki_stadium,"Luzhniki Stadium", "EST. 1956","Group A - Russia vs Saudi Arabia \n"+
                "Group F - Germany vs Mexico\n" +
                "Group B - Portugal vs Morocco\n" +
                "Group C - Denmark vs France", "80,000"));
        stadiums.add(new Stadium(R.drawable.st_petersburg_stadium,"Saint Petersburg Stadium", "EST. 2017","Group B - Morocco vs Iran\n" +
                "Group A - Russia vs Egypt\n" +
                "Group E - Brazil vs Costa Rica\n" +
                "Group D - Nigeria vs Argentina", "64,287"));
        stadiums.add(new Stadium(R.drawable.fisht_stadium,"Fisht Stadium", "EST. 2013","Group B - Portugal vs Spain\n" +
                "Group G - Belgium vs Panama\n" +
                "Group F - Germany vs Sweden\n" +
                "Group C - Australia vs Peru", "47,659"));
        stadiums.add(new Stadium( R.drawable.ekaterinburg_arena,"Ekaterinburg Arena", "EST. 1957" ,"Group A - Egypt vs Uruguay\n" +
                "Group C - France vs Peru\n" +
                "Group H - Japan vs Senegal\n" +
                "Group F - Mexico vs Sweden", "35,696"));
        stadiums.add(new Stadium(R.drawable.kazan_arena,"Kazan Arena", "EST. 2013" ,"Group C - France vs Australia\n" +
                "Group B - Iran vs Spain\n" +
                "Group H - Poland vs Colombia\n" +
                "Group F - Korea Republic vs Germany", "45,379"));
        stadiums.add(new Stadium(R.drawable.novgorod_stadium,"Nizhny Novgorod Stadium", "EST. 2018" , "Group F - Sweden vs Korea Republic\n" +
                "Group D - Argentina vs Croatia\n" +
                "Group G - England vs Panama\n" +
                "Group E - Switzerland vs Costa Rica", "44,899"));
        stadiums.add(new Stadium(R.drawable.rostov_arena,"Rostov Arena", "EST. 2018" ,"Group E - Brazil vs Switzerland\n" +
                "Group A - Uruguay vs Saudi Arabia\n" +
                "Group F - Korea Republic vs Mexico\n" +
                "Group D - Iceland vs Croatia", "45,145"));
        stadiums.add(new Stadium( R.drawable.samara_arena,"Samara Arena", "EST. 2018" ,"Group E - Costa Rica vs Serbia\n" +
                "Group C - Denmark vs Australia\n" +
                "Group A - Uruguay vs Russia\n" +
                "Group H - Senegal vs Colombia","44,807"));
        stadiums.add(new Stadium(R.drawable.mordovia_arena,"Mordovia Arena", "EST. 2018" ,"Group C - Peru vs Denmark\n" +
                "Group H - Colombia vs Japan\n" +
                "Group B - Iran vs Portugal\n" +
                "Group G - Panama vs Tunisia", "44,442"));
        stadiums.add(new Stadium(R.drawable.spartak_stadium,"Spartak Stadium ", "EST. 2014" , "Group D - Argentina vs Iceland\n" +
                "Group H - Poland vs Senegal\n" +
                "Group B - Belgium vs Tunisia\n" +
                "Group C - Serbia vs Brazil", "45,360"));

        ArrayList<ListItem> listItems = new ArrayList<ListItem>();
        for (int i = 0; i<stadiums.size(); i++){
            listItems.add((ListItem) stadiums.get(i));
        }

        ListItemAdapter adapter = new ListItemAdapter(this, listItems);
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent showItemDetailsActivity = new Intent(getBaseContext(), ItemDetailsActivity.class);
                Stadium selectedStadium = stadiums.get(position);
                showItemDetailsActivity.putExtra("category","stadiums");
                showItemDetailsActivity.putExtra("imageResource",selectedStadium.getItemImageResourceId());
                showItemDetailsActivity.putExtra("title", selectedStadium.getItemTitle());
                showItemDetailsActivity.putExtra("subtitle", selectedStadium.getItemSubtitle());
                showItemDetailsActivity.putExtra("desc1", selectedStadium.getCapacity());
                showItemDetailsActivity.putExtra("desc2", selectedStadium.getMatches());
                startActivity(showItemDetailsActivity);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    private void showGreatsList(){
        final ArrayList<Great> greats = new ArrayList<Great>();
        //Image resources wikipedia, independent.co.uk, goal
        greats.add(new Great (R.drawable.maradona,"Diego Maradona", "Argentina" , "Lanus, Argentina (1960)", "Forward","Argentinos Juniors\n"+ "Boca Juniors\n"+ "Barcelona\n"+ "Napoli\n"+ "Sevilla\n"+ "Newell's Old Boys"));
        greats.add(new Great(R.drawable.pele,"Pelé", "Brazil" ,"Três Corações, Brazil (1940)", "Forward","Santos\n"+ "New York Cosmos"));
        greats.add(new Great(R.drawable.franz,"Franz Beckenbauer", "Germany" , "Munich, Germany (1985)","Defender", "Bayern Munich\n"+ "New York Cosmos\n"+ "Hamburger SV"));
        greats.add(new Great(R.drawable.ronaldo,"Ronaldo Luís Nazário", "Brazil" , "Rio de Janeiro, Brazil (1976)", "Forward", "Cruzeiro\n" + "PSV\n" + "Barcelona\n" +"Inter Milan\n"+ "Real Madrid\n" +"A.C. Milan\n"+ "Corinthians"));
        greats.add(new Great(R.drawable.zidane,"Zinedine Zidane", "France" , "Marseille, France (1972)", "Midfielder", "Cannes\n" + "Bordeaux\n" + "Juventus\n" + "Real Madrid"));
        greats.add(new Great(R.drawable.johan,"Johan Cruyff", "Holland" , "Amsterdam, Netherlands (1947)","Forward Midfielder","Ajax\n" + "Barcelona\n"+"Los Angeles Aztecs\n"+ "Washington Doplomats\n"+ "Levante\n" +"Feyenoord"));
        greats.add(new Great(R.drawable.lothar,"Lothar Matthäus", "Germany" , "Erlangen, Germany (1961)","Defensive Midfielder", "Borussia Mönchengladbach\n"+ "Bayern Munich\n"+ "Inter Milan\n" +"Metro Stars\n" + "1.FC Herzogenaurach"));
        greats.add(new Great(R.drawable.muller,"Gerd Müller", "Germany" ,"Nördlingen, Germany (1945)","Forward","1861 Nördlingen\n"+ "Bayern Munich\n"+ "Fort Lauderdale Strikers"));
        greats.add(new Great(R.drawable.michel, "Michel Platini", "France" , "Jœuf, France (1955)","Midfielder", "Nancy\n"+ "Saint-Étienne\n"+ "Juventus\n"));
        greats.add(new Great(R.drawable.paolo,"Paolo Maldini", "Italy" , "Milan, Italy (1968)","Defender","Milan"));

        ArrayList<ListItem> listItems = new ArrayList<ListItem>();
        for (int i = 0; i<greats.size(); i++){
            listItems.add((ListItem) greats.get(i));
        }

        ListItemAdapter adapter = new ListItemAdapter(this, listItems);
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent showItemDetailsActivity = new Intent(getBaseContext(), ItemDetailsActivity.class);
                Great selectedGreat = greats.get(position);
                showItemDetailsActivity.putExtra("category","greats");
                showItemDetailsActivity.putExtra("imageResource",selectedGreat.getItemImageResourceId());
                showItemDetailsActivity.putExtra("title", selectedGreat.getItemTitle());
                showItemDetailsActivity.putExtra("subtitle", selectedGreat.getItemSubtitle());
                showItemDetailsActivity.putExtra("desc1", selectedGreat.getBirthPlace());
                showItemDetailsActivity.putExtra("desc2", selectedGreat.getPostion());
                showItemDetailsActivity.putExtra("desc3", selectedGreat.getClubsRepresented());
                startActivity(showItemDetailsActivity);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

}
