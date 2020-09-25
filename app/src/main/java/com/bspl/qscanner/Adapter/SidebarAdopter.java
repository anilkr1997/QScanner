package com.bspl.qscanner.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bspl.qscanner.AllImagefrommobile;
import com.bspl.qscanner.R;
import com.bspl.qscanner.homeActivity;
import com.bspl.qscanner.modelclass.sidebar_class;


import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static java.sql.DriverManager.println;

public class SidebarAdopter extends RecyclerView.Adapter<SidebarAdopter.MyViewHolder> {

   private homeActivity homeActivity;
   private sidebar_class[] dasModelclasses;
    public SidebarAdopter(homeActivity homeActivity, sidebar_class[] dasModelclasses) {
        this.homeActivity=homeActivity;
        this.dasModelclasses=dasModelclasses;
    }



    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.sidebarrecycleview ,parent,false);
        return new MyViewHolder(listItem);

    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int position) {
        Log.e("anil", dasModelclasses[position].getProducetname());

        holder.name.setText(dasModelclasses[position].getProducetname());
holder.imageView.setImageResource(dasModelclasses[position].getImage());
    }

    @Override
    public int getItemCount() {
        Log.e("anil", String.valueOf(dasModelclasses.length));

        return dasModelclasses.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView imageView;
        public MyViewHolder( View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.sidbartext);
            imageView=itemView.findViewById(R.id.sidbar_img);
        }
    }
}
