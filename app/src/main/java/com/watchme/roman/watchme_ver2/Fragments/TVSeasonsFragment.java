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
import com.watchme.roman.watchme_ver2.Activities.SeasonActivity;
import com.watchme.roman.watchme_ver2.Adapters.SeasonsRecyclerAdapter;
import com.watchme.roman.watchme_ver2.Model.Season;
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
public class TVSeasonsFragment extends Fragment {

    // TV series id (from intent)
    String movie_series_id;

    // RecyclerView
    private RecyclerView recyclerView;
    private SeasonsRecyclerAdapter seasonsRecyclerAdapter;

    // Seasons List
    private List<Season> mySeasons;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getActivity().getIntent();
        if (intent != null)
            movie_series_id = intent.getStringExtra(Constants.ID_TAG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.recyclerlist_fragment, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
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
        Uri seasons = Utility.buildUriForMovieDetails(getActivity(),movie_series_id, Constants.TV_PATH);
        ParseJSON parseJSON = new ParseJSON();
        parseJSON.ParseTVSeriesSeasons(seasons, new ParseJSON.VolleyCallBackTVSeriesSeasons() {
            @Override
            public void onSuccess(List<Season> seasonList) {
                mySeasons = seasonList;
                seasonsRecyclerAdapter = new SeasonsRecyclerAdapter(mySeasons);
                recyclerView.setAdapter(seasonsRecyclerAdapter);
                seasonsRecyclerAdapter.SetOnItemClickListener(new SeasonsRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getActivity(), SeasonActivity.class);
                        intent.putExtra(Constants.TITLE_TAG, mySeasons.get(position).getTitle());
                        intent.putExtra(Constants.BACKDROP_TAG, mySeasons.get(position).getTVSerie_thumb());
                        intent.putExtra(Constants.ID_TAG, movie_series_id);
                        intent.putExtra(Constants.SEASON_NUM_TAG, mySeasons.get(position).getSeason_number());
                        startActivity(intent);
                    }
                });
            }
        });
    }
}
