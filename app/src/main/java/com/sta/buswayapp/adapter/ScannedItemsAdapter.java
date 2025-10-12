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
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.sta.buswayapp.R;
import com.sta.buswayapp.model.ConstantNames;
import com.sta.buswayapp.model.box.admin.boxItems.BoxedItemsData;
import com.sta.buswayapp.model.item.Item;
import java.util.ArrayList;

public class ScannedItemsAdapter extends RecyclerView.Adapter<ScannedItemsAdapter.ItemsViewHolder> {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Fragment fragment;
    private String processName;
    private Context context;
    private ArrayList<String> itemCodeArrayList;
    private ArrayList<BoxedItemsData> returnedItemList;

    private ArrayList<Item> wrongItemArrayList;
    boolean hasError = false;


    public ScannedItemsAdapter(Context context, ArrayList<String> itemCodeArrayList, Fragment fragment) {
        this.fragment = fragment;
        this.context = context;
        this.itemCodeArrayList = itemCodeArrayList;
    }

    @NonNull
    @Override
    public ScannedItemsAdapter.ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        sharedPreferences = fragment.requireContext().getSharedPreferences(ConstantNames.SHARED_PREF_FILE_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        processName = sharedPreferences.getString(ConstantNames.PROCESS, "def");
        return new ItemsViewHolder(LayoutInflater.from(context).inflate(R.layout.scanned_item_container, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ScannedItemsAdapter.ItemsViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String code = itemCodeArrayList.get(position);
        holder.itemCodeTextView.setText(code);
        holder.cardOrderTextView.setText(String.valueOf((holder.getAdapterPosition() + 1)));

        holder.scannedItemCardView.setStrokeColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.gray));
        holder.scannedItemCardView.setStrokeWidth(2);
        holder.itemCodeTextView.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.black));

        if (hasError) {
            for (Item wrong : wrongItemArrayList) {
                if (wrong.barcode.equals(code)) {
                    holder.scannedItemCardView.setStrokeColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.error_bg));
                    holder.scannedItemCardView.setStrokeWidth(4); // thickness of border
                    holder.itemCodeTextView.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.error_bg));
                    break;
                }
            }
        }
        holder.removeItemIcon.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                itemCodeArrayList.remove(position);
                notifyDataSetChanged();
            }
        });
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
            removeItemIcon = itemView.findViewById(R.id.removeItemIcon);
            cardOrderTextView = itemView.findViewById(R.id.cardOrder);

        }
    }

    public void setWrongItemArrayList(ArrayList<Item> wrongItemArrayList) {
        this.wrongItemArrayList = wrongItemArrayList;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }
}
