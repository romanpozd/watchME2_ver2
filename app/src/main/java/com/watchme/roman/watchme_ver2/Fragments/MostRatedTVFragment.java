package com.watchme.roman.watchme_ver2.Fragments;

import android.net.Uri;
import com.watchme.roman.watchme_ver2.Utils.Constants;
import com.watchme.roman.watchme_ver2.Utils.ParseJSON;
import com.watchme.roman.watchme_ver2.Utils.Utility;
import org.json.JSONException;
import java.io.UnsupportedEncodingException;

/**
 * Created by roman on 16/09/2015.
 */
public class MostRatedTVFragment extends PopularTVFragment {


    @Override
    protected void UpdateTVSeries(int page) throws UnsupportedEncodingException, JSONException {

        Uri uri = Utility.buildUriCurrentTheaters(getActivity(), page, Constants.TV_PATH, Constants.TOP_PATH);
        ParseJSON parseJSON = new ParseJSON(uri,gridRecyclerAdapter,myTVList);
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
