package com.sta.buswayapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.sta.buswayapp.R;
import com.sta.buswayapp.model.BoxStatusModel;

import java.util.ArrayList;

public class CompletedBoxAdapter extends RecyclerView.Adapter<CompletedBoxAdapter.BoxViewHolder> {

    private Context context;
    private ArrayList<BoxStatusModel> boxStatusModelArrayList;
    private Fragment fragment;

    public CompletedBoxAdapter(Context context, ArrayList<BoxStatusModel> boxStatusModelArrayList, Fragment fragment) {
        this.context = context;
        this.boxStatusModelArrayList = boxStatusModelArrayList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public CompletedBoxAdapter.BoxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BoxViewHolder(LayoutInflater.from(context).inflate(R.layout.completed_box_container, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedBoxAdapter.BoxViewHolder holder, int position) {
        holder.boxNumberTextView.setText(boxStatusModelArrayList.get(position).getBoxNumber());
        holder.boxStatusTextView.setText(boxStatusModelArrayList.get(position).getBoxStatus());
    }

    @Override
    public int getItemCount() {
        return boxStatusModelArrayList.size();
    }

    public static class BoxViewHolder extends RecyclerView.ViewHolder {
        TextView boxNumberTextView, boxStatusTextView;
        public BoxViewHolder(@NonNull View itemView) {
            super(itemView);
            boxNumberTextView = itemView.findViewById(R.id.boxNumber);
            boxStatusTextView = itemView.findViewById(R.id.boxStatus);
        }
    }
}
