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
import com.watchme.roman.watchme_ver2.Model.Review;
import com.watchme.roman.watchme_ver2.R;
import com.watchme.roman.watchme_ver2.Utils.Constants;
import com.watchme.roman.watchme_ver2.Volley.VolleyController;

import java.util.List;

/**
 * Created by roman on 07/09/2015.
 */
public class ReviewsRecyclerAdapter extends RecyclerView.Adapter<ReviewsRecyclerAdapter.CustomViewHolder> {

    private List<Review> reviewsList;

    // RecyclerAdapter constructor with list of review objects
    public ReviewsRecyclerAdapter(List<Review> reviewsList) {
        this.reviewsList = reviewsList;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_single_row, viewGroup, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int position) {
        Review reviewObj = reviewsList.get(position);
        if (reviewObj.getAuthor().equals("null"))
            customViewHolder.review.setText(reviewObj.getReview());
        else {
            customViewHolder.author.setText("Author: " + reviewObj.getAuthor());
            customViewHolder.review.setText(reviewObj.getReview());
        }
    }

    @Override
    public int getItemCount() {
        return reviewsList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView author, review;

        public CustomViewHolder(View itemView) {
            super(itemView);
            this.author = (TextView) itemView.findViewById(R.id.tv_author);
            this.review = (TextView) itemView.findViewById(R.id.tv_review);
        }
    }
}
