package com.example.dairyservice;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class Get_Complains_adapter_class extends FirebaseRecyclerAdapter<Get_complains,Get_Complains_adapter_class.Complain_view_holder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */


    Context context;

    public Get_Complains_adapter_class(@NonNull Context context, FirebaseRecyclerOptions<Get_complains> options) {
        super(options);
        this.context = context;

    }

    @NonNull
    @Override
    public Get_Complains_adapter_class.Complain_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.get_complains, parent, false);

        return new Get_Complains_adapter_class.Complain_view_holder(view);

    }

    @Override
    protected void onBindViewHolder(@NonNull Complain_view_holder holder, int position, @NonNull Get_complains model) {

        holder.from_customer_tv.setText(model.getFrom_customer());
        holder.about_farm_owner_tv.setText(model.getAbout_farm_owner());
        holder.title_tv.setText(model.getTitle());
        holder.message_tv.setText(model.getMessage());

          holder.itemView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

//                  Intent intent = new Intent(context,Milk_data_of_specific_farm.class);
//                  intent.putExtra("farm_owner_email",model.getEmail());
//                  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                  context.startActivity(intent);
             }
          });
    }

    public class Complain_view_holder extends RecyclerView.ViewHolder {
        TextView from_customer_tv,about_farm_owner_tv,title_tv,message_tv;
        public Complain_view_holder(@NonNull View itemView) {
            super(itemView);


            from_customer_tv = itemView.findViewById(R.id.from_customer_tv);
            about_farm_owner_tv = itemView.findViewById(R.id.about_farm_owner_tv);
            title_tv = itemView.findViewById(R.id.title_tv);
            message_tv = itemView.findViewById(R.id.message_tv);

        }
    }
}
