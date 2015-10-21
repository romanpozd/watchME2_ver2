package com.watchme.roman.watchme_ver2.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.vlonjatg.progressactivity.ProgressActivity;
import com.watchme.roman.moviesgreendao.model.DaoSession;
import com.watchme.roman.moviesgreendao.model.Movie;
import com.watchme.roman.moviesgreendao.model.MovieDao;
import com.watchme.roman.watchme_ver2.Model.Actors;
import com.watchme.roman.watchme_ver2.R;
import com.watchme.roman.watchme_ver2.Utils.AnalyticsApplication;
import com.watchme.roman.watchme_ver2.Utils.Constants;
import com.watchme.roman.watchme_ver2.Utils.ParseJSON;
import com.watchme.roman.watchme_ver2.Utils.Utility;
import com.watchme.roman.watchme_ver2.Utils.RoundedNetworkImageView;
import com.watchme.roman.watchme_ver2.Volley.VolleyController;
import com.watchme.roman.watchme_ver2.Volley.WatchMeApplication;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by roman on 15/09/2015.
 */
public class TVSeriesInfoFragment extends Fragment {

    private Animation animation;
    ImageLoader imageLoader = VolleyController.getmInstance().getmImageLoader();


    // ProgressBar
    ProgressActivity progressActivity;

    // TV series id (from intent)
    String movie_series_id;

    // TV Series details views
    private RoundedNetworkImageView movie_poster;
    private TextView movie_title, movie_year, movie_company_name, movie_runtime, movie_overview, movie_genres, tv_favorite, tv_voteCount;
    private ImageButton favorite;
    private RatingBar ratingBar;

    // Creator details views
    private RoundedNetworkImageView creatorImg;
    private TextView creatorName, creatorBirhtday, creatorBirthPlace, creatorBio;


    // Object from VolleyCallback
    private com.watchme.roman.watchme_ver2.Model.Movie tvSeriesObj;
    private Actors creator;

    // View
    private View view;

    // DAO object
    MovieDao tvSeriesDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startDaoSession();

