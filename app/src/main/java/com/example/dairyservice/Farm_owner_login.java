package com.example.dairyservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Farm_owner_login extends AppCompatActivity implements View.OnClickListener {

    private Button farm_owner_signin_btn,farm_owner_signup_activity_btn;
    private EditText farm_owner_email_signin_et,farm_owner_password_signin_et;

    private SharedPreferences.Editor editor;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    String isMatched;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_owner_login);


        editor = getSharedPreferences("EmailPassing", MODE_PRIVATE).edit();

        myRef = database.getReference().child("Farm_owner");
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        farm_owner_email_signin_et = findViewById(R.id.farm_owner_email_signin_et);
        farm_owner_password_signin_et = findViewById(R.id.farm_owner_password_signin_et);

        farm_owner_signin_btn = findViewById(R.id.farm_owner_signin_btn);
        farm_owner_signup_activity_btn = findViewById(R.id.farm_owner_signup_activity_btn);

        farm_owner_signin_btn.setOnClickListener(this);
        farm_owner_signup_activity_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.farm_owner_signin_btn:

                if (TextUtils.isEmpty(farm_owner_email_signin_et.getText().toString())) {
                    farm_owner_email_signin_et.setError("Email cannot be empty");
                    return;
                }
                else if (TextUtils.isEmpty(farm_owner_password_signin_et.getText().toString())) {
                    farm_owner_password_signin_et.setError("Password cannot be empty");
                    return;
                }
                else if (!checkEmail(farm_owner_email_signin_et.getText().toString())) {
                    farm_owner_email_signin_et.setError("Email is not valid");
                    return;
                }
                else{
                    checkUser();
                }
                break;

            case R.id.farm_owner_signup_activity_btn:
                Intent intent = new Intent(this, Farm_owner_signup.class);
                startActivity(intent);
                break;
        }
    }

    private void checkUser() {
        isMatched = "false";
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if ((farm_owner_email_signin_et.getText().toString()).equals(ds.child("Email").getValue().toString())) {
                        Login();
                        isMatched = "true";
                    }
                }
                if (isMatched.equals("false")) {
                    Toast.makeText(Farm_owner_login.this, "No Farm Owner with this email", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void Login(){

        final String email = farm_owner_email_signin_et.getText().toString();
        String password = farm_owner_password_signin_et.getText().toString();

        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    Toast.makeText(Farm_owner_login.this, "Successfully Signed In", Toast.LENGTH_SHORT).show();
                    editor.putString("farm_owner_email", email);
                    editor.apply();
                    progressDialog.dismiss();
                    Intent intent = new Intent(Farm_owner_login.this, Farm_owner.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(Farm_owner_login.this, "Wrong User name or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean checkEmail(CharSequence mail)
    {
        return(!TextUtils.isEmpty(mail) && Patterns.EMAIL_ADDRESS.matcher(mail).matches());
    }
}
