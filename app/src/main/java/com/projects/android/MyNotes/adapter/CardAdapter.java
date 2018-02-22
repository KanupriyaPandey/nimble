package com.projects.android.MyNotes.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.projects.android.MyNotes.R;
import com.projects.android.MyNotes.helper.Data;

import java.util.List;

/**
 * Created by kanupriya on 2/7/2018.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {
    private List<Data> list;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, text, date;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            text = (TextView) view.findViewById(R.id.text);
            date = (TextView) view.findViewById(R.id.date);
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
        holder.title.setText(details.getTitle());
        holder.text.setText(details.getText());
        holder.date.setText(details.getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
