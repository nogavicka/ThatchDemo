package com.nogavicka.thatchdemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.toolbox.NetworkImageView;
import com.nogavicka.thatchdemo.databinding.FragmentMovieDetailBinding;
import com.nogavicka.thatchdemo.network.ImageRequester;

/** Fragment that displays movie details. */
public class MovieDetailFragment extends Fragment {

    private static final String MOVIE_TITLE = "movie_title";
    private static final String MOVIE_YEAR = "movie_year";
    private static final String MOVIE_POSTER = "movie_poster";

    private FragmentMovieDetailBinding binding;
    private ImageRequester imageRequester;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false);
        imageRequester = ImageRequester.getInstance();
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView movieTitle = (TextView) view.findViewById(R.id.movie_title);
        TextView movieYear = (TextView) view.findViewById(R.id.movie_year);
        NetworkImageView posterImage = (NetworkImageView) view.findViewById(R.id.poster_image);

        if (getArguments() != null) {
            movieTitle.setText(getArguments().getString(MOVIE_TITLE));
            movieYear.setText(getArguments().getString(MOVIE_YEAR));
            imageRequester.setImageFromUrl(posterImage, getArguments().getString(MOVIE_POSTER));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}