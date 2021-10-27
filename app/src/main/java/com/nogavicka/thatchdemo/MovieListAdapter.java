package com.nogavicka.thatchdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.nogavicka.thatchdemo.network.ImageRequester;
import com.nogavicka.thatchdemo.network.MovieInfoRequester;

import java.util.List;

/** Adapter that shows list of movies. */
public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MoviePosterViewHolder> {
    private static final String TAG = "MovieListAdapter";

    private final ImageRequester imageRequester;
    private final MovieInfoRequester movieInfoRequester;
    private final List<MovieInfo> movieInfoList;
    private final MovieListViewModel movieListViewModel;

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class MoviePosterViewHolder extends RecyclerView.ViewHolder {
        private final TextView movieTitle;
        private final TextView movieYear;
        private final NetworkImageView posterImage;

        public MoviePosterViewHolder(View view) {
            super(view);

            movieTitle = (TextView) view.findViewById(R.id.movie_title);
            movieYear = (TextView) view.findViewById(R.id.movie_year);
            posterImage = (NetworkImageView) view.findViewById(R.id.poster_image);
        }
    }
    /**
     * Initialize the dataset of the Adapter.
     *
     * @param movieInfoList containing the data to populate views to be used by RecyclerView.
     */
    public MovieListAdapter(FragmentActivity fragment, List<MovieInfo> movieInfoList) {
        this.movieInfoList = movieInfoList;
        movieListViewModel = ViewModelProviders.of(fragment).get(MovieListViewModel.class);
        imageRequester = ImageRequester.getInstance();
        movieInfoRequester = MovieInfoRequester.getInstance(fragment);
    }

    @NonNull
    @Override
    public MoviePosterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View layoutView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.movie_poster_card, viewGroup, false);

        return new MoviePosterViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviePosterViewHolder viewHolder, final int position) {
        if (movieInfoList != null && position < movieInfoList.size()) {
            MovieInfo movieInfo = movieInfoList.get(position);
            viewHolder.movieTitle.setText(movieInfo.title);
            viewHolder.movieYear.setText(String.valueOf(movieInfo.year));
            imageRequester.setImageFromUrl(viewHolder.posterImage, movieInfo.poster);

            viewHolder.itemView.setOnClickListener(v -> {
                if (movieInfoRequester != null) {
                    movieInfoRequester.requestMovieDetail(movieInfo.imdbId);
                }
                movieListViewModel.setSelected(movieInfo);
                Navigation.findNavController(v).navigate(
                        R.id.action_FirstFragment_to_SecondFragment);
            });
        }
    }

    @Override
    public int getItemCount() {
        return movieInfoList.size();
    }
}