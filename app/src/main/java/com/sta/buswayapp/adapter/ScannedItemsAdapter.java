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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.sta.buswayapp.R;
import com.sta.buswayapp.model.ConstantNames;
import com.sta.buswayapp.model.item.ItemCode;
import com.sta.buswayapp.model.project.ProjectData;

import java.util.ArrayList;

public class ScannedItemsAdapter extends RecyclerView.Adapter<ScannedItemsAdapter.ItemsViewHolder> {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Fragment fragment;
    private String processName;
    private Context context;
    private ArrayList<ItemCode> itemCodeArrayList;


    public ScannedItemsAdapter(Context context, ArrayList<ItemCode> itemCodeArrayList, Fragment fragment) {
        this.fragment = fragment;
        this.context = context;
        this.itemCodeArrayList = itemCodeArrayList;
    }

    @NonNull
    @Override
    public ScannedItemsAdapter.ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        sharedPreferences = fragment.getContext().getSharedPreferences(ConstantNames.SHARED_PREF_FILE_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        processName = sharedPreferences.getString(ConstantNames.PROCESS, "def");
        return new ItemsViewHolder(LayoutInflater.from(context).inflate(R.layout.scanned_item_container, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ScannedItemsAdapter.ItemsViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.itemCodeTextView.setText(itemCodeArrayList.get(position).getCode());
        holder.cardOrderTextView.setText(String.valueOf((holder.getAdapterPosition() + 1)));
        holder.removeItemIcon.setOnClickListener(new View.OnClickListener() {
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
        CardView scannedItemCardView;
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
}