        Intent intent = getActivity().getIntent();
        if (intent != null)
            movie_series_id = intent.getStringExtra(Constants.ID_TAG);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tv_series_info_fragment, container, false);

        // Favorite button shake
        animation = AnimationUtils.loadAnimation(getContext(), R.anim.shake);

        // TV series details
        tv_voteCount = (TextView) view.findViewById(R.id.tv_vote_count);
        ratingBar = (RatingBar) view.findViewById(R.id.rating_bar);
        tv_favorite = (TextView) view.findViewById(R.id.tv_favorite);
        favorite = (ImageButton) view.findViewById(R.id.btn_favorite);
        movie_poster = (RoundedNetworkImageView) view.findViewById(R.id.niv_series_poster);
        movie_poster.setCornerRadius((float) 5, (float) 5, (float) 5, (float) 5);
        movie_title = (TextView) view.findViewById(R.id.tv_series_title);
        movie_year = (TextView) view.findViewById(R.id.tv_series_year);
        movie_company_name = (TextView) view.findViewById(R.id.tv_series_company);
        movie_runtime = (TextView) view.findViewById(R.id.tv_series_runtime);
        movie_overview = (TextView) view.findViewById(R.id.tv_series_overview);
        movie_genres = (TextView) view.findViewById(R.id.tv_series_genres);

        // Creator details
        creatorImg = (RoundedNetworkImageView) view.findViewById(R.id.niv_creator_poster);
        creatorName = (TextView) view.findViewById(R.id.tv_creator_name);
        creatorBirhtday = (TextView) view.findViewById(R.id.tv_creator_birthday);
        creatorBirthPlace = (TextView) view.findViewById(R.id.tv_creator_birthplace);
        creatorBio = (TextView) view.findViewById(R.id.tv_creator_bio);

        // ProgressActivity
        progressActivity = (ProgressActivity) view.findViewById(R.id.progressActivity);
        progressActivity.showLoading();

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tracker tracker = ((AnalyticsApplication)getActivity().getApplication()).getmTracker();

                if (!isFavorited()) {
                    // Send GoogleAnalytics event tracker of Favorite button
                    tracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Favorite")
                            .setAction("Favorited tv series")
                            .setLabel(tvSeriesObj.getTitle())
                            .build());

                    favorite.setBackgroundResource(R.mipmap.btn_favorited);
                    favorite.setAnimation(animation);
                    tv_favorite.setText("Favorited");
                    insertDataToFavorite();
                } else {
                    tracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Favorite")
                            .setAction("Unfavorited tv series")
                            .setLabel(tvSeriesObj.getTitle())
                            .build());

                    getActivity().setResult(Activity.RESULT_OK);
                    favorite.setBackgroundResource(R.mipmap.btn_favorite);
                    favorite.setAnimation(animation);
                    tv_favorite.setText("Favorite");
                    deleteMovieDataFromFavorite();
                }
            }
        });

        try {
            UpdateTVSeries();
        } catch (UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
        }
        return view;
    }

    private void UpdateTVSeries() throws UnsupportedEncodingException, JSONException {
        Uri completeDetails = Utility.buildUriForMovieDetails(getActivity(), movie_series_id, Constants.TV_PATH);
        final ParseJSON parseJSON = new ParseJSON();
        parseJSON.ParseTVSeriesCompleteDetails(completeDetails, new ParseJSON.VolleyCallBackSingleMovie() {
            @Override
            public void onSuccess(com.watchme.roman.watchme_ver2.Model.Movie tvSeries) throws UnsupportedEncodingException, JSONException {
                // Store all objects from VolleyCallback
                tvSeriesObj = tvSeries;

                // Check witch favorite button
                if (isFavorited()) {
                    favorite.setBackgroundResource(R.mipmap.btn_favorited);
                    tv_favorite.setText("Favorited");
                } else {
                    favorite.setBackgroundResource(R.mipmap.btn_favorite);
                    tv_favorite.setText("Favorite");
                }

                tv_voteCount.setText(String.valueOf(tvSeries.getVoteCount()));
                movie_poster.setImageUrl(Constants.BASE_IMG_URL + Constants.SMALL_POSTER + tvSeriesObj.getPosterURL(), imageLoader);
                movie_title.setText(tvSeries.getTitle());
                movie_year.setText("Release: " + String.valueOf(tvSeriesObj.getYear()));
                ratingBar.setRating((float) tvSeries.getRating() / 2);
                movie_company_name.setText("Production: " + tvSeriesObj.getCompanyName());
                movie_runtime.setText("Episode runtime: " + String.valueOf(tvSeriesObj.getRuntime()));
                movie_overview.setText(tvSeriesObj.getOverview());
                if (tvSeriesObj.getGenres().size() == 1)
                    movie_genres.setText("Genres: " + tvSeriesObj.getGenres().get(0));

                else if (tvSeriesObj.getGenres().size() > 1) {
                    String joinedGenres = TextUtils.join(", ", tvSeriesObj.getGenres());
                    movie_genres.setText("Genres: " + joinedGenres);
                }

                Uri creatorUri = Utility.buildUriForPerson(getActivity(), tvSeriesObj.getCreatorID());
                parseJSON.ParseActorDetails(creatorUri, new ParseJSON.VolleyCallBackActorDetails() {
                    @Override
                    public void onSuccess(Actors actor) {
                        creator = actor;
                        creatorImg.setImageUrl(Constants.BASE_IMG_URL + Constants.SMALL_POSTER + creator.getActorIMG(), imageLoader);
                        creatorName.setText(creator.getActorName());
                        if (creator.getBirthday().equals("null") || creator.getBirthday().equals(""))
                            creatorBirhtday.setText("Birthday: no details");
                        else {
                            String temp = creator.getBirthday().replace("-", "/");
                            creatorBirhtday.setText("Birthday: " + temp);
                        }
                        if (creator.getBirthPlace().equals("null") || creator.getBirthPlace().equals(""))
                            creatorBirthPlace.setText("Birthplace: no details");
                        else
                            creatorBirthPlace.setText("Birthplace: " + creator.getBirthPlace());
                        creatorBio.setText(creator.getBiography());
                        if (creator.getBiography().equals("null") || creator.getBiography().equals(""))
                            creatorBio.setText("no details");
                        else
                            creatorBio.setText(creator.getBiography());
                    }
                });
                progressActivity.showContent();
            }
        });
    }

    private void startDaoSession() {
        DaoSession daoSession = ((WatchMeApplication) getActivity().getApplicationContext()).getDaoSession();
        tvSeriesDao = daoSession.getMovieDao();
    }

    private boolean isFavorited() {
        // Check if current movie already exist in favorite table
        List tempList = tvSeriesDao.queryBuilder().where(MovieDao.Properties.Title.eq(tvSeriesObj.getTitle())).list();
        if (tempList.size() != 0)
            return true;
        return false;
    }

    private void insertDataToFavorite() {
        // This method inserting movie details to movie-db of favorite movies
        Movie tvSeries = new Movie();


        tvSeries.setTitle(tvSeriesObj.getTitle());
        tvSeries.setPosterURL(tvSeriesObj.getPosterURL());
        tvSeries.setThumbURL(Constants.BASE_IMG_URL + Constants.BIG_POSTER + tvSeriesObj.getBackdrop());
        tvSeries.setRelease(tvSeriesObj.getYear());
        tvSeries.setMovie_id(movie_series_id);
        tvSeries.setTv(true);
        tvSeriesDao.insertOrReplace(tvSeries);

        // Set SnackBar after data is inserted
        Snackbar.make(view, tvSeriesObj.getTitle() + " Favorited!", Snackbar.LENGTH_LONG).show();


    }

    private void deleteMovieDataFromFavorite() {
        com.watchme.roman.moviesgreendao.model.Movie movie = tvSeriesDao.queryBuilder().where(MovieDao.Properties.Title.eq(tvSeriesObj.getTitle())).unique();
        // Set SnackBar after data is deleted
        Snackbar.make(view, tvSeriesObj.getTitle() + " Deleted!", Snackbar.LENGTH_LONG).show();
        tvSeriesDao.delete(movie);
    }

    @Override
    public void onPause() {
        super.onPause();
        VolleyController.getmInstance().getmRequestQueue().getCache().clear();
    }
}
