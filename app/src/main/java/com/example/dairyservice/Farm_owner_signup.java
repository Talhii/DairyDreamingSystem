package com.example.dairyservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Farm_owner_signup extends AppCompatActivity {

    private EditText farm_owner_name_signup_et,farm_owner_email_signup_et,farm_owner_password_signup_et,farm_owner_confirm_password_signup_et;
    private Button farm_owner_signup_btn;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_owner_signup);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        progressDialog = new ProgressDialog(this);


        farm_owner_name_signup_et = findViewById(R.id.farm_owner_name_signup_et);
        farm_owner_email_signup_et = findViewById(R.id.farm_owner_email_signup_et);
        farm_owner_password_signup_et = findViewById(R.id.farm_owner_password_signup_et);
        farm_owner_confirm_password_signup_et = findViewById(R.id.farm_owner_confirm_password_signup_et);

        farm_owner_signup_btn = findViewById(R.id.farm_owner_signup_btn);

        farm_owner_signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterUser();
            }
        });

    }

    private void RegisterUser(){

        final String name = farm_owner_name_signup_et.getText().toString();
        final String email = farm_owner_email_signup_et.getText().toString();
        String password = farm_owner_password_signup_et.getText().toString();
        String confirm_password = farm_owner_confirm_password_signup_et.getText().toString();

        if (TextUtils.isEmpty(name)) {
            farm_owner_name_signup_et.setError("Name cannot be empty");
            return;
        }
        else if (TextUtils.isEmpty(email)) {
            farm_owner_email_signup_et.setError("Email cannot be empty");
            return;
        }
        else if (TextUtils.isEmpty(password)) {
            farm_owner_password_signup_et.setError("Password cannot be empty");
            return;
        }
        else if (TextUtils.isEmpty(confirm_password)) {
            farm_owner_confirm_password_signup_et.setError("Confirm Password cannot be empty");
            return;
        }
        else if (!password.equals(confirm_password)) {
            farm_owner_confirm_password_signup_et.setError("Confirm Password should be same to Password");
            return;
        }
        else if (password.length()<6) {
            farm_owner_password_signup_et.setError("Password length should not be less than 6");
            return;
        }
        else if (!checkEmail(email)) {
            farm_owner_email_signup_et.setError("Email is not valid");
            return;
        }

        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    myRef.child("Farm_owner").child(name).child("Email").setValue(email);
                    myRef.child("Farm_owner").child(name).child("Name").setValue(name);
                    Toast.makeText(Farm_owner_signup.this, "Successfully Signed Up", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Farm_owner_signup.this, Farm_owner_login.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Log.w("Tag", "createUserWithEmail:failure", task.getException());
                    Toast.makeText(Farm_owner_signup.this, "Sign Up Failed", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }


    private boolean checkEmail(CharSequence mail)
    {
        return(!TextUtils.isEmpty(mail) && Patterns.EMAIL_ADDRESS.matcher(mail).matches());
    }
}
