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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.vlonjatg.progressactivity.ProgressActivity;
import com.watchme.roman.moviesgreendao.model.DaoSession;
import com.watchme.roman.moviesgreendao.model.MovieDao;
import com.watchme.roman.watchme_ver2.Model.Movie;
import com.watchme.roman.watchme_ver2.Model.Trailer;
import com.watchme.roman.watchme_ver2.R;
import com.watchme.roman.watchme_ver2.Utils.Constants;
import com.watchme.roman.watchme_ver2.Utils.ParseJSON;
import com.watchme.roman.watchme_ver2.Utils.Utility;
import com.watchme.roman.watchme_ver2.Volley.RoundedNetworkImageView;
import com.watchme.roman.watchme_ver2.Volley.VolleyController;
import com.watchme.roman.watchme_ver2.Volley.WatchMeApplication;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by roman on 04/09/2015.
 */
public class MovieInfoFragment extends Fragment {

    private Animation animation;
    private YouTubeThumbnailLoader thumbnailLoader;

    private View view;
    private ImageLoader imageLoader = VolleyController.getmInstance().getmImageLoader();


    // ProgressBar
    private ProgressActivity progressActivity;

    // Movie id (from intent)
    private String movie_id;

    // Youtube Thumb
    private YouTubeThumbnailView youTubeThumbnailView;

    // View objects
    private RoundedNetworkImageView movie_poster;
    private TextView movie_title, movie_year, movie_company_name, movie_runtime, movie_overview,
            movie_genres, tv_favorite, tv_voteCount, tv_status;
    private Button play;
    private ImageButton favorite;
    private RatingBar ratingBar;

    // Trailer object with trailer key and id
    private Trailer trailerObj;

    // Movie object
    private Movie myMovie;

    // String for converted genres
    String joinedGenres;

    // DAO object
    MovieDao movieDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startDaoSession();

        Intent intent = getActivity().getIntent();
        if (intent != null)
            movie_id = intent.getStringExtra(Constants.ID_TAG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.movie_details_fragment, container, false);

        animation = AnimationUtils.loadAnimation(getContext(), R.anim.shake);

        tv_status = (TextView)view.findViewById(R.id.tv_status);
        tv_voteCount = (TextView) view.findViewById(R.id.tv_vote_count);
        ratingBar = (RatingBar) view.findViewById(R.id.rating_bar);
        tv_favorite = (TextView) view.findViewById(R.id.tv_favorite);
        youTubeThumbnailView = (YouTubeThumbnailView) view.findViewById(R.id.youtube_thumbnail);
        play = (Button) view.findViewById(R.id.btn_play);
        play.getBackground().setAlpha(200);
        favorite = (ImageButton) view.findViewById(R.id.btn_favorite);
        favorite.getBackground().setAlpha(150);
        movie_poster = (RoundedNetworkImageView) view.findViewById(R.id.niv_poster);
        movie_poster.setCornerRadius((float) 5, (float) 5, (float) 5, (float) 5);
        movie_title = (TextView) view.findViewById(R.id.tv_movie_title);
        movie_year = (TextView) view.findViewById(R.id.tv_movie_year);
        movie_company_name = (TextView) view.findViewById(R.id.tv_company);
        movie_runtime = (TextView) view.findViewById(R.id.tv_runtime);
        movie_overview = (TextView) view.findViewById(R.id.tv_overview);
        movie_genres = (TextView) view.findViewById(R.id.tv_genres);

