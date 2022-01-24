package com.example.dairyservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Customer_animal_order_info extends AppCompatActivity {

    TextView age_tv,total_price_tv,animal_tv,delivery_type_tv,token_number_tv;
    private SharedPreferences prefs;
    Button share_location_btn,show_location_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_animal_order_info);



        age_tv = findViewById(R.id.age_tv);
        total_price_tv = findViewById(R.id.total_price_tv);
        animal_tv = findViewById(R.id.animal_tv);
        delivery_type_tv = findViewById(R.id.delivery_type_tv);
        token_number_tv = findViewById(R.id.token_number_tv);

        show_location_btn = findViewById(R.id.show_location_btn);
        share_location_btn = findViewById(R.id.share_location_btn);


        prefs = getSharedPreferences("OrderSaving", MODE_PRIVATE);


        final String farm_owner_email = prefs.getString("farm_owner_email", "Not defined");
        final String Animal = prefs.getString("Animal", "Not defined");
        final String Age = prefs.getString("Age", "Not defined");
        final String Price = prefs.getString("Price", "Not defined");
        final String deliveryType = prefs.getString("deliveryType", "Not defined");
        final String orderOf = prefs.getString("orderOf", "Not defined");
        final String Token_number = prefs.getString("Token_number", "Not defined");


        age_tv.setText("Age: "+Age+" months");
        total_price_tv.setText("Price: "+Price);
        animal_tv.setText("Animal: "+Animal);
        delivery_type_tv.setText("Delivery: "+deliveryType);

        if(deliveryType.equals("Offline")){
            token_number_tv.setText("Token Number: " +Token_number);
        }
        else if(deliveryType.equals("Online")){
            token_number_tv.setText("");
        }

        share_location_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(deliveryType.equals("Online")){

                    Intent intent = new Intent(Customer_animal_order_info.this,Share_location.class);
                    intent.putExtra("orderOf",orderOf);
                    startActivity(intent);

                }
            }
        });


        show_location_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(deliveryType.equals("Offline")) {

                    Intent intent = new Intent(Customer_animal_order_info.this,Show_location.class);
                    intent.putExtra("farm_owner_email",farm_owner_email);

                    startActivity(intent);
                }
            }
        });
    }
}