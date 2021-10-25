package com.nogavicka.thatchdemo.network;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nogavicka.thatchdemo.MovieInfo;
import com.nogavicka.thatchdemo.application.ThatchDemoApplication;

import java.util.List;

/**
 * Class that handles OMDB search requests with Volley.
 */
public class MovieInfoRequester {
    public interface MovieInfoRequesterListener {
        void populateMovieList(List<MovieInfo> movieInfoList);
    }

    private static final String TAG = "MovieInfoRequester";

    private static final String URL_PREFIX = "https://www.omdbapi.com/?apikey=748b22ac&";
    private static MovieInfoRequester instance = null;

    private final RequestQueue requestQueue;

    private final MovieInfoRequesterListener listener;

    public static MovieInfoRequester getInstance(MovieInfoRequesterListener listener) {
        if (instance == null) {
            instance = new MovieInfoRequester(listener);
        }
        return instance;
    }

    public MovieInfoRequester(MovieInfoRequesterListener listener) {
        this.listener = listener;
        this.requestQueue = Volley.newRequestQueue(ThatchDemoApplication.getAppContext());
        this.requestQueue.start();
    }

    public void cancelQueue() {
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG);
        }
    }

    public void requestMovieInfo(String query) {
        String url = String.format("%ss=%s&plot=full",URL_PREFIX, query);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                        List<MovieInfo> movieInfoList = MovieInfo.parseFromJson(response);
                        listener.populateMovieList(movieInfoList);
                },
                error -> {
                    Log.e(TAG, "Error requesting: " + error);
                });
        stringRequest.setTag(TAG);

        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest);
    }
}
