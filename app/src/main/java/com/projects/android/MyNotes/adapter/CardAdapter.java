package com.projects.android.MyNotes.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.projects.android.MyNotes.R;
import com.projects.android.MyNotes.helper.Data;

import java.lang.reflect.Type;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {
    private List<Data> list;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, text, date;
        public ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            text = (TextView) view.findViewById(R.id.text);
            date = (TextView) view.findViewById(R.id.date);
            imageView = (ImageView) view.findViewById(R.id.image);
        }
    }

    public CardAdapter(Context mContext, List<Data> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public CardAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Data details = list.get(position);
        if (details.getImage() == null) {
            if (details.getTitle().length() >= 15) {
                holder.title.setVisibility(View.GONE);
                holder.text.setTextSize(20);
                holder.text.setText(details.getTitle() + "\n" + details.getText());
            } else {
                if (details.getText().length() <= 10) {
                    holder.title.setTextSize(23);
                    holder.text.setTextSize(23);
                }
                if (details.getText().length() == 0) {
                    holder.title.setTextSize(30);
                    holder.text.setVisibility(View.GONE);
                }
                holder.title.setText(details.getTitle());
                holder.text.setText(details.getText());
            }

            holder.date.setText(details.getDate());
        } else {
                    holder.title.setVisibility(View.GONE);
                    holder.text.setVisibility(View.GONE);
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
