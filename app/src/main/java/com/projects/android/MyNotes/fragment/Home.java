package com.projects.android.MyNotes.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.projects.android.MyNotes.R;
import com.projects.android.MyNotes.activity.Notes;
import com.projects.android.MyNotes.adapter.CardAdapter;
import com.projects.android.MyNotes.database.DbHelper;
import com.projects.android.MyNotes.helper.Data;
import com.projects.android.MyNotes.helper.Shared_Preferences;
import com.projects.android.MyNotes.listener.OnFragmentInteraction;
import com.projects.android.MyNotes.listener.RecyclerTouch;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class Home extends Fragment {
    public CardAdapter cardAdapter;
    public RecyclerView recyclerView;
    public List<Data> list;
    public static int select;
    private OnFragmentInteraction onFragmentInteraction;
    SQLiteDatabase db;
    DbHelper help;
    public static String content;
    public static Data item;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Shared_Preferences shared_preferences;

    public Home() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        shared_preferences = new Shared_Preferences(getContext());
        if (onFragmentInteraction != null) {
            onFragmentInteraction.setActionBarTitle("Home");
        }
        help = new DbHelper(getContext());
        db = help.getReadableDatabase();
        list = new ArrayList<>();
        cardAdapter = new CardAdapter(getActivity(), list);
        ButterKnife.bind(getActivity());
        shared_preferences.check_layout();
        try {
            viewChange(view);
        } catch (Exception e) {
            Toast.makeText(getContext(), "View Change" + String.valueOf(e), Toast.LENGTH_LONG).show();
        }

        recyclerView.addOnItemTouchListener(new RecyclerTouch(getContext(), recyclerView, new RecyclerTouch.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Cursor data = help.getAll(db);
                int i = 0;
                if (data.moveToLast()) {
                    while (i < position) {
                        data.moveToPrevious();
                        i++;
                    }
                }
                if(data.getString(0).equalsIgnoreCase("Audio_Recorded"))
                {
                    try {
                        content = data.getString(1);
                        //Toast.makeText(this, "Audio_Recorded", Toast.LENGTH_SHORT).show();
                        item = new Data(null, content, null, null);
                        PlayFragment playFragment = new PlayFragment().newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                        playFragment.show(transaction, "dialog_playback");
                    }
                    catch(Exception e)
                    {
                        Toast.makeText(getActivity(), String.valueOf(e), Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Intent intent = new Intent(getContext(), Notes.class);
                    String k1 = "v1";
                    intent.putExtra(k1, String.valueOf(position));
                    intent.putExtra(Intent.EXTRA_TEXT, "Preview");
                    startActivity(intent);
                }
            }

            @Override
            public void onLongClick(View view, int position) {
                showBottomSheetDialog(position);
            }
        }));
        try {
            prepareAlbums();
        } catch (Exception e) {
            Toast.makeText(getContext(), "Prepare Album " + String.valueOf(e), Toast.LENGTH_LONG).show();
        }
        return view;
    }

    public void showBottomSheetDialog(int position) {
        try {
            Home.select = position;
            new BottomSheetMain().show(getActivity().getSupportFragmentManager(), "BottomSheet");
        } catch (Exception e) {
            Toast.makeText(getContext(), String.valueOf(e), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onFragmentInteraction = (OnFragmentInteraction) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteraction");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onFragmentInteraction = null;
    }

    public void viewChange(View view) {
        switch (shared_preferences.return_layout()) {
            case "Grid": {
                recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_home);
                cardAdapter = new CardAdapter(getContext(), list);
                StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(cardAdapter);
                break;
            }
            case "List": {
                try {
                    recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_home);
                    cardAdapter = new CardAdapter(getContext(), list);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(cardAdapter);
                    break;
                } catch (Exception e) {
                    Toast.makeText(getContext(), String.valueOf(e), Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void prepareAlbums() {
        Cursor note = help.getAll(db);
        Cursor image = help.getImg(db);
        if (note.moveToLast() && image.moveToLast()) {
            do {
                if (image.getBlob(0) == null) {
                    if ((note.getString(0).length() == 0) || (note.getString(1).length() == 0)) {
                        try {
                            Data a = new Data(note.getString(0), note.getString(1), note.getString(2),null);
                            list.add(a);
                        } catch (Exception e) {
                            Toast.makeText(getContext(), String.valueOf(e), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        String Content = "";
                        int i = 0;
                        do {
                            Content += note.getString(1).charAt(i);
                            i++;
                        }
                        while (i < note.getString(1).length() && i < 50);
                        if (i >= 50) {
                            Content += "....";
                        }
                        Data a = new Data(note.getString(0), Content, note.getString(2), null);
                        list.add(a);
                    }
                }
                else
                {
                    Data a = new Data("","", note.getString(2), image.getBlob(0));
                    list.add(a);
                }
            }
            while (note.moveToPrevious()&& image.moveToPrevious());
        }
        if ((shared_preferences.return_layout()).equals("Grid"))
            cardAdapter.notifyDataSetChanged();
        else
            cardAdapter.notifyDataSetChanged();
    }

}
