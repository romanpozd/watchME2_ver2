package com.watchme.roman.watchme_ver2;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

public class MoviesDaoGenerator {
    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "com.watchme.roman.moviesgreendao.model");

        Entity movie = schema.addEntity("Movie");
        movie.addIdProperty();
        movie.addStringProperty("movie_id");
        movie.addStringProperty("title");
        movie.addStringProperty("posterURL");
        movie.addStringProperty("thumbURL");
        movie.addIntProperty("release");
        movie.addBooleanProperty("tv");



        new DaoGenerator().generateAll(schema, "../app/src/main/java");
    }
}
