package com.team19.gtmovies.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.team19.gtmovies.R;
import com.team19.gtmovies.activity.MovieDetailActivity;
import com.team19.gtmovies.activity.MovieListActivity;
import com.team19.gtmovies.data.CurrentState;
import com.team19.gtmovies.data.SingletonMagic;
import com.team19.gtmovies.pojo.Movie;

import java.util.Arrays;
import java.util.List;

/**
 * A fragment representing a single Movie detail screen.
 * This fragment is either contained in a {@link MovieListActivity}
 * in two-pane mode (on tablets) or a {@link MovieDetailActivity}
 * on handsets.
 * @author Austin Leal
 * @version 1.0
 */
public class MovieListFragment extends Fragment {
    //private static List<List<Movie>> tabMovieList = null;
    public static final int NEW_MOVIES_TAB = 0;
    public static final int TOP_RENTALS_TAB = 1;
    public static final int YOUR_RECOMMENDATIONS_TAB = 2;
    public static final int SEARCH = 3;

    private static int currentTab = 0;

    private static List<Movie> newMoviesList = null;
    private static List<Movie> topRentalsList = null;
    private static List<Movie> yourRecommendationsList = null;
    private static List<Movie> searchMovieList = null;

    private static boolean mTwoPane = false;
    private static boolean tabs = false;
    private boolean search = false;

    private static MovieRecyclerViewAdapter newMoviesAdapter;
    private static MovieRecyclerViewAdapter topRentalsAdapter;
    private static MovieRecyclerViewAdapter yourRecommendationsAdapter;
    //private static final MovieListFragment searchFragment = new MovieListFragment(3);

