package com.hunglee.bikerentalapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hunglee.bikerentalapp.Models.bikes.Bike;
import com.hunglee.bikerentalapp.R;
import com.hunglee.bikerentalapp.activities.DetailActivity;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.viewholder> {

    List<Bike> list;
    Context context;

    public MainAdapter(List<Bike> list, Context context) {
        this.list = list;
        this.context = context;


    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_main_bike, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        final Bike model = list.get(position);
        holder.bikeImage.setImageResource(model.image);
        holder.mainName.setText(model.name);
        holder.price.setText(String.valueOf(model.price));
        holder.description.setText(model.description);

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("image", model.image);
            intent.putExtra("price", model.price);
            intent.putExtra("desc", model.description);
            intent.putExtra("name", model.name);
            intent.putExtra("code", model.code);
            intent.putExtra("parkingId", model.parkingId);
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        ImageView bikeImage;
        TextView mainName, price, description;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            bikeImage = itemView.findViewById(R.id.imageView);
            mainName = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.mainPrice);
            description = itemView.findViewById(R.id.description);
        }
    }
}
