package com.watchme.roman.watchme_ver2.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.watchme.roman.watchme_ver2.Adapters.ReviewsRecyclerAdapter;
import com.watchme.roman.watchme_ver2.Model.Review;
import com.watchme.roman.watchme_ver2.R;
import com.watchme.roman.watchme_ver2.Utils.Constants;
import com.watchme.roman.watchme_ver2.Utils.ParseJSON;
import com.watchme.roman.watchme_ver2.Utils.Utility;
import org.json.JSONException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by roman on 07/09/2015.
 */
public class ReviewsFragment extends Fragment {

    // Movie id
    String movie_id;

    // RecyclerView
    private RecyclerView recyclerView;
    private ReviewsRecyclerAdapter reviewsRecyclerAdapter;
    private List<Review> reviewsList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getActivity().getIntent();

        if (intent != null) {
            movie_id = intent.getStringExtra(Constants.ID_TAG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerlist_fragment, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        try {
            UpdateList();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }

    private void UpdateList() throws UnsupportedEncodingException, JSONException {
        Uri uri = Utility.buildUriForReviews(getActivity(), movie_id);
        ParseJSON parseJSON = new ParseJSON();
        parseJSON.ParseMovieReviews(uri, new ParseJSON.VolleyCallBackReviews() {
            @Override
            public void onSuccess(List<Review> reviews) {
                reviewsList = reviews;
                reviewsRecyclerAdapter = new ReviewsRecyclerAdapter(reviewsList);
                recyclerView.setAdapter(reviewsRecyclerAdapter);
            }
        });
    }

}
