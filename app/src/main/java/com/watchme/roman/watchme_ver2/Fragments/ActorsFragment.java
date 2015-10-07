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

import com.watchme.roman.watchme_ver2.Activities.ActorDetailsActivity;
import com.watchme.roman.watchme_ver2.Adapters.ActorsRecyclerAdapter;
import com.watchme.roman.watchme_ver2.Model.Actors;
import com.watchme.roman.watchme_ver2.R;
import com.watchme.roman.watchme_ver2.Utils.Constants;
import com.watchme.roman.watchme_ver2.Utils.ParseJSON;
import com.watchme.roman.watchme_ver2.Utils.Utility;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by roman on 06/09/2015.
 */
public class ActorsFragment extends Fragment {


    // Movie id and boolean check if it's a movie or tv
    private String movie_id;
    private boolean is_tv;

    // RecyclerView
    private RecyclerView recyclerView;
    private ActorsRecyclerAdapter actorsRecyclerAdapter;
    private List<Actors> actorsList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getActivity().getIntent();

        if (intent != null) {
            movie_id = intent.getStringExtra(Constants.ID_TAG);
            is_tv = intent.getBooleanExtra(Constants.IS_TVSERIES_TAG, false);
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
        String path;
        if (is_tv)
            path = Constants.TV_PATH;
        else
            path = Constants.MOVIE_PATH;
        Uri uri = Utility.buildUriForCredits(getActivity(), movie_id, path);
        ParseJSON parseJSON = new ParseJSON();
        parseJSON.ParseMovieActors(uri, new ParseJSON.VolleyCallBackActors() {
            @Override
            public void onSuccess(final List<Actors> actorList) {
                actorsList = actorList;
                actorsRecyclerAdapter = new ActorsRecyclerAdapter(actorsList);
                recyclerView.setAdapter(actorsRecyclerAdapter);
                actorsRecyclerAdapter.SetOnItemClickListener(new ActorsRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getActivity(), ActorDetailsActivity.class);
                        intent.putExtra(Constants.ACTOR_ID_TAG, actorList.get(position).getActorID());
                        intent.putExtra(Constants.ACTOR_IMG_TAG, actorList.get(position).getActorIMG());
                        intent.putExtra(Constants.ACTOR_NAME_TAG, actorList.get(position).getActorName());
                        startActivity(intent);
                    }
                });
            }
        });
    }
}
