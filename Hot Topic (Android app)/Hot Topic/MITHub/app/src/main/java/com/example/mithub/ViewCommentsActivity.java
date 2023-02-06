package com.example.mithub;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mithub.Model.Comment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewCommentsActivity extends AppCompatActivity {

    private  String receiverDiscussionId, currentUserId, commentMessage;

    private TextView nameTextView, uploadDateTextView, uploadTimeTextView, discussionMessageTextView,upvotesCounterTextView,commentsCounterTextView;
    private CircleImageView posterProfileImage;
    private ImageView discussionImageView, voterIcon;
    private EditText commentEditText;
    private RecyclerView commentsRecyclerView;
    private ImageButton submitCommentButton;
    FirebaseRecyclerAdapter<Comment, ViewHolders.CommentsViewHolder> adapter;


    boolean upvoteChecker = false;

    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_comments);
        getSupportActionBar().setTitle("Discussion Board");

        nameTextView = findViewById(R.id.discussion_poster_full_name_tv);
        uploadDateTextView = findViewById(R.id.discussion_upload_date_tv);
        uploadTimeTextView = findViewById(R.id.discussion_upload_time_tv);
        discussionMessageTextView = findViewById(R.id.discussion_message_tv);
        posterProfileImage = findViewById(R.id.dicsussion_poster_profile_image);
        discussionImageView = findViewById(R.id.discussion_image_view);
        commentEditText = findViewById(R.id.comment_edit_text);
        submitCommentButton = findViewById(R.id.submit_comment_button);
        voterIcon = findViewById(R.id.voter_icon);
        upvotesCounterTextView = findViewById(R.id.upvotes_counter_text_view);
        commentsCounterTextView = findViewById(R.id.comments_counter_text_view);

        commentsRecyclerView = findViewById(R.id.comments_recycler_view);
        commentsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewCommentsActivity.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        commentsRecyclerView.setLayoutManager(linearLayoutManager);

        receiverDiscussionId = getIntent().getExtras().get("discussion_id").toString();
        showDiscussionDetails(receiverDiscussionId);

        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getUid();

        toggleVoterImage();
        getCommentsCounter();

        submitCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitComment();
            }
        });
        voterIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                discussionVoter();
            }
        });

    }

    private void getCommentsCounter() {
        DatabaseReference discussionReference = firebaseDatabase.getReference().child("Discussions").child(receiverDiscussionId);
        discussionReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.hasChild("Comments")){
                    int commentsCounter = (int)dataSnapshot.child("Comments").getChildrenCount();
                    commentsCounterTextView.setText(Integer.toString(commentsCounter) + " Comments");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void toggleVoterImage() {
        DatabaseReference upvotesReference = firebaseDatabase.getReference().child("Upvotes");
        upvotesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(receiverDiscussionId).hasChild(currentUserId)){
                    int upvotesCounter = (int) dataSnapshot.child(receiverDiscussionId).getChildrenCount();
                    voterIcon.setImageResource(R.drawable.downvote_icon);
                    upvotesCounterTextView.setText(Integer.toString(upvotesCounter) + " Upvotes");
                    upvotesCounterTextView.setTextColor(getResources().getColor(R.color.upvoted));
                }
                else {
                    int upvotesCounter = (int) dataSnapshot.child(receiverDiscussionId).getChildrenCount();
                    voterIcon.setImageResource(R.drawable.upvote_icon);
                    upvotesCounterTextView.setText(Integer.toString(upvotesCounter) + " Upvotes");
                    upvotesCounterTextView.setTextColor(getResources().getColor(R.color.light_gray));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void discussionVoter() {
        final DatabaseReference upvotesReference= firebaseDatabase.getReference().child("Upvotes");
        upvoteChecker = true;
        upvotesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (upvoteChecker == true){
                    if(dataSnapshot.child(receiverDiscussionId).hasChild(currentUserId)){
                        upvotesReference.child(receiverDiscussionId).child(currentUserId).removeValue();
                        upvoteChecker = false;
                    }
                    else {
                        upvotesReference.child(receiverDiscussionId).child(currentUserId).setValue(true);
                        upvoteChecker = false;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference commentsReference = firebaseDatabase.getReference().child("Discussions").child(receiverDiscussionId).child("Comments");
        FirebaseRecyclerOptions options =
                new FirebaseRecyclerOptions.Builder<Comment>()
                        .setQuery(commentsReference, Comment.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<Comment, ViewHolders.CommentsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolders.CommentsViewHolder holder, int position, @NonNull Comment model) {
                holder.nameTextView.setText(model.getCommenter_name());
                holder.dateTextView.setText(model.getComment_date());
                holder.timeTextView.setText(model.getComment_time());
                holder.commentTextView.setText(model.getComment_message());
            }

            @NonNull
            @Override
            public ViewHolders.CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comments_layout, viewGroup, false);
                ViewHolders.CommentsViewHolder viewHolder = new ViewHolders.CommentsViewHolder(view);
                return viewHolder;
            }
        };

        commentsRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private void submitComment() {
        DatabaseReference currentUserRef = firebaseDatabase.getReference().child("Users").child(currentUserId);
        currentUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String fullName = dataSnapshot.child("first_name").getValue().toString() + " " + dataSnapshot.child("last_name").getValue().toString();

                    commentMessage = commentEditText.getText().toString();
                    if(TextUtils.isEmpty(commentMessage)){
                        Toast.makeText(ViewCommentsActivity.this, "Please enter a comment to submit", Toast.LENGTH_SHORT).show();
                    }
                    else{ saveCommentToDatabase(fullName);}
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void saveCommentToDatabase(String fullName) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMMM-yyyy");
        Date currentDate = new Date();
        final String commentDate = dateFormat.format(currentDate);
        final String commentTime = timeFormat.format(currentDate);
        final String randomCommentKey = currentUserId + commentDate + commentTime;

        Comment comment = new Comment(currentUserId, fullName, commentMessage, commentDate, commentTime);
        DatabaseReference commentsReference = firebaseDatabase.getReference().child("Discussions").child(receiverDiscussionId).child("Comments");
        commentsReference.child(randomCommentKey).setValue(comment).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ViewCommentsActivity.this, "Comment uploaded successfully", Toast.LENGTH_SHORT).show();
                    commentEditText.setText("");
                }
                else{
                    Toast.makeText(ViewCommentsActivity.this, "Error occurred: Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void showDiscussionDetails(String receiverDiscussionId) {
        DatabaseReference discussionReference = FirebaseDatabase.getInstance().getReference().child("Discussions").child(receiverDiscussionId);
        discussionReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    if(dataSnapshot.hasChild("discussion_image_url")){
                        String discussionImage = dataSnapshot.child("discussion_image_url").getValue().toString();
                        Picasso.get().load(discussionImage).into(discussionImageView);
                        discussionImageView.setVisibility(View.VISIBLE);
                    }

                    String name = dataSnapshot.child("poster_full_name").getValue().toString();
                    String uploadDate = dataSnapshot.child("upload_date").getValue().toString();
                    String uploadTime = dataSnapshot.child("upload_time").getValue().toString();
                    String discussionMessage = dataSnapshot.child("discussion_message").getValue().toString();
                    String profileImage = dataSnapshot.child("poster_profile_image_url").getValue().toString();
                    Picasso.get().load(profileImage).into(posterProfileImage);
                    nameTextView.setText(name);
                    uploadDateTextView.setText(uploadDate);
                    uploadTimeTextView.setText(uploadTime);
                    discussionMessageTextView.setText(discussionMessage);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(adapter != null){
            adapter.stopListening();
        }
    }
}
