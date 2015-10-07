package com.watchme.roman.watchme_ver2.Utils;

import android.net.Uri;
import android.os.DropBoxManager;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.watchme.roman.watchme_ver2.Adapters.GridRecyclerAdapter;
import com.watchme.roman.watchme_ver2.Model.Actors;
import com.watchme.roman.watchme_ver2.Model.Episode;
import com.watchme.roman.watchme_ver2.Model.Movie;
import com.watchme.roman.watchme_ver2.Model.Review;
import com.watchme.roman.watchme_ver2.Model.SearchList;
import com.watchme.roman.watchme_ver2.Model.Season;
import com.watchme.roman.watchme_ver2.Model.Trailer;
import com.watchme.roman.watchme_ver2.Volley.VolleyController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by roman on 03/09/2015.
 */
public class ParseJSON extends Volley {

    private Uri uri;

    // Usable lists
    private List<Movie> movieList = new ArrayList<>();
    private List<Actors> actorsList = new ArrayList<>();
    private List<Review> reviewList = new ArrayList<>();
    private List<Season> seasonList = new ArrayList<>();
    private List<Episode> episodes = new ArrayList<>();
    private List<SearchList> searchLists = new ArrayList<>();

    // Adapters
    private GridRecyclerAdapter gridRecyclerAdapter;

    // Constants for JSON parsing
    private static final String RESULTS_ARRAY = "results";
    private static final String IMAGE_PATH = "poster_path";
    private static final String TITLE_PATH = "original_title";
    private static final String RATE_PATH = "vote_average";
    private static final String RELEASE_PATH = "release_date";
    private static final String OVERVIEW_PATH = "overview";
    private static final String GENRES_PATH = "genres";
    private static final String ID_PATH = "id";
    private static final String YOUTUBE_TRAILER_KEY = "key";
    private static final String BACK_DROP_PATH = "backdrop_path";
    private static final String COMPANY_PATH = "production_companies";
    private static final String RUNTIME_PATH = "runtime";
    private static final String NAME_PATH = "name";
    private static final String CAST_PATH = "cast";
    private static final String CHARACTER_PATH = "character";
    private static final String ACTOR_IMG = "profile_path";
    private static final String REVIEW_AUTHOR = "author";
    private static final String REVIEW_PATH = "content";
    private static final String BIOGRAPHY_PATH = "biography";
    private static final String BIRTHDAY_PATH = "birthday";
    private static final String BIRTH_PLACE = "place_of_birth";
    private static final String AIRING_PATH = "first_air_date";
    private static final String TV_TITLE = "original_name";
    private static final String VOTE_COUNT = "vote_count";
    private static final String STATUS_PATH = "status";


    // Actor constants
    private static final String KNOWN_FOR = "known_for";
    private static final String POPULARITY = "popularity";
    // TV Series constants
    private static final String FIRST_AIR = "first_air_date";
    private static final String AIR_DATE = "air_date";
    private static final String SEASONS_ARRAY = "seasons";
    private static final String CREATORS_ARRAY = "created_by";
    private static final String EPISODE_RUNTIME_ARRAY = "episode_run_time";
    private static final String SEASON_NUMBER = "season_number";
    private static final String EPISODES_COUNT = "episode_count";
    private static final String EPISODES_ARRAY = "episodes";
    private static final String EPISODE_IMG = "still_path";


    // Search constants
    private static final String MEDIA_TYPE = "media_type";

    // Default Constructor
    public ParseJSON() {
    }

    // Constructor with arguments
    public ParseJSON(Uri uri, GridRecyclerAdapter gridRecyclerAdapter, List<Movie> movieList) {
        this.uri = uri;
        this.gridRecyclerAdapter = gridRecyclerAdapter;
        this.movieList = movieList;
    }

    // Volley callback interface to return youtube object onResponse
    public interface VolleyCallBackYouTube {
        void onSuccess(Trailer trailer);
    }


    public interface VolleyCallBackActors {
        void onSuccess(List<Actors> actorList);
    }

    public interface VolleyCallBackReviews {
        void onSuccess(List<Review> reviewList);
    }

    public interface VolleyCallBackActorDetails {
        void onSuccess(Actors actor);
    }

    public interface VolleyCallBackMovieList {
        void onSuccess(List<Movie> movieList);
    }

