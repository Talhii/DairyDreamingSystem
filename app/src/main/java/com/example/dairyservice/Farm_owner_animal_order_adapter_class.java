package com.example.dairyservice;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Farm_owner_animal_order_adapter_class  extends FirebaseRecyclerAdapter<Farm_animal_orders, Farm_owner_animal_order_adapter_class.Farm_view_holder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     * @param farm_owner_email
     */
    String farm_owner_email;
    Context context;
    private ArrayList<Token> token = new ArrayList<>();
    public Farm_owner_animal_order_adapter_class(@NonNull FirebaseRecyclerOptions<Farm_animal_orders> options, String farm_owner_email, Context applicationContext) {
        super(options);
        this.farm_owner_email = farm_owner_email;
        this.context = applicationContext;


    }

    @Override
    protected void onBindViewHolder(@NonNull Farm_owner_animal_order_adapter_class.Farm_view_holder holder, int position, @NonNull final Farm_animal_orders model) {

        if(model.getFarm_owner_email().equals(farm_owner_email)){

            if(model.getDelivery().equals("Offline")){
                token.add(new Token(model.getToken_number(),model.getCustomer(),model.getPrice()));
            }

            holder.customer_email.setText(model.getCustomer());
            holder.animal.setText(model.getAnimal());
            holder.amount.setText(model.getPrice());
            holder.age.setText(model.getAge());
            holder.delivery.setText(model.getDelivery());


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(model.getDelivery().equals("Online") && model.getLongitude() != null && model.getLatitude() != null){
                        double latitude = Double.parseDouble(model.getLatitude());
                        double longitude = Double.parseDouble(model.getLongitude());

                        Intent intent = new Intent(context,MapsActivity.class);
                        intent.putExtra("latitude",latitude);
                        intent.putExtra("longitude",longitude);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                    if(model.getDelivery().equals("Offline")){
                        Toast.makeText(context, "Token Number: "+ model.getToken_number(), Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

    }


    @NonNull
    @Override
    public Farm_owner_animal_order_adapter_class.Farm_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.farm_animal_orders, parent, false);

        return new Farm_owner_animal_order_adapter_class.Farm_view_holder(view);


    }

    public void printTokenData(String query){
        String value = "false";
        for(int i = 0;i<token.size();i++){
            if(token.get(i).getToken_number().equals(query)){
                value = "true";
                Toast.makeText(context,"Customer Email: "+ token.get(i).getCustomer_name() +"\n"+"Price: "+ token.get(i).getPrice()  +"\n"+ "Token Number: "+ token.get(i).getToken_number() , Toast.LENGTH_LONG).show();
            }
        }

        if(value.equals("false")){
            Toast.makeText(context, "No Order exists with this Token Number", Toast.LENGTH_SHORT).show();
        }
    }

    class Farm_view_holder extends RecyclerView.ViewHolder {
        TextView customer_email,animal,amount,age,delivery;
        public Farm_view_holder(@NonNull View itemView)
        {

            super(itemView);

            customer_email = itemView.findViewById(R.id.customer_email);
            animal = itemView.findViewById(R.id.animal);
            amount = itemView.findViewById(R.id.amount);
            age = itemView.findViewById(R.id.age);
            delivery = itemView.findViewById(R.id.delivery);

        }
    }
}
