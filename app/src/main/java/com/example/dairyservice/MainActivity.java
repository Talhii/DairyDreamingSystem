package com.example.dairyservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button customer_btn, whole_seller_btn, farm_owner_btn, food_authority_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customer_btn =  findViewById(R.id.customer_btn);
        whole_seller_btn = findViewById(R.id.whole_seller_btn);
        farm_owner_btn = findViewById(R.id.farm_owner_btn);
        food_authority_btn = findViewById(R.id.food_authority_btn);

        customer_btn.setOnClickListener(this);
        whole_seller_btn.setOnClickListener(this);
        farm_owner_btn.setOnClickListener(this);
        food_authority_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.customer_btn:
                Intent customer = new Intent(this, Customer_login.class);
                startActivity(customer);
                break;
            case R.id.whole_seller_btn:
                Intent whole_seller = new Intent(this, Whole_saler_login.class);
                startActivity(whole_seller);
                break;
            case R.id.farm_owner_btn:
                Intent farm_owner = new Intent(this, Farm_owner_login.class);
                startActivity(farm_owner);
                break;
            case R.id.food_authority_btn:
                Intent food_authority = new Intent(this, Food_authority_login.class);
                startActivity(food_authority);
        }
    }
}
