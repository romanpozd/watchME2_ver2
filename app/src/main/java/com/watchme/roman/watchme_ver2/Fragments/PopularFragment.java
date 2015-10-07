package com.watchme.roman.watchme_ver2.Fragments;


import com.watchme.roman.watchme_ver2.Utils.Constants;
import com.watchme.roman.watchme_ver2.Utils.ParseJSON;
import com.watchme.roman.watchme_ver2.Utils.Utility;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;

/**
 * Created by roman on 03/09/2015.
 */
public class PopularFragment extends NewestFragment {

    @Override
    protected void UpdateMovies(int pageNum) throws UnsupportedEncodingException, JSONException {

        uri = Utility.buildUri(getActivity(), page, Constants.MOVIE_PATH, "popularity.desc");
        // Parse JSON from build URI
        ParseJSON parseJSON = new ParseJSON(uri, gridRecyclerAdapter, myMovieList);
        parseJSON.movieJSONparse(new ParseJSON.VolleyEmptyCallBack() {
            @Override
            public void onSuccess() {
                progressActivity.showContent();
            }

            @Override
            public void onError() {
                showErrorMsg();
            }
        });
    }


}
