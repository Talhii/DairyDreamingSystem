package com.example.dairyservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Show_previous_data extends AppCompatActivity {


    private DatabaseReference mrefgoat,mrefcow,mrefbuffalo,mrefgoatstock,mrefcowstock,mrefbuffalostock;
    TextView cow_milk_price_of_specific_farm,cow_milk_stock_of_specific_farm
            ,buffalo_milk_price_of_specific_farm,buffalo_milk_stock_of_specific_farm
            ,goat_milk_price_of_specific_farm,goat_milk_stock_of_specific_farm;


    private SharedPreferences prefs;
    String cow_milk_price,goat_milk_price,buffalo_milk_price;
    String cow_milk_stock,goat_milk_stock,buffalo_milk_stock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_previous_data);

        cow_milk_price_of_specific_farm = findViewById(R.id.cow_milk_price_of_specific_farm);
        cow_milk_stock_of_specific_farm = findViewById(R.id.cow_milk_stock_of_specific_farm);

        goat_milk_price_of_specific_farm = findViewById(R.id.goat_milk_price_of_specific_farm);
        goat_milk_stock_of_specific_farm = findViewById(R.id.goat_milk_stock_of_specific_farm);

        buffalo_milk_price_of_specific_farm = findViewById(R.id.buffalo_milk_price_of_specific_farm);
        buffalo_milk_stock_of_specific_farm = findViewById(R.id.buffalo_milk_stock_of_specific_farm);


        prefs = getSharedPreferences("EmailPassing", MODE_PRIVATE);
        final String farm_owner_email = prefs.getString("farm_owner_email", "No Mail defined");


        mrefgoat = FirebaseDatabase.getInstance().getReference().child("Data").child("Milk").child("Prices").child("Goat");
        mrefcow = FirebaseDatabase.getInstance().getReference().child("Data").child("Milk").child("Prices").child("Cow");
        mrefbuffalo = FirebaseDatabase.getInstance().getReference().child("Data").child("Milk").child("Prices").child("Buffalo");


        mrefgoatstock = FirebaseDatabase.getInstance().getReference().child("Data").child("Milk").child("Stocks").child("Goat");
        mrefcowstock = FirebaseDatabase.getInstance().getReference().child("Data").child("Milk").child("Stocks").child("Cow");
        mrefbuffalostock = FirebaseDatabase.getInstance().getReference().child("Data").child("Milk").child("Stocks").child("Buffalo");


        mrefcow.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if ((farm_owner_email.equals(ds.child("Email").getValue().toString()))) {

                            cow_milk_price = ds.child("Price").getValue().toString();
                            cow_milk_price_of_specific_farm.setText(cow_milk_price);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        mrefgoat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if ((farm_owner_email.equals(ds.child("Email").getValue().toString()))) {

                            goat_milk_price = ds.child("Price").getValue().toString();
                            goat_milk_price_of_specific_farm.setText(goat_milk_price);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mrefbuffalo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if ((farm_owner_email.equals(ds.child("Email").getValue().toString()))) {

                            buffalo_milk_price = ds.child("Price").getValue().toString();
                            buffalo_milk_price_of_specific_farm.setText(buffalo_milk_price);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        mrefcowstock.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if ((farm_owner_email.equals(ds.child("Email").getValue().toString()))) {

                        cow_milk_stock = ds.child("Stock").getValue().toString();
                        cow_milk_stock_of_specific_farm.setText(cow_milk_stock);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        mrefgoatstock.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if ((farm_owner_email.equals(ds.child("Email").getValue().toString()))) {
                        goat_milk_stock = ds.child("Stock").getValue().toString();
                        goat_milk_stock_of_specific_farm.setText(goat_milk_stock);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mrefbuffalostock.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if ((farm_owner_email.equals(ds.child("Email").getValue().toString()))) {
                        buffalo_milk_stock = ds.child("Stock").getValue().toString();
                        buffalo_milk_stock_of_specific_farm.setText(buffalo_milk_stock);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}