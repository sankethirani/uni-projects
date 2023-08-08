package com.example.mithub;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mithub.Model.Discussion;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class CategoryDiscussionsActivity extends AppCompatActivity {
    private String receiverCategory;
    private RecyclerView selectedCategoryDiscussionsList;
    private FirebaseRecyclerAdapter<Discussion, ViewHolders.DiscussionViewHolder> adapter;
    DatabaseReference discussionsReference;
    private RelativeLayout noDiscussionsView;

    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_discussions);

        receiverCategory = getIntent().getStringExtra("Category");
        discussionsReference = FirebaseDatabase.getInstance().getReference().child("Categories").child(receiverCategory);
        getSupportActionBar().setTitle(receiverCategory);

        noDiscussionsView = findViewById(R.id.no_discussions_view);
    }

    @Override
    protected void onStart() {
        super.onStart();
        discussionsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()){
                    showDiscussionsList();
                }
                else{
                    noDiscussionsView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private void showDiscussionsList() {
        selectedCategoryDiscussionsList = findViewById(R.id.selected_category_discussions_list);
        linearLayoutManager = new LinearLayoutManager(CategoryDiscussionsActivity.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        selectedCategoryDiscussionsList.setLayoutManager(linearLayoutManager);

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
                        Intent intent = new Intent(CategoryDiscussionsActivity.this, ViewCommentsActivity.class);
                        intent.putExtra("discussion_id", discussionId);
                        startActivity(intent);
                        CategoryDiscussionsActivity.this.finish();
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

        selectedCategoryDiscussionsList.setAdapter(adapter);
        adapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null){
            adapter.stopListening();
        }

    }
}
