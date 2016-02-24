package com.team19.gtmovies.pojo;

import android.media.Image;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Jim Jang on 2016-02-17.
 */
public class Movie implements Comparable<Movie> {
    // Remembers immediate things
    // Rest in JSON
    private int id;
    private String title;
    private ArrayList<Genre> genres = new ArrayList<>();
    private Image poster;
    private int rating = 0;
    private String description;
    private JSONObject fullInfo;
//    static String base =
//            "http://api.rottentomatoes.com/api/public/v1.0/movies/%d.json?apikey=%s";

    /**
     * Creates a placeholder movie for when Internet connection is unavailable
     *
     * @param j used in MovieListFragment to mark spot of placeholder Movie
     */
    public Movie(int j) {
        title = "Title" + j;
        rating = 10;
        description = "Description of the movie number " + j
                + " of the list of movies";

    }

    /**
     * Accepts a JSONObject and creates a Movie out of it
     *
     * @param jObj the JSONObject to base this Movie off of
     */
    public Movie(JSONObject jObj) {
        fullInfo = jObj;
        JSONArray tmpJArray;
        try {
            id = fullInfo.getInt("id");
            title = fullInfo.getString("title");
            description = fullInfo.getString("synopsis");
            rating = fullInfo.getJSONObject("ratings").getInt("critics_score");
//            tmpJArray = fullInfo.getJSONArray("genres");
//            for (int i = 0; i < tmpJArray.length(); i++) {
//                genres.add(Genre.toGenre(tmpJArray.getString(i)));
//            }
        } catch (JSONException e) {
            Log.e("JSON Error", "JSONException while parsing single movie" + e.toString());
        }
    }

    @Override
    public int compareTo(Movie other) {
        return this.id - other.getID();
    }

    /**
     * Returns the id of the Movie
     *
     * @return the int id of the Movie
     */
    public int getID() {
        return id;
    }

    /**
     * Returns the rating of the Movie
     *
     * @return the rating of the Movie
     */
    public int getRating() {
        return rating;
    }

    /**
     * Returns the description of the Movie
     *
     * @return the description of the Movie
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the title of the Movie
     *
     * @return the title of the Movie
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the Genres of the Movie as an ArrayList
     *
     * @return the Genres of the Movie as an ArrayList
     */
    public ArrayList<Genre> getGenres() {
        return genres;
    }

    /**
     * Returns backing JSONObject of this Movie
     *
     * @return the backing JSONObject of this Movie
     */
    public JSONObject getFullInfo() {
        return fullInfo;
    }
}