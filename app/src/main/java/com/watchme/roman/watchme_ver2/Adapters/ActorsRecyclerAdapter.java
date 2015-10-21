package com.watchme.roman.watchme_ver2.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.watchme.roman.watchme_ver2.Model.Actors;
import com.watchme.roman.watchme_ver2.R;
import com.watchme.roman.watchme_ver2.Utils.Constants;
import com.watchme.roman.watchme_ver2.Volley.VolleyController;

import java.util.List;

/**
 * Created by roman on 06/09/2015.
 */
public class ActorsRecyclerAdapter extends RecyclerView.Adapter<ActorsRecyclerAdapter.CustomViewHolder> {
    private List<Actors> actorsList;

    // OnClickListener
    OnItemClickListener mItemClickListener;


    ImageLoader imageLoader = VolleyController.getmInstance().getmImageLoader();

    // RecyclerAdapter constructor with context and actors list
    public ActorsRecyclerAdapter(List<Actors> actorsList) {
        this.actorsList = actorsList;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.actors_single_row, viewGroup, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int position) {
        Actors actor = actorsList.get(position);
        customViewHolder.actorIMG.setImageUrl(Constants.BASE_IMG_URL + Constants.SMALLEST_POSTER + actor.getActorIMG(), imageLoader);
        customViewHolder.actorName.setText(actor.getActorName());
        if (actor.getMovieCharacter() != "null")
            customViewHolder.actorCharacter.setText("Character: " + actor.getMovieCharacter());
    }

    @Override
    public int getItemCount() {
        return actorsList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected NetworkImageView actorIMG;
        protected TextView actorName;
        protected TextView actorCharacter;

        public CustomViewHolder(View itemView) {
            super(itemView);
            this.actorName = (TextView) itemView.findViewById(R.id.tv_actor_name);
            this.actorCharacter = (TextView) itemView.findViewById(R.id.tv_actor_character);
            this.actorIMG = (NetworkImageView) itemView.findViewById(R.id.iv_actor_img);
            actorIMG.setScaleType(ImageView.ScaleType.FIT_XY);
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
