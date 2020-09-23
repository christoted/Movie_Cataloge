package com.example.movietime.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static com.example.movietime.Contract.MovieContract.MovieEntry.COLUMN_MOVIE_ID;
import static com.example.movietime.Contract.MovieContract.MovieEntry.COLUMN_MOVIE_IMBD_ID;
import static com.example.movietime.Contract.MovieContract.MovieEntry.COLUMN_MOVIE_POSTER;
import static com.example.movietime.Contract.MovieContract.MovieEntry.COLUMN_MOVIE_TITLE;
import static com.example.movietime.Contract.MovieContract.MovieEntry.COLUMN_MOVIE_YEAR;
import static com.example.movietime.Contract.MovieContract.MovieEntry.TABLE_MOVIE;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "movie.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String CREATE_TABLE_MOVIE = " CREATE TABLE IF NOT EXISTS " + TABLE_MOVIE + "(" +
            COLUMN_MOVIE_IMBD_ID + " TEXT PRIMARY KEY, " +
            COLUMN_MOVIE_TITLE + " TEXT, " +
            COLUMN_MOVIE_ID + " TEXT," +
            COLUMN_MOVIE_POSTER + " TEXT," +
            COLUMN_MOVIE_YEAR + " TEXT);";

    private static final String SQL_DELETE_ENTRIES_BOOKING_TRANSACTION =
            "DROP TABLE IF EXISTS " + TABLE_MOVIE;
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES_BOOKING_TRANSACTION);
        onCreate(db);
    }
}
