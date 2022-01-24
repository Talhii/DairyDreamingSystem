package com.example.dairyservice;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.UUID;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<GetData> getData;

    public ImageAdapter(Context context, List<GetData> get) {
        mContext = context;
        getData = get;
    }
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {

        final GetData getDataObject = getData.get(position);


        holder.animal_price.setText(getDataObject.getPrice());
        holder.animal_age.setText(getDataObject.getAge());
        holder.animal_name.setText(getDataObject.getAnimal());
        holder.farm_owner.setText(getDataObject.getEmail());

        Glide.with(mContext)
                .load(getDataObject.getImageURL())
                .into(holder.imageView);

        holder.buy_animal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext,Order_animal.class);
                intent.putExtra("age",getDataObject.getAge());
                intent.putExtra("price",getDataObject.getPrice());
                intent.putExtra("farm_owner_email",getDataObject.getEmail());
                intent.putExtra("animal_name",getDataObject.getAnimal());
                intent.putExtra("image_url",getDataObject.getImageURL());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return getData.size();
    }
    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView animal_price,animal_age,animal_name,farm_owner;
        public Button buy_animal;
        public ImageView imageView;
        public ImageViewHolder(View itemView) {
            super(itemView);

            animal_price = itemView.findViewById(R.id.animal_price);
            animal_age = itemView.findViewById(R.id.animal_age);
            animal_name = itemView.findViewById(R.id.animal_name);
            farm_owner = itemView.findViewById(R.id.farm_owner);
            buy_animal = itemView.findViewById(R.id.buy_animal);

            imageView = itemView.findViewById(R.id.image_view);
        }
    }
}

