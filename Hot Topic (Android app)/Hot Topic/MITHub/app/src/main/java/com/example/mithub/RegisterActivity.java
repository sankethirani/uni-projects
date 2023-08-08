package com.example.mithub;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    private EditText txtFirstName, txtLastName, txtEmail, txtPassword, txtConfirmPassword;
    private TextInputLayout firstNameInput, lastNameInput, emailInput, passwordInput, confirmPasswordInput;
    private Button btn_register;
    private CircleImageView profileImage;
    private ProgressDialog loadingBar;
    private RelativeLayout imageLayout;

    private FirebaseAuth mAuth;
    private DatabaseReference currentUserReference;
    private StorageReference UserProfileImagesReference;

    private Uri profilePicUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //change title of view
        getSupportActionBar().setTitle("Register");

        txtFirstName = findViewById(R.id.txt_first_name);
        txtLastName = findViewById(R.id.txt_last_name);
        txtEmail = findViewById(R.id.txt_email);
        txtPassword = findViewById(R.id.txt_password);
        txtConfirmPassword = findViewById(R.id.txt_confirm_password);
        firstNameInput = findViewById(R.id.first_name_input);
        lastNameInput = findViewById(R.id.last_name_input);
        emailInput = findViewById(R.id.register_email_input);
        passwordInput = findViewById(R.id.password_input);
        confirmPasswordInput = findViewById(R.id.confirm_password_input);
        btn_register = findViewById(R.id.buttonRegister);
        profileImage = findViewById(R.id.profile_image);
        imageLayout = findViewById(R.id.upload_image_layout_view);
        //uploadIcon = findViewById(R.id.upload_icon);
        loadingBar = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        UserProfileImagesReference = FirebaseStorage.getInstance().getReference().child("Profile Images");


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewUser();
            }
        });

        imageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.startPickImageActivity(RegisterActivity.this);
            }
        });
    }


    //get selected image from the crop activity and display on the screen
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK ){
            Uri imageUri = CropImage.getPickImageResultUri(this, data);
            //once user has selected the image, user is sent to crop activity
            cropRequest(imageUri);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                //after user crops image, it is displayed on screen
                profilePicUri = result.getUri();
                profileImage.setImageURI(profilePicUri);
            }
            else{
                Toast.makeText(RegisterActivity.this, "Error Occured: Image Crop Failed" , Toast.LENGTH_LONG).show();
            }
        }
    }

    private void cropRequest(Uri imageUri){
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
                .start(this);
    }

    private void CreateNewUser() {
        firstNameInput.setError(null);
        lastNameInput.setError(null);
        emailInput.setError(null);
        passwordInput.setError(null);
        confirmPasswordInput.setError(null);

        String email = txtEmail.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();
        String confirmPassword = txtConfirmPassword.getText().toString().trim();
        String firstName = txtFirstName.getText().toString();
        String lastName = txtLastName.getText().toString();

        if(TextUtils.isEmpty(firstName)){
            firstNameInput.setError("Please enter your first name");
            txtFirstName.requestFocus();
            return;
        }

        else if(TextUtils.isEmpty(lastName)){
            lastNameInput.setError("Please enter your last name");
            txtLastName.requestFocus();
            return;
        }

        else if(TextUtils.isEmpty(email)){
            emailInput.setError("Please enter your student email");
            txtEmail.requestFocus();
            return;
        }

        else if(!email.contains("manukaumail.com")){
            emailInput.setError("Only MIT student emails can be used");
            txtEmail.requestFocus();
            return;
        }

        else if(TextUtils.isEmpty(password)){
            passwordInput.setError("Please enter a password");
            txtPassword.requestFocus();
            return;
        }

        else if(TextUtils.isEmpty(confirmPassword)){
            confirmPasswordInput.setError("Please re-enter your password");
            txtConfirmPassword.requestFocus();
            return;
        }

        else if(!password.equals(confirmPassword)){
            confirmPasswordInput.setError("The passwords you have entered do not match");
            txtConfirmPassword.requestFocus();
            return;
        }

        else if (profilePicUri == null){
            Toast.makeText(RegisterActivity.this, "Please upload an image to identify yourself", Toast.LENGTH_SHORT).show();
            return;
        }

        else {
            final HashMap userMap = new HashMap();
            userMap.put("first_name", firstName);
            userMap.put("last_name", lastName);
            userMap.put("role", "Student");
            loadingBar.setMessage("Registering new user");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);
            //code for adding user to database
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String currentUserId = mAuth.getCurrentUser().getUid();

                                //create new user in database with the entered user details
                                currentUserReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);
                                currentUserReference.updateChildren(userMap);

                                //store the profile image to firebase storage
                                storePhotoToDatabase(currentUserId);

                                loadingBar.dismiss();
                                Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                RegisterActivity.this.finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(RegisterActivity.this, "Error Occurred: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                loadingBar.dismiss();
                            }

                        }
                    });
        }
    }

    private void storePhotoToDatabase(final String currentUserId) {
        //save the file to firebase storage, file being named after userId
        StorageReference filePath = UserProfileImagesReference.child(currentUserId + ".jpg");
        filePath.putFile(profilePicUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    //once image is stored, save the download link of the image to the database under the current user
                    //Toast.makeText(RegisterActivity.this, "Image Uploaded to firebase storage", Toast.LENGTH_LONG).show();
                    Task<Uri> result = task.getResult().getMetadata().getReference().getDownloadUrl();
                    result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            final String downloadUrl = uri.toString();
                            currentUserReference.child("profile_image_url").setValue(downloadUrl);
                        }
                    });
                }
            }
        });
    }
}
