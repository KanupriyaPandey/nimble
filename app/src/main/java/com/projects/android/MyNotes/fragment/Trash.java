package com.projects.android.MyNotes.fragment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.projects.android.MyNotes.R;
import com.projects.android.MyNotes.adapter.CardAdapter;
import com.projects.android.MyNotes.database.Dbhelper2;
import com.projects.android.MyNotes.helper.Data;
import com.projects.android.MyNotes.listener.OnFragmentInteraction;
import com.projects.android.MyNotes.listener.RecyclerTouch;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class Trash extends Fragment {
    public CardAdapter cardAdapter;
    public RecyclerView recyclerView;
    private OnFragmentInteraction onFragmentInteraction;
    SQLiteDatabase db;
    Dbhelper2 help;
    public List<Data> list;
    static int select;
    BottomSheetTrash trash;
    public Trash(){
    }

    public static Trash newInstance(String param1, String param2) {   //??
        Trash fragment=new Trash();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_trash, container, false);
        if (onFragmentInteraction != null) {
            onFragmentInteraction.setActionBarTitle("Trash");
        }
        trash=new BottomSheetTrash();
        ButterKnife.bind(getActivity());
        try {
            list = new ArrayList<>();
            help=new Dbhelper2(getContext());
            db=help.getReadableDatabase();
            viewChange(view);
            }
        catch (Exception e)
        {
            Toast.makeText(getActivity(), String.valueOf(e), Toast.LENGTH_LONG).show();
        }
        recyclerView.addOnItemTouchListener(new RecyclerTouch(getActivity(), recyclerView, new RecyclerTouch.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                select=position;
                trash.show(getActivity().getSupportFragmentManager(), trash.getTag());
            }
            @Override
            public void onLongClick(View view, int position) {

                            }
                        }));
        try {
            prepareAlbums();
                }
        catch (Exception e)
        {
            Toast.makeText(getActivity(), String.valueOf(e), Toast.LENGTH_LONG).show();
                }
        return view;
    }
    public void viewChange(View view)
    {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_trash);
        cardAdapter = new CardAdapter(getContext(), list);
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cardAdapter);
    }
    private void prepareAlbums() {
        Cursor note=help.getAll(db);
        if(note.moveToLast()) {
            do {
                if ((note.getString(0).length() == 0)||(note.getString(1).length() == 0)) {
                    try {   Data a = new Data(note.getString(0), note.getString(1), note.getString(2), null);
                        list.add(a);
                    } catch (Exception e) {
                        Toast.makeText(getContext(), String.valueOf(e), Toast.LENGTH_LONG).show();
                    }
                }
                else {
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
            while (note.moveToPrevious()) ;
        }
            cardAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteraction) {
            onFragmentInteraction = (OnFragmentInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onFragmentInteraction = null;
    }

}
