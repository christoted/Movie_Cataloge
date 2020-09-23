package com.example.movietime.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movietime.Adapter.MovieSavedAdapter;
import com.example.movietime.Database.DatabaseMovie;
import com.example.movietime.Fragment.SavedMovieFragment;
import com.example.movietime.R;
import com.squareup.picasso.Picasso;

public class MovieDetailActivityDelete extends AppCompatActivity implements View.OnClickListener {
    TextView movieTitle, movieYear, movieIMBD;
    ImageView movieImage;
    Button btnDelete;

    String title, year, IMBD, image;

    private DatabaseMovie databaseMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail_delete);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseMovie = new DatabaseMovie(getApplicationContext());

        movieTitle = findViewById(R.id.movie_detail_tv_title_delete);
        movieYear = findViewById(R.id.movie_detail_tv_year_delete);
        movieIMBD = findViewById(R.id.movie_detail_tv_imbd_id_delete);
        movieImage = findViewById(R.id.movie_detail_iv_delete);
        btnDelete = findViewById(R.id.movie_detail_button_delete);
        btnDelete.setOnClickListener(this);

        Intent intent = getIntent();
        title = intent.getStringExtra(MovieSavedAdapter.KEY_TITLE);
        year = intent.getStringExtra(MovieSavedAdapter.KEY_YEAR);
        IMBD = intent.getStringExtra(MovieSavedAdapter.KEY_IMDB_ID);
        image = intent.getStringExtra(MovieSavedAdapter.KEY_IMAGE);

        movieTitle.setText(title);
        movieYear.setText(year);
        movieIMBD.setText(IMBD);
        Picasso.with(this)
                .load(image)
                .into(movieImage);
    }

    @Override
    public void onClick(View v) {
        if ( v == btnDelete) {
            databaseMovie.deleteMovie(IMBD);
            Toast.makeText(this, title + "has been deleted", Toast.LENGTH_SHORT).show();
            finish();
          //  movieSavedAdapter.notifyDataSetChanged();
          //  finish();

        }
    }
}