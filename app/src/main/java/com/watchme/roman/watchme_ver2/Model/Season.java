package com.watchme.roman.watchme_ver2.Model;

/**
 * Created by roman on 15/09/2015.
 */
public class Season {
    int season_number, year, numof_episodes;
    String season_poster, season_id, title, TVSerie_thumb;

    public Season() {
    }

    // Getters


    public String getTitle() {
        return title;
    }

    public String getTVSerie_thumb() {
        return TVSerie_thumb;
    }

    public String getSeason_id() {
        return season_id;
    }

    public int getSeason_number() {
        return season_number;
    }

    public int getYear() {
        return year;
    }

    public int getNumof_episodes() {
        return numof_episodes;
    }

    public String getSeason_poster() {
        return season_poster;
    }

    // Setters

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTVSerie_thumb(String TVSerie_thumb) {
        this.TVSerie_thumb = TVSerie_thumb;
    }

    public void setSeason_id(String season_id) {
        this.season_id = season_id;
    }

    public void setSeason_number(int season_number) {
        this.season_number = season_number;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setNumof_episodes(int numof_episodes) {
        this.numof_episodes = numof_episodes;
    }

    public void setSeason_poster(String season_poster) {
        this.season_poster = season_poster;
    }
}