    private RecyclerView mRecyclerView;
    private MovieRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    //TODO: ERASE THIS FUNCTION AFTER DEBUG
    /**
     * Used only for debugging purposes
     * Returns the name of the said tab and the titles of the Movies associated with the tab
     * @param position the target position of the tab
     * @return the title of the tab and the title of the Movies associated with the tab
     */
    public String toPrettyString(int position) {
        String result = "";
        if (position == NEW_MOVIES_TAB) {
            result += "New Movies!\n";
            if (newMoviesList == null) {
                result += "List is null";
                return result;
            } else {
                for (Movie m : newMoviesList) {
                    result += m.getTitle();
                    result += "\n";
                }
                return result;
            }
        } else if (position == TOP_RENTALS_TAB) {
            result += "Top Rentals!\n";
            if (topRentalsList == null) {
                result += "List is null";
                return result;
            } else {
                for (Movie m : topRentalsList) {
                    result += m.getTitle();
                    result += "\n";
                }
                return result;
            }
        } else if (position == YOUR_RECOMMENDATIONS_TAB) {
            result += "Recommendations!\n";
            if (yourRecommendationsList == null) {
                result += "List is null";
                return result;
            } else {
                for (Movie m : yourRecommendationsList) {
                    result += m.getTitle();
                    result += "\n";
                }
                return result;
            }
        } else if (position == SEARCH) {
            result += "Search!\n";
            if (searchMovieList == null) {
                result += "List is null";
                return result;
            } else {
                for (Movie m : searchMovieList) {
                    result += m.getTitle();
                    result += "\n";
                }
                return result;
            }
        } else {
            return "Wrong position given";
        }
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MovieListFragment() {
        for (int i = 0; i < 1; i++) {
            Log.v("useless", "I'm taking a break to think about my life");
        }
    }

    /*
     * creates new MovieListFragment instance
     * @param page number for page
     * @return new MovieListFragment instance
     */
    public static MovieListFragment newInstance(int page) {
        /*switch (page) { //this was commented out before
            case 0: return tab0;
            case 1: return tab1;
            case 2: return tab2;
            case 3: return searchFragment;
            default: return null;
        }*/
        Log.d("MLFrag.newInstance", "page " + page + "\nstack:\n" + Arrays.toString(Thread.currentThread().getStackTrace()));
        Bundle mBundle = new Bundle();
        mBundle.putInt(ARG_ITEM_ID, page);
        MovieListFragment fragment = new MovieListFragment();
        fragment.setArguments(mBundle);
        return fragment;
    }

    /*
     * obtains MovieListFragment instance
     * @param page number for page
     * @return current MovieListFragment instance
     */
    /*public static MovieListFragment getInstance(int page) {
        switch (page) {
            case 0: return tab0;
            case 1: return tab1;
            case 2: return tab2;
            case 3: return searchFragment;
            default: return null;
        }
    }*/

    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.content);
            }
        }
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 1; j++) {
                Log.v("Useless", "Taking a minute to think about what it all means");
            }
            for (int j = 0; j < 1; j++) {
                Log.v("Useless", "Contemplating Life");
            }
        }
        View rootView = inflater.inflate(R.layout.movie_list, container, false);
        Log.d("MLFrag", "popping into onCreateView");
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.movie_list_view);
        if (rootView.findViewById(R.id.movie_list_view) == null) {
            Log.e("GTMovies", "Well there's your problem");
        }


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean scrolled;
            int oldy = -1;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == 0 && !scrolled && oldy < 0 && mAdapter.movieList != null
                        && mAdapter.movieList.size() > 4
                        && getArguments().getInt(ARG_ITEM_ID) != YOUR_RECOMMENDATIONS_TAB) {
                    if (getActivity().findViewById(R.id.main_toolbar).getVisibility()
                            == View.VISIBLE) {
                        //Todo: Jinu, this would be where we would refresh the page
                        Log.d("Scroll", "refresh page");
                    } else {
                        mAdapter.toggleTopBars(-1);
                    }
                } /*else if (newState == 0
                        && (getArguments().getInt(ARG_ITEM_ID) == YOUR_RECOMMENDATIONS_TAB
                        || mAdapter.getMovieList() == yourRecommendationsList)) {
                    Log.d("RecScroll", "called");
                    if (getActivity() != null && getActivity().findViewById(R.id.main_toolbar) != null) {
                        Log.d("RecScroll", "not null");
                        if (getActivity().findViewById(R.id.main_toolbar).getVisibility() == View.GONE) {
                            Log.d("RecScroll", "viewgone");
                            getActivity().findViewById(R.id.view_pager).getLayoutParams().height
                                    = CurrentState.getOpenHeight() - R.dimen.text_margin;
                        } else {
                            Log.d("RecScroll", "view visible");
                            //getActivity().findViewById(R.id.view_pager).getLayoutParams().height
                            //        = CurrentState.getClosedHeight() - R.dimen.text_margin;
                        }
                    }*/
                if (newState == 2 && mAdapter.movieList != null
                        && mAdapter.movieList.size() > 4
                        && getArguments().getInt(ARG_ITEM_ID) != YOUR_RECOMMENDATIONS_TAB) {
                        mAdapter.toggleTopBars(oldy);
                        Log.d("Toggle", "2scroll oldy=" + oldy);
                }
                super.onScrollStateChanged(recyclerView, newState);
                Log.d("OnScrollState", "newState=" + newState);
                scrolled = false;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d("OnScroll", "let me know dx=" + dx + " dy=" + dy);
                scrolled = true;
                if (dy != 0) {
                    oldy = dy;
                }
            }
        });



        mLayoutManager = new LinearLayoutManager(getActivity());

        /*
        if (search) {
            if (searchMovieList != null) {
                mAdapter = new MovieRecyclerViewAdapter(searchMovieList);
            }
            search = false;
        } else if (getArguments() != null) {
            Log.d("GTMovies: onCreateView", currentTab + "");
            mAdapter = new MovieRecyclerViewAdapter(tabMovieList.get(getArguments().getInt(ARG_ITEM_ID)));
            //Log.d("GTMovies", tabMovieList.get(currentTab).toString());
            tabs = false;
        } else if (getArguments() != null) {
            Log.d("GTMovies: getargs", getArguments().toString());
        } else {
            Log.e("GTMovies:", "No bundle.");
        }*/
        if (getArguments() != null) {
            switch (getArguments().getInt(ARG_ITEM_ID)) {
                case NEW_MOVIES_TAB:
                    Log.d("JinuMLFrag", "newMoviesList RecyclerViewAdapter");
                    if (newMoviesList == null || newMoviesList.isEmpty()) {
                        Log.e("MovieListFragment", "newMovieList is null or empty");
                    } else {
                        Log.d("MovieListFragment", "newMovieList is ok " + newMoviesList);
                    }
                    newMoviesAdapter = new MovieRecyclerViewAdapter(newMoviesList);
                    mAdapter = newMoviesAdapter;
                    Log.d("MLFrag", "newMoviesList " + mAdapter);
                    break;
                case TOP_RENTALS_TAB:
                    Log.d("JinuMLFrag", "topRentalsList RecyclerViewAdapter");
                    if (null == topRentalsList || topRentalsList.isEmpty()) {
                        Log.e("MovieListFragment", "topRentalsList is null or empty");
                    } else {
                        Log.d("MovieListFragment", "topRentalsList is ok " + topRentalsList);
                    }
                    topRentalsAdapter = new MovieRecyclerViewAdapter(topRentalsList);
                    mAdapter = topRentalsAdapter;
                    Log.d("MLFrag", "topRentalsList " + mAdapter);
                    break;
                case YOUR_RECOMMENDATIONS_TAB:
                    Log.d("JinuMLFrag", "yourRecommendationsList RecyclerViewAdapter");
                    if (null == yourRecommendationsList || yourRecommendationsList.isEmpty()) {
                        Log.e("MovieListFragment", "yourRecommendationsList is null or empty");
                    } else {
                        Log.d("MovieListFragment", "yourRecList is ok " + yourRecommendationsList);
                    }
                    yourRecommendationsAdapter = new MovieRecyclerViewAdapter(yourRecommendationsList);
                    mAdapter = yourRecommendationsAdapter;
                    Log.d("MLFrag", "yourRecList " + mAdapter);
                    break;
                case SEARCH:
                    Log.d("JinuMLFrag", "searchMovieList RecyclerViewAdapter");
                    if (null == searchMovieList || searchMovieList.isEmpty()) {
                        Log.e("MovieListFragment", "searchMovieList is null or empty");
                    } else {
                        Log.d("MovieListFragment", "searchMovieList is ok " + searchMovieList);
                    }
                    mAdapter = new MovieRecyclerViewAdapter(searchMovieList);
                    Log.d("MLFrag", "searchMovieList " + mAdapter);
                    break;
                default:
                    Log.e("MLFrag", "Incorrect int for fragment list.");
            }
        }

        if (mRecyclerView != null) {
            mRecyclerView.setAdapter(mAdapter);
        } else {
            Log.e("MLFrag", "This shouldn't be null :)");
        }

        if (rootView.findViewById(R.id.movie_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;                        //TODO: implement twopane check for extra credit
        }

        return rootView;
    }

    /*
     * Fills movie list if able to. If unable to, sets movieList to default.
     * @param list list to set movie list to
     * @return true if successfully set to provided argument false if unable to
     */
    /*public static boolean fillTabMovieList(List<List<Movie>> list) {
        if (list != null) {
            tabMovieList = list;
            return true;
        } else {
            tabMovieList = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                ArrayList<Movie> arrayList = new ArrayList<>();
                for (int j = 0; j < 3 * (i + 1); j++) {
                    Movie movie;
                    movie = new Movie(j);
                    arrayList.add(movie);
                }
                tabMovieList.add(arrayList);
            }
        }
        return false;
    }*/

    /**
     * Changes newMovieList.
     * @param list list to set new movies list to
     * @return true if successfully set to provided argument false if unable to
     */
    public static boolean setNewMoviesList(List<Movie> list) {
        Log.d("MLFrag", "getMovie setNewMovies " + list);
        if (list != null) {
            newMoviesList = list;
            return true;
        }
        return false;
    }

    /**
     * Changes topRentalsList.
     * @param list list to set top rentals list to
     * @return true if successfully set to provided argument false if unable to
     */
    public static boolean setTopRentalsList(List<Movie> list) {
        Log.d("MLFrag", "getMovie setTopRentals " + list);
        if (list != null) {
            topRentalsList = list;
            return true;
        }
        return false;
    }

    /**
     * Changes movielist.
     * @param list list to set recommendations to
     * @return true if successfully set to provided argument false if unable to
     */
    public static boolean setYourRecommendationsList(List<Movie> list) {
        Log.d("MLFrag", "getMovie setYourRecommendations " + list);
        if (list != null) {
            yourRecommendationsList = list;
            return true;
        }
        return false;
    }

    /**
     * Changes searchMovieList on search
     * @param list new list of movies from search query
     * @return boolean if list can be used to replace movie search list
     */
    public static boolean setSearchMovieList(List<Movie> list) {
        if (list != null) {
            searchMovieList = list;
            return true;
        }
        return false;
    }

    /**
     * Indicates whether newMoviesList has been set
     * @return True if newMoviesList not null, false otherwise
     */
    public static boolean hasNewMoviesList() {
        return newMoviesList != null;
    }

    /**
     * Indicates whether topRentalsList has been set
     * @return True if topRentalsList not null, false otherwise
     */
    public static boolean hasTopRentalsList() {
        if (topRentalsList == null) {
            Log.d("MLFrag", "Rent null");
        } else {
            Log.d("MLFrag", "Rent exists: " + topRentalsList);
        }
        return topRentalsList != null;
    }

    /**
     * Indicates whether yourRecommendationsList has been set
     * @return True if yourRecommendationsList not null, false otherwise
     */
    public static boolean hasYourRecommendationsList() {
        if (yourRecommendationsList == null) {
            Log.d("MLFrag", "Rec null");
        } else {
            Log.d("MLFrag", "Rec exists: " + yourRecommendationsList);
        }
        return yourRecommendationsList != null;
    }

    /**
     * public getter for newMovieList
     * @return current newMovieList
     */
    public static List getNewMovieList() {
        return newMoviesList;
    }

    /**
     * Sets current tab
     * @param position position of current tab
     */
    public static void setTabPosition(int position) {
        currentTab = position;
    }


    /**
     * Sets fragment to display search
     */
    public void setSearch() {
        search = true;
    }

    /**
     * Sets fragment to display tabs
     */
    public static void setTabs() {
        tabs = true;
    }

    /**
     * A getter for where or not able to display in two panes
     * @return true if able to display in two panes
     */
    public static boolean isTwoPane() {
        return mTwoPane;
    }

    @Override
    public String toString() {
        return super.toString() + " ARG_ITEM_ID=" + getArguments().getInt(ARG_ITEM_ID)
                + " newMoviesList=" + hasNewMoviesList()
                + " topRentalsList=" + hasTopRentalsList()
                + " youRecommendationsList=" + hasYourRecommendationsList()
                + "\ndisplay: " + toPrettyString(getArguments().getInt(ARG_ITEM_ID)) + "\n\n";
    }

    /**
     * Updates array in ArrayList
     * @param page page to change
     */
    public static void updateAdapter(int page) {
        switch (page) {
            case NEW_MOVIES_TAB:
                if (newMoviesAdapter != null)
                    newMoviesAdapter.swapList(newMoviesList);
                break;
            case TOP_RENTALS_TAB:
                if (topRentalsAdapter != null)
                    topRentalsAdapter.swapList(topRentalsList);
                break;
            case YOUR_RECOMMENDATIONS_TAB:
                if (yourRecommendationsAdapter != null)
                    yourRecommendationsAdapter.swapList(yourRecommendationsList);
                break;
            default:
                Log.e("MovieListFragment", "invalid page for updateAdapter");
        }
    }

    /**
     * Determins of all of the arrays have been populated or not.
     * @return true if all arrays not null, false otherwise
     */
    public static boolean isFilled() {
        return (newMoviesList != null)
                && (topRentalsList != null)
                && (yourRecommendationsList != null);
    }

    /**
     * getter for search movie list
     *
     * @return List of movies in current search movie list
     */
    public static List getSearchMovieList() {
        return searchMovieList;
    }
























    /**
     * Innter class for recyclerview adapter
     */
    public class MovieRecyclerViewAdapter
            extends RecyclerView.Adapter<MovieRecyclerViewAdapter.MovieViewHolder> {
        private List<Movie> movieList;
        private View itemView;
        private int oldPosition;

        /**
         * inner class for RecyclerView.ViewHolder
         */
        public class MovieViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final NetworkImageView mMoviePosterView;
            public final TextView mMovieTitleView;
            public final TextView mMovieRatingView;
            public final TextView mMovieDescriptionView;
            public Movie mMovieInfo;

            /**
             * public constructor for viewholder
             * @param itemView current view
             */
            public MovieViewHolder(View itemView) {
                super(itemView);

                Log.d("MLFrag", "create new MovieViewHolder");

                mView = itemView;
                mMoviePosterView = (NetworkImageView) itemView.findViewById(R.id.movie_poster);
                mMoviePosterView.setDefaultImageResId(R.mipmap.slowpoke);
                mMoviePosterView.setErrorImageResId(R.mipmap.load_error3);
                mMovieTitleView = (TextView) itemView.findViewById(R.id.movie_title);
                mMovieRatingView = (TextView) itemView.findViewById(R.id.movie_rating);
                mMovieDescriptionView = (TextView) itemView.findViewById(R.id.movie_description);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mMovieTitleView.getText() + "'";
            }
        }

        /**
         * Public constructor for adapter
         * @param movieInfo list of movies
         */
        public MovieRecyclerViewAdapter(List<Movie> movieInfo) {
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < 1; j++) {
                    Log.v("Useless", "Don't care what it all means");
                }
                for (int j = 0; j < 1; j++) {
                    Log.v("Useless", "Don't care about life rn.");
                }
            }
            movieList = movieInfo;
            Log.d("GTMovie", "create new MovieRecyclerViewAdapter " + movieList);
            if (movieInfo == null) {
                Log.e("movieList null", Arrays.toString(Thread.currentThread().getStackTrace()));
            }
        }

        @Override
        public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            oldPosition = 0;
            itemView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.movie_list_content, parent, false);

            Log.d("MLFrag", "onCreateViewHolder");
            return new MovieViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MovieRecyclerViewAdapter.MovieViewHolder holder, int position) {
            holder.mMovieInfo = movieList.get(position);

            Log.d("MLFrag", "onBindViewHolder");

            //holder.poster.setImageResource(mMovieInfo.poster);
            holder.mMoviePosterView.setImageUrl(
                    holder.mMovieInfo.getPosterURL(),
                    SingletonMagic.getInstance(getContext()).getImageLoader());
            holder.mMovieTitleView.setText(holder.mMovieInfo.getTitle());
            String rating = holder.mMovieInfo.getRating() + "%";
            holder.mMovieRatingView.setText(rating);
            holder.mMovieDescriptionView.setText(holder.mMovieInfo.getDescription());


            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("MLFrag", "holder setOnClickListener");
                    if (MovieListFragment.isTwoPane()) {
                        Log.d("MLFrag", "TWO PANE PROBLEM!!!");
                        Bundle arguments = new Bundle();
                        arguments.putString(MovieDetailFragment.ARG_ITEM_ID, holder.mMovieInfo.getTitle());
                        MovieDetailFragment fragment = new MovieDetailFragment();
                        fragment.setArguments(arguments);
                        if (!fragment.isVisible()) {
                            Log.d("MLFrag", "one pane");
                            Context context = v.getContext();
                            Intent intent = new Intent(context, MovieDetailActivity.class);
                            intent.putExtra(MovieDetailFragment.ARG_ITEM_TITLE,
                                    holder.mMovieInfo.getTitle());
                            intent.putExtra(MovieDetailFragment.ARG_ITEM_ID,
                                    holder.mMovieInfo.getID());
                            intent.putExtra(MovieDetailFragment.ARG_ITEM_DESC,
                                    holder.mMovieInfo.getDescription());
                            intent.putExtra(MovieDetailFragment.ARG_ITEM_RATE,
                                    holder.mMovieInfo.getRating() + "%");
                            Log.d("Placing", holder.mMovieInfo + " "
                                    + holder.mMovieInfo.getTitle() + " "
                                    + holder.mMovieInfo.getID() + " "
                                    + holder.mMovieInfo.getDescription() + " "
                                    + holder.mMovieInfo.getRating());

                            context.startActivity(intent);
                        }
                        /*getFragmentManager().beginTransaction()
                                .replace(R.id.content_main, fragment)
                                .commit();*/
                    } else {
                        Log.d("MLFrag", "one pane");
                        Context context = v.getContext();
                        Intent intent = new Intent(context, MovieDetailActivity.class);
                        intent.putExtra(MovieDetailFragment.ARG_ITEM_TITLE,
                                holder.mMovieInfo.getTitle());
                        intent.putExtra(MovieDetailFragment.ARG_ITEM_ID,
                                holder.mMovieInfo.getID());
                        intent.putExtra(MovieDetailFragment.ARG_ITEM_DESC,
                                holder.mMovieInfo.getDescription());
                        intent.putExtra(MovieDetailFragment.ARG_ITEM_RATE,
                                holder.mMovieInfo.getRating());

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            if (movieList != null) {
                return movieList.size();
            } else {
                Log.e("MLFrag", "null movieList given for getItemCount " + Arrays.toString(Thread.currentThread().getStackTrace()));
                return -1;
            }
        }


        /**
         * Swaps current adapter list
         * @param list new list to change to
         * @return true if changed, false if no action
         */
        public boolean swapList(List<Movie> list) {
            if (movieList != null) {
                movieList.clear();
                movieList.addAll(list);
                notifyDataSetChanged();
                return true;
            }
            return false;
        }

        /**
         * Collapses top bars on scroll
         * @param position new scroll position
         */
        private void toggleTopBars(int position) {
            Log.d("toggleTopBars", "old:" + oldPosition + " new:" + position);
            //ScrollView scrollView = (ScrollView) getActivity().findViewById(R.id.main_view2);
            View linearView = getActivity().findViewById(R.id.main_view2);
            View criteriaBar =  getActivity().findViewById(R.id.criteria_bar);
            Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.main_toolbar);
            ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.view_pager);

            if (toolbar != null) {
                //On scroll up/down, shows/hides top bars
                if (position > 0) {
                    //scroll down, close top bars
                    Log.d("heights", "oldViewPager:" + viewPager.getHeight());
                    viewPager.getLayoutParams().height = CurrentState.getClosedHeight();
                    Log.d("heights", "viewPager:" + viewPager.getHeight()
                            + " toolbar:" + toolbar.getHeight());
                    toolbar.setVisibility(View.GONE);
                    criteriaBar.setVisibility(View.GONE);
                    oldPosition = position;
                    //scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                } else if (position < 0) {
                    //scroll up, open top bars
                    viewPager.getLayoutParams().height = CurrentState.getOpenHeight();
                    toolbar.setVisibility(View.VISIBLE);
                    if (movieList == yourRecommendationsList) {
                        criteriaBar.setVisibility(View.VISIBLE);
                        viewPager.getLayoutParams().height = CurrentState.getOpenHeight() - R.dimen.text_margin;
                    }
                    oldPosition = position;
                    //scrollView.fullScroll(ScrollView.FOCUS_UP);
                }
                Log.d("toggleTopBars", "change: " + (oldPosition - position));

            }
        }

        public List getMovieList() {
            return movieList;
        }
    }
}


