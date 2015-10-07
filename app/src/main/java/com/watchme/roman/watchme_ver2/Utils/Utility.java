package com.watchme.roman.watchme_ver2.Utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.watchme.roman.watchme_ver2.R;

/**
 * Created by roman on 03/09/2015.
 */
public class Utility {

    // Building URI with page, sort order and api key
    public static Uri buildUri(Context mContext, int page, String path, String sort) {
        String pageNum = String.valueOf(page);
        String sortOrder = sort;
        String path_name = path;
        String api_key = mContext.getString(R.string.API);
        Uri uri = Uri.parse(Constants.BASE_URL).buildUpon()
                // .appendEncodedPath(Constants.CATEGORY_PATH)
                .appendEncodedPath(path_name)
                .appendPath(Constants.POPULAR_PATH)
                .appendQueryParameter(Constants.PAGE, pageNum)
                .appendQueryParameter(Constants.SORT_PARAM, sortOrder)
                .appendQueryParameter(Constants.API_PARAM, api_key)
                .build();

        Log.d("MYTAG", "Uri successfully built: " + uri);
        return uri;
    }

    // Building URI with default year that set to 2010
    public static Uri buildUriWithVoteCount(Context mContext, int page, String sort) {
        String pageNum = String.valueOf(page);
        String sortOrder = sort;
        String api_key = mContext.getString(R.string.API);
        Uri uri = Uri.parse(Constants.BASE_URL).buildUpon()
                .appendPath(Constants.MOVIE_PATH)
                .appendEncodedPath(Constants.TOP_PATH)
                .appendQueryParameter(Constants.VOTE_PATH, Constants.DEFAULT_VOTE)
                .appendQueryParameter(Constants.PAGE, pageNum)
                .appendQueryParameter(Constants.SORT_PARAM, sortOrder)
                .appendQueryParameter(Constants.API_PARAM, api_key)
                .build();
        return uri;
    }

    // Uri to get url with newest movies
    public static Uri buildUriCurrentTheaters(Context mContext, int page, String path, String param) {
        String pageNum = String.valueOf(page);
        String path_name = path;
        String param_name = param;
        String api_key = mContext.getString(R.string.API);
        Uri uri = Uri.parse(Constants.BASE_URL).buildUpon()
                .appendPath(path_name)
                .appendEncodedPath(param_name)
                .appendQueryParameter(Constants.PAGE, pageNum)
                .appendQueryParameter(Constants.API_PARAM, api_key)
                .build();
        return uri;
    }

    // Uri to get movie's youtube url by movie id
    public static Uri buildUriForYouTube(Context mContext, String id) {
        String movie_id = id;
        String api_key = mContext.getString(R.string.API);
        Uri uri = Uri.parse(Constants.BASE_URL).buildUpon()
                .appendPath(Constants.MOVIE_PATH)
                .appendPath(movie_id)
                .appendEncodedPath(Constants.VIDEOS_PATH)
                .appendQueryParameter(Constants.API_PARAM, api_key)
                .build();
        return uri;
    }

    // Uri to get movie backdrop by movie id
    public static Uri buildUriForBackDrop(Context mContext, int id) {
        String movie_id = String.valueOf(id);
        String api_key = mContext.getString(R.string.API);
        Uri uri = Uri.parse(Constants.BASE_URL).buildUpon()
                .appendPath(Constants.MOVIE_PATH)
                .appendPath(movie_id)
                .appendEncodedPath(Constants.IMAGES_PATH)
                .appendQueryParameter(Constants.API_PARAM, api_key)
                .build();
        return uri;
    }

    // Uri to get movie duration and company name by movie id
    public static Uri buildUriForMovieDetails(Context mContext, String id, String path) {
        String movie_id = id;
        String path_name = path;
        String api_key = mContext.getString(R.string.API);
        Uri uri = Uri.parse(Constants.BASE_URL).buildUpon()
                .appendPath(path_name)
                .appendPath(movie_id)
                .appendQueryParameter(Constants.API_PARAM, api_key)
                .build();
        return uri;
    }

    // Uri to get actors by movie id
    public static Uri buildUriForCredits(Context mContext, String id, String path) {
        String movie_id = id;
        String path_name = path;
        String api_key = mContext.getString(R.string.API);
        Uri uri = Uri.parse(Constants.BASE_URL).buildUpon()
                .appendPath(path_name)
                .appendPath(movie_id)
                .appendPath(Constants.CREDITS_PATH)
                .appendQueryParameter(Constants.API_PARAM, api_key)
                .build();
        return uri;
    }

    // Uri to get movie reviews by movie id
    public static Uri buildUriForReviews(Context mContext, String id) {
        String movie_id = id;
        String api_key = mContext.getString(R.string.API);
        Uri uri = Uri.parse(Constants.BASE_URL).buildUpon()
                .appendPath(Constants.MOVIE_PATH)
                .appendPath(movie_id)
                .appendPath(Constants.REVIEWS_PATH)
                .appendQueryParameter(Constants.API_PARAM, api_key)
                .build();
        return uri;
    }

    // Uri to get specific person details by id
    public static Uri buildUriForPerson(Context mContext, String id) {
        String person_id = id;
        String api_key = mContext.getString(R.string.API);
        Uri uri = Uri.parse(Constants.BASE_URL).buildUpon()
                .appendPath(Constants.PERSON_PATH)
                .appendPath(person_id)
                .appendQueryParameter(Constants.API_PARAM, api_key)
                .build();
        return uri;
    }

    // Uri to get actor filmography
    public static Uri buildUriForPersonFilmography(Context mContext, String id) {
        String person_id = id;
        String api_key = mContext.getString(R.string.API);
        Uri uri = Uri.parse(Constants.BASE_URL).buildUpon()
                .appendPath(Constants.PERSON_PATH)
                .appendPath(person_id)
                .appendPath(Constants.MOVIE_CREDITS_PATH)
                .appendQueryParameter(Constants.API_PARAM, api_key)
                .build();
        return uri;
    }

    // Uri to get Season episodes
    public static Uri buildUriForSeasonEpisodes(Context mContext, String season_id, int seasonNum) {
        String seasonID = season_id;
        String seasonNUM = String.valueOf(seasonNum);
        String api_key = mContext.getString(R.string.API);
        Uri uri = Uri.parse(Constants.BASE_URL).buildUpon()
                .appendPath(Constants.TV_PATH)
                .appendPath(seasonID)
                .appendPath(Constants.SEASON_PATH)
                .appendPath(seasonNUM)
                .appendQueryParameter(Constants.API_PARAM, api_key)
                .build();
        return uri;
    }

    // Uri to get search query
    public static Uri buildUriForSearchQuery(Context mContext, String query) {
        String api_key = mContext.getString(R.string.API);
        String my_query = query;
        Uri uri = Uri.parse(Constants.BASE_URL).buildUpon()
                .appendPath(Constants.SEARCH_PATH)
                .appendPath(Constants.SEARCH_MULTI)
                .appendQueryParameter(Constants.QUERY_PATH, my_query)
                .appendQueryParameter(Constants.API_PARAM, api_key)
                .build();
        return uri;
    }
}
