package com.watchme.roman.watchme_ver2.Adapters;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.watchme.roman.watchme_ver2.Model.SearchList;
import com.watchme.roman.watchme_ver2.R;
import com.watchme.roman.watchme_ver2.Utils.Constants;
import com.watchme.roman.watchme_ver2.Volley.VolleyController;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

/**
 * Created by roman on 24/09/2015.
 */
public class SearchListRecycleAdapter extends RecyclerView.Adapter<SearchListRecycleAdapter.CustomViewHolder> {

    // Movie list
    private List<SearchList> searchList;

    // OnClickListener
    OnItemClickListener mItemClickListener;

    ImageLoader imageLoader = VolleyController.getmInstance().getmImageLoader();

    // RecyclerAdapter constructor with context and actors list
    public SearchListRecycleAdapter(List<SearchList> searchList) {
        this.searchList = searchList;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.filmography_single_row, viewGroup, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, int position) {
        SearchList search = searchList.get(position);

        customViewHolder.rowImage.setImageUrl(search.getImage(), imageLoader);
        customViewHolder.rowTitle.setText(search.getName());

        if (search.is_actor()) {
            customViewHolder.rowTag.setText(R.string.actor_tag);
            NumberFormat formatter = new DecimalFormat("#0.0");
            String temp = formatter.format(search.getRating());
            if (temp.equals("0.0"))
                customViewHolder.rowRating.setText("Popularity: --");
            else
                customViewHolder.rowRating.setText("Popularity: " + temp);

            if (search.getKnownFOR().size() == 1)
                customViewHolder.rowRelease.setText("Known for: \n" + search.getKnownFOR().get(0));
            else if (search.getKnownFOR().size() > 1) {
                String joinedKnownFor = TextUtils.join(",\n- ", search.getKnownFOR());
                customViewHolder.rowRelease.setText("Known for: \n- " + joinedKnownFor);
            }
        } else if (search.is_movie() || search.is_tv()) {

            if (search.is_movie())
                customViewHolder.rowTag.setText(R.string.movie_tag);
            else
                customViewHolder.rowTag.setText(R.string.tv_tag);
            if (search.getYear() == 0)
                customViewHolder.rowRelease.setText("Release: -- ");
            else
                customViewHolder.rowRelease.setText("Release: " + String.valueOf(search.getYear()));
            if (search.getRating() == 0.0)
                customViewHolder.rowRating.setText("Rating: -- ");
            else
                customViewHolder.rowRating.setText("Rating: " + search.getRating());
        }
        customViewHolder.rowTag.getBackground().setAlpha(100);

    }


    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected NetworkImageView rowImage;
        protected TextView rowTitle;
        protected TextView rowRating;
        protected TextView rowRelease;
        protected TextView rowTag;

        public CustomViewHolder(View itemView) {
            super(itemView);

            this.rowImage = (NetworkImageView) itemView.findViewById(R.id.iv_filmography_thumb);
            this.rowTitle = (TextView) itemView.findViewById(R.id.tv_filmography_title);
            this.rowRating = (TextView) itemView.findViewById(R.id.tv_filmography_character);
            this.rowRelease = (TextView) itemView.findViewById(R.id.tv_filmography_release);
            this.rowTag = (TextView) itemView.findViewById(R.id.tv_tag);
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
