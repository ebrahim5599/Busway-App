package com.sta.buswayapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sta.buswayapp.R;
import com.sta.buswayapp.model.MealModel;

import java.util.ArrayList;


public class BoxingAdapter extends RecyclerView.Adapter<BoxingAdapter.BoxViewHolder>{
    Context context;
    ArrayList<MealModel> list;

    public BoxingAdapter(Context context, ArrayList<MealModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public BoxingAdapter.BoxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BoxViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.completed_box_container, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BoxingAdapter.BoxViewHolder holder, int position) {
        holder.boxNumberTextView.setText(list.get(position).idCategory);
        holder.boxStatusTextView.setText(list.get(position).strCategory);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

//    public void setList(Root root) {
//        this.root = root;
//        notifyDataSetChanged();
//
//    }
    public static class BoxViewHolder extends RecyclerView.ViewHolder {
        TextView boxNumberTextView, boxStatusTextView;

        public BoxViewHolder(@NonNull View itemView) {
            super(itemView);
            boxNumberTextView = itemView.findViewById(R.id.boxNumber);
            boxStatusTextView = itemView.findViewById(R.id.boxStatus);
        }
    }
}
