package com.example.dairyservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Customer extends AppCompatActivity {


    private SharedPreferences prefs;
    private Button buy_milk_btn,buy_animal_btn,complain_activity_btn,last_order_info_activity_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        buy_milk_btn = findViewById(R.id.buy_milk_btn);
        buy_animal_btn = findViewById(R.id.buy_animal_btn);
        complain_activity_btn = findViewById(R.id.complain_activity_btn);
        last_order_info_activity_btn = findViewById(R.id.last_order_info_activity_btn);



        prefs = getSharedPreferences("OrderSaving", MODE_PRIVATE);

        buy_animal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Customer.this,Animal_data_of_selected_farm.class);
                startActivity(intent);
            }
        });

        buy_milk_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Customer.this,Select_farm_for_buying_milk.class);
                startActivity(intent);
            }
        });

        complain_activity_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Customer.this,Complain.class);
                startActivity(intent);
            }
        });


        last_order_info_activity_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String lastOrder = prefs.getString("orderOf", "No Mail defined");

                if(lastOrder.equals("milk")){
                    Intent intent = new Intent(Customer.this, Customer_milk_order_info.class);
                    startActivity(intent);
                }
                else if(lastOrder.equals("animal")){
                    Intent intent = new Intent(Customer.this, Customer_animal_order_info.class);
                    startActivity(intent);
                }

            }
        });
    }
}
