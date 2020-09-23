package com.example.movietime.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movietime.Activity.MovieDetailActivityDelete;
import com.example.movietime.Database.DatabaseMovie;
import com.example.movietime.Fragment.SavedMovieFragment;
import com.example.movietime.Model.Movie;
import com.example.movietime.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;

public class MovieSavedAdapter extends RecyclerView.Adapter<MovieSavedAdapter.MovieSavedViewHolder> {

    public static final String KEY_IMAGE = "image";
    public static final String KEY_IMDB_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_YEAR = "year";

    private Context mContext;
    private DatabaseMovie databaseMovie;
    private SavedMovieFragment savedMovieFragment;
    private ArrayList<Movie> moviesSaved = new ArrayList<>();

    public MovieSavedAdapter(SavedMovieFragment savedMovieFragment){
        this.savedMovieFragment = savedMovieFragment;
        this.mContext = savedMovieFragment.getActivity();
        databaseMovie = new DatabaseMovie(mContext);
        moviesSaved.addAll(databaseMovie.getAllMovie());
        Log.d("12345", "size : " + moviesSaved.size());
    }

    public void refreshAdapterOy(){
        moviesSaved.clear();
        moviesSaved.addAll(databaseMovie.getAllMovie());

    }

    @NonNull
    @Override
    public MovieSavedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.single_row_item,parent,false);
        return new MovieSavedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieSavedViewHolder holder, int position) {

        final Movie movie = moviesSaved.get(position);
        holder.movieTitle.setText(movie.getMovieTitle());
        Picasso.with(mContext)
                .load(movie.getMoviePoster())
                .into(holder.movieImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MovieDetailActivityDelete.class);
                intent.putExtra(KEY_TITLE, movie.getMovieTitle());
                intent.putExtra(KEY_YEAR, movie.getMovieYear());
                intent.putExtra(KEY_IMDB_ID, movie.getMovieIMBD());
                intent.putExtra(KEY_IMAGE, movie.getMoviePoster());
                Log.d("title", "onClick: " + movie.getMovieTitle());
                v.getContext().startActivity(intent);
            }
        });

//        holder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
//            @Override
//            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//                menu.add(0, 3, 0, "Delete");
//                //   Intent intent = new Intent(v.getContext(), SavedMovieFragment.class);
//                //   intent.putExtra(KEY_IMDB_ID, movie.getMovieIMBD());
//                //   v.getContext().startActivity(intent);
//            }
//        });


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final String imbd = movie.getMovieIMBD();
                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                popupMenu.inflate(R.menu.menu_delete);
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(mContext, movie.getMovieTitle()+" is Deleted", Toast.LENGTH_SHORT).show();
                        databaseMovie.deleteMovie(imbd);
                        savedMovieFragment.onResume();
                        return true;
                    }
                });

                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesSaved.size();
    }

    public class MovieSavedViewHolder extends RecyclerView.ViewHolder {
        ImageView movieImage;
        TextView movieTitle;
        public MovieSavedViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.image);
            movieTitle = itemView.findViewById(R.id.name);
        }
    }
}
