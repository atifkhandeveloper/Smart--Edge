package com.abh80.smartedge.aod_edgelighting.adapter;

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
import androidx.recyclerview.widget.RecyclerView;


import com.abh80.smartedge.R;
import com.abh80.smartedge.aod_edgelighting.model.CustomImageModel;

import java.util.ArrayList;

public class CustomThemeAdapter extends RecyclerView.Adapter<CustomThemeAdapter.ViewHolder> {
    Context context;
    ArrayList<CustomImageModel> list;
    ClickedLister clickedLister;
    int count;
    int selected_position = 0; // You have to set this globally in the Adapter class


    public CustomThemeAdapter(Context context, ArrayList<CustomImageModel> list, ClickedLister clickedLister) {
        this.context = context;
        this.list = list;
        this.clickedLister = clickedLister;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.custom_theme_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        CustomImageModel model = list.get(position);
        holder.textView.setText(model.getName());
        holder.imageView.setBackgroundResource(model.getImage());

        if (selected_position == position) {
            holder.imageView.setBackgroundResource(model.getImage_selected());
        } else {
            holder.imageView.setBackgroundResource(model.getImage());
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences("default_image", 0);
        int savePosition = sharedPreferences.getInt("image", 0);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor sharePref = context.getSharedPreferences("default_image", 0).edit();
                sharePref.putInt("image", position);
                sharePref.apply();
                sharePref.commit();
                // Below line is just like a safety check, because sometimes holder could be null,
                // in that case, getAdapterPosition() will return RecyclerView.NO_POSITION
                if (position == RecyclerView.NO_POSITION) return;

                // Updating old as well as new positions
                notifyItemChanged(selected_position);
                selected_position = position;
                notifyItemChanged(selected_position);
                // Do your another stuff for your onClick
                clickedLister.onClicked(position, holder.imageView);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public interface ClickedLister {
        void onClicked(int position, ImageView imageview);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_default_theme_view);
            textView = itemView.findViewById(R.id.default_theme_name);
        }
    }

}
