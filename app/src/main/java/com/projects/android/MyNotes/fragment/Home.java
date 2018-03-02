package com.projects.android.MyNotes.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projects.android.MyNotes.R;
import com.projects.android.MyNotes.activity.Notes;
import com.projects.android.MyNotes.adapter.Adapter;
import com.projects.android.MyNotes.helper.Data;
import com.projects.android.MyNotes.listener.FragmentInteraction;
import com.projects.android.MyNotes.listener.RecyclerTouch;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {
    private FragmentInteraction listener;
    public RecyclerView recyclerView;
    public Adapter adapter;
    //public RowAdapter rowAdapter;
    public List<Data> list;

    public Home() {
    }

    public static Home newInstance(String param1, String param2) {
        Home fragment=new Home();
      //  Bundle args = new Bundle();
      //  args.putString(ARG_PARAM1, param1);
      //  args.putString(ARG_PARAM2, param2);
      //  fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view_home);
        list = new ArrayList<>();
        adapter = new Adapter(getActivity(), list);

        //StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareAlbums();
        recyclerView.addOnItemTouchListener(new RecyclerTouch(getActivity(), recyclerView, new RecyclerTouch.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Data data = list.get(position);
                Intent i=new Intent(getActivity(), Notes.class);
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (listener != null) {
            listener.onFragmentInteraction(uri);
        }
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



    private void prepareAlbums() {
        Data a = new Data("MEAN stands for: empowering businesses to be more agile and scalable.","kldsl","7/6/8");
        list.add(a);

        a = new Data("Xscpae", "8dagvasa","6/7/8");
        list.add(a);

        a = new Data("Maroon 5", "11, covers[2]","6/7/8");
        list.add(a);

        a = new Data("Born to Die empowering businesses to be more agile and s", " covers[3]","6/7/8");
        list.add(a);

        a = new Data("Honeymoonempowering businesses to be more agile and s","covers[4]","6/7/8");
        list.add(a);

        a = new Data("I Need a Doctor","5","6/7/8");
        list.add(a);

        a = new Data("Loud", "11","6/7/8");
        list.add(a);

        a = new Data("Legend", "14","6/7/8");
        list.add(a);

        a = new Data("Hello", "11","6/7/8");
        list.add(a);

        a = new Data("Greatest Hits empowering businesses to be more agile and s", "17","6/7/8");
        list.add(a);

        adapter.notifyDataSetChanged();
    }

}
