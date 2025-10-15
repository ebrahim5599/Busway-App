package com.sta.buswayapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.sta.buswayapp.R;
import com.sta.buswayapp.model.ConstantNames;
import com.sta.buswayapp.model.boxing.box.admin.completedBox.CompletedBoxData;

import java.util.ArrayList;

public class CompletedBoxAdapter extends RecyclerView.Adapter<CompletedBoxAdapter.BoxViewHolder> {

    private final Context context;
    private final ArrayList<CompletedBoxData> boxStatusModelArrayList;
    private final Fragment fragment;
    private final ArrayList<Integer> selectedBoxIds = new ArrayList<>();

    NavOptions options = new NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setPopExitAnim(R.anim.slide_out_right)
            .build();

    public CompletedBoxAdapter(Context context, ArrayList<CompletedBoxData> boxStatusModelArrayList, Fragment fragment) {
        this.context = context;
        this.boxStatusModelArrayList = boxStatusModelArrayList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public CompletedBoxAdapter.BoxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CompletedBoxAdapter.BoxViewHolder(LayoutInflater.from(context).inflate(R.layout.completed_boxes_with_checkbox_container, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CompletedBoxAdapter.BoxViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CompletedBoxData box = boxStatusModelArrayList.get(position);
        holder.boxNumberCheckbox.setText("Box " + box.getBoxNumber());
        holder.boxStatusTextView.setText(box.getStatus());
        holder.boxCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putInt(ConstantNames.BOX_ID, box.getBoxId());
                args.putInt(ConstantNames.BOX_NUMBER, box.getBoxNumber());
                NavHostFragment.findNavController(fragment)
                        .navigate(R.id.reviewCompletedItemsFragment, args, options);
            }
        });

        holder.boxNumberCheckbox.setOnCheckedChangeListener(null);
        boolean isSelected = selectedBoxIds.contains(box.getBoxId());
        holder.boxNumberCheckbox.setChecked(isSelected);

        holder.boxNumberCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (!selectedBoxIds.contains(box.getBoxId())) {
                    selectedBoxIds.add(box.getBoxId());
                }
            } else {
                selectedBoxIds.remove(Integer.valueOf(box.getBoxId()));
            }
        });

        holder.boxCardView.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putInt(ConstantNames.BOX_ID, box.getBoxId());
            args.putInt(ConstantNames.BOX_NUMBER, box.getBoxNumber());
            NavHostFragment.findNavController(fragment)
                    .navigate(R.id.reviewCompletedItemsFragment, args, options);
        });

        if (!holder.boxStatusTextView.getText().equals("Completed"))
            holder.boxNumberCheckbox.setEnabled(false);
          else
            holder.boxNumberCheckbox.setEnabled(true);
    }

    public ArrayList<Integer> getSelectedBoxIds() {
        return new ArrayList<>(selectedBoxIds);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void clearSelections() {
        selectedBoxIds.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return boxStatusModelArrayList.size();
    }

    public static class BoxViewHolder extends RecyclerView.ViewHolder {
        TextView boxStatusTextView;
        CardView boxCardView;
        CheckBox boxNumberCheckbox;
        public BoxViewHolder(@NonNull View itemView) {
            super(itemView);
            boxNumberCheckbox = itemView.findViewById(R.id.boxNumber);
            boxStatusTextView = itemView.findViewById(R.id.boxStatus);
            boxCardView = itemView.findViewById(R.id.boxCardView);
        }
    }
}
