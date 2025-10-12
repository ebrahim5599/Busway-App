package com.sta.buswayapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.sta.buswayapp.R;
import com.sta.buswayapp.model.box.worker.modifyBox.ModifyBoxData;

import java.util.ArrayList;

public class ModifiedBoxAdapter extends RecyclerView.Adapter<ModifiedBoxAdapter.BoxViewHolder> {
    private final Context context;
    private final ArrayList<ModifyBoxData> modifyBoxData;
    private Fragment fragment;

    public ModifiedBoxAdapter(Context context, ArrayList<ModifyBoxData> modifyBoxData, Fragment fragment) {
        this.context = context;
        this.modifyBoxData = modifyBoxData;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ModifiedBoxAdapter.BoxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ModifiedBoxAdapter.BoxViewHolder(LayoutInflater.from(context).inflate(R.layout.completed_box_container, parent, false));
    }

    NavOptions options = new NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setPopExitAnim(R.anim.slide_out_right)
            .build();
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ModifiedBoxAdapter.BoxViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.boxNumberTextView.setText("Box " + modifyBoxData.get(position).getId());
        holder.boxLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putInt("boxId", modifyBoxData.get(position).getId());
                NavHostFragment.findNavController(fragment)
                        .navigate(R.id.boxingWorkerSideFragment, args, options);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modifyBoxData.size();
    }

    public static class BoxViewHolder extends RecyclerView.ViewHolder {
        TextView boxNumberTextView;
        LinearLayout boxLinear;
        public BoxViewHolder(@NonNull View itemView) {
            super(itemView);
            boxNumberTextView = itemView.findViewById(R.id.boxNumber);
            boxLinear = itemView.findViewById(R.id.linear);
        }
    }
}
