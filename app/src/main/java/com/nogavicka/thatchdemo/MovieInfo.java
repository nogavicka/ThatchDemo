package com.nogavicka.thatchdemo;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/** Simple class representing results retrieved from OMDB. */
public class MovieInfo {
  private static final String TAG = MovieInfo.class.getName();
  public String title;
  public String year;
  public String genre;
  public String plot;
  public String poster;

  public MovieInfo(String title, String year, String genre, String plot, String poster) {
    this.title = title;
    this.year = year;
    this.genre = genre;
    this.plot = plot;
    this.poster = poster;
  }

  /**
   * Parses results that are returned in JSON.
   */
  public static List<MovieInfo> parseFromJson(String jsonString) {
    List<MovieInfo> movieInfoList = new ArrayList<>();
    try {
      JSONObject jsonObject = new JSONObject(jsonString);
      JSONArray jsonArray = jsonObject.getJSONArray("Search");
      for (int i = 0; i < jsonArray.length(); i++) {
        JSONObject searchResult = jsonArray.getJSONObject(i);
        movieInfoList.add(new MovieInfo(
                searchResult.optString("Title"),
                searchResult.optString("Year"),
                searchResult.optString("Genre"),
                searchResult.optString("Plot"),
                searchResult.optString("Poster")));
      }
    } catch (JSONException e) {
      Log.e(TAG, "Error parsing response: " + e);
    }
    return movieInfoList;
  }
}
