package com.watchme.roman.watchme_ver2.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**************************************************************************
 * Created by roman on 03/09/2015.
 * This class stores all movie information that I get after JSON parsing
 **************************************************************************/
public class Movie {
    private String title, overview, posterURL, backdrop, character, companyName, creatorID, status;
    private int year, runtime, voteCount;
    private double rating;
    private String id;
    private ArrayList<String> genres;

    // Default constructor
    public Movie() {
    }


    // Getters


    public String getStatus() {
        return status;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public String getCreatorID() {
        return creatorID;
    }

    public int getRuntime() {
        return runtime;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCharacter() {
        return character;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public int getYear() {
        return year;
    }

    public double getRating() {
        return rating;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public String getMovieId() {
        return id;
    }


    // Setters


    public void setStatus(String status) {
        this.status = status;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public void setCreatorID(String creatorID) {
        this.creatorID = creatorID;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public void setMovieId(String id) {
        this.id = id;
    }


}
