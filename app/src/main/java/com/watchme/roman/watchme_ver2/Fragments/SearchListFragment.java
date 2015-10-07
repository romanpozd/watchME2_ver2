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
import com.watchme.roman.watchme_ver2.Activities.DetailsActivity;
import com.watchme.roman.watchme_ver2.Adapters.SearchListRecycleAdapter;
import com.watchme.roman.watchme_ver2.Model.SearchList;
import com.watchme.roman.watchme_ver2.R;
import com.watchme.roman.watchme_ver2.Utils.Constants;
import com.watchme.roman.watchme_ver2.Utils.ParseJSON;
import com.watchme.roman.watchme_ver2.Utils.Utility;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by roman on 24/09/2015.
 */
public class SearchListFragment extends Fragment {

    // RecyclerView
    private RecyclerView recyclerView;
    private SearchListRecycleAdapter searchListRecycleAdapter;

    // Search query
    private String searchQuery;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchQuery = getArguments().getString(Constants.SEARCH_QUERY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerlist_fragment, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setNestedScrollingEnabled(false);

        try {
            GetSearchQuery(searchQuery);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }

    private void GetSearchQuery(String query) throws UnsupportedEncodingException, JSONException {
        final String search_query = query;
        Uri uri = Utility.buildUriForSearchQuery(getActivity(), search_query);
        ParseJSON parseJSON = new ParseJSON();
        parseJSON.ParseSearchQuery(uri, new ParseJSON.VolleyCallBackSearch() {
            @Override
            public void onSuccess(final List<SearchList> searchList) {
                searchListRecycleAdapter = new SearchListRecycleAdapter(searchList);
                recyclerView.setAdapter(searchListRecycleAdapter);
                searchListRecycleAdapter.SetOnItemClickListener(new SearchListRecycleAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (searchList.get(position).is_actor()) {
                            Intent intent = new Intent(getActivity(), ActorDetailsActivity.class);
                            intent.putExtra(Constants.ACTOR_ID_TAG, searchList.get(position).getID());
                            intent.putExtra(Constants.ACTOR_IMG_TAG, searchList.get(position).getImage());
                            intent.putExtra(Constants.ACTOR_NAME_TAG, searchList.get(position).getName());
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(getActivity(), DetailsActivity.class);
                            intent.putExtra(Constants.BACKDROP_TAG, searchList.get(position).getBackdrop());
                            intent.putExtra(Constants.ID_TAG, searchList.get(position).getID());
                            intent.putExtra(Constants.TITLE_TAG, searchList.get(position).getName());
                            if (searchList.get(position).is_tv())
                                intent.putExtra(Constants.IS_TVSERIES_TAG, true);
                            else
                                intent.putExtra(Constants.IS_TVSERIES_TAG, false);
                            startActivity(intent);
                        }
                    }
                });
            }
        });

    }
}
