package com.watchme.roman.watchme_ver2.Adapters;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.watchme.roman.watchme_ver2.Model.Episode;
import com.watchme.roman.watchme_ver2.R;
import com.watchme.roman.watchme_ver2.Utils.Constants;
import com.watchme.roman.watchme_ver2.Volley.VolleyController;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

/**
 * Created by roman on 17/09/2015.
 */
public class EpisodeRecyclerAdapter extends RecyclerView.Adapter<EpisodeRecyclerAdapter.CustomViewHolder> {


    ImageLoader imageLoader = VolleyController.getmInstance().getmImageLoader();

    // List of Season episodes
    private List<Episode> episodeList;

    // Constructor
    public EpisodeRecyclerAdapter(List<Episode> episodeList) {
        this.episodeList = episodeList;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.episode_single_row, viewGroup, false);
        CustomViewHolder customViewHolder = new CustomViewHolder(view);
        return customViewHolder;

    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int position) {
        Episode episode = episodeList.get(position);
        customViewHolder.episode_thumb.setImageUrl(Constants.BASE_IMG_URL + Constants.BIG_POSTER + episode.getEpisode_thumb(), imageLoader);
        customViewHolder.episode_name.setText("Episode: " + episode.getEpisode_name());
        customViewHolder.episode_num.setText("Episode number: " + String.valueOf(episode.getEpisode_num()));
        DecimalFormat df = new DecimalFormat("#.#");
        String rate = df.format(episode.getRating());
        customViewHolder.episode_rating.setText("Rating: " + rate);
        customViewHolder.episode_overview.setText(episode.getEpisode_overview());

    }

    @Override
    public int getItemCount() {
        return episodeList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected NetworkImageView episode_thumb;
        protected TextView episode_name, episode_num,
                episode_rating, episode_overview;

        public CustomViewHolder(View itemView) {
            super(itemView);
            this.episode_thumb = (NetworkImageView) itemView.findViewById(R.id.niv_episode_thumb);
            this.episode_name = (TextView) itemView.findViewById(R.id.tv_episode_name);
            this.episode_num = (TextView) itemView.findViewById(R.id.tv_episode_number);
            this.episode_rating = (TextView) itemView.findViewById(R.id.tv_episode_rating);
            this.episode_overview = (TextView) itemView.findViewById(R.id.tv_episode_overview);
        }

    }


}
