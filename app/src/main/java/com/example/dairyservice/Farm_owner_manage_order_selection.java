package com.example.dairyservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Farm_owner_manage_order_selection extends AppCompatActivity {


    Button milk_orders_activity_btn,animal_orders_activity_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_owner_manage_order_selection);

        milk_orders_activity_btn = findViewById(R.id.milk_orders_activity_btn);
        animal_orders_activity_btn = findViewById(R.id.animal_orders_activity_btn);

        milk_orders_activity_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Farm_owner_manage_order_selection.this,Farm_owner_manage_orders.class);
                startActivity(intent);
            }
        });

        animal_orders_activity_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Farm_owner_manage_order_selection.this,Farm_owner_animal_orders.class);
                startActivity(intent);
            }
        });

    }
}