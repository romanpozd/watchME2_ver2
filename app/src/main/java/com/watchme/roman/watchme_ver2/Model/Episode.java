package com.watchme.roman.watchme_ver2.Model;

/**
 * Created by roman on 17/09/2015.
 */
public class Episode {
    private String episode_name, episode_thumb, episode_overview;
    private int episode_num;
    private double rating;

    // Constructor
    public Episode(){};

    // Getters


    public String getEpisode_name() {
        return episode_name;
    }

    public String getEpisode_thumb() {
        return episode_thumb;
    }

    public String getEpisode_overview() {
        return episode_overview;
    }

    public int getEpisode_num() {
        return episode_num;
    }

    public double getRating() {
        return rating;
    }

    // Setters

    public void setEpisode_name(String episode_name) {
        this.episode_name = episode_name;
    }

    public void setEpisode_thumb(String episode_thumb) {
        this.episode_thumb = episode_thumb;
    }

    public void setEpisode_overview(String episode_overview) {
        this.episode_overview = episode_overview;
    }

    public void setEpisode_num(int episode_num) {
        this.episode_num = episode_num;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
