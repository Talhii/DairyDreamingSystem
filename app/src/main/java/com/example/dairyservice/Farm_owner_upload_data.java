package com.example.dairyservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Farm_owner_upload_data extends AppCompatActivity {

    private ImageButton animal_button,milk_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_owner_upload_data);

        animal_button = findViewById(R.id.animal_button);
        milk_button = findViewById(R.id.milk_button);

        animal_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Farm_owner_upload_data.this, Animal_selection.class);
                startActivity(intent);
            }
        });


        milk_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Farm_owner_upload_data.this,Milk_selection.class);
                startActivity(intent);
            }
        });
    }
}