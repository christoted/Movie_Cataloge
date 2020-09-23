package com.example.movietime.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.movietime.Model.Movie;

import java.util.ArrayList;

import static com.example.movietime.Contract.MovieContract.MovieEntry.COLUMN_MOVIE_ID;
import static com.example.movietime.Contract.MovieContract.MovieEntry.COLUMN_MOVIE_IMBD_ID;
import static com.example.movietime.Contract.MovieContract.MovieEntry.COLUMN_MOVIE_POSTER;
import static com.example.movietime.Contract.MovieContract.MovieEntry.COLUMN_MOVIE_TITLE;
import static com.example.movietime.Contract.MovieContract.MovieEntry.COLUMN_MOVIE_YEAR;
import static com.example.movietime.Contract.MovieContract.MovieEntry.TABLE_MOVIE;

public class DatabaseMovie {
    DatabaseHelper databaseHelper;
    private Context mContext;
    private SQLiteDatabase sqLiteDatabase;

    public DatabaseMovie(Context context) {
        this.mContext = context;
        databaseHelper = new DatabaseHelper(context);
    }

    public void open(){
        sqLiteDatabase = databaseHelper.getWritableDatabase();
    }

    public void insertMovie(Movie movie){
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MOVIE_TITLE, movie.getMovieTitle());
        contentValues.put(COLUMN_MOVIE_YEAR, movie.getMovieYear());
        contentValues.put(COLUMN_MOVIE_ID, movie.getMovieId());
        contentValues.put(COLUMN_MOVIE_IMBD_ID, movie.getMovieIMBD());
        contentValues.put(COLUMN_MOVIE_POSTER,movie.getMoviePoster());
        sqLiteDatabase.insert(TABLE_MOVIE, null,contentValues);
        sqLiteDatabase.close();
    }

    public void deleteMovie(String MovieId) {
        open();
        sqLiteDatabase.delete(TABLE_MOVIE,COLUMN_MOVIE_IMBD_ID + " = ? ", new String[]{MovieId});
        sqLiteDatabase.close();
    }

    public ArrayList<Movie> getAllMovie(){
        open();
        String SELECT_QUERY = "SELECT * FROM " + TABLE_MOVIE;
        ArrayList<Movie> movieArrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_QUERY,null);
        if ( cursor.moveToFirst()){
            do{
                Movie movie = new Movie();
                movie.setMovieId(cursor.getString(cursor.getColumnIndex(COLUMN_MOVIE_ID)));
                movie.setMovieTitle(cursor.getString(cursor.getColumnIndex(COLUMN_MOVIE_TITLE)));
                movie.setMovieIMBD(cursor.getString(cursor.getColumnIndex(COLUMN_MOVIE_IMBD_ID)));
                movie.setMovieYear(cursor.getString(cursor.getColumnIndex(COLUMN_MOVIE_YEAR)));
                movie.setMoviePoster(cursor.getString(cursor.getColumnIndex(COLUMN_MOVIE_POSTER)));
                movieArrayList.add(movie);
            } while (cursor.moveToNext());
        }


        return movieArrayList;
    }

    public boolean checkValidMovie (String IMBD) {
        open();
        String selection = COLUMN_MOVIE_IMBD_ID + " = ? ";
        String[] selectionArgss = {IMBD};

        Cursor cursor = sqLiteDatabase.query(TABLE_MOVIE,null,selection,
                selectionArgss,null,null,null);

        int getCursorCount = cursor.getCount();
        cursor.close();
        sqLiteDatabase.close();

        if ( getCursorCount > 0) {
            return true;
        }
        return false;
    }




}
