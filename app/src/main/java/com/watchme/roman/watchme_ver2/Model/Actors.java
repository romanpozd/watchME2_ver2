package com.watchme.roman.watchme_ver2.Model;

/**
 * Created by roman on 06/09/2015.
 */
public class Actors {
    String actorName, actorIMG, movieCharacter, actorID;
    String biography, birthday, birthPlace;


    public Actors(){}

    // Getters

    public String getBiography() {
        return biography;
    }
    public String getBirthday() {
        return birthday;
    }
    public String getBirthPlace() {
        return birthPlace;
    }
    public String getActorName(){return actorName;}
    public String getActorIMG(){return actorIMG;}
    public String getMovieCharacter(){return movieCharacter;}
    public String getActorID(){return actorID;}

    // Setters

    public void setBiography(String biography) {
        this.biography = biography;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }
    public void setActorName(String actorName){this.actorName = actorName;}
    public void setActorIMG(String actorIMG){this.actorIMG = actorIMG;}
    public void setMovieCharacter(String movieCharacter){this.movieCharacter = movieCharacter;}
    public void setActorID(String id){this.actorID = id;}



}
