package com.team19.gtmovies.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.team19.gtmovies.R;
import com.team19.gtmovies.activity.MovieDetailActivity;
import com.team19.gtmovies.activity.MovieListActivity;
import com.team19.gtmovies.data.IOActions;
import com.team19.gtmovies.pojo.Movie;

/**
 * A fragment representing a single Movie detail screen.
 * This fragment is either contained in a {@link MovieListActivity}
 * in two-pane mode (on tablets) or a {@link MovieDetailActivity}
 * on handsets.
 * @author Austin Leal
 * @version 1.0
 */
public class MovieDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_TITLE = "item_title";
    public static final String ARG_ITEM_ID = "item_id";
    public static final String ARG_ITEM_DESC = "item_description";
    public static final String ARG_ITEM_RATE = "item_rating";

    private static ListView commentsList;
    private TextView userRatingView;

    /**
     * The dummy content this fragment is presenting.
     */
    private Movie mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MovieDetailFragment() {
    }

    /**
     * creates new MovieDetailFragment instance
     * @return new MovieDetailFragment instance
     */
    public static MovieDetailFragment newInstance() {
        return new MovieDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_TITLE)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(getArguments().getString(ARG_ITEM_TITLE));
            }
        }
        mItem = IOActions.getMovieById(getArguments().getInt(ARG_ITEM_ID, -1));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_detail, container, false);
        //set description
        if (getArguments().containsKey(ARG_ITEM_DESC)) {
            ((TextView) rootView.findViewById(R.id.detailView))
                    .setText(getArguments().getString(ARG_ITEM_DESC));
        } else {
            Log.println(Log.ERROR, "GTMovie", "No description for movie");
        }
        //set tomato rating
        if (getArguments().containsKey(ARG_ITEM_RATE)) {
            ((TextView) rootView.findViewById(R.id.ratingView))
                    .setText(getArguments().getString(ARG_ITEM_RATE));
        } else {
            Log.println(Log.ERROR, "GTMovie", "No rating for movie");
        }
        userRatingView = ((TextView) rootView.findViewById(R.id.userRatingView));
        commentsList = (ListView) rootView.findViewById(R.id.listView);
        //if users have review this movie then it will exist
        //and we will get their averaged scores.
        if (mItem != null) {
            int temp = mItem.getUserRating();
            //set user rating
            userRatingView.setText(mItem.getUserRating() + "%");
            Log.println(Log.DEBUG, "GTMovies", "user rating: " + temp + "");
        } else {
            Log.println(Log.DEBUG, "GTMovies", "mItem is null in MovieDetailFragment");
        }
        return rootView;
    }

    public boolean updateFrag() {
        //update user rating
        mItem = IOActions.getMovieById(getArguments().getInt(ARG_ITEM_ID, -1));
        userRatingView.setText(mItem.getUserRating() + "%");
        return true;
    }
}
