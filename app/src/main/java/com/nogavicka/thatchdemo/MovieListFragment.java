package com.nogavicka.thatchdemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.nogavicka.thatchdemo.databinding.FragmentMovieListBinding;
import com.nogavicka.thatchdemo.network.MovieInfoRequester;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment that displays list of movies. It contains a search. Once user searches for a string,
 * list of movies that correspond to that string from OMDB are listed in this fragment.
 */
public class MovieListFragment extends Fragment implements
        MovieInfoRequester.MovieInfoRequesterListener {

    private FragmentMovieListBinding binding;
    private RecyclerView recyclerView;
    private MovieListAdapter movieListAdapter;
    private MovieInfoRequester movieInfoRequester;
    private List<MovieInfo> movieInfoList = new ArrayList<>();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentMovieListBinding.inflate(inflater, container, false);
        movieInfoRequester = MovieInfoRequester.getInstance(this);
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        movieListAdapter = new MovieListAdapter(movieInfoList);
        recyclerView.setAdapter(movieListAdapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onStop () {
        super.onStop();
        if (movieInfoRequester != null) {
            movieInfoRequester.cancelQueue();
        }
    }

    @Override
    public void populateMovieList(List<MovieInfo> movieInfoList) {
        this.movieInfoList = movieInfoList;
        movieListAdapter.setMovieList(movieInfoList);
    }

    /** Search for query in movie requester. */
    private void search(String query) {
        if (movieInfoRequester != null) {
            movieInfoRequester.requestMovieInfo(query);
        }
    }

}