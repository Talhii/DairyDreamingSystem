package com.example.dairyservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Random;
import java.util.UUID;

public class Order_animal extends AppCompatActivity {

    Spinner delivery_spinner;
    Button order_animal_btn;


    private FirebaseDatabase database;
    private DatabaseReference orderRef,image_database;
    private FirebaseStorage image_storage;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_animal);

        database = FirebaseDatabase.getInstance();
        orderRef = database.getReference().child("Orders").child("Animal");

        image_database = database.getReference("ImageData");
        image_storage = FirebaseStorage.getInstance();


        editor = getSharedPreferences("OrderSaving", MODE_PRIVATE).edit();
        prefs = getSharedPreferences("EmailPassing", MODE_PRIVATE);


        delivery_spinner = new Spinner(this);
        delivery_spinner = findViewById(R.id.delivery_spinner);
        order_animal_btn = findViewById(R.id.order_animal_btn);

        ArrayAdapter<CharSequence> delivery_spinner_adapter = ArrayAdapter.createFromResource(this, R.array.delivery_spinner_array, android.R.layout.simple_spinner_item);
        delivery_spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        delivery_spinner.setAdapter(delivery_spinner_adapter);


        final String customer_email = prefs.getString("Customer_email", "No Mail defined");
        final String status = prefs.getString("Status", "No Status defined");


        final String animal_name = getIntent().getStringExtra("animal_name");
        final String age = getIntent().getStringExtra("age");
        final String price = getIntent().getStringExtra("price");
        final String farm_owner_email = getIntent().getStringExtra("farm_owner_email");
        final String image_url = getIntent().getStringExtra("image_url");




        order_animal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int animal_price = 0;
                animal_price = Integer.parseInt(price);


                if(status.equals("Simple_customer")) {

                    if(delivery_spinner.getSelectedItem().toString().equals("Online")){
                        animal_price += 500;   //online delivery charges
                    }

                }
                else if(status.equals("Whole_saler")) {

                    if(delivery_spinner.getSelectedItem().toString().equals("Online")){
                        animal_price += 500;     // online delivery charges
                        animal_price -= 1000;   // discount for Whole_saler
                    }
                }

                final String randomKey = UUID.randomUUID().toString();

                final int min = 100001;
                final int max = 999999;
                final int token_number = new Random().nextInt((max - min) + 1) + min;


                if(delivery_spinner.getSelectedItem().toString().equals("Offline")){
                    orderRef.child(randomKey).child("Token_number").setValue(String.valueOf(token_number));
                }

                orderRef.child(randomKey).child("Farm_owner_email").setValue(farm_owner_email);
                orderRef.child(randomKey).child("Customer").setValue(customer_email);
                orderRef.child(randomKey).child("Animal").setValue(animal_name);
                orderRef.child(randomKey).child("Delivery").setValue(delivery_spinner.getSelectedItem().toString());
                orderRef.child(randomKey).child("Price").setValue(String.valueOf(animal_price));
                orderRef.child(randomKey).child("Age").setValue(String.valueOf(age));
                orderRef.child(randomKey).child("key").setValue(randomKey);


                image_database.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            if(image_url.equals(ds.child("imageURL").getValue().toString())){
                                key = ds.getKey();
                            }

                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(Order_animal.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


                StorageReference imageRef = image_storage.getReferenceFromUrl(image_url);
                imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        image_database.child(key).removeValue();
                       // Toast.makeText(Order_animal.this, "Item deleted ", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(Order_animal.this, exception.toString(), Toast.LENGTH_SHORT).show();
                    }
                });


                Toast.makeText(Order_animal.this, "Order Created Successfully", Toast.LENGTH_SHORT).show();

                editor.putString("farm_owner_email", farm_owner_email);
                editor.putString("deliveryType", delivery_spinner.getSelectedItem().toString());
                editor.putString("orderOf", "animal");
                editor.putString("Price", String.valueOf(animal_price));
                editor.putString("Age", String.valueOf(age));
                editor.putString("Animal", animal_name);
                editor.putString("Token_number", String.valueOf(token_number));

                editor.apply();

                Intent intent = new Intent(Order_animal.this,Customer_animal_order_info.class);
                startActivity(intent);

            }
        });
    }
}