package com.example.dairyservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class Select_farm_for_buying_milk extends AppCompatActivity {

    private RecyclerView recyclerView;
    Farm_for_milk_adapter_class
            adapter; // Create Object of the Adapter class
    DatabaseReference mref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_farm_for_buying_milk);

        mref = FirebaseDatabase.getInstance().getReference().child("Farm_owner");

        recyclerView = findViewById(R.id.recycler);

        // To display the Recycler view linearly
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));


        FirebaseRecyclerOptions<Farm_for_milk> options
                = new FirebaseRecyclerOptions.Builder<Farm_for_milk>()
                .setQuery(mref, Farm_for_milk.class)
                .build();
        // Connecting object of required Adapter class to
        // the Adapter class itself
        adapter = new Farm_for_milk_adapter_class(getApplicationContext(), options);
        // Connecting Adapter class with the Recycler view*/
        recyclerView.setAdapter(adapter);
    }

    @Override protected void onStart()
    {
        super.onStart();
        adapter.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stoping of the activity
    @Override protected void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }
}