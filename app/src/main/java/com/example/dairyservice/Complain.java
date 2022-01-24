package com.example.dairyservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class Complain extends AppCompatActivity {

    EditText complain_title_et,farm_owner_email_et,complain_message_et;
    Button complain_btn;

    private Spinner spinner;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);


        complain_title_et = findViewById(R.id.complain_title_et);
        farm_owner_email_et = findViewById(R.id.farm_owner_email_et);
        complain_message_et = findViewById(R.id.complain_message_et);

        complain_btn = findViewById(R.id.complain_btn);


        spinner = new Spinner(this);
        spinner = findViewById(R.id.province_spinner);



        prefs = getSharedPreferences("EmailPassing", MODE_PRIVATE);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.province_spinner_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        complain_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String randomKey = UUID.randomUUID().toString();
                final String customer_email = prefs.getString("Customer_email", "No Mail defined");
                myRef = database.getReference().child("Complains").child(spinner.getSelectedItem().toString());


                if (TextUtils.isEmpty(farm_owner_email_et.getText().toString())) {
                    farm_owner_email_et.setError("Farm Owner's mail cannot be empty");
                    return;
                }
                else if (TextUtils.isEmpty(complain_title_et.getText().toString())) {
                    complain_title_et.setError("Title of complain cannot be empty");
                    return;
                }
                else if (TextUtils.isEmpty(complain_message_et.getText().toString())) {
                    complain_message_et.setError("Message cannot be empty");
                    return;
                }
                else{

                    myRef.child(randomKey).child("From_customer").setValue(customer_email);
                    myRef.child(randomKey).child("About_farm_owner").setValue(farm_owner_email_et.getText().toString());
                    myRef.child(randomKey).child("Food_authority").setValue(spinner.getSelectedItem().toString());
                    myRef.child(randomKey).child("Title").setValue(complain_title_et.getText().toString());
                    myRef.child(randomKey).child("Message").setValue(complain_message_et.getText().toString());

                    Toast.makeText(Complain.this, "Your Complain is Forwarded", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}