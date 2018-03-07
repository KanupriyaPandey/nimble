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
import com.projects.android.MyNotes.adapter.Adapter;
import com.projects.android.MyNotes.database.DbHelper;
import com.projects.android.MyNotes.database.Dbhelper2;
import com.projects.android.MyNotes.helper.Data;
import com.projects.android.MyNotes.listener.FragmentInteraction;
import com.projects.android.MyNotes.listener.RecyclerTouch;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class Trash extends Fragment {
    /*private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;*/
    private FragmentInteraction listener;
    public Adapter adapter;
    public RecyclerView recyclerView;
    SQLiteDatabase db;
    Dbhelper2 help;
    public List<Data> list;
    public Trash(){
    }

    public static Trash newInstance(String param1, String param2) {   //??
        Trash fragment=new Trash();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_trash, container, false);
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
    public void onButtonPressed(Uri uri) {
        if (listener != null) {
            listener.onFragmentInteraction(uri);
        }
    }
    public void viewChange(View view)
    {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_trash);
        adapter = new Adapter(getContext(), list);
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
    private void prepareAlbums() {
        Cursor note=help.getAll(db);
        if(note.moveToLast())
        {
            do {
                String Content="";
                int i=0;
                do
                {
                    Content+=note.getString(1).charAt(i);
                    i++;
                }
                while (i<note.getString(1).length()&&i<50);
                if(i>=50)
                {
                    Content+="....";
                }
                Data a=new Data(note.getString(0),Content,note.getString(2));
                list.add(a);
            }
            while(note.moveToPrevious());
        }
            adapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentInteraction) {
            listener = (FragmentInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

}
