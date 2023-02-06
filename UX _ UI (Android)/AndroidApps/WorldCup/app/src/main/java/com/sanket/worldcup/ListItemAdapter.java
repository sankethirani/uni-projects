package com.sanket.worldcup;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListItemAdapter extends ArrayAdapter {
    public ListItemAdapter(Activity context, ArrayList<ListItem> team){
        super(context,0,team);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_view_item,parent,false);
        }

        ListItem currentItem = (ListItem) getItem(position);
        TextView teamNameTextView = listItemView.findViewById(R.id.list_title_text_view);
        teamNameTextView.setText(currentItem.getItemTitle());

        TextView teamDetailsTextView = listItemView.findViewById(R.id.list_subtitle_text_view);
        teamDetailsTextView.setText(currentItem.getItemSubtitle());

        ImageView teamIcon = listItemView.findViewById(R.id.list_image_view);
        teamIcon.setImageResource(currentItem.getItemImageResourceId());
        return listItemView;
    }
}
