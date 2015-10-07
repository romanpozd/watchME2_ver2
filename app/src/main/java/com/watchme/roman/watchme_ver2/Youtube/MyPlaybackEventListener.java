package com.watchme.roman.watchme_ver2.Youtube;

import android.content.res.Resources;

import com.google.android.youtube.player.YouTubePlayer;
import com.watchme.roman.watchme_ver2.Activities.DetailsActivity;

/**
 * Created by roman on 24/09/2015.
 */

public class MyPlaybackEventListener implements YouTubePlayer.PlaybackEventListener{

    private YouTubePlayer youTubePlayer;

    public MyPlaybackEventListener(YouTubePlayer youTubePlayer){
        this.youTubePlayer = youTubePlayer;
    }
    @Override
    public void onPlaying() {
        youTubePlayer.setFullscreen(true);
    }

    @Override
    public void onPaused() {

    }

    @Override
    public void onStopped() {
    }

    @Override
    public void onBuffering(boolean b) {

    }

    @Override
    public void onSeekTo(int i) {

    }
}