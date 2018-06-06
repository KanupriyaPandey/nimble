package com.projects.android.MyNotes.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.projects.android.MyNotes.R;
import com.projects.android.MyNotes.activity.Notes;
import com.projects.android.MyNotes.adapter.ImageAdapter;
import com.projects.android.MyNotes.helper.Image;
import com.projects.android.MyNotes.listener.RecyclerTouch;

import java.util.ArrayList;
import java.util.List;

public class BackgroundSheet extends BottomSheetDialogFragment {
    public RecyclerView recyclerView;
    List<Image> list;

    public BackgroundSheet() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.background_sheet, container, false);
        list=createImages();
        ImageAdapter adapter = new ImageAdapter(getActivity(), list);
        recyclerView = view.findViewById(R.id.recycler_view_notes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouch(getContext(), recyclerView, new RecyclerTouch.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Image img=list.get(position);
                String k="v2";
                String k1="v1";
                Intent intent=new Intent(getContext(), Notes.class);
                intent.putExtra(k,String.valueOf(img.getImageResource()));
                intent.putExtra(k1, String.valueOf(Notes.position));
                intent.putExtra(Intent.EXTRA_TEXT, "Preview");
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }

        }));

        return view;
    }

    public List<Image> createImages() {
        ArrayList<Image> items = new ArrayList<>();
        items.add(new Image(R.drawable.image1, 1));
        items.add(new Image(R.drawable.image2, 2));
        items.add(new Image(R.drawable.image3, 3));
        items.add(new Image(R.drawable.image4, 4));
        items.add(new Image(R.drawable.image5, 5));
        items.add(new Image(R.drawable.image6, 6));
        items.add(new Image(R.drawable.image7, 7));
        return items;
    }

}



