package com.projects.android.MyNotes.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
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

import com.google.gson.Gson;
import com.projects.android.MyNotes.R;
import com.projects.android.MyNotes.adapter.CardAdapter;
import com.projects.android.MyNotes.adapter.Adapter;
import com.projects.android.MyNotes.database.DbHelper;
import com.projects.android.MyNotes.helper.Data;
import com.projects.android.MyNotes.listener.OnFragmentInteraction;
import com.projects.android.MyNotes.listener.RecyclerTouch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Notebooks extends Fragment {

    public RecyclerView recyclerView;
    CardAdapter cardAdapter;
    Adapter adapter2;
    int k = 0;
    AlertDialogFragment dialogFragment;
    public List<String> list_name;
    public List<Data> list;
    DbHelper help;
    SQLiteDatabase db;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private OnFragmentInteraction onFragmentInteraction;

    public Notebooks() {
    }

    public static Notebooks newInstance(String param1, String param2) {   //??
        Notebooks fragment = new Notebooks();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notebooks, container, false);
        if (onFragmentInteraction != null) {
            onFragmentInteraction.setActionBarTitle("Notebooks");
        }
        dialogFragment = new AlertDialogFragment();
        help = new DbHelper(getActivity());
        db = help.getReadableDatabase();
        list=new ArrayList<>();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor=sharedPreferences.edit();
        try {
            if (!sharedPreferences.contains("name")) {
                editor.putString("name", "List");
                editor.apply();
            }
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(), String.valueOf(e), Toast.LENGTH_SHORT).show();
        }
        try {
            viewChange(view);
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(), String.valueOf(e), Toast.LENGTH_SHORT).show();
        }
        recyclerView.addOnItemTouchListener(new RecyclerTouch(getContext(), recyclerView, new RecyclerTouch.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(sharedPreferences.contains("name")) {
                    editor.putString("name", String.valueOf(list_name.get(position)));
                    editor.apply();
                    try {
                        Fragment fragment_replace = new Notebooks();
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, fragment_replace);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                        ft.commit();
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), String.valueOf(e), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onLongClick(View view, int position) {
                    BottomSheetNotebooks bottomSheetNotebooks=new BottomSheetNotebooks();
                    bottomSheetNotebooks.show(getActivity().getSupportFragmentManager(),bottomSheetNotebooks.getTag());
            }
        }));
        return view;
    }

    public void viewChange(View view) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor=sharedPreferences.edit();
        if(sharedPreferences.getString("name","").equals("List")) {    //At first the noteBook list will be implemented and then the fragment will reload to display the related notes
            recyclerView = (RecyclerView) view.findViewById(R.id.Notebooks);
            Gson gson = new Gson();
            if (sharedPreferences.contains("Notebook")) {
                String Note_list = sharedPreferences.getString("Notebook", null);
                String[] note = gson.fromJson(Note_list, String[].class);
                List<String> list1 = Arrays.asList(note);
                list_name = new ArrayList<>(list1); //Getting the list that is saved in shared preferences
                adapter2 = new Adapter(getContext(), list_name);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter2);
            }
        }
               else
        {
                recyclerView = (RecyclerView) view.findViewById(R.id.Notebooks);
                cardAdapter = new CardAdapter(getContext(), list);
                StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(cardAdapter);
                prepareAlbums();
                editor.remove("name"); //Removing the shared prefrences after setting it once.
                editor.apply();
        }
    }
    private void prepareAlbums() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        Cursor note = help.getAll(db);
        if (note.moveToLast()){
            do {
                if (note.getString(4)!=null&&note.getString(4).equals(sharedPreferences.getString("name", "")))
                    {
                        if ((note.getString(0).length() == 0) || (note.getString(1).length() == 0)) {
                            try {
                                Data a = new Data(note.getString(0), note.getString(1), note.getString(2), null);
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
                }
            while (note.moveToPrevious());
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

