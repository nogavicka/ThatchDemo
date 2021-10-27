package com.nogavicka.thatchdemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.toolbox.NetworkImageView;
import com.nogavicka.thatchdemo.databinding.FragmentMovieDetailBinding;
import com.nogavicka.thatchdemo.network.ImageRequester;

/** Fragment that displays movie details. */
public class MovieDetailFragment extends Fragment {

    private FragmentMovieDetailBinding binding;
    private ImageRequester imageRequester;

    private MovieListViewModel movieListViewModel;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false);
        imageRequester = ImageRequester.getInstance();
        movieListViewModel = ViewModelProviders.of(getActivity()).get(MovieListViewModel.class);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Observer<MovieInfo> movieInfoObserver = movieInfo -> {
            if (movieInfo == null) {
                // Print error.
                return;
            }
            binding.movieTitle.setText(movieInfo.title);
            binding.movieYear.setText(movieInfo.year);
            binding.movieGenre.setText(movieInfo.genre);
            binding.moviePlot.setText(movieInfo.plot);
            imageRequester.setImageFromUrl(binding.posterImage, movieInfo.poster);
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        movieListViewModel.getSelected().observe(getViewLifecycleOwner(), movieInfoObserver);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}