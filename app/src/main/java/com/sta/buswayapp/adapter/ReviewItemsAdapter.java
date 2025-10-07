package com.sta.buswayapp.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.sta.buswayapp.R;
import com.sta.buswayapp.model.ConstantNames;
import com.sta.buswayapp.model.box.admin.boxItems.BoxedItemsData;
import com.sta.buswayapp.model.item.Item;

import java.util.ArrayList;

public class ReviewItemsAdapter extends RecyclerView.Adapter<ReviewItemsAdapter.ItemsViewHolder> {

    private SharedPreferences sharedPreferences;
    private Fragment fragment;
    private Context context;
    private ArrayList<BoxedItemsData> itemCodeArrayList;


    public ReviewItemsAdapter(Context context, ArrayList<BoxedItemsData> itemCodeArrayList, Fragment fragment) {
        this.fragment = fragment;
        this.context = context;
        this.itemCodeArrayList = itemCodeArrayList;
    }

    @NonNull
    @Override
    public ReviewItemsAdapter.ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        sharedPreferences = fragment.requireContext().getSharedPreferences(ConstantNames.SHARED_PREF_FILE_NAME, MODE_PRIVATE);
        return new ItemsViewHolder(LayoutInflater.from(context).inflate(R.layout.review_item_container, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewItemsAdapter.ItemsViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String code = itemCodeArrayList.get(position).barcode;
        holder.itemCodeTextView.setText(code);
        holder.cardOrderTextView.setText(String.valueOf((holder.getAdapterPosition() + 1)));
    }

    @Override
    public int getItemCount() {
        return itemCodeArrayList.size();
    }

    public static class ItemsViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView scannedItemCardView;
        TextView itemCodeTextView, cardOrderTextView;
        ImageView removeItemIcon;

        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            scannedItemCardView = itemView.findViewById(R.id.itemCodeCardView);
            itemCodeTextView = itemView.findViewById(R.id.itemCodeTextView);
            cardOrderTextView = itemView.findViewById(R.id.cardOrder);

        }
    }

}
