package com.example.movietime.Contract;

import android.provider.BaseColumns;

public class MovieContract  {
    private  MovieContract(){

    }

    public static final class MovieEntry implements BaseColumns{
        public static final String TABLE_MOVIE = "MasterMovie";
        public static final String COLUMN_MOVIE_TITLE = "MovieTitle";
        public static final String COLUMN_MOVIE_IMBD_ID = "MovieIMBDId";
        public static final String COLUMN_MOVIE_YEAR = "MovieYear";
        public static final String COLUMN_MOVIE_ID = "MovieId";
        public static final String COLUMN_MOVIE_POSTER = "MoviePoster";

    }
}
