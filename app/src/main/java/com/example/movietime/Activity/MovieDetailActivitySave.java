package com.example.movietime.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movietime.Adapter.MovieAdapter;
import com.example.movietime.Database.DatabaseMovie;
import com.example.movietime.Model.Movie;
import com.example.movietime.R;
import com.squareup.picasso.Picasso;

public class MovieDetailActivitySave extends AppCompatActivity implements View.OnClickListener {
    TextView movieTitle, movieYear, movieIMBD;
    ImageView movieImage;
    Button btnSave;

    String title, year, IMBD, image;

    private DatabaseMovie databaseMovie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail_save);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseMovie = new DatabaseMovie(getApplicationContext());

        movieTitle = findViewById(R.id.movie_detail_tv_title);
        movieYear = findViewById(R.id.movie_detail_tv_year);
        movieIMBD = findViewById(R.id.movie_detail_tv_imbd_id);
        movieImage = findViewById(R.id.movie_detail_iv);
        btnSave = findViewById(R.id.movie_detail_button_save);

        btnSave.setOnClickListener(this);

        final Intent intent = getIntent();
        title = intent.getStringExtra(MovieAdapter.KEY_TITLE);
        year = intent.getStringExtra(MovieAdapter.KEY_YEAR);
        IMBD = intent.getStringExtra(MovieAdapter.KEY_IMDB_ID);
        image = intent.getStringExtra(MovieAdapter.KEY_IMAGE);

        movieTitle.setText(title);
        movieYear.setText(year);
        movieIMBD.setText(IMBD);
        Picasso.with(this)
                .load(image)
                .into(movieImage);
    }

    @Override
    public void onClick(View v) {
        if ( v == btnSave) {
            Movie movie = new Movie();
            movie.setMovieTitle(title);
            movie.setMovieYear(year);
            movie.setMovieIMBD(IMBD);
            movie.setMoviePoster(image);
            databaseMovie.insertMovie(movie);
            Toast.makeText(this, title+" is Saved!", Toast.LENGTH_SHORT).show();
            Log.d("111", " data : " + databaseMovie.getAllMovie().size());
            finish();
        }
    }
}