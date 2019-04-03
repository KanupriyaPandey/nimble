package com.projects.android.MyNotes.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projects.android.MyNotes.R;
import com.projects.android.MyNotes.activity.NoteText;
import com.projects.android.MyNotes.adapter.BackgroundAdapter;
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
        list = createImages();

        BackgroundAdapter adapter = new BackgroundAdapter(getActivity(), list);
        recyclerView = view.findViewById(R.id.recycler_view_notes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouch(getContext(), recyclerView, new RecyclerTouch.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Image image = list.get(position);
                //v1,v2
                String k = "v2", k1 = "v1";
                Intent intent = new Intent(getContext(), NoteText.class);
                intent.putExtra(k, String.valueOf(image.getImageResource()));
                intent.putExtra(k1, String.valueOf(NoteText.position));
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
        items.add(new Image(R.drawable.image0, 1));
        items.add(new Image(R.drawable.image1, 2));
        items.add(new Image(R.drawable.image2, 3));
        items.add(new Image(R.drawable.image3, 4));
        items.add(new Image(R.drawable.image4, 5));
        items.add(new Image(R.drawable.image5, 6));
        items.add(new Image(R.drawable.image6, 7));
        items.add(new Image(R.drawable.image8, 8));
        return items;
    }

}



