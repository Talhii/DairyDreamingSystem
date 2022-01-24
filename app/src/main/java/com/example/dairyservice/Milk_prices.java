package com.example.dairyservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
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

import java.util.UUID;

public class Milk_prices extends AppCompatActivity {

    Spinner spinner;
    EditText milk_price_et;
    Button milk_update_price_btn;

    private SharedPreferences prefs;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    String isMatched;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milk_prices);

        milk_price_et = findViewById(R.id.milk_price_et);

        milk_update_price_btn = findViewById(R.id.milk_update_price_btn);

        database = FirebaseDatabase.getInstance();


        prefs = getSharedPreferences("EmailPassing", MODE_PRIVATE);

        spinner = new Spinner(this);
        spinner = findViewById(R.id.milk_price_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.animal_spinner_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



        final String farm_owner_email = prefs.getString("farm_owner_email", "No Mail defined");

        milk_update_price_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isMatched = "false";
                myRef = database.getReference().child("Data").child("Milk").child("Prices").child(spinner.getSelectedItem().toString());

                if(TextUtils.isEmpty(milk_price_et.getText().toString())){
                    milk_price_et.setError("Price cannot be empty");
                }
                else {

                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                if ((farm_owner_email.equals(ds.child("Email").getValue().toString()))) {
                                    DatabaseReference priceReference = ds.getRef().child("Price");
                                    priceReference.setValue(milk_price_et.getText().toString());
                                    isMatched = "true";
                                }
                            }

                            if (isMatched.equals("false")) {
                                final String randomKey = UUID.randomUUID().toString();

                                myRef.child(randomKey).child("Email").setValue(farm_owner_email);
                                myRef.child(randomKey).child("Price").setValue(milk_price_et.getText().toString());
                                myRef.child(randomKey).child("key").setValue(randomKey);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    Toast.makeText(Milk_prices.this, "New Price Uploaded Successfully", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}