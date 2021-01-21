package com.bspl.qscanner.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bspl.qscanner.AllImagefrommobile;
import com.bspl.qscanner.R;
import com.bspl.qscanner.extraclass.showToast;
import com.bspl.qscanner.modelclass.Imagefromgallery;
import com.bspl.qscanner.modelclass.Selectedimage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AllimgAdopter extends RecyclerView.Adapter<AllimgAdopter.MyViewHolder> {
    ArrayList<Imagefromgallery> imagefromgallery;
    AllImagefrommobile allImagefrommobile;
    ArrayList<Imagefromgallery> selected;
    ClickListener clickListener;
    View listItem;
    public AllimgAdopter(AllImagefrommobile allImagefrommobile, ArrayList<Imagefromgallery> imagefromgallery) {
        this.allImagefrommobile = allImagefrommobile;
        this.imagefromgallery = imagefromgallery;

    }

    public void setImagefromgallery(ArrayList<Imagefromgallery> imagefromgallery) {
        this.imagefromgallery = new ArrayList<>();
        this.imagefromgallery = imagefromgallery;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
         listItem = layoutInflater.inflate(R.layout.allimage, parent, false);
        return new MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

       // holder.bind(imagefromgallery.get(position));
     // holder.  checkBox.setVisibility(!imagefromgallery.get(position).isSelected() ? View.VISIBLE : View.VISIBLE);
        if(!imagefromgallery.get(position).isSelected()){
            holder.checkBox.setVisibility(View.GONE);
        }else {
            holder.checkBox.setVisibility(View.VISIBLE);

            holder.checkBox.setImageResource(R.drawable.ic_baseline_check_circle_24);

        }
        holder. imageView.setImageURI(Uri.parse(imagefromgallery.get(position).getImagename()));

        holder. imageView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(!imagefromgallery.get(position).isSelected()){
            holder.checkBox.setVisibility(View.VISIBLE);

            holder.checkBox.setImageResource(R.drawable.ic_baseline_check_circle_24);
            imagefromgallery.get(position).setSelected(true);
        }else if(imagefromgallery.get(position).isSelected()) {
            holder.checkBox.setVisibility(View.GONE);
            imagefromgallery.get(position).setSelected(false);

        }
    }
});



    }


    @Override
    public int getItemCount() {
        return imagefromgallery.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        ImageView checkBox;
        CardView cardView;
        boolean click = false;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            imageView = itemView.findViewById(R.id.thumbImage);
            checkBox = itemView.findViewById(R.id.itemCheckBox);
            cardView = itemView.findViewById(R.id.cardview);


        }


//        public void bind(final Imagefromgallery imagefromgallery) {
//
//          itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    // if(selected.size()<0)
//                    imagefromgallery.setSelected(!imagefromgallery.isSelected());
//
//                   checkBox.setVisibility(imagefromgallery.isSelected() ? View.VISIBLE : View.GONE);
//
//                    return false;
//                }
//            });
//
//
//        }


    }


    public List<Imagefromgallery> getAll() {
        return imagefromgallery;
    }

    public ArrayList<Imagefromgallery> getSelected() {
        selected = new ArrayList<>();
        for (int i = 0; i < imagefromgallery.size(); i++) {
            if (imagefromgallery.get(i).isSelected()) {
                selected.add(imagefromgallery.get(i));
            }
        }
        return selected;
    }
    public interface ClickListener {
        public void onClick(View view, Imagefromgallery position);
        public void onLongClick(View view, Imagefromgallery position);
    }

}
