package com.sta.buswayapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.sta.buswayapp.R;
import com.sta.buswayapp.model.ConstantNames;
import com.sta.buswayapp.model.boxing.item.Item;
import com.sta.buswayapp.model.packing.PackedBoxesData;

import java.util.ArrayList;

public class PackedBoxAdapter extends RecyclerView.Adapter<PackedBoxAdapter.PackedBoxViewHolder> {
    private Context context;
    private ArrayList<PackedBoxesData> packedBoxArrayList;
    private ArrayList<Integer> idsOfReadyBoxes = new ArrayList<>();
    private int reviewedBoxNumber;
    private Fragment fragment;

    public PackedBoxAdapter(Context context, ArrayList<PackedBoxesData> packedBoxArrayList, Fragment fragment) {
        this.context = context;
        this.packedBoxArrayList = packedBoxArrayList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public PackedBoxAdapter.PackedBoxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PackedBoxViewHolder(LayoutInflater.from(context).inflate(R.layout.one_item_box_container, parent, false));
    }

    NavOptions options = new NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setPopExitAnim(R.anim.slide_out_right)
            .build();
    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    public void onBindViewHolder(@NonNull PackedBoxAdapter.PackedBoxViewHolder holder, int position) {

        PackedBoxesData box = packedBoxArrayList.get(position);
        int boxNumber = box.boxNumber;
        holder.packedBoxTextView.setText("Box " + boxNumber);

        holder.boxCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.gray));
        if (box.isReadyForSubmit()){
            holder.boxCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.green));
            if (!idsOfReadyBoxes.contains(box.getBoxId())){
                idsOfReadyBoxes.add(box.getBoxId());
            }
        }
        holder.boxCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putInt(ConstantNames.BOX_ID, packedBoxArrayList.get(position).getBoxId());
                args.putInt(ConstantNames.BOX_NUMBER, packedBoxArrayList.get(position).getBoxNumber());
                args.putBoolean(ConstantNames.PACKING_STAGE, true);
                NavHostFragment.findNavController(fragment)
                        .navigate(R.id.reviewCompletedItemsFragment, args, options);
            }
        });
    }

    @Override
    public int getItemCount() {
        return packedBoxArrayList.size();
    }

    public static class PackedBoxViewHolder extends RecyclerView.ViewHolder {
        TextView packedBoxTextView;
        CardView boxCard;
        public PackedBoxViewHolder(@NonNull View itemView) {
            super(itemView);
            packedBoxTextView = itemView.findViewById(R.id.one_item_text_view);
            boxCard = itemView.findViewById(R.id.one_item_card_view);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setPackedBoxArrayList(ArrayList<PackedBoxesData> packedBoxArrayList) {
        this.packedBoxArrayList = packedBoxArrayList;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setReviewedBoxNumber(int reviewedBoxNumber) {
        this.reviewedBoxNumber = reviewedBoxNumber;
        notifyDataSetChanged();
    }

    public ArrayList<Integer> getIdsOfReadyBoxes() {
        return idsOfReadyBoxes;
    }
}