        // ProgressActivity
        progressActivity = (ProgressActivity) view.findViewById(R.id.progressActivity);
        progressActivity.showLoading();

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFavorited()) {
                    favorite.setBackgroundResource(R.mipmap.btn_favorited);
                    favorite.setAnimation(animation);
                    tv_favorite.setText("Favorited");
                    insertMovieDataToFavorite();
                } else {
                    getActivity().setResult(Activity.RESULT_OK);
                    favorite.setBackgroundResource(R.mipmap.btn_favorite);
                    favorite.setAnimation(animation);
                    tv_favorite.setText("Favorite");
                    deleteMovieDataFromFavorite();
                }
            }
        });
        try {
            UpdateMovies();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }


    private void UpdateMovies() throws UnsupportedEncodingException, JSONException {

        Uri completeDetails = Utility.buildUriForMovieDetails(getActivity(), movie_id, Constants.MOVIE_PATH);
        ParseJSON parseJSON = new ParseJSON();
        parseJSON.ParseMovieCompleteDetails(completeDetails, new ParseJSON.VolleyCallBackSingleMovie() {
            @Override
            public void onSuccess(Movie singleMovie) {
                myMovie = singleMovie;
                // Check witch favorite button
                if (isFavorited()) {
                    favorite.setBackgroundResource(R.mipmap.btn_favorited);
                    tv_favorite.setText("Favorited");
                } else {
                    getActivity().setResult(Activity.RESULT_OK);
                    favorite.setBackgroundResource(R.mipmap.btn_favorite);
                    tv_favorite.setText("Favorite");
                }

                tv_status.setText("Status: " + myMovie.getStatus());
                movie_poster.setImageUrl(Constants.BASE_IMG_URL + Constants.SMALL_POSTER + myMovie.getPosterURL(), imageLoader);
                movie_title.setText(myMovie.getTitle());
                if (myMovie.getYear() == 0)
                    movie_year.setText("Release: no details");
                else
                    movie_year.setText("Release: " + String.valueOf(myMovie.getYear()));
                ratingBar.setRating((float) myMovie.getRating() / 2);
                tv_voteCount.setText(String.valueOf(myMovie.getVoteCount()));
                if (myMovie.getOverview().equals("null"))
                    movie_overview.setText("No overview");
                else
                    movie_overview.setText(myMovie.getOverview());
                if (myMovie.getRuntime() == 0)
                    movie_runtime.setText("Duration: --");
                else
                    movie_runtime.setText("Duration: " + String.valueOf(myMovie.getRuntime()) + " minutes");
                movie_company_name.setText("Production: " + myMovie.getCompanyName());

                if (myMovie.getGenres().size() == 1)
                    movie_genres.setText("Genres: " + myMovie.getGenres().get(0));

                else if (myMovie.getGenres().size() > 1) {
                    joinedGenres = TextUtils.join(", ", myMovie.getGenres());
                    movie_genres.setText("Genres: " + joinedGenres);
                }
            }
        });

        Uri youtubeUri = Utility.buildUriForYouTube(getActivity(), movie_id);
        parseJSON.ParseYouTubeDetails(youtubeUri, new ParseJSON.VolleyCallBackYouTube() {
            @Override
            public void onSuccess(final Trailer trailer) {
                // When callback successed get the parsed data for trailer and store in new object
                trailerObj = trailer;
                youTubeThumbnailView.initialize(getString(R.string.youtube_api_key), new YouTubeThumbnailView.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
                        thumbnailLoader = youTubeThumbnailLoader;
                        thumbnailLoader.setVideo(trailerObj.getKey());
                        progressActivity.showContent();
                    }

                    @Override
                    public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
                play.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = YouTubeStandalonePlayer.createVideoIntent(getActivity(), getString(R.string.youtube_api_key), trailerObj.getKey(), 0, true, true);
                        startActivity(intent);
                    }
                });

            }
        });

    }

    private void insertMovieDataToFavorite() {
        // This method inserting movie details to movie-db of favorite movies
        com.watchme.roman.moviesgreendao.model.Movie movie = new com.watchme.roman.moviesgreendao.model.Movie();

        movie.setTitle(myMovie.getTitle());
        movie.setMovie_id(myMovie.getMovieId());
        movie.setPosterURL(myMovie.getPosterURL());
        movie.setRelease(myMovie.getYear());
        movie.setTv(false);
        movieDao.insertOrReplace(movie);

        // Set SnackBar after data is inserted
        Snackbar.make(view, myMovie.getTitle() + " Favorited!", Snackbar.LENGTH_LONG).show();

    }

    private void deleteMovieDataFromFavorite() {
        com.watchme.roman.moviesgreendao.model.Movie movie = movieDao.queryBuilder().where(MovieDao.Properties.Title.eq(myMovie.getTitle())).unique();
        // Set SnackBar after data is deleted
        Snackbar.make(view, myMovie.getTitle() + " Deleted!", Snackbar.LENGTH_LONG).show();
        movieDao.delete(movie);
    }

    private void startDaoSession() {
        DaoSession daoSession = ((WatchMeApplication) getActivity().getApplicationContext()).getDaoSession();
        movieDao = daoSession.getMovieDao();
    }

    private boolean isFavorited() {
        // Check if current movie already exist in favorite table
        List tempList = movieDao.queryBuilder().where(MovieDao.Properties.Title.eq(myMovie.getTitle())).list();
        if (tempList.size() != 0)
            return true;
        return false;
    }

    @Override
    public void onPause() {
        super.onPause();
        VolleyController.getmInstance().getmRequestQueue().getCache().clear();
    }


}

