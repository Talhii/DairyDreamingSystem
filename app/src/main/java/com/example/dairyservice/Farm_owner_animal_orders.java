package com.example.dairyservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Farm_owner_animal_orders extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SharedPreferences prefs;
    Farm_owner_animal_order_adapter_class adapter;
    private DatabaseReference mref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_owner_animal_orders);


        mref = FirebaseDatabase.getInstance().getReference().child("Orders").child("Animal");

        recyclerView = findViewById(R.id.recycler1);


        prefs = getSharedPreferences("EmailPassing", MODE_PRIVATE);
        final String farm_owner_email = prefs.getString("farm_owner_email", "No Mail defined");
        // To display the Recycler view linearly
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));


        FirebaseRecyclerOptions<Farm_animal_orders> options
                = new FirebaseRecyclerOptions.Builder<Farm_animal_orders>()
                .setQuery(mref, Farm_animal_orders.class)
                .build();
        // Connecting object of required Adapter class to
        // the Adapter class itself
        adapter = new Farm_owner_animal_order_adapter_class(options,farm_owner_email,getApplicationContext());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();

        searchView.setInputType(InputType.TYPE_CLASS_NUMBER);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.printTokenData(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
             //   adapter.getFilter().filter(newText.toString());
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}