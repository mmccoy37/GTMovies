package com.team19.gtmovies.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.team19.gtmovies.R;
import com.team19.gtmovies.controller.ReviewController;
import com.team19.gtmovies.data.IOActions;
import com.team19.gtmovies.data.SingletonMagic;
import com.team19.gtmovies.fragment.MovieListFragment;
import com.team19.gtmovies.pojo.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashScreenActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    //private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    //private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    /*private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };*/
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    /*private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        try {
            MainActivity.setIOA(new IOActions(this));
        } catch (Exception e) {
            Log.e("GTMovies", e.getMessage());
        }
        if (!IOActions.userSignedIn()) {
            Log.println(Log.INFO, "GTMovies", "not signed in! starting LoginActivity.");
            startActivityForResult(new Intent(this, LoginActivity.class), 1);
            //TODO: onActivityResult which checks if user did login successfully
        } else {
            new UpdateUITask().execute(MovieListFragment.TOP_RENTALS_TAB);
        }


        /*mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);


        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        new UpdateUITask().execute(MovieListFragment.TOP_RENTALS_TAB);
        if (data != null && !data.getBooleanExtra("done", true)) {
        }
    }

    /**
     * Obtains the movies from the API
     * @param requestType differetiates new movies and top rental
     * @param movieList list of movies to get details about for recommendations
     */
    private void getMoviesFromAPI(final String requestType, final List<Movie> movieList) {
        //initializing new movieArray to return
        final List<Movie> movieArray = new ArrayList<>();
        Log.d("getMoviesFromAPI", "request" + requestType);

        // Creating the JSONRequest
        JsonObjectRequest movieRequest = null;
        if (requestType.equals(SingletonMagic.recommendations)) {
            Log.d("getMoviesFromAPI", "recommendations");
            //The last url request. Used to compare to find last movie in onResponse
            final String lastURL;
            if (movieList != null && movieList.size() > 0) {
                Movie movie = movieList.get(movieList.size() - 1);
                String movieID = SingletonMagic.search + "/" + movie.getID();
                lastURL = String.format(SingletonMagic.baseURL,
                        movieID, "", SingletonMagic.profKey);
            } else {
                return;
            }

            //Run for each of the recommended movies
            if (movieList != null) {
                for (Movie movie : movieList) {

                    //create the request
                    String movieID = SingletonMagic.search + "/" + movie.getID();
                    final String urlRaw = String.format(
                            SingletonMagic.baseURL, movieID, "", SingletonMagic.profKey);
                    movieRequest = new JsonObjectRequest(Request.Method.GET,
                            urlRaw, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject resp) {
                            if (resp == null) {
                                Log.e("JSONRequest ERROR", "getMovie Null Response Received");
                            }

                            movieArray.add(new Movie(resp));

                            //Check if last
                            if (urlRaw.equals(lastURL)) {
                                //Now update UI
                                MovieListFragment.setYourRecommendationsList(movieArray);
                            }

                            Log.d("getMovie movie success", "rec movie:" + new Movie(resp));
                            Log.d("getMovie volley success", "rec movie:" + urlRaw);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("getMovie VOLLEY FAIL", "Couldn't getJSON. rec movie:" + urlRaw);
                        }
                    });
                    SingletonMagic.getInstance(this).addToRequestQueue(movieRequest);
                }
            }
            return;
        } else {
            final String urlRaw = String.format(
                    SingletonMagic.baseURL, requestType, "", SingletonMagic.profKey);

            movieRequest = new JsonObjectRequest
                    (Request.Method.GET, urlRaw, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject resp) {
                            if (resp == null) {
                                Log.e("JSONRequest ERROR", "Null Response Received");
                            }

                            // put movies into a JSONArray
                            JSONArray tmpMovies = null;
                            try {
                                tmpMovies = resp.getJSONArray("movies");
                            } catch (JSONException e) {
                                Log.e("JSON ERROR", "Error when getting movies in Main.");
                            }
                            if (tmpMovies == null) {
                                Log.e("Movie Error", "movies JSONArray is null!");
                            }
                            for (int i = 0; i < tmpMovies.length(); i++) {
                                try {
                                    movieArray.add(new Movie(tmpMovies.getJSONObject(i)));
                                } catch (JSONException e) {
                                    Log.e("Movie Error", "Couldn't make Movie" + i + "in Main");
                                }
                            }

                            //Create proper MovieListFragment
                            String tab;
                            if (requestType.equals(SingletonMagic.newMovie)) {
                                Log.d("JinuMain", "newMovieFragment");

                                MovieListFragment.setNewMoviesList(movieArray);

                                tab = "newMovies";
                            } else if (requestType.equals(SingletonMagic.topRental)) {
                                Log.d("JinuMain", "topRentalFragment");
                                MovieListFragment.setTopRentalsList(movieArray);
                                tab = "topRentals";
                            } else {
                                Log.d("JinuMain", "nullFragment");
                                tab = "nullFragment";
                            }

                            Log.d("Main", "getMoviesFromAPI tab " + tab + " calling updateUI");
                            // Added the executePend....() thing to onScrollStateChanged
                            //
                            Log.d("JinuMain", "End of request");
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("VOLLEY FAIL", "Couldn't getJSON");
                        }
                    });
        }

        // Access the RequestQueue through singleton class.
        // Add Requests to RequestQueue
        SingletonMagic.getInstance(this).addToRequestQueue(movieRequest);
        for (int i = 0; i < 1; i++) {
            Log.i("Useless", "What is Android? What is Android? What is Android? What is Android?");
        }
        for (int i = 0; i < 1; i++) {
            Log.i("Useless", "Do Androids Dream of Electric Sheep?");
        }
    }

    private class UpdateUITask extends AsyncTask<Integer, Integer, Integer> {
        private List<Movie> movieList;

        @Override
        protected Integer doInBackground(Integer... params) {
            switch (params[0]) {
                case MovieListFragment.TOP_RENTALS_TAB:
                    getMoviesFromAPI(SingletonMagic.recommendations,
                            ReviewController.getRecommendations());
                    getMoviesFromAPI(SingletonMagic.newMovie, null);
                    getMoviesFromAPI(SingletonMagic.topRental, null);
                    for (int i = 0; i < 10; i++) {
                        for (int j = 0; j < 1000; j++) {
                            Log.i("Useless", "Well, this is a thing...A thing that doesn't work");
                        }
                        for (int j = 0; j < 1000; j++) {
                            Log.i("Useless", "Write your own code. I QUITTTTT!!!");
                        }
                    }
                    break;
                default:
            }
            return params[0];
        }

        @Override
        protected void onPostExecute(Integer integer) {
            finished();
        }
    }

    /**
     * What to do after UI updated
     */
    private void finished() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }




































    /*@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }*/

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    /*private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }*/
}