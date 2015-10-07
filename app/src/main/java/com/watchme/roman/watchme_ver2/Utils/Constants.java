package com.watchme.roman.watchme_ver2.Utils;

/**
 * Created by roman on 03/09/2015.
 */
public class Constants {

    // Base TMBD URL
    public static final String BASE_URL = "http://api.themoviedb.org/3/";

    // Constants for movie images
    public static final String BASE_IMG_URL = "http://image.tmdb.org/t/p/";
    public static final String SMALLEST_POSTER = "w185";
    public static final String SMALL_POSTER = "w185";
    public static final String BIG_POSTER = "w780";
    public static final String IMAGES_PATH = "images";

    // Parameters for buildURI
    public static final String MOVIE_PATH = "movie";
    public static final String TV_PATH = "tv";
    public static final String SORT_PARAM = "sort_by";
    public static final String API_PARAM = "api_key";
    public static final String PAGE = "page";
    public static final String VOTE_PATH = "vote_count.gte";
    public static final String DEFAULT_VOTE = "100";
    public static final String NEWEST_PARAM = "now_playing";
    public static final String VIDEOS_PATH = "videos";
    public static final String REVIEWS_PATH = "reviews";
    public static final String CREDITS_PATH = "credits";
    public static final String MOVIE_CREDITS_PATH = "movie_credits";
    public static final String PERSON_PATH = "person";
    public static final String SEASON_PATH = "season";
    public static final String SEARCH_PATH = "search";
    public static final String SEARCH_MULTI = "multi";
    public static final String QUERY_PATH = "query";


    // Parameters for YouTube API

    public static final String YOUTUBE_BASE_URL = "http://youtube.com/watch";

    // Intent extra tags
    public static final String ID_TAG = "id";
    public static final String ACTOR_ID_TAG = "id";
    public static final String ACTOR_IMG_TAG = "img";
    public static final String TITLE_TAG = "title";
    public static final String ACTOR_NAME_TAG = "actor_name";
    public static final String IS_TVSERIES_TAG = "is_tv";
    public static final String POPULAR_PATH = "popular";
    public static final String TOP_PATH = "top_rated";
    public static final String SEASON_NUM_TAG = "season_num";
    public static final String BACKDROP_TAG = "backdrop_tag";
    public static final String SEARCH_QUERY = "query";
}
