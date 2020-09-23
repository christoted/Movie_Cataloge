package com.example.movietime.Model;

public class Movie {
    private String movieTitle;
    private String movieYear;
    private String movieId;
    private String movieIMBD;
    private String moviePoster;

    public String getMovieIMBD() {
        return movieIMBD;
    }

    public void setMovieIMBD(String movieIMBD) {
        this.movieIMBD = movieIMBD;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieYear() {
        return movieYear;
    }

    public void setMovieYear(String movieYear) {
        this.movieYear = movieYear;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }
}
