package com.watchme.roman.watchme_ver2.Model;

import java.util.ArrayList;

/**
 * Created by roman on 24/09/2015.
 */
public class SearchList {
    private String name, ID, image, backdrop;
    private int year;
    private double rating;
    private boolean is_tv, is_movie, is_actor;
    private ArrayList<String> knownFOR;


    // Constructor
    public SearchList (){};

    // Getters


    public String getBackdrop() {
        return backdrop;
    }

    public ArrayList<String> getKnownFOR() {
        return knownFOR;
    }

    public double getRating() {
        return rating;
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }

    public String getImage() {
        return image;
    }

    public int getYear() {
        return year;
    }

    public boolean is_tv() {
        return is_tv;
    }

    public boolean is_movie() {
        return is_movie;
    }

    public boolean is_actor() {
        return is_actor;
    }

    // Setters


    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public void setKnownFOR(ArrayList<String> knownFOR) {
        this.knownFOR = knownFOR;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setIs_tv(boolean is_tv) {
        this.is_tv = is_tv;
    }

    public void setIs_movie(boolean is_movie) {
        this.is_movie = is_movie;
    }

    public void setIs_actor(boolean is_actor) {
        this.is_actor = is_actor;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
