package com.watchme.roman.watchme_ver2.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.watchme.roman.watchme_ver2.Model.Season;
import com.watchme.roman.watchme_ver2.R;
import com.watchme.roman.watchme_ver2.Utils.Constants;
import com.watchme.roman.watchme_ver2.Volley.VolleyController;

import java.util.List;

/**
 * Created by roman on 17/09/2015.
 */
public class SeasonsRecyclerAdapter extends RecyclerView.Adapter<SeasonsRecyclerAdapter.CustomViewHolder> {

    // Seasons list
    private List<Season> seasonList;

    // OnClickListener
    OnItemClickListener mItemClickListener;

    ImageLoader imageLoader = VolleyController.getmInstance().getmImageLoader();

    // Constructor
    public SeasonsRecyclerAdapter(List<Season> seasonList) {
        this.seasonList = seasonList;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.seasons_single_row, viewGroup, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return seasonList.size();
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int position) {
        Season season = seasonList.get(position);
        customViewHolder.seasonsThumb.setImageUrl(Constants.BASE_IMG_URL + Constants.SMALL_POSTER + season.getSeason_poster(), imageLoader);
        customViewHolder.seasonsNum.setText("Season number: " + String.valueOf(season.getSeason_number()));
        customViewHolder.numofEpisodes.setText("Episodes: " + String.valueOf(season.getNumof_episodes()));
        customViewHolder.seasonRelease.setText("Release: " + String.valueOf(season.getYear()));
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected NetworkImageView seasonsThumb;
        protected TextView seasonsNum, numofEpisodes, seasonRelease;

        public CustomViewHolder(View itemView) {
            super(itemView);

            this.seasonsThumb = (NetworkImageView) itemView.findViewById(R.id.iv_season_thumb);
            this.seasonsNum = (TextView) itemView.findViewById(R.id.tv_seasons_number);
            this.numofEpisodes = (TextView) itemView.findViewById(R.id.tv_number_of_episodes);
            this.seasonRelease = (TextView) itemView.findViewById(R.id.tv_seasons_release);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null)
                mItemClickListener.onItemClick(v, getPosition());
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
