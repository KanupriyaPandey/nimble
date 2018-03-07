package com.projects.android.MyNotes.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.projects.android.MyNotes.activity.Preview;
import com.projects.android.MyNotes.database.DbHelper;
import com.projects.android.MyNotes.R;
import com.projects.android.MyNotes.adapter.Adapter;
import com.projects.android.MyNotes.helper.Data;
import com.projects.android.MyNotes.listener.FragmentInteraction;
import com.projects.android.MyNotes.listener.RecyclerTouch;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class Home extends Fragment {
    private FragmentInteraction listener;
    public Adapter adapter;
    public RecyclerView recyclerView;
    SQLiteDatabase db;
    DbHelper help;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public List<Data> list;
    public int position;

    public Home() {
    }

    public static Home newInstance(String param1, String param2) {   //??
        Home fragment=new Home();
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
        help=new DbHelper(getContext());
        db=help.getReadableDatabase();
        list = new ArrayList<>();
        adapter = new Adapter(getActivity(), list);
        ButterKnife.bind(getActivity());      //To allow bottom sheets functionality
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getActivity());
        if(!sharedPreferences.contains("k"))   //To check if the application is opened for the first time or not.
        {
            editor = sharedPreferences.edit();
            editor.putString("k", "Grid");
            editor.apply();
        }
        try {
            viewChange(view);
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(),"View Change"+String.valueOf(e), Toast.LENGTH_LONG).show();
        }
        recyclerView.addOnItemTouchListener(new RecyclerTouch(getContext(), recyclerView, new RecyclerTouch.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                //Data data = list.get(position);
                Intent i=new Intent(getContext(), Preview.class);
                String k1="v1";
                i.putExtra(k1,String.valueOf(position));   //To pass the position of the selected card across the intent.
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {
                showBottomSheetDialogFragment(position);
            }
        }));
        try {
            prepareAlbums();
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(), "Prepare Album"+String.valueOf(e), Toast.LENGTH_LONG).show();
        }
        return view;
    }

    public void viewChange(View view) {
        switch (sharedPreferences.getString("k","")) {
            case "Grid": {
                recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_home);
                adapter = new Adapter(getContext(), list);
                StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);
                break;
            }
            case "List": {
                try {
                    recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_home);
                    adapter = new Adapter(getContext(), list);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(adapter);
                    break;
                } catch (Exception e) {
                    Toast.makeText(getContext(), String.valueOf(e), Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    public void showBottomSheetDialogFragment(int position) {
        try {
            this.position=position;
            BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
            bottomSheetFragment.show(getActivity().getSupportFragmentManager(), bottomSheetFragment.getTag());
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(), String.valueOf(e), Toast.LENGTH_LONG).show();
        }
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
        Cursor note=help.getAll(db);
        if(note.moveToLast())
        {
            do {
                if(note.getString(1).length()==0)
                {
                    try {
                        help.delete(note.getString(0), db);
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(getContext(), String.valueOf(e), Toast.LENGTH_LONG).show();
                    }
                }
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
        if(sharedPreferences.getString("k","").equals("Grid"))
            adapter.notifyDataSetChanged();
        else
            adapter.notifyDataSetChanged();
    }
}