///////////////////////////////////////////////////////////////////////
/////////////////////////JINU LOOK HERE////////////////////////////////
/////////////////////////IS THAT A BIRD////////////////////////////////
///////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////
/*Comments for Jinu
    Whenever setting a tab/search use one of :

    MovieListFragment.setNewMoviesList(list);
    MovieListFragment.setTopRentalsList(list);
    MovieListFragment.setYourRecommendationsList(list);
    MovieListFragment.setSearchMovieList(list);



    Whenever using MovieListFragment, do:

    MovieListFragment movieListFragment = MovieListFragment.newInstance(page);

    the page value can be:
    MovieListFragment.NEW_MOVIES_TAB
    MovieListFragment.TOP_RENTALS_TAB
    MovieListFragment.YOUR_RENTALS_TAB
    MovieListFragment.SEARCH


    Last, but certainly not least, we need to update th UI.
    fragmentManager.beginTransaction().replace(R.id.main_frame_layout,
                        movieListFragment).commit();




    Search may get weird. In which case, I will need to look more into setting currenttab
    Actually, performance may take a hit and we may be able to do movieListFragment with those
    tabs that are at the top that I'm not currently using rather than creating a new fragment each
    time.

    In the case that search does not work properly, we need to try to place this on the line before
    creating the new fragment instance:

    MovieListFragment.setTabPosition(page);

    with the page values I mentioned above. Likely will not work though.


    So, putting it all together, I will provide the following example:

    MovieListFragment.setNewMoviesList(list);
    MovieListFragment movieListFragment = MovieListFragment.newInstance(
            MovieListFragment.NEW_MOVIES_TAB);
    fragmentManager.beginTransaction().replace(R.id.main_frame_layout,
                        movieListFragment).commit();




 */
///////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////
