package com.example.mithub;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentPersonalDetails extends Fragment {

    EditText userFirstNameEditText;
    EditText userLastNameEditText;
    TextView userEmailTextView;
    TextView userRoleTextView;
    Button logoutButton, updateDetailsButton;

    FirebaseAuth mAuth;
    DatabaseReference firebaseDatabase;

    public FragmentPersonalDetails() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.personal_details_fragment, container, false);
        mAuth = FirebaseAuth.getInstance();
        userFirstNameEditText = view.findViewById(R.id.personal_details_first_name_txt);
        userLastNameEditText = view.findViewById(R.id.personal_details_last_name_txt);
        userEmailTextView = view.findViewById(R.id.personal_details_email_txt);
        userRoleTextView = view.findViewById(R.id.personal_details_role);
        logoutButton = view.findViewById(R.id.logout_button);
        updateDetailsButton = view.findViewById(R.id.update_personal_details_button);
        firebaseDatabase = FirebaseDatabase.getInstance().getReference();

        updateDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDetails();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(getActivity(),LoginActivity.class));
                getActivity().finish();
            }
        });

        DatabaseReference currentUserRef = firebaseDatabase.child("Users").child(mAuth.getUid());
        currentUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String firstName = dataSnapshot.child("first_name").getValue().toString();
                    String lastName = dataSnapshot.child("last_name").getValue().toString();
                    String role = dataSnapshot.child("role").getValue().toString();
                    String email = mAuth.getCurrentUser().getEmail();
                    userFirstNameEditText.setText(firstName);
                    userLastNameEditText.setText(lastName);
                    userEmailTextView.setText(email);
                    userRoleTextView.setText(role);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }

    private void updateDetails() {
        String firstName = userFirstNameEditText.getText().toString();
        String lastName = userLastNameEditText.getText().toString();
        if(TextUtils.isEmpty(firstName)){
            Toast.makeText(getContext(), "Please enter first name", Toast.LENGTH_SHORT).show();
            return;
        }

        else if(TextUtils.isEmpty(lastName)){
            Toast.makeText(getContext(), "Please enter last name", Toast.LENGTH_SHORT).show();
            return;
        }

        else{
            String currentUserId = mAuth.getUid();
            DatabaseReference userReference = firebaseDatabase.child("Users").child(currentUserId);
            try{
                userReference.child("first_name").setValue(firstName);
                userReference.child("last_name").setValue(lastName);
                Toast.makeText(getContext(), "Personal Details Updated Successfully", Toast.LENGTH_SHORT).show();
                getActivity().finish();
                //getActivity().getFragmentManager().popBackStack();
                startActivity(new Intent(getContext(),MainActivity.class));
            }
            catch (Exception e){
                Toast.makeText(getContext(), "Your Details could not be updated", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }



}
