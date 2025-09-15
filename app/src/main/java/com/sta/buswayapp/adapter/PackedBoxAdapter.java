package com.sta.buswayapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.sta.buswayapp.R;

import java.util.ArrayList;

public class PackedBoxAdapter extends RecyclerView.Adapter<PackedBoxAdapter.PackedBoxViewHolder> {
    private Context context;
    private ArrayList<String> packedBoxArrayList;
    private Fragment fragment;

    public PackedBoxAdapter(Context context, ArrayList<String> packedBoxArrayList, Fragment fragment) {
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
    @Override
    public void onBindViewHolder(@NonNull PackedBoxAdapter.PackedBoxViewHolder holder, int position) {
        holder.packedBoxTextView.setText(packedBoxArrayList.get(position));
        holder.boxCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(fragment)
                        .navigate(R.id.reviewPackedItemsFragment, null, options);
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
}
