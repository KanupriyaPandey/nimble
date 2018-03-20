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



public class Adapter2 extends RecyclerView.Adapter<Adapter2.MyViewHolder> {
    private List<String> list;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name_notebook;

        public MyViewHolder(View view) {
            super(view);
            name_notebook = (TextView) view.findViewById(R.id.name_notebook);
        }
    }

    public Adapter2(Context mContext, List<String> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public Adapter2.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_notebook, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        String details = list.get(position);
        holder.name_notebook.setText(details);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
