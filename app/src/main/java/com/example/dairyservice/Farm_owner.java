package com.example.dairyservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Farm_owner extends AppCompatActivity {


    Button farm_owner_upload_data_activity_btn,manage_order_btn,manage_location_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_owner);

        farm_owner_upload_data_activity_btn = findViewById(R.id.farm_owner_upload_data_activity_btn);
        manage_order_btn = findViewById(R.id.manage_order_btn);
        manage_location_btn = findViewById(R.id.share_location_btn);


        farm_owner_upload_data_activity_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Farm_owner.this, Farm_owner_upload_data.class);
                startActivity(intent);
            }
        });

        manage_order_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Farm_owner.this, Farm_owner_manage_order_selection.class);
                startActivity(intent);
            }
        });

        manage_location_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Farm_owner.this,Farm_owner_share_location_middle.class);
                intent.putExtra("first","share_location");
                startActivity(intent);
            }
        });
    }
}