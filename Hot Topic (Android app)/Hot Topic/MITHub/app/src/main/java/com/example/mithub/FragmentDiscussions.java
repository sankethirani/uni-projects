package com.example.mithub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mithub.Model.Discussion;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class FragmentDiscussions extends Fragment {

    private View view;
    private FloatingActionButton fab;
    private RecyclerView discussionsRecyclerView;
    private FirebaseRecyclerAdapter<Discussion, ViewHolders.DiscussionViewHolder> adapter;

    private DatabaseReference discussionsReference;

    public FragmentDiscussions() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.discussions_fragment, container, false);
        discussionsRecyclerView = view.findViewById(R.id.discussions_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        discussionsRecyclerView.setLayoutManager(linearLayoutManager);

        discussionsReference = FirebaseDatabase.getInstance().getReference().child("Discussions");

        //floating action button on click event
        fab = view.findViewById(R.id.fab);
        fab.setRippleColor(Color.RED);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                startActivity(new Intent(getActivity(), PostDiscussionActivity.class));
            }
        });

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions options
                = new FirebaseRecyclerOptions.Builder<Discussion>()
                .setQuery(discussionsReference, Discussion.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Discussion, ViewHolders.DiscussionViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ViewHolders.DiscussionViewHolder holder, final int position, @NonNull final Discussion model) {
                //clear the image view for a discussion
                holder.discussionImageView.setVisibility(View.GONE);
                //get discussion reference to check if discussion has an image
                String discussionId = getRef(position).getKey();
                DatabaseReference discussionReference = FirebaseDatabase.getInstance().getReference().child("Discussions").child(discussionId);
                discussionReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild("discussion_image_url")){
                            Picasso.get().load(model.getDiscussion_image_url()).into(holder.discussionImageView);
                            holder.discussionImageView.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                holder.nameTextView.setText(model.getPoster_full_name());
                holder.uploadDateTextView.setText(model.getUpload_date());
                holder.uploadTimeTextView.setText(model.getUpload_time());
                holder.discussionMessageTextView.setText(model.getDiscussion_message());
                Picasso.get().load(model.poster_profile_image_url).into(holder.posterProfileImage);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String discussionId = getRef(position).getKey();
                        Intent intent = new Intent(getContext(), ViewCommentsActivity.class);
                        intent.putExtra("discussion_id", discussionId);
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public ViewHolders.DiscussionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.discussions_layout, viewGroup, false);
                ViewHolders.DiscussionViewHolder viewHolder = new ViewHolders.DiscussionViewHolder(view);
                return viewHolder;
            }
        };

        discussionsRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }


    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null){
            adapter.stopListening();
        }

    }
}