    public interface VolleyCallBackSingleMovie {
        void onSuccess(Movie movie) throws UnsupportedEncodingException, JSONException;
    }


    public interface VolleyCallBackTVSeriesSeasons {
        void onSuccess(List<Season> seasonList);
    }

    public interface VolleyCallBackEpisodes {
        void onSuccess(List<Episode> episodeList);
    }

    public interface VolleyCallBackSearch {
        void onSuccess(List<SearchList> searchLists);
    }

    public interface VolleyEmptyCallBack {
        void onSuccess();

        void onError();
    }


    // Method that parses Search query
    public void ParseSearchQuery(final Uri searchUri, final VolleyCallBackSearch callBackSearch) throws UnsupportedEncodingException, JSONException {
        String url = searchUri.toString();
        //**************************************************************************//
        //* Check if there is data saved in cache, if yes,                         *//
        //* get the data and send it to setSearchQueryData method                  *//
        //* if there is no data make Volley request and take new data              *//
        //* for TMDB server.                                                       *//
        //**************************************************************************//
        Cache cache = VolleyController.getmInstance().getmRequestQueue().getCache();
        Cache.Entry entry = cache.get(url);
        if (entry != null) {
            String jsonString = new String(entry.data, "UTF-8");
            JSONObject jsonObject = new JSONObject(jsonString);
            setSearchQueryData(jsonObject, callBackSearch);
        } else {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            setSearchQueryData(jsonObject, callBackSearch);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    VolleyLog.d("MYTAG", "Error: " + volleyError.getMessage());
                }
            });
            // Add current Volley request to VolleyController
            VolleyController.getmInstance().addToRequestQueue(jsonObjectRequest);
        }
    }
    private void setSearchQueryData(JSONObject jsonObject, VolleyCallBackSearch volleyCallBackSearch){
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(RESULTS_ARRAY);
            JSONObject tempObj;
            for (int i = 0; i < jsonArray.length(); ++i) {
                tempObj = jsonArray.getJSONObject(i);
                switch (tempObj.getString(MEDIA_TYPE)) {
                    case Constants.PERSON_PATH:
                        SearchList person = new SearchList();
                        person.setImage(Constants.BASE_IMG_URL + Constants.SMALL_POSTER + tempObj.getString(ACTOR_IMG));
                        person.setName(tempObj.getString(NAME_PATH));
                        person.setID(tempObj.getString(ID_PATH));
                        person.setIs_actor(true);
                        person.setRating(tempObj.getDouble(POPULARITY));
                        JSONArray tempArray = tempObj.getJSONArray(KNOWN_FOR);
                        JSONObject knownForObj;
                        ArrayList<String> known = new ArrayList<>();
                        for (int j = 0; j < tempArray.length(); ++j) {
                            knownForObj = tempArray.optJSONObject(j);
                            known.add(knownForObj.getString(TITLE_PATH));
                        }
                        person.setKnownFOR(known);
                        searchLists.add(person);
                        break;
                    case Constants.MOVIE_PATH:
                        SearchList movie = new SearchList();
                        movie.setBackdrop(Constants.BASE_IMG_URL + Constants.BIG_POSTER + tempObj.getString(BACK_DROP_PATH));
                        movie.setImage(Constants.BASE_IMG_URL + Constants.SMALL_POSTER + tempObj.getString(IMAGE_PATH));
                        movie.setName(tempObj.getString(TITLE_PATH));
                        String[] separate = tempObj.getString(RELEASE_PATH).split("-");
                        if (separate[0].equals("null"))
                            movie.setYear(0);
                        else
                            movie.setYear(Integer.parseInt(separate[0]));
                        movie.setID(tempObj.getString(ID_PATH));
                        movie.setRating(tempObj.getDouble(RATE_PATH));
                        movie.setIs_movie(true);
                        searchLists.add(movie);
                        break;
                    case Constants.TV_PATH:
                        SearchList tvSeries = new SearchList();
                        tvSeries.setBackdrop(Constants.BASE_IMG_URL + Constants.BIG_POSTER + tempObj.getString(BACK_DROP_PATH));
                        tvSeries.setImage(Constants.BASE_IMG_URL + Constants.SMALL_POSTER + tempObj.getString(IMAGE_PATH));
                        tvSeries.setName(tempObj.getString(TV_TITLE));
                        separate = tempObj.getString(FIRST_AIR).split("-");
                        if (separate[0].equals("null"))
                            tvSeries.setYear(0);
                        else
                            tvSeries.setYear(Integer.parseInt(separate[0]));
                        tvSeries.setID(tempObj.getString(ID_PATH));
                        tvSeries.setRating(tempObj.getDouble(RATE_PATH));
                        tvSeries.setIs_tv(true);
                        searchLists.add(tvSeries);
                        break;
                    default:
                        continue;
                }

            }
            volleyCallBackSearch.onSuccess(searchLists);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Method that parses Season episodes
    public void ParseSeasonEpisodes(Uri episodesUri, final VolleyCallBackEpisodes callBackEpisodes) throws UnsupportedEncodingException, JSONException {
        String url = episodesUri.toString();
        //**************************************************************************//
        //* Check if there is data saved in cache, if yes,                         *//
        //* get the data and send it to setSeasonEpisodesData  method              *//
        //* if there is no data make Volley request and take new data              *//
        //* for TMDB server.                                                       *//
        //**************************************************************************//
        Cache cache = VolleyController.getmInstance().getmRequestQueue().getCache();
        Cache.Entry entry = cache.get(url);
        if (entry != null) {
            String jsonString = new String(entry.data, "UTF-8");
            JSONObject jsonObject = new JSONObject(jsonString);
            setSeasonEpisodesData(jsonObject, callBackEpisodes);
        } else {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                           setSeasonEpisodesData(jsonObject,callBackEpisodes);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    VolleyLog.d("MYTAG", "Error: " + volleyError.getMessage());
                }
            });
            // Add current Volley request to VolleyController
            VolleyController.getmInstance().addToRequestQueue(jsonObjectRequest);
        }
    }
    private void setSeasonEpisodesData(JSONObject jsonObject, VolleyCallBackEpisodes volleyCallBackEpisodes){
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(EPISODES_ARRAY);
            JSONObject tempObj;
            for (int i = 0; i < jsonArray.length(); ++i) {
                tempObj = jsonArray.getJSONObject(i);
                Episode episode = new Episode();
                episode.setEpisode_name(tempObj.getString(NAME_PATH));
                episode.setEpisode_thumb(tempObj.getString(EPISODE_IMG));
                episode.setEpisode_num(i + 1);
                episode.setEpisode_overview(tempObj.getString(OVERVIEW_PATH));
                episode.setRating(tempObj.getDouble(RATE_PATH));
                episodes.add(episode);
            }
            volleyCallBackEpisodes.onSuccess(episodes);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Method that parse TV series seasons
    public void ParseTVSeriesSeasons(Uri seasonsUri, final VolleyCallBackTVSeriesSeasons callBackTVSeriesSeasons) throws UnsupportedEncodingException, JSONException {
        String url = seasonsUri.toString();
        //**************************************************************************//
        //* Check if there is data saved in cache, if yes,                         *//
        //* get the data and send it to setTVSeriesSeasonsData method              *//
        //* if there is no data make Volley request and take new data              *//
        //* for TMDB server.                                                       *//
        //**************************************************************************//
        Cache cache = VolleyController.getmInstance().getmRequestQueue().getCache();
        Cache.Entry entry = cache.get(url);
        if (entry != null) {
            String jsonString = new String(entry.data, "UTF-8");
            JSONObject jsonObject = new JSONObject(jsonString);
            setTVSeriesSeasonsData(jsonObject, callBackTVSeriesSeasons);
        } else {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            setTVSeriesSeasonsData(jsonObject,callBackTVSeriesSeasons);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    VolleyLog.d("MYTAG", "Error: " + volleyError.getMessage());
                }
            });
            // Add current Volley request to VolleyController
            VolleyController.getmInstance().addToRequestQueue(jsonObjectRequest);
        }
    }
    private void setTVSeriesSeasonsData(JSONObject jsonObject, VolleyCallBackTVSeriesSeasons volleyCallBackTVSeriesSeasons){
        // Get Seasons
        JSONObject tempObj;
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(SEASONS_ARRAY);

            for (int i = 0; i < jsonArray.length(); ++i) {
                Season season = new Season();
                season.setTVSerie_thumb(jsonObject.getString(BACK_DROP_PATH));
                season.setTitle(jsonObject.getString(NAME_PATH));
                tempObj = jsonArray.getJSONObject(i);
                // Set season year
                String[] separate = tempObj.getString(AIR_DATE).split("-");
                if (separate[0].equals("null"))
                    season.setYear(0);
                else
                    season.setYear(Integer.parseInt(separate[0]));

                // If there is no episodes continue the loop without inserting this object
                if (tempObj.getInt(EPISODES_COUNT) == 0)
                    continue;
                season.setNumof_episodes(tempObj.getInt(EPISODES_COUNT));
                season.setSeason_id(tempObj.getString(ID_PATH));
                season.setSeason_number(tempObj.getInt(SEASON_NUMBER));
                season.setSeason_poster(tempObj.getString(IMAGE_PATH));
                seasonList.add(season);
            }

            volleyCallBackTVSeriesSeasons.onSuccess(seasonList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Method that parses TV Series complete details
    public void ParseTVSeriesCompleteDetails(final Uri tv_details_uri, final VolleyCallBackSingleMovie callBackMovie) throws UnsupportedEncodingException, JSONException {
        String url = tv_details_uri.toString();
        //**************************************************************************//
        //* Check if there is data saved in cache, if yes,                         *//
        //* get the data and send it to setTVSeriesCompleteDetails method          *//
        //* if there is no data make Volley request and take new data              *//
        //* for TMDB server.                                                       *//
        //**************************************************************************//
        Cache cache = VolleyController.getmInstance().getmRequestQueue().getCache();
        Cache.Entry entry = cache.get(url);
        if (entry != null) {
            String jsonString = new String(entry.data, "UTF-8");
            JSONObject jsonObject = new JSONObject(jsonString);
            setTVSeriesCompleteDetails(jsonObject, callBackMovie);
        } else {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            try {
                                setTVSeriesCompleteDetails(jsonObject,callBackMovie);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }

                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    VolleyLog.d("MYTAG", "Error: " + volleyError.getMessage());

                }
            });
            // Add current Volley request to VolleyController
            VolleyController.getmInstance().addToRequestQueue(jsonObjectRequest);
        }
    }
    private void setTVSeriesCompleteDetails(JSONObject jsonObject, VolleyCallBackSingleMovie volleyCallBackSingleMovie) throws UnsupportedEncodingException {
        Movie tvSeries = new Movie();

        try {

            JSONObject tempObj;
            // Get only the first creator
            JSONArray jsonArray = jsonObject.getJSONArray(CREATORS_ARRAY);
            tempObj = jsonArray.getJSONObject(0);
            tvSeries.setCreatorID(tempObj.getString(ID_PATH));

            // Get posters
            tvSeries.setBackdrop(jsonObject.getString(BACK_DROP_PATH));
            tvSeries.setPosterURL(jsonObject.getString(IMAGE_PATH));
            // Get TV series title
            tvSeries.setTitle(jsonObject.getString(TV_TITLE));
            // Create jsonArray to get all episodes runtime
            jsonArray = jsonObject.getJSONArray(EPISODE_RUNTIME_ARRAY);
            tvSeries.setRuntime(jsonArray.getInt(0));

            // Get TV series year
            String[] separate = jsonObject.getString(FIRST_AIR).split("-");
            if (separate[0].equals("null"))
                tvSeries.setYear(0);
            else
                tvSeries.setYear(Integer.parseInt(separate[0]));

            // Get Genres
            jsonArray = jsonObject.getJSONArray(GENRES_PATH);
            ArrayList<String> genres = new ArrayList<>();
            if (jsonArray.length() == 0)
                genres.add("--");
            else {
                for (int i = 0; i < jsonArray.length(); ++i) {
                    tempObj = jsonArray.getJSONObject(i);
                    genres.add(tempObj.getString(NAME_PATH));
                }
            }
            tvSeries.setGenres(genres);

            // Get ID
            tvSeries.setMovieId(jsonObject.getString(ID_PATH));
            tvSeries.setOverview(jsonObject.getString(OVERVIEW_PATH));

            // Get array of companies and store only the first one
            jsonArray = jsonObject.getJSONArray(COMPANY_PATH);
            if (jsonArray.length() == 0)
                tvSeries.setCompanyName("--");
            else {
                tempObj = jsonArray.getJSONObject(0);
                tvSeries.setCompanyName(tempObj.getString(NAME_PATH));
            }
            // Get rating
            tvSeries.setRating(jsonObject.getDouble(RATE_PATH));

            tvSeries.setVoteCount(jsonObject.getInt(VOTE_COUNT));
            volleyCallBackSingleMovie.onSuccess(tvSeries);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Method that parses specific actor filmography
    public void ParseActorFilmography(Uri filmography_uri, final VolleyCallBackMovieList actorFilmography) throws UnsupportedEncodingException, JSONException {
        String url = filmography_uri.toString();
        //***********************************************************************//
        //* Check if there is data saved in cache, if yes,                      *//
        //* get the data and send it to setActorFilmographyData method          *//
        //* if there is no data make Volley request and take new data           *//
        //* for TMDB server.                                                    *//
        //***********************************************************************//
        Cache cache = VolleyController.getmInstance().getmRequestQueue().getCache();
        Cache.Entry entry = cache.get(url);
        if (entry != null) {
            String jsonString = new String(entry.data, "UTF-8");
            JSONObject jsonObject = new JSONObject(jsonString);
            setActorFilmographyData(jsonObject, actorFilmography);
        } else {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            setActorFilmographyData(jsonObject,actorFilmography);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    VolleyLog.d("MYTAG", "Error: " + volleyError.getMessage());
                }
            });
            // Add current Volley request to VolleyController
            VolleyController.getmInstance().addToRequestQueue(jsonObjectRequest);
        }
    }

    private void setActorFilmographyData(JSONObject jsonObject, VolleyCallBackMovieList volleyCallBackMovieList){
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(CAST_PATH);
            for (int i = 0; i < jsonArray.length(); ++i) {
                JSONObject singleMovie = jsonArray.getJSONObject(i);
                Movie movie = new Movie();
                movie.setPosterURL(singleMovie.getString(IMAGE_PATH));
                movie.setTitle(singleMovie.getString(TITLE_PATH));
                String[] separate = singleMovie.getString(RELEASE_PATH).split("-");
                if (separate[0].equals("null"))
                    movie.setYear(0);
                else
                    movie.setYear(Integer.parseInt(separate[0]));
                movie.setMovieId(singleMovie.getString(ID_PATH));
                movie.setCharacter(singleMovie.getString(CHARACTER_PATH));
                movieList.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        volleyCallBackMovieList.onSuccess(movieList);
    }
    // Method that parses specific actor details
    public void ParseActorDetails(Uri actorUri, final VolleyCallBackActorDetails actorDetails) throws UnsupportedEncodingException, JSONException {
        String url = actorUri.toString();
        //*******************************************************************//
        //* Check if there is data saved in cache, if yes,                  *//
        //* get the data and send it to setActorDetailsData method          *//
        //* if there is no data make Volley request and take new data       *//
        //* for TMDB server.                                                *//
        //*******************************************************************//
        Cache cache = VolleyController.getmInstance().getmRequestQueue().getCache();
        Cache.Entry entry = cache.get(url);
        if (entry != null) {
            String jsonString = new String(entry.data, "UTF-8");
            JSONObject jsonObject = new JSONObject(jsonString);
            setActorDetailsData(jsonObject, actorDetails);
        } else {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                           setActorDetailsData(jsonObject, actorDetails);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    VolleyLog.d("MYTAG", "Error: " + volleyError.getMessage());
                }
            });
            // Add current Volley request to VolleyController
            VolleyController.getmInstance().addToRequestQueue(jsonObjectRequest);
        }
    }

    private void setActorDetailsData(JSONObject jsonObject, VolleyCallBackActorDetails volleyCallBackActorDetails){
        Actors actor = new Actors();
        try {
            actor.setBiography(jsonObject.getString(BIOGRAPHY_PATH));
            actor.setBirthday(jsonObject.getString(BIRTHDAY_PATH));
            actor.setBirthPlace(jsonObject.getString(BIRTH_PLACE));
            actor.setActorID(jsonObject.getString(ID_PATH));
            actor.setActorName(jsonObject.getString(NAME_PATH));
            actor.setActorIMG(jsonObject.getString(ACTOR_IMG));

            volleyCallBackActorDetails.onSuccess(actor);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Method that parses JSON with movie reviews
    public void ParseMovieReviews(Uri reviewUri, final VolleyCallBackReviews callBackReviews) throws UnsupportedEncodingException, JSONException {
        String url = reviewUri.toString();

        //*******************************************************************//
        //* Check if there is data saved in cache, if yes,                  *//
        //* get the data and send it to setMovieReviewsData method          *//
        //* if there is no data make Volley request and take new data       *//
        //* for TMDB server.                                                *//
        //*******************************************************************//
        Cache cache = VolleyController.getmInstance().getmRequestQueue().getCache();
        Cache.Entry entry = cache.get(url);
        if (entry != null) {
            String jsonString = new String(entry.data, "UTF-8");
            JSONObject jsonObject = new JSONObject(jsonString);
            setMovieReviewsData(jsonObject, callBackReviews);
        } else {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            setMovieReviewsData(jsonObject, callBackReviews);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    VolleyLog.d("MYTAG", "Error: " + volleyError.getMessage());
                }
            });
            // Add current Volley request to VolleyController
            VolleyController.getmInstance().addToRequestQueue(jsonObjectRequest);
        }
    }
    private void setMovieReviewsData(JSONObject jsonObject, VolleyCallBackReviews callBackReviews){
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(RESULTS_ARRAY);
            // If there is no reviews return custom string
            if (jsonArray.length() == 0) {
                Review review = new Review();
                review.setAuthor("null");
                review.setReview("No review available");
                reviewList.add(review);
                callBackReviews.onSuccess(reviewList);
            }
            for (int i = 0; i < jsonArray.length(); ++i) {
                Review review = new Review();
                JSONObject singleReview = jsonArray.getJSONObject(i);
                review.setAuthor(singleReview.getString(REVIEW_AUTHOR));
                review.setReview(singleReview.getString(REVIEW_PATH));

                // Add current object to reviews list
                reviewList.add(review);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        callBackReviews.onSuccess(reviewList);
    }

    // Method that parses JSON with actors
    public void ParseMovieActors(Uri actorsUri, final VolleyCallBackActors callBackActors) throws UnsupportedEncodingException, JSONException {
        String url = actorsUri.toString();

        //*******************************************************************//
        //* Check if there is data saved in cache, if yes,                  *//
        //* get the data and send it to setMovieActorsData method  *//
        //* if there is no data make Volley request and take new data       *//
        //* for TMDB server.                                                *//
        //*******************************************************************//
        Cache cache = VolleyController.getmInstance().getmRequestQueue().getCache();
        Cache.Entry entry = cache.get(url);
        if (entry != null) {
            String jsonString = new String(entry.data, "UTF-8");
            JSONObject jsonObject = new JSONObject(jsonString);
            setMovieActorsData(jsonObject, callBackActors);
        } else {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            setMovieActorsData(jsonObject,callBackActors);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    VolleyLog.d("MYTAG", "Error: " + volleyError.getMessage());
                }
            });
            // Add current Volley request to VolleyController
            VolleyController.getmInstance().addToRequestQueue(jsonObjectRequest);
        }
    }

    private void setMovieActorsData(JSONObject jsonObject, VolleyCallBackActors callBackActors) {
        JSONArray jsonArray;
        try {
            jsonArray = jsonObject.getJSONArray(CAST_PATH);
            for (int i = 0; i < jsonArray.length(); ++i) {
                JSONObject singleActor = jsonArray.getJSONObject(i);
                Actors actor = new Actors();
                actor.setActorName(singleActor.getString(NAME_PATH));
                // Check if there is known movie character
                if (singleActor.getString(CHARACTER_PATH).equals(""))
                    actor.setMovieCharacter("--");
                else
                    actor.setMovieCharacter(singleActor.getString(CHARACTER_PATH));
                actor.setActorID(singleActor.getString(ID_PATH));
                if (singleActor.getString(ACTOR_IMG).equals("null"))
                    //TODO if actor image is null insert custom placeholder, for now just skip
                    continue;
                actor.setActorIMG(singleActor.getString(ACTOR_IMG));

                // Add object to actorsList
                actorsList.add(actor);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        callBackActors.onSuccess(actorsList);
    }

    // Method that gets complete movie details
    public void ParseMovieCompleteDetails(final Uri complete_uri, final VolleyCallBackSingleMovie callbackSinglemovie) throws UnsupportedEncodingException, JSONException {
        String url = complete_uri.toString();
        //*******************************************************************//
        //* Check if there is data saved in cache, if yes,                  *//
        //* get the data and send it to setMovieCompleteDetailsData method  *//
        //* if there is no data make Volley request and take new data       *//
        //* for TMDB server.                                                *//
        //*******************************************************************//
        Cache cache = VolleyController.getmInstance().getmRequestQueue().getCache();
        Cache.Entry entry = cache.get(url);
        if (entry != null) {
            String jsonString = new String(entry.data, "UTF-8");
            JSONObject jsonObject = new JSONObject(jsonString);
            setMovieCompleteDetailsData(jsonObject, complete_uri, callbackSinglemovie);
        } else {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            try {
                                setMovieCompleteDetailsData(jsonObject, complete_uri, callbackSinglemovie);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    VolleyLog.d("MYTAG", "Error: " + volleyError.getMessage());
                }
            });
            // Add current Volley request to VolleyController
            VolleyController.getmInstance().addToRequestQueue(jsonObjectRequest);
        }
    }

    private void setMovieCompleteDetailsData(JSONObject jsonObject, Uri complete_uri, VolleyCallBackSingleMovie callBackSingleMovie) throws UnsupportedEncodingException, JSONException {
        Movie movie = new Movie();
        try {
            String[] separate;
            if (complete_uri.getPathSegments().get(1).equals(Constants.MOVIE_PATH)) {
                separate = jsonObject.getString(RELEASE_PATH).split("-");
                movie.setTitle(jsonObject.getString(TITLE_PATH));
            } else {
                separate = jsonObject.getString(AIRING_PATH).split("-");
                movie.setTitle(jsonObject.getString(TV_TITLE));
            }
            // Get movie status
            movie.setStatus(jsonObject.getString(STATUS_PATH));
            // Any movies dont have release year in the api so need to check this
            if (separate[0].equals("null") || separate[0].equals(""))
                movie.setYear(0);
            else
                movie.setYear(Integer.parseInt(separate[0]));
            movie.setVoteCount(jsonObject.getInt(VOTE_COUNT));
            movie.setOverview(jsonObject.getString(OVERVIEW_PATH));
            movie.setBackdrop(jsonObject.getString(BACK_DROP_PATH));
            movie.setMovieId(jsonObject.getString(ID_PATH));
            movie.setPosterURL(jsonObject.getString(IMAGE_PATH));
            movie.setRating(jsonObject.getDouble(RATE_PATH));
            // Get movie runtime
            if (jsonObject.getString(RUNTIME_PATH).equals("null"))
                movie.setRuntime(0);
            else
                movie.setRuntime(jsonObject.getInt(RUNTIME_PATH));

            // Get array of companies and store only the first one
            JSONArray jsonArray = jsonObject.getJSONArray(COMPANY_PATH);
            JSONObject tempObj;
            if (jsonArray.length() == 0)
                movie.setCompanyName("--");
            else {
                tempObj = jsonArray.getJSONObject(0);
                movie.setCompanyName(tempObj.getString(NAME_PATH));
            }
            // Get movie genres
            jsonArray = jsonObject.getJSONArray(GENRES_PATH);
            ArrayList<String> genres = new ArrayList<>();
            if (jsonArray.length() == 0)
                genres.add("--");
            else {
                for (int i = 0; i < jsonArray.length(); ++i) {
                    tempObj = jsonArray.getJSONObject(i);
                    genres.add(tempObj.getString(NAME_PATH));
                }
            }
            movie.setGenres(genres);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        callBackSingleMovie.onSuccess(movie);
    }

    public void ParseYouTubeDetails(Uri details_uri, final VolleyCallBackYouTube callback) throws UnsupportedEncodingException, JSONException {
        String url = details_uri.toString();

        //*************************************************************//
        //* Check if there is data saved in cache, if yes,            *//
        //* get the data and send it to setYouTubeDetailsData method  *//
        //* if there is no data make Volley request and take new data *//
        //* for TMDB server.                                          *//
        //*************************************************************//
        Cache cache = VolleyController.getmInstance().getmRequestQueue().getCache();
        Cache.Entry entry = cache.get(url);
        if (entry != null) {
            String jsonString = new String(entry.data, "UTF-8");
            JSONObject jsonObject = new JSONObject(jsonString);
            setYouTubeDetailsData(jsonObject, callback);
        } else {
            JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            setYouTubeDetailsData(jsonObject, callback);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("MYTAG", "Error: " + error.getMessage());
                }
            });
            // Add current Volley request to VolleyController
            VolleyController.getmInstance().addToRequestQueue(objectRequest);
        }
    }

    private void setYouTubeDetailsData(JSONObject jsonObject, VolleyCallBackYouTube callBackYouTube) {
        JSONArray jsonArray;
        Trailer trailer = new Trailer();
        try {
            jsonArray = jsonObject.getJSONArray(RESULTS_ARRAY);
            if (jsonArray.length() == 0)
                trailer.setKey("null");
            else {
                JSONObject singleTrailer = jsonArray.getJSONObject(0);
                trailer.setId(singleTrailer.getString(ID_PATH));
                trailer.setKey(singleTrailer.getString(YOUTUBE_TRAILER_KEY));
            }
            // Send trailer object with data to onSuccess method that will used in activity
            callBackYouTube.onSuccess(trailer);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void movieJSONparse(final VolleyEmptyCallBack volleyEmptyCallBack) throws UnsupportedEncodingException, JSONException {
        // URL of requested JSON object
        String url = uri.toString();

        //*************************************************************//
        //* Check if there is data saved in cache, if yes,            *//
        //* get the data and send it to setMovieJSONParseData method  *//
        //* if there is no data make Volley request and take new data *//
        //* for TMDB server.                                          *//
        //*************************************************************//
        Cache cache = VolleyController.getmInstance().getmRequestQueue().getCache();
        Cache.Entry entry = cache.get(url);
        if (entry != null) {
            String jsonString = new String(entry.data, "UTF-8");
            JSONObject jsonObject = new JSONObject(jsonString);
            setMovieJSONparseData(jsonObject, volleyEmptyCallBack);
        } else {
            JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            try {
                                setMovieJSONparseData(jsonObject, volleyEmptyCallBack);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    volleyEmptyCallBack.onError();
                }
            });
            // Add current Volley request to VolleyController
            VolleyController.getmInstance().addToRequestQueue(objectRequest);
        }
    }

    //********************************************************************************//
    //* Method that sets a data from jsonObject to Movie object                      *//
    //********************************************************************************//
    private void setMovieJSONparseData(JSONObject jsonObject, VolleyEmptyCallBack volleyEmptyCallBack) throws JSONException {
        JSONArray jsonArray;

        jsonArray = jsonObject.getJSONArray(RESULTS_ARRAY);
        Log.d("MYTAG", "JSON ARRAY: " + jsonArray.toString());
        for (int i = 0; i < jsonArray.length(); ++i) {
            JSONObject singleMovie = jsonArray.getJSONObject(i);
            Movie movie = new Movie();
            // Skip movies without a poster
            if (singleMovie.getString(IMAGE_PATH).equals("null"))
                continue;
            movie.setPosterURL(Constants.BASE_IMG_URL + Constants.SMALL_POSTER + singleMovie.getString(IMAGE_PATH));
            movie.setBackdrop(Constants.BASE_IMG_URL + Constants.BIG_POSTER + singleMovie.getString(BACK_DROP_PATH));
            String[] separate;
            if (uri.getPathSegments().get(1).equals(Constants.MOVIE_PATH)) {
                separate = singleMovie.getString(RELEASE_PATH).split("-");
                movie.setTitle(singleMovie.getString(TITLE_PATH));
            } else {
                separate = singleMovie.getString(AIRING_PATH).split("-");
                movie.setTitle(singleMovie.getString(TV_TITLE));
            }
            // Any movies dont have release year in the api so need to check this
            if (separate[0].equals("null") || separate[0].equals(""))
                movie.setYear(0);
            else
                movie.setYear(Integer.parseInt(separate[0]));

            movie.setMovieId(singleMovie.getString(ID_PATH));
            movie.setVoteCount(singleMovie.getInt(VOTE_COUNT));
            // Add object to ArrayList
            Log.d("MYTAG", "Movie: " + movie.getTitle() + " was added successfully!");
            movieList.add(movie);
            gridRecyclerAdapter.notifyItemInserted(movieList.size() - 1);
            volleyEmptyCallBack.onSuccess();
        }
    }
}
