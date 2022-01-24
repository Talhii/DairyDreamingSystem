package com.example.dairyservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Animal_selection extends AppCompatActivity {


    private Button animal_cow_btn,animal_buffalo_btn,animal_goat_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_selection);

        animal_cow_btn = findViewById(R.id.animal_cow_btn);
        animal_buffalo_btn = findViewById(R.id.animal_buffalo_btn);
        animal_goat_btn = findViewById(R.id.animal_goat_btn);

        animal_cow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Animal_selection.this,Upload_animal_cow.class);
                startActivity(intent);
            }
        });

        animal_buffalo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Animal_selection.this,Upload_animal_buffalo.class);
                startActivity(intent);
            }
        });

        animal_goat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Animal_selection.this,Upload_animal_goat.class);
                startActivity(intent);
            }
        });
    }
}