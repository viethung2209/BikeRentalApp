package com.hunglee.bikerentalapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hunglee.bikerentalapp.MainActivity;
import com.hunglee.bikerentalapp.Models.BikeParkingModel;
import com.hunglee.bikerentalapp.R;

import java.util.ArrayList;

public class ParkingAdapter extends RecyclerView.Adapter<ParkingAdapter.viewholder> {

    ArrayList<BikeParkingModel> list;
    Context context;

    public ParkingAdapter(ArrayList<BikeParkingModel> list, Context context) {
        this.list = list;
        this.context = context;

    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_bike_parking, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        final BikeParkingModel model = list.get(position);
        holder.parkingImage.setImageResource(model.getImage());
        holder.parkingName.setText(model.getName());
        holder.bikeNumber.setText(Integer.toString(model.getBikeNumber()));
        holder.parkingDescription.setText(model.getDescription());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("image", model.getImage());
            intent.putExtra("bikeNumber", model.getBikeNumber());
            intent.putExtra("desc", model.getDescription());
            intent.putExtra("parkingName", model.getName());

            SharedPreferences sharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(context);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("parkingName", model.getName());
            editor.apply();
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        ImageView parkingImage;
        TextView parkingName, bikeNumber, parkingDescription;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            parkingImage = itemView.findViewById(R.id.parkingImage);
            parkingName = itemView.findViewById(R.id.parkingName);
            bikeNumber = itemView.findViewById(R.id.bikeNumber);
            parkingDescription = itemView.findViewById(R.id.parkingDescription);
        }
    }
}
