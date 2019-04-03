package com.projects.android.MyNotes.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.projects.android.MyNotes.R;
import com.projects.android.MyNotes.helper.Data;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {
    private List<Data> list;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, text, date;
        public ImageView imageView;
        public RelativeLayout relativeLayout;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            text = view.findViewById(R.id.text);
            date = view.findViewById(R.id.date);
            imageView = view.findViewById(R.id.image);
            relativeLayout = view.findViewById(R.id.relativeLayout);
        }
    }

    public CardAdapter(Context mContext, List<Data> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public CardAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notes_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Data details = list.get(position);
        if (details.getImage() == null) {
            if (details.getTitle().equalsIgnoreCase("Audio_Recorded")) {
                holder.title.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_mic, 0, 0, 0);
                String[] content = details.getText().split("\n", 2);
                int len = content[0].length();
                holder.title.setText(content[0].substring(0, len - 4));
                holder.title.setTextSize(18);
                long duration = Integer.parseInt(content[1]);
                long minutes = TimeUnit.MILLISECONDS.toMinutes(duration);
                long seconds = TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(minutes);
                holder.text.setText(String.format("%02d:%02d", minutes, seconds));
                holder.date.setText(details.getDate());
            } else {
                if (details.getTitle().length() >= 15) {
                    holder.title.setVisibility(View.GONE);
                    if (details.getTitle().length() < 25)
                        holder.text.setTextSize(25);
                    else if (details.getTitle().length() <= 40)
                        holder.text.setTextSize(20);
                    else if (details.getTitle().length() <= 65)
                        holder.text.setTextSize(28);
                    else
                        holder.text.setTextSize(15);
                    holder.text.setText(details.getTitle() + "\n" + details.getText());
                } else {
                    if (details.getText().length() <= 20) {
                        holder.title.setTextSize(22);
                        holder.text.setTextSize(18);
                    }
                    if (details.getText().length() == 0) {
                        if (details.getTitle().length() <= 5)
                            holder.title.setTextSize(28);
                        else if (details.getTitle().length() < 15)
                            holder.title.setTextSize(20);
                        holder.text.setVisibility(View.GONE);
                    }
                    holder.title.setText(details.getTitle());
                    holder.text.setText(details.getText());
                }
                holder.date.setText(details.getDate());
            }
        } else {
            holder.relativeLayout.setVisibility(View.GONE);
            holder.imageView.setVisibility(View.VISIBLE);
            Bitmap bitmap = BitmapFactory.decodeByteArray(details.getImage(), 0, details.getImage().length);
            holder.imageView.setImageBitmap(bitmap);
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
