package com.example.dairyservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Milk_selection extends AppCompatActivity {

    Button milk_update_price_btn,milk_update_stcok_btn,show_previous_data_activity_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milk_selection);

        milk_update_price_btn = findViewById(R.id.milk_update_price_activity_btn);
        milk_update_stcok_btn = findViewById(R.id.milk_update_stock_activity_btn);
        show_previous_data_activity_btn = findViewById(R.id.show_previous_data_activity_btn);

        milk_update_price_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Milk_selection.this,Milk_prices.class);
                startActivity(intent);
            }
        });

        milk_update_stcok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Milk_selection.this,Milk_stock.class);
                startActivity(intent);
            }
        });


        show_previous_data_activity_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Milk_selection.this,Show_previous_data.class);
                startActivity(intent);
            }
        });

    }
}