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
import com.example.movietime.Activity.MovieDetailActivitySave;
import com.example.movietime.Database.DatabaseMovie;
import com.example.movietime.Fragment.SavedMovieFragment;
import com.example.movietime.Fragment.SearchedMovieFragment;
import com.example.movietime.Model.Movie;
import com.example.movietime.R;
import com.example.movietime.Storage.MovieStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

//import static com.example.movietime.Storage.MovieStorage.movieArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    Context mContext;

    public static final String KEY_IMAGE = "image";
    public static final String KEY_IMDB_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_YEAR = "year";
    private ArrayList<Movie> movieArrayList = new ArrayList<>();

    DatabaseMovie databaseMovie;


    public MovieAdapter(ArrayList<Movie> movies , Context context){
        this.movieArrayList = movies;
        this.mContext = context;
        databaseMovie = new DatabaseMovie(context);
    }

    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.single_row_item,parent,false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder holder,  int position) {
        final Movie movie = movieArrayList.get(position);
        holder.movieTitle.setText(movie.getMovieTitle());
        Picasso.with(mContext)
                .load(movie.getMoviePoster())
                .into(holder.movieImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imbd = movie.getMovieIMBD();
                boolean validMovie= true;
                if (databaseMovie.checkValidMovie(imbd)) {
                    validMovie = false;
                    Intent intent = new Intent(v.getContext(), MovieDetailActivityDelete.class);
                    intent.putExtra(KEY_TITLE, movie.getMovieTitle());
                    intent.putExtra(KEY_IMAGE, movie.getMoviePoster());
                    intent.putExtra(KEY_IMDB_ID, movie.getMovieIMBD());
                    intent.putExtra(KEY_YEAR, movie.getMovieYear());
                    v.getContext().startActivity(intent);
                }

                if (validMovie){
                    Intent intent = new Intent(v.getContext(), MovieDetailActivitySave.class);
                    intent.putExtra(KEY_TITLE, movie.getMovieTitle());
                    intent.putExtra(KEY_IMAGE, movie.getMoviePoster());
                    intent.putExtra(KEY_IMDB_ID, movie.getMovieIMBD());
                    intent.putExtra(KEY_YEAR, movie.getMovieYear());
                    v.getContext().startActivity(intent);
                }

            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final String imbd = movie.getMovieIMBD();
                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                boolean validMovie= true;
                if (databaseMovie.checkValidMovie(imbd)){
                    validMovie = false;
                    popupMenu.inflate(R.menu.menu_delete);
                    popupMenu.show();
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            Toast.makeText(mContext, movie.getMovieTitle()+" is Deleted!", Toast.LENGTH_SHORT).show();
                            databaseMovie.deleteMovie(imbd);
                            MovieStorage.savedMovieFragment.onResume();
                            return true;
                        }
                    });
                }

                if (validMovie) {
                    popupMenu.inflate(R.menu.menu_save);
                    popupMenu.show();
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            movie.setMovieTitle(movie.getMovieTitle());
                            movie.setMovieYear(movie.getMovieYear());
                            movie.setMovieIMBD(movie.getMovieIMBD());
                            movie.setMoviePoster(movie.getMoviePoster());
                            databaseMovie.insertMovie(movie);
                            Toast.makeText(mContext, movie.getMovieTitle()+ " is Saved!", Toast.LENGTH_SHORT).show();
                            MovieStorage.savedMovieFragment.onResume();
                            return true;
                        }
                    });
                }

                return false;
            }
        });


    }

    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView movieImage;
        TextView movieTitle;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.image);
            movieTitle = itemView.findViewById(R.id.name);
        }
    }
}
