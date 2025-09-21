package com.sta.buswayapp.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.sta.buswayapp.model.ConstantNames;

import java.util.ArrayList;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Fragment fragment;
    private String processName;
    private Context context;
    private ArrayList<String> projectSalesOrder;

    public ProjectAdapter(Context context, ArrayList<String> projectSalesOrder, Fragment fragment) {
        this.fragment = fragment;
        this.context = context;
        this.projectSalesOrder = projectSalesOrder;
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        sharedPreferences = fragment.getContext().getSharedPreferences(ConstantNames.SHARED_PREF_FILE_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        processName = sharedPreferences.getString(ConstantNames.PROCESS, "def");
        return new ProjectViewHolder(LayoutInflater.from(context).inflate(R.layout.one_item_container, parent, false));
    }

    NavOptions options = new NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setPopExitAnim(R.anim.slide_out_right)
            .build();

    @Override
    public void onBindViewHolder(@NonNull ProjectAdapter.ProjectViewHolder holder, int position) {
        holder.projectSalesOrderTextView.setText(projectSalesOrder.get(position));
        holder.projectCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (processName.equals(ConstantNames.BOXING)) {
                    if (sharedPreferences.getString(ConstantNames.TYPE_OF_USER, "def").equals(ConstantNames.WORKER))
                        NavHostFragment.findNavController(fragment)
                                .navigate(R.id.newOrEditBoxFragment, null, options);
                    else
                        NavHostFragment.findNavController(fragment)
                                .navigate(R.id.reviewCompletedBoxesFragment, null, options);

                } else if (processName.equals(ConstantNames.PACKING_CHECK)) {
                    NavHostFragment.findNavController(fragment)
                            .navigate(R.id.packingSupervisorSideFragment, null, options);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return projectSalesOrder.size();
    }


    public static class ProjectViewHolder extends RecyclerView.ViewHolder {

        TextView projectSalesOrderTextView;
        CardView projectCardView;
        public ProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            projectSalesOrderTextView = itemView.findViewById(R.id.one_item_text_view);
            projectCardView = itemView.findViewById(R.id.one_item_card_view);
        }
    }
}
