package com.example.movietime.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.movietime.Adapter.MovieAdapter;
import com.example.movietime.Model.Movie;
import com.example.movietime.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

public class SearchedMovieFragment extends Fragment  {

    RecyclerView recyclerView;
    MovieAdapter movieAdapter;
    ArrayList<Movie> movieArrayList;
    String title;
    private TextView mTextViewResult;

    private TextInputEditText mEDTSearch;
    private TextInputLayout mLAYSearch;

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.activity_movie_rv);
        mEDTSearch = view.findViewById(R.id.search_movie_fragment_edt_search);
        mLAYSearch = view.findViewById(R.id.search_movie_fragment_iptlayout_search);

    }


    public static SearchedMovieFragment getInstance(){
        SearchedMovieFragment searchedMovieFragment = new SearchedMovieFragment();
        return searchedMovieFragment;
    }

    @Override
    public void onAttach(@NonNull Context context) { super.onAttach(context); }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_movie,container,false);

        initView(view);

        initializeMovie();

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        mEDTSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            searchReq();
                            return true;
                        default:
                            break;
                    }
                }

                return false;
            }
        });



        return view;
    }

    private void initializeMovie(){
       String url = "https://www.omdbapi.com/?apikey=99182b8c&s=*avenger*&page=1";
     //   String url = "http://omdbapi.com/?apikey=67c7eb50&s=batman";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        movieArrayList = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("Search");

                            for(int i = 0; i < jsonArray.length(); i++){
                                Movie movie = new Movie(); // C++ malloc , reserve suatu memory (0002) [30] {m10}{i10}{y10}{u10}
                                JSONObject obj = (JSONObject) jsonArray.get(i);
                                String movieTitle = obj.getString("Title");
                                String movieYear = obj.getString("Year");
                                String movieIMBD = obj.getString("imdbID");
                                String movieURL = obj.getString("Poster");

                                movie.setMovieTitle(movieTitle);
                                movie.setMovieIMBD(movieIMBD);
                                movie.setMovieYear(movieYear);
                                movie.setMoviePoster(movieURL);
                                movieArrayList.add(movie); //[0002,0002,0002,0002,0002,0002]
                                Log.d("123", "Title : "+ movieTitle +";title MovieModel : " + movie.getMovieTitle() + ";year : " + movieYear + "\n");
                            }

                            movieAdapter = new MovieAdapter(movieArrayList,getActivity());
                            recyclerView.setAdapter(movieAdapter);

                        }

                        catch (JSONException e ){
                            e.printStackTrace();
                        }

                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }

    public void searchReq(){

        if ( mEDTSearch.length() == 0){
            mEDTSearch.setError("Please Input");
        } else {
            title = mEDTSearch.getText().toString().trim();
            String url = "https://www.omdbapi.com/?apikey=99182b8c&s="+title+"&page=1";

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

            movieArrayList = new ArrayList<>();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray jsonArray = response.getJSONArray("Search");
                                for(int i = 0; i < jsonArray.length(); i++){
                                    Movie movie = new Movie();
                                    JSONObject obj = (JSONObject) jsonArray.get(i);
                                    String movieTitle = obj.getString("Title");
                                    String movieYear = obj.getString("Year");
                                    String movieIMBD = obj.getString("imdbID");
                                    String movieURL = obj.getString("Poster");

                                    movie.setMovieTitle(movieTitle);
                                    movie.setMovieIMBD(movieIMBD);
                                    movie.setMovieYear(movieYear);
                                    movie.setMoviePoster(movieURL);

                                    movieArrayList.add(movie);
                                    Log.d("123", "title : " + movieTitle + "year : " + movieYear + "\n");
                                    //   mTextViewResult.append(movieTitle + ", " + movieYear + ", "+ movieIMBD +  "\n\n");
                                }
                            }

                            catch (JSONException e ){
                                e.printStackTrace();
                            }
                            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
                            movieAdapter = new MovieAdapter(movieArrayList,getActivity());
                            recyclerView.setAdapter(movieAdapter);

                            //       movieAdapter.notifyDataSetChanged();

                        }

                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    });

            requestQueue.add(jsonObjectRequest);
        }

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case 1:{
                Toast.makeText(getActivity(), "Test Save", Toast.LENGTH_SHORT).show();
                saveMovie();
            }
            case 2:{
                Toast.makeText(getActivity(), "Test Delete", Toast.LENGTH_SHORT).show();
                deleteMovie();
            }
        }

        return super.onContextItemSelected(item);
    }

    private void deleteMovie() {

    }

    private void saveMovie() {
    }
}
