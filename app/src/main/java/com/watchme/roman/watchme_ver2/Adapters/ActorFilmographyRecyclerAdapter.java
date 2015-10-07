package com.watchme.roman.watchme_ver2.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.watchme.roman.watchme_ver2.Model.Movie;
import com.watchme.roman.watchme_ver2.R;
import com.watchme.roman.watchme_ver2.Utils.Constants;
import com.watchme.roman.watchme_ver2.Volley.VolleyController;

import java.util.List;

/**
 * Created by roman on 08/09/2015.
 */
public class ActorFilmographyRecyclerAdapter extends RecyclerView.Adapter<ActorFilmographyRecyclerAdapter.CustomViewHolder> {

    // Movie list
    private List<Movie> movieList;

    // OnClickListener
    OnItemClickListener mItemClickListener;

    ImageLoader imageLoader = VolleyController.getmInstance().getmImageLoader();

    // RecyclerAdapter constructor with context and actors list
    public ActorFilmographyRecyclerAdapter(List<Movie> movieList) {
        this.movieList = movieList;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.filmography_single_row, viewGroup, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int position) {
        Movie movie = movieList.get(position);
        customViewHolder.movieThumb.setImageUrl(Constants.BASE_IMG_URL + Constants.SMALL_POSTER + movie.getPosterURL(), imageLoader);
        customViewHolder.movieTitle.setText(movie.getTitle());
        customViewHolder.movieRelease.setText("Release: " + String.valueOf(movie.getYear()));
        customViewHolder.actorCharacter.setText("Character: " + movie.getCharacter());
        customViewHolder.tag.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        protected NetworkImageView movieThumb;
        protected TextView movieTitle;
        protected TextView actorCharacter;
        protected TextView movieRelease;
        protected TextView tag;

        public CustomViewHolder(View itemView) {
            super(itemView);

            this.movieThumb = (NetworkImageView) itemView.findViewById(R.id.iv_filmography_thumb);
            this.movieTitle = (TextView) itemView.findViewById(R.id.tv_filmography_title);
            this.actorCharacter = (TextView) itemView.findViewById(R.id.tv_filmography_character);
            this.movieRelease = (TextView) itemView.findViewById(R.id.tv_filmography_release);
            this.tag = (TextView)itemView.findViewById(R.id.tv_tag);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            if (mItemClickListener != null)
                mItemClickListener.onItemClick(v, getPosition());
        }
    }
    public interface OnItemClickListener{
        public void onItemClick(View view, int position);
    }
    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }

}
