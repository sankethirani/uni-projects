package com.example.learnmaori;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NumberAdaptor extends ArrayAdapter {
    MediaPlayer mediaPlayer;
    int mLayoutID;
    ArrayList<Number> mNumbers;
    Context mContext;

    public NumberAdaptor(@NonNull Activity context, @LayoutRes int resource, @NonNull ArrayList<Number> objects) {
        super(context, resource, objects);
        mLayoutID = resource;
        mNumbers = objects;
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentListViewItem = convertView;

        if(currentListViewItem == null){
            currentListViewItem = LayoutInflater.from(getContext()).inflate(mLayoutID, parent, false);
        }

        Number currentNumber = mNumbers.get(position);
        ImageView iconImageView = currentListViewItem.findViewById(R.id.image_view_icon);
        int i = mContext.getResources().getIdentifier(
                currentNumber.getIcon(),"drawable",
                mContext.getPackageName());

        iconImageView.setImageResource(i);

        TextView maoriTextView = currentListViewItem.findViewById(R.id.text_view_maori_word);
        maoriTextView.setText(currentNumber.getMaoriTranslation());

        final String audio = currentNumber.getAudio();
        final ImageView play = currentListViewItem.findViewById(R.id.image_view_play);

        play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                int i = mContext.getResources().getIdentifier(
                        audio, "raw",
                        mContext.getPackageName());
                if (mediaPlayer != null)
                    mediaPlayer.release();
                mediaPlayer = MediaPlayer.create(mContext, i);
                mediaPlayer.start();
                Animation animation1 = new AlphaAnimation(0.3f, 1.0f);
                animation1.setDuration(800);
                play.startAnimation(animation1);
            }
        });

        return currentListViewItem;
    }
}
