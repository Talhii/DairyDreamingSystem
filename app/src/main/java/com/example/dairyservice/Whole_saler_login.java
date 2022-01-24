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

public class Whole_saler_login extends AppCompatActivity implements View.OnClickListener{

    private Button whole_saler_signin_btn,whole_saler_signup_activity_btn;
    private EditText whole_saler_email_signin_et,whole_saler_password_signin_et;


    private SharedPreferences.Editor editor;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    String isMatched;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whole_saler_login);


        myRef = database.getReference().child("Customer");
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);


        editor = getSharedPreferences("EmailPassing", MODE_PRIVATE).edit();

        whole_saler_email_signin_et = findViewById(R.id.whole_saler_email_signin_et);
        whole_saler_password_signin_et = findViewById(R.id.whole_saler_password_signin_et);

        whole_saler_signin_btn = findViewById(R.id.whole_saler_signin_btn);
        whole_saler_signup_activity_btn = findViewById(R.id.whole_saler_signup_activity_btn);

        whole_saler_signin_btn.setOnClickListener(this);
        whole_saler_signup_activity_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.whole_saler_signin_btn:
                if (TextUtils.isEmpty(whole_saler_email_signin_et.getText().toString())) {
                    whole_saler_email_signin_et.setError("Email cannot be empty");
                    return;
                }
                else if (TextUtils.isEmpty(whole_saler_password_signin_et.getText().toString())) {
                    whole_saler_password_signin_et.setError("Password cannot be empty");
                    return;
                }
                else if (!checkEmail(whole_saler_email_signin_et.getText().toString())) {
                    whole_saler_email_signin_et.setError("Email is not valid");
                    return;
                }
                else{
                    checkUser();
                }
                break;
            case R.id.whole_saler_signup_activity_btn:
                Intent intent = new Intent(this, Whole_saler_signup.class);
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
                    if ((whole_saler_email_signin_et.getText().toString()).equals(ds.child("Email").getValue().toString()) && ds.child("Status").getValue().toString().equals("Whole_saler")) {
                        Login();
                        isMatched = "true";
                    }
                }
                if (isMatched.equals("false")) {
                    Toast.makeText(Whole_saler_login.this, "No Wholesaler with this email", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void Login(){

        final String email = whole_saler_email_signin_et.getText().toString();
        String password = whole_saler_password_signin_et.getText().toString();

        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    Toast.makeText(Whole_saler_login.this, "Successfully Signed In", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    Intent intent = new Intent(Whole_saler_login.this, Customer.class);
                    editor.putString("Customer_email", email);
                    editor.putString("Status", "Whole_saler");

                    editor.apply();
                    startActivity(intent);
                    finish();
                }
                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(Whole_saler_login.this, "Wrong User name or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean checkEmail(CharSequence mail)
    {
        return(!TextUtils.isEmpty(mail) && Patterns.EMAIL_ADDRESS.matcher(mail).matches());
    }
}
