package com.abh80.smartedge.aod_edgelighting.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abh80.smartedge.R;
import com.abh80.smartedge.aod_edgelighting.model.DefaultThemeModel;

import java.util.ArrayList;

public class CustomColorAdapter extends RecyclerView.Adapter<CustomColorAdapter.ViewHolder>  {
    Context context;
    ArrayList<DefaultThemeModel> list;
    ClickedLister clickedLister;

    public interface ClickedLister{
        void onClicked(int position,DefaultThemeModel model);
    }

    public CustomColorAdapter(Context context, ArrayList<DefaultThemeModel> list, ClickedLister clickedLister) {
        this.context=context;
        this.list=list;
        this.clickedLister=clickedLister;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.custom_color_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        DefaultThemeModel model=list.get(position);
        holder.textView.setText(model.getName());
        holder.imageView.setBackgroundResource(model.getImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedLister.onClicked(position,model);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.iv_default_theme_view);
            textView=itemView.findViewById(R.id.default_theme_name);
        }
    }
}
