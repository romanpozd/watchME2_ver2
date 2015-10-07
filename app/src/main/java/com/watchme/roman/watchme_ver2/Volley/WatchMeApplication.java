package com.watchme.roman.watchme_ver2.Volley;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.watchme.roman.moviesgreendao.model.DaoMaster;
import com.watchme.roman.moviesgreendao.model.DaoSession;
import com.watchme.roman.moviesgreendao.model.Movie;

/**
 * Created by roman on 19/09/2015.
 */
public class WatchMeApplication extends Application {

    public DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "movies-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

}
