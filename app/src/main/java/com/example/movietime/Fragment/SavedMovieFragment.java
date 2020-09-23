package com.example.movietime.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movietime.Adapter.MovieAdapter;
import com.example.movietime.Adapter.MovieSavedAdapter;
import com.example.movietime.Database.DatabaseMovie;
import com.example.movietime.Model.Movie;
import com.example.movietime.R;
import com.example.movietime.Storage.MovieStorage;

import java.util.ArrayList;

public class SavedMovieFragment extends Fragment {


    RecyclerView recyclerView;
    MovieSavedAdapter movieSavedAdapter;

    DatabaseMovie databaseMovie;

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.saved_movie_fragment_rv);
    }

    public static SavedMovieFragment getInstance(){
        SavedMovieFragment callsFragment = new SavedMovieFragment();
        return callsFragment;
    }

    @Override
    public void onAttach(@NonNull Context context) { super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved_movie,container,false);
        initView(view);

        MovieStorage.savedMovieFragment = this;


        movieSavedAdapter = new MovieSavedAdapter(this);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        recyclerView.setAdapter(movieSavedAdapter);

        //Test
        registerForContextMenu(recyclerView);
        databaseMovie = new DatabaseMovie(getContext());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        movieSavedAdapter.notifyDataSetChanged();
        movieSavedAdapter.refreshAdapterOy();
        Log.d("bebas", "onResume: HAHAHAH");
    }


}
