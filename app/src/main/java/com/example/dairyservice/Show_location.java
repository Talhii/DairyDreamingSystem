package com.example.dairyservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Show_location extends AppCompatActivity {

    Button check_location;
    private DatabaseReference mDatabaseRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_location);

        check_location = findViewById(R.id.check_location);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Farm_owner");
        final String farm_owner_email = getIntent().getStringExtra("farm_owner_email");

        check_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {

                            if ((farm_owner_email.equals(ds.child("Email").getValue().toString()))) {

                                double latitude = Double.parseDouble(ds.child("latitude").getValue().toString());
                                double longitude = Double.parseDouble(ds.child("longitude").getValue().toString());

                                Intent intent = new Intent(Show_location.this,MapsActivity.class);
                                intent.putExtra("latitude",latitude);
                                intent.putExtra("longitude",longitude);
                                startActivity(intent);

                            }
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