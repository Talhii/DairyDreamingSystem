package com.example.dairyservice;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


public class Farm_for_milk_adapter_class extends FirebaseRecyclerAdapter<Farm_for_milk,Farm_for_milk_adapter_class.Farm_view_holder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */


    Context context;

    public Farm_for_milk_adapter_class(@NonNull Context context,FirebaseRecyclerOptions<Farm_for_milk> options) {
        super(options);
        this.context = context;

    }

    @Override
    protected void onBindViewHolder(@NonNull final Farm_view_holder holder, final int position, @NonNull final Farm_for_milk model) {
        holder.email.setText(model.getEmail());
        holder.name.setText(model.getName());
        holder.location.setText(model.getAddress());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,Milk_data_of_specific_farm.class);
                intent.putExtra("farm_owner_email",model.getEmail());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public Farm_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.farm_for_milk, parent, false);

        return new Farm_for_milk_adapter_class.Farm_view_holder(view);

    }

    public class Farm_view_holder extends RecyclerView.ViewHolder {
        TextView email,name,location;
        public Farm_view_holder(@NonNull View itemView) {
            super(itemView);


            email = itemView.findViewById(R.id.farm_owner_email_for_milk_buying);
            name = itemView.findViewById(R.id.farm_owner_name_for_milk_buying);
            location = itemView.findViewById(R.id.farm_owner_location_for_milk_buying);

        }
    }
}
