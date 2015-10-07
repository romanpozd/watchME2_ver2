package com.watchme.roman.watchme_ver2.Youtube;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;

import com.google.android.youtube.player.YouTubePlayer;
import com.watchme.roman.watchme_ver2.Activities.DetailsActivity;

/**
 * Created by roman on 24/09/2015.
 */
public class MyPlayerStateChangeListener implements YouTubePlayer.PlayerStateChangeListener{
    YouTubePlayer youTubePlayer;
    Activity activity;

    public MyPlayerStateChangeListener(YouTubePlayer youTubePlayer, Activity activity){
        this.activity = activity;
        this.youTubePlayer = youTubePlayer;
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoaded(String s) {
        if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            youTubePlayer.play();
    }

    @Override
    public void onAdStarted() {

    }

    @Override
    public void onVideoStarted() {

    }

    @Override
    public void onVideoEnded() {

    }

    @Override
    public void onError(YouTubePlayer.ErrorReason errorReason) {

    }
}
