package com.example.dairyservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Food_authority_login extends AppCompatActivity {


    private Spinner spinner;
    private EditText food_authority_signin_pin_et;
    private Button food_authority_signin_btn;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    String isMatched;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_authority_login);


        myRef = database.getReference().child("Food_authority");

        spinner = new Spinner(this);
        spinner = findViewById(R.id.province_spinner);

        food_authority_signin_pin_et = findViewById(R.id.food_authority_signin_pin_et);
        food_authority_signin_btn = findViewById(R.id.food_authority_signin_btn);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.province_spinner_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        food_authority_signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isMatched = "false";
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {

                            if(TextUtils.isEmpty(food_authority_signin_pin_et.getText().toString())){
                                food_authority_signin_pin_et.setError("Pin cannot be empty");
                            }
                            else if ((food_authority_signin_pin_et.getText().toString()).equals(ds.child("Pin").getValue().toString()) && spinner.getSelectedItem().toString().equals(ds.child("Selected").getValue().toString())) {
                                isMatched = "true";
                                Toast.makeText(Food_authority_login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Food_authority_login.this,Food_authority.class);
                                intent.putExtra("food_authority",spinner.getSelectedItem().toString());
                                startActivity(intent);
                                finish();
                            }
                        }
                        if (isMatched.equals("false")) {
                            Toast.makeText(Food_authority_login.this, "Wrong Pin", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}
