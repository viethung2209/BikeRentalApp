package com.hunglee.bikerentalapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hunglee.bikerentalapp.R;
import com.hunglee.bikerentalapp.ultis.Constant;
import com.hunglee.bikerentalapp.Models.transaction.Transaction;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.viewHolder> {

    List<Transaction> list;
    Context context;

    public TransactionAdapter(List<Transaction> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_activity, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        final Transaction model = list.get(position);
        holder.transactionImage.setImageResource(R.drawable.ic_baseline_monetization_on_24);
        holder.name.setText(model.name);
        holder.description.setText(model.description);
        if (model.type == Constant.DEPOSIT) {
            String trx = "-" + model.value;
            holder.value.setText(trx);
            int color = holder.value.getResources().getColor(R.color.red);
            holder.value.setTextColor(color);
        } else {
            String trx = "+" + model.value;
            holder.value.setText(trx);
            int color = holder.value.getResources().getColor(R.color.green);
            holder.value.setTextColor(color);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView transactionImage;
        TextView name, description, value;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            transactionImage = itemView.findViewById(R.id.transactionImage);
            name = itemView.findViewById(R.id.transactionName);
            description = itemView.findViewById(R.id.transactionDescription);
            value = itemView.findViewById(R.id.transactionValue);
        }
    }


}


