package com.example.mithub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {
    Button resetButton, backToLoginButton;
    String receiverEmail;
    EditText emailEditText;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        receiverEmail = getIntent().getStringExtra("Email");
        resetButton = findViewById(R.id.send_reset_link_button);
        backToLoginButton = findViewById(R.id.back_button);
        emailEditText = findViewById(R.id.txt_email_reset_password);
        progressDialog = new ProgressDialog(ResetPasswordActivity.this);


        if(!TextUtils.isEmpty(receiverEmail)){
            emailEditText.setText(receiverEmail);
        }

        backToLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToLoginActivity();
            }
        });
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trySendResetEmail();
            }
        });
    }

    private void trySendResetEmail(){
        progressDialog.setMessage("Creating resent link");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();
        String email = emailEditText.getText().toString();
        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter your email address", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
        else{
            mAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(ResetPasswordActivity.this, "Email sent", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                        progressDialog.dismiss();
                        ResetPasswordActivity.this.finish();
                    }
                    else{
                        Toast.makeText(ResetPasswordActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            });
        }

    }

    private void sendToLoginActivity() {
        startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
        ResetPasswordActivity.this.finish();
    }
}
