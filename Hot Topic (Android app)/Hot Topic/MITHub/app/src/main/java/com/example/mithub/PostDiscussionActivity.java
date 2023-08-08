package com.example.mithub;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mithub.Model.Discussion;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class PostDiscussionActivity extends AppCompatActivity {
    private ImageView discussionImageUpload;
    private TextView discussionMessageTextView;

    private Uri discussionImageUri;
    ArrayList<String> selectedCategories = new ArrayList();

    private String saveCurrentDate, saveCurrentTime, discussionRandomName, currentUserId, discussionMessage;

    private StorageReference storageReference;
    private DatabaseReference currentUserReference, discussionsReference;
    private FirebaseAuth mAuth;

    private SimpleDateFormat dateFormat,timeFormat;
    Date currentDate;

    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_discussion);

        //hide action bar
        getSupportActionBar().hide();

        //bottom navigation menu
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //ListView for categories
        ListView categoryChecklist = findViewById(R.id.categories_check_list);
        categoryChecklist.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        String[] categories = {"Animal Care", "Automotive", "Business", "Construction", "Creative Arts", "Digital Technologies", "Education", "Engineering", "Foundation",
                "Hairdressing", "Health", "Horticulture", "Hospitality", "Language", "Logistics", "Maritime", "Nursing", "Police",
                "Social Service", "Sports", "Support Learning"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.categories_checkbox_layout, R.id.txt_category_checkbox, categories);
        categoryChecklist.setAdapter(adapter);
        categoryChecklist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = ((TextView)view).getText().toString();
                if (selectedCategories.contains(selectedItem)){
                    selectedCategories.remove(selectedItem);
                }
                else{
                    selectedCategories.add(selectedItem);
                }
            }
        });

        timeFormat = new SimpleDateFormat("HH:mm");
        dateFormat = new SimpleDateFormat("dd-MMMM-yyyy");

        loadingBar = new ProgressDialog(this);

        discussionImageUpload = findViewById(R.id.post_discussion_image);
        discussionMessageTextView = findViewById(R.id.post_dicussion_message);
        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        storageReference = FirebaseStorage.getInstance().getReference();
        currentUserReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_discard:
                    discardPost();
                    return true;
                case R.id.navigation_upload_image:
                    uploadImage();
                    return true;
                case R.id.navigation_post:
                    post();
                    return true;
            }
            return false;
        }
    };

    private void post() {
        discussionMessage = discussionMessageTextView.getText().toString();

        if(TextUtils.isEmpty(discussionMessage)){
            Toast.makeText(getApplicationContext(), "Please enter something about the discussion", Toast.LENGTH_SHORT).show();
        }

        else if(selectedCategories.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please select at least one category for your discussion", Toast.LENGTH_SHORT).show();
        }

        else{
            loadingBar.setMessage("Posting Discussion");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);
            currentDate = new Date();
            saveCurrentDate = dateFormat.format(currentDate);
            saveCurrentTime = timeFormat.format(currentDate);

            discussionRandomName =  saveCurrentDate + saveCurrentTime + currentUserId;

            discussionsReference = FirebaseDatabase.getInstance().getReference().child("Discussions");

            if(discussionImageUri != null){
                storeImageToFirebaseStorage();
            }
            else{
                saveDiscussionToDatabase(null, false);
            }
        }

    }

    private void storeImageToFirebaseStorage() {
        StorageReference postDiscussionImageReference = storageReference.child("Discussion Images").child(discussionImageUri.getLastPathSegment() +  discussionRandomName + ".jpg");
        postDiscussionImageReference.putFile(discussionImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    Task<Uri> result = task.getResult().getMetadata().getReference().getDownloadUrl();
                    result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            final String downloadUrl = uri.toString();
                            saveDiscussionToDatabase(downloadUrl, true);
                        }
                    });
                    
                }
                else{
                    Toast.makeText(getApplicationContext(),"Error Occurred: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveDiscussionToDatabase(final String downloadUrl, final boolean discussionHasImage) {
        currentUserReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String userFullName = dataSnapshot.child("first_name").getValue().toString() + " " + dataSnapshot.child("last_name").getValue().toString();
                    String userProfileImage = dataSnapshot.child("profile_image_url").getValue().toString();
                    final Discussion discussion;

                    if(discussionHasImage){
                        discussion = new Discussion(
                                currentUserId, userFullName, userProfileImage, saveCurrentDate, saveCurrentTime, discussionMessage, downloadUrl
                        );
                    }
                    else {discussion = new Discussion(currentUserId, userFullName, userProfileImage, saveCurrentDate, saveCurrentTime, discussionMessage);}

                    discussionsReference.child(discussionRandomName).setValue(discussion).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Discussion created successfully", Toast.LENGTH_SHORT).show();
                                addDiscussionToCategories(discussion);
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                PostDiscussionActivity.this.finish();
                                loadingBar.dismiss();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Posting discussion failed", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void addDiscussionToCategories(Discussion discussion) {
        DatabaseReference categoriesReference= FirebaseDatabase.getInstance().getReference().child("Categories");
        for (String category : selectedCategories){
            DatabaseReference categoryReference = categoriesReference.child(category);
            categoryReference.child(discussionRandomName).setValue(discussion);
        }


    }

    private void uploadImage() {
        //code for uploading an image
        CropImage.startPickImageActivity(PostDiscussionActivity.this);
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);
            //once user has selected the image, user is sent to crop activity
            cropRequest(imageUri);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                //after user crops image, it is displayed on screen
                discussionImageUri = result.getUri();
                discussionImageUpload.setImageURI(discussionImageUri);
                discussionImageUpload.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(PostDiscussionActivity.this, "Error Occured: Image Crop Failed", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void cropRequest(Uri imageUri){
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }

    private void discardPost() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        PostDiscussionActivity.this.finish();
    }

}
