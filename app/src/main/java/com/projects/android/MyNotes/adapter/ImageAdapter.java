package com.projects.android.MyNotes.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.projects.android.MyNotes.R;
import com.projects.android.MyNotes.helper.Image;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {
    private List<Image> list;
    private Context context;

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;

        MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.background_image);
        }
    }

    public ImageAdapter(Context context, List<Image> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Image details = list.get(position);
        holder.image.setImageResource(details.getImageResource());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}