package com.example.mithub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {

    EditText txtEmail, txtPassword;
    Button btn_login, btn_register,btn_reset_password;
    private TextInputLayout input_email, input_password;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            LoginActivity.this.finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //hide action bar
        getSupportActionBar().hide();
        txtEmail = findViewById(R.id.txt_email_login);
        txtPassword = findViewById(R.id.txt_password_login);
        btn_login = findViewById(R.id.button_login);
        btn_register = findViewById(R.id.button_register_login);
        input_email = findViewById(R.id.email_input_login);
        input_password = findViewById(R.id.password_input_login);
        btn_reset_password = findViewById(R.id.reset_password_button);
        loadingBar = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // Reset errors.
                attemptLogin();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { startActivity(new Intent(getApplicationContext(), RegisterActivity.class));}
        });

        btn_reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startResetActivity();


            }
        });
    }

    private void startResetActivity() {
        String email = txtEmail.getText().toString().trim();
        Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
        if(!TextUtils.isEmpty(email)){
            intent.putExtra("Email", email);
        }
        startActivity(intent);
        LoginActivity.this.finish();
    }

    private void attemptLogin() {
        input_email.setError(null);
        input_password.setError(null);
        String email = txtEmail.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();


        if(TextUtils.isEmpty(email)){
            input_email.setError("Input your email");
            txtEmail.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(password)){
            input_password.setError("Please enter your password");
            txtPassword.requestFocus();
            return;
        }
        else{
            loadingBar.setMessage("Logging In");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                LoginActivity.this.finish();
                                loadingBar.dismiss();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(LoginActivity.this, "Error Occured: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
        }
    }
}
