package com.nogavicka.thatchdemo.network;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nogavicka.thatchdemo.MovieInfo;
import com.nogavicka.thatchdemo.MovieListViewModel;
import com.nogavicka.thatchdemo.application.ThatchDemoApplication;

import java.util.List;

/**
 * Class that handles OMDB search requests with Volley.
 */
public class MovieInfoRequester {
    private final MovieListViewModel movieListViewModel;

    private static final String TAG = MovieInfoRequester.class.getName();

    private static final String URL_PREFIX = "https://www.omdbapi.com/?apikey=748b22ac&";
    private static MovieInfoRequester instance = null;

    private final RequestQueue requestQueue;

    public static MovieInfoRequester getInstance(FragmentActivity fragment) {
        if (instance == null) {
            instance = new MovieInfoRequester(fragment);
        }
        return instance;
    }

    public MovieInfoRequester(FragmentActivity fragment) {
        this.requestQueue = Volley.newRequestQueue(ThatchDemoApplication.getAppContext());
        this.requestQueue.start();
        this.movieListViewModel = ViewModelProviders
                .of(fragment)
                .get(MovieListViewModel.class);
    }

    public void cancelQueue() {
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG);
        }
    }

    public void requestMovieInfo(String query) {
        String url = String.format("%ss=%s&plot=full", URL_PREFIX, query);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                        List<MovieInfo> movieInfoList = MovieInfo.parseFromJson(response);
                        movieListViewModel.setMovieInfoLiveData(movieInfoList);
                },
                error -> {
                    Log.e(TAG, "Error requesting: " + error);
                });
        stringRequest.setTag(TAG);

        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest);
    }

    public void requestMovieDetail(String imdbId) {
        String url = String.format("%si=%s&plot=full", URL_PREFIX, imdbId);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    MovieInfo movieInfo = MovieInfo.parseMovieInfoDetailsFromJson(response);
                    movieListViewModel.setSelected(movieInfo);
                },
                error -> {
                    Log.e(TAG, "Error requesting: " + error);
                });
        stringRequest.setTag(TAG);

        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest);
    }
}
