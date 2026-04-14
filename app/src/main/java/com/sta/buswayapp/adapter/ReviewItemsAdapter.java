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
import com.sta.buswayapp.model.boxing.box.admin.boxItems.BoxedItemsData;
import com.sta.buswayapp.model.boxing.item.BaseItem;
import com.sta.buswayapp.model.boxing.item.modifyItem.ModifyItemData;

import java.util.ArrayList;

public class ReviewItemsAdapter extends RecyclerView.Adapter<ReviewItemsAdapter.ItemsViewHolder> {

    private SharedPreferences sharedPreferences;
    private Fragment fragment;
    private Context context;
    private ArrayList<BoxedItemsData> itemCodeArrayList;
    private ArrayList<ModifyItemData> wrongItemArrayList;
    private boolean editItemsFlag = false;
    boolean hasError = false;


    public ReviewItemsAdapter(Context context, ArrayList<BoxedItemsData> itemCodeArrayList, Fragment fragment) {
        this.fragment = fragment;
        this.context = context;
        this.itemCodeArrayList = itemCodeArrayList;
    }

    public ReviewItemsAdapter(Context context , ArrayList<BoxedItemsData> itemCodeArrayList, Fragment fragment, boolean editItemsFlag) {
        this.fragment = fragment;
        this.context = context;
        this.itemCodeArrayList = itemCodeArrayList;
        this.editItemsFlag = editItemsFlag;
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
        String type = itemCodeArrayList.get(position).getType();
        holder.itemCodeTextView.setText(code);
        holder.itemTypeTextView.setText(type);
        holder.cardOrderTextView.setText(String.valueOf((holder.getAdapterPosition() + 1)));

        if (hasError) {
            for (ModifyItemData wrong : wrongItemArrayList) {
                if (wrong.barcode.equals(code)) {
                    holder.scannedItemCardView.setStrokeColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.error_bg));
                    holder.scannedItemCardView.setStrokeWidth(4); // thickness of border
                    holder.itemTypeTextView.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.error_bg));
                    holder.itemCodeTextView.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.error_bg));
                    break;
                }
            }
        }

        if (editItemsFlag){
            holder.removeItemIcon.setVisibility(View.VISIBLE);
            holder.removeItemIcon.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onClick(View v) {
                    itemCodeArrayList.remove(position);
                    notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return itemCodeArrayList.size();
    }

    public static class ItemsViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView scannedItemCardView;
        TextView itemCodeTextView, cardOrderTextView, itemTypeTextView;
        ImageView removeItemIcon;

        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            scannedItemCardView = itemView.findViewById(R.id.itemCodeCardView);
            itemCodeTextView = itemView.findViewById(R.id.itemCodeTextView);
            itemTypeTextView = itemView.findViewById(R.id.itemTypeTextView);
            cardOrderTextView = itemView.findViewById(R.id.cardOrder);
            removeItemIcon = itemView.findViewById(R.id.removeItemIcon);

        }
    }

    public void setWrongItemArrayList(ArrayList<ModifyItemData> wrongItemArrayList) {
        this.wrongItemArrayList =  wrongItemArrayList;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

}
