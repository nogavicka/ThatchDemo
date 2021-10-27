package com.nogavicka.thatchdemo;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class MovieListViewModel extends ViewModel {
    MutableLiveData<List<MovieInfo>> movieInfoLiveDataList;
    MutableLiveData<MovieInfo> selectedMovieInfo;

    public MovieListViewModel() {
        movieInfoLiveDataList = new MutableLiveData<>();
        selectedMovieInfo = new MutableLiveData<>();
    }

    public MutableLiveData<List<MovieInfo>> getMovieInfoLiveData() {
        return movieInfoLiveDataList;
    }

    public void setMovieInfoLiveData(List<MovieInfo> movieInfoList) {
        movieInfoLiveDataList.setValue(movieInfoList);
    }

    public MutableLiveData<MovieInfo> getSelected() {
        return selectedMovieInfo;
    }

    public void setSelected(MovieInfo movieInfo) {
        selectedMovieInfo.setValue(movieInfo);
    }
}
