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

import com.hunglee.bikerentalapp.Models.bikeparkings.BikeParking;
import com.hunglee.bikerentalapp.R;
import com.hunglee.bikerentalapp.activities.main.MainActivity;

import java.util.List;

public class ParkingAdapter extends RecyclerView.Adapter<ParkingAdapter.viewholder> {

    List<BikeParking> list;
    Context context;

    public ParkingAdapter(List<BikeParking> list, Context context) {
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
        final BikeParking model = list.get(position);
        holder.parkingImage.setImageResource(model.image);
        holder.parkingName.setText(model.name);
        holder.bikeNumber.setText(Integer.toString(model.bikeNumber));
        holder.parkingDescription.setText(model.description);

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("image", model.image);
            intent.putExtra("bikeNumber", model.bikeNumber);
            intent.putExtra("desc", model.description);
            intent.putExtra("parkingName", model.name);
            SharedPreferences sharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(context);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong("parkingId", model.parkingId);
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
