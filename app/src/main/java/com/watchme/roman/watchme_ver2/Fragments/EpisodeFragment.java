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

import com.android.volley.toolbox.NetworkImageView;
import com.watchme.roman.watchme_ver2.Adapters.EpisodeRecyclerAdapter;
import com.watchme.roman.watchme_ver2.Model.Episode;
import com.watchme.roman.watchme_ver2.R;
import com.watchme.roman.watchme_ver2.Utils.Constants;
import com.watchme.roman.watchme_ver2.Utils.ParseJSON;
import com.watchme.roman.watchme_ver2.Utils.Utility;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by roman on 17/09/2015.
 */
public class EpisodeFragment extends Fragment {


    // List of episodes
    private List<Episode> myEpisodes;

    // TV id and season num
    private String tv_id;
    private int season_num;

    // RecyclerView
    private RecyclerView recyclerView;
    private EpisodeRecyclerAdapter episodeRecyclerAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getActivity().getIntent();
        if (intent != null) {
            tv_id = intent.getStringExtra(Constants.ID_TAG);
            season_num = intent.getIntExtra(Constants.SEASON_NUM_TAG, 0);
        }
    }

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
        Uri uri = Utility.buildUriForSeasonEpisodes(getActivity(), tv_id, season_num);
        ParseJSON parseJSON = new ParseJSON();
        parseJSON.ParseSeasonEpisodes(uri, new ParseJSON.VolleyCallBackEpisodes() {
            @Override
            public void onSuccess(List<Episode> episodeList) {
                myEpisodes = episodeList;
                episodeRecyclerAdapter = new EpisodeRecyclerAdapter(myEpisodes);
                recyclerView.setAdapter(episodeRecyclerAdapter);
            }
        });
    }
}
