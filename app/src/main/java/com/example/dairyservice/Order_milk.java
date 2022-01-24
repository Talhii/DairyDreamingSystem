package com.example.dairyservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;
import java.util.UUID;

public class Order_milk extends AppCompatActivity {


    Spinner animal_spinner,delivery_spinner;
    EditText milk_amount_et;
    TextView cow_milk_stock_tv,goat_milk_stock_tv,buffalo_milk_stock_tv;
    Button order_milk_btn;

    String cow_milk_price,goat_milk_price,buffalo_milk_price;
    String cow_milk_stock,goat_milk_stock,buffalo_milk_stock;
    String farm_owner_email;

    private FirebaseDatabase database;
    private DatabaseReference stockRef,orderRef;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_milk);


        animal_spinner = new Spinner(this);
        animal_spinner = findViewById(R.id.animal_spinner);

        delivery_spinner = new Spinner(this);
        delivery_spinner = findViewById(R.id.delivery_spinner);

        cow_milk_stock_tv = findViewById(R.id.cow_milk_stock_tv);
        buffalo_milk_stock_tv = findViewById(R.id.buffalo_milk_stock_tv);
        goat_milk_stock_tv = findViewById(R.id.goat_milk_stock_tv);

        milk_amount_et = findViewById(R.id.milk_amount_et);
        order_milk_btn = findViewById(R.id.order_milk_btn);

        database = FirebaseDatabase.getInstance();
        stockRef =  database.getReference();
        orderRef = database.getReference().child("Orders").child("Milk");


        editor = getSharedPreferences("OrderSaving", MODE_PRIVATE).edit();
        prefs = getSharedPreferences("EmailPassing", MODE_PRIVATE);


        ArrayAdapter<CharSequence> animal_spinner_adapter = ArrayAdapter.createFromResource(this, R.array.animal_spinner_array, android.R.layout.simple_spinner_item);
        animal_spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        animal_spinner.setAdapter(animal_spinner_adapter);


        ArrayAdapter<CharSequence> delivery_spinner_adapter = ArrayAdapter.createFromResource(this, R.array.delivery_spinner_array, android.R.layout.simple_spinner_item);
        delivery_spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        delivery_spinner.setAdapter(delivery_spinner_adapter);


        cow_milk_price = getIntent().getStringExtra("cow_milk_price");
        goat_milk_price = getIntent().getStringExtra("goat_milk_price");
        buffalo_milk_price = getIntent().getStringExtra("buffalo_milk_price");

        cow_milk_stock = getIntent().getStringExtra("cow_milk_stock");
        goat_milk_stock = getIntent().getStringExtra("goat_milk_stock");
        buffalo_milk_stock = getIntent().getStringExtra("buffalo_milk_stock");

        farm_owner_email = getIntent().getStringExtra("farm_owner_email");

        cow_milk_stock_tv.setText(cow_milk_stock);
        goat_milk_stock_tv.setText(goat_milk_stock);
        buffalo_milk_stock_tv.setText(buffalo_milk_stock);


        order_milk_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(milk_amount_et.getText().toString())){
                    milk_amount_et.setError("Milk amount cannot be empty");
                }
                else if(animal_spinner.getSelectedItem().toString().equals("Cow") && Integer.parseInt(milk_amount_et.getText().toString()) > Integer.parseInt(cow_milk_stock)){
                    Toast.makeText(Order_milk.this, "Amount of milk is greater than Stock", Toast.LENGTH_SHORT).show();
                }
                else if(animal_spinner.getSelectedItem().toString().equals("Buffalo") && Integer.parseInt(milk_amount_et.getText().toString()) > Integer.parseInt(buffalo_milk_stock)){
                    Toast.makeText(Order_milk.this, "Amount of milk is greater than Stock", Toast.LENGTH_SHORT).show();
                }
                else if(animal_spinner.getSelectedItem().toString().equals("Goat") && Integer.parseInt(milk_amount_et.getText().toString()) > Integer.parseInt(goat_milk_stock)){
                    Toast.makeText(Order_milk.this, "Amount of milk is greater than Stock", Toast.LENGTH_SHORT).show();
                }
                else{
                    stockRef = database.getReference().child("Data").child("Milk").child("Stocks").child(animal_spinner.getSelectedItem().toString());
                    updateStock();
                }
            }
        });
    }




    private void createOrder(int milk_amount,String animal) {

        final String customer_email = prefs.getString("Customer_email", "No Mail defined");
        final String status = prefs.getString("Status", "No Status defined");


        int milk_price = 0;


        if(animal.equals("Cow") && delivery_spinner.getSelectedItem().toString().equals("Online")){
            milk_price = milk_amount * Integer.parseInt(cow_milk_price);
            milk_price +=  100;      //charges for online delivery
        }
        else if(animal.equals("Cow") && delivery_spinner.getSelectedItem().toString().equals("Offline")){
            milk_price = milk_amount * Integer.parseInt(cow_milk_price);
        }
        else if(animal.equals("Buffalo")  && delivery_spinner.getSelectedItem().toString().equals("Online")){
            milk_price = milk_amount * Integer.parseInt(buffalo_milk_price);
            milk_price += 100;
        }
        else if(animal.equals("Buffalo")  && delivery_spinner.getSelectedItem().toString().equals("Offline")){
            milk_price = milk_amount * Integer.parseInt(buffalo_milk_price);
        }
        else if(animal.equals("Goat") && delivery_spinner.getSelectedItem().toString().equals("Online")){
            milk_price = milk_amount * Integer.parseInt(goat_milk_price);
            milk_price += 100;
        }
        else if(animal.equals("Goat")  && delivery_spinner.getSelectedItem().toString().equals("Offline")){
            milk_price = milk_amount * Integer.parseInt(goat_milk_price);
        }

        final String randomKey = UUID.randomUUID().toString();

        final int min = 100001;
        final int max = 999999;
        final int token_number = new Random().nextInt((max - min) + 1) + min;


        if(delivery_spinner.getSelectedItem().toString().equals("Offline")){
            orderRef.child(randomKey).child("Token_number").setValue(String.valueOf(token_number));
        }
        orderRef.child(randomKey).child("Farm_owner_email").setValue(farm_owner_email);
        orderRef.child(randomKey).child("Customer").setValue(customer_email);
        orderRef.child(randomKey).child("Animal").setValue(animal_spinner.getSelectedItem().toString());
        orderRef.child(randomKey).child("Amount").setValue(milk_amount_et.getText().toString());
        orderRef.child(randomKey).child("Total_Price").setValue(String.valueOf(milk_price));
        orderRef.child(randomKey).child("Delivery").setValue(delivery_spinner.getSelectedItem().toString());
        orderRef.child(randomKey).child("key").setValue(randomKey);




        editor.putString("farm_owner_email", farm_owner_email);
        editor.putString("Animal", animal_spinner.getSelectedItem().toString());
        editor.putString("Amount", milk_amount_et.getText().toString());
        editor.putString("Total_Price", String.valueOf(milk_price));
        editor.putString("deliveryType", delivery_spinner.getSelectedItem().toString());
        editor.putString("orderOf", "milk");
        editor.putString("Token_number", String.valueOf(token_number));

        editor.apply();

        Toast.makeText(this, "Order Created Successfully", Toast.LENGTH_SHORT).show();


        Intent intent = new Intent(Order_milk.this, Customer_milk_order_info.class);
        startActivity(intent);


    }

    private void updateStock() {

        final int[] count = {1};

        stockRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    if (farm_owner_email.equals(ds.child("Email").getValue().toString())){

                        int previous_stock = Integer.parseInt(ds.child("Stock").getValue().toString());
                        int milk_amount =  Integer.parseInt(milk_amount_et.getText().toString());


                        if(previous_stock >= milk_amount && count[0] == 1){
                            int update_stock = previous_stock-milk_amount;
                            DatabaseReference stockReference =  ds.getRef().child("Stock");
                            stockReference.setValue(String.valueOf(update_stock));

                            ++count[0];
                            createOrder(milk_amount,animal_spinner.getSelectedItem().toString());
                        }

                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}