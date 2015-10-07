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

import com.watchme.roman.watchme_ver2.Activities.DetailsActivity;
import com.watchme.roman.watchme_ver2.Adapters.ActorFilmographyRecyclerAdapter;
import com.watchme.roman.watchme_ver2.Model.Movie;
import com.watchme.roman.watchme_ver2.R;
import com.watchme.roman.watchme_ver2.Utils.Constants;
import com.watchme.roman.watchme_ver2.Utils.ParseJSON;
import com.watchme.roman.watchme_ver2.Utils.Utility;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by roman on 08/09/2015.
 */
public class FilmographyFragment extends Fragment {

    // Movie objects list
    private List<Movie> actorFilmographyList;

    // RecyclerView
    private RecyclerView recyclerView;
    private ActorFilmographyRecyclerAdapter actorFilmographyRecyclerAdapter;

    // Actor id (from intent)
    String actor_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getActivity().getIntent();
        actor_id = intent.getStringExtra(Constants.ACTOR_ID_TAG);
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

        Uri uri = Utility.buildUriForPersonFilmography(getActivity(), actor_id);
        ParseJSON parseJSON = new ParseJSON();
        parseJSON.ParseActorFilmography(uri, new ParseJSON.VolleyCallBackMovieList() {
            @Override
            public void onSuccess(List<Movie> movieList) {
                actorFilmographyList = movieList;
                actorFilmographyRecyclerAdapter = new ActorFilmographyRecyclerAdapter(actorFilmographyList);
                recyclerView.setAdapter(actorFilmographyRecyclerAdapter);
                actorFilmographyRecyclerAdapter.SetOnItemClickListener(new ActorFilmographyRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getActivity(), DetailsActivity.class);
                        intent.putExtra(Constants.ID_TAG, actorFilmographyList.get(position).getMovieId());
                        startActivity(intent);
                    }
                });
            }
        });

    }
}
