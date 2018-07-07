package moviesapp.com.myapplication.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import moviesapp.com.myapplication.R;
import moviesapp.com.myapplication.beans.PopularResult;
import moviesapp.com.myapplication.data.MoviesDbHelper;
import moviesapp.com.myapplication.view.adapter.MovieListAdapter;

public class MyFavouritesFragment extends Fragment {

    private Context mContext;
    private RecyclerView mRecyclerView;
    private ProgressBar progDialog;
    private TextView errorText;
    private MoviesDbHelper moviesDbHelper;
    MovieListAdapter movieListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        if (mContext != null) {
            moviesDbHelper = new MoviesDbHelper(mContext);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_fav_fragment, container, false);
        setupViews(view);
        return view;
    }

    private void setupViews(View view) {
        // Calling the RecyclerView
        mRecyclerView = view.findViewById(R.id.fav_recycler_view_movies);


        // The number of Columns
        GridLayoutManager manager = new GridLayoutManager(mContext, 2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);

        progDialog = view.findViewById(R.id.loadingDialog);


        errorText = view.findViewById(R.id.txtError);
        errorText.setVisibility(View.GONE);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


            }
        });
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadMyFavouriteMovies();
    }

    private void loadMyFavouriteMovies() {

        showProgress();
        getFavoutitesFromDatabase();

    }

    private void getFavoutitesFromDatabase() {
        List<PopularResult> result = moviesDbHelper.getAllMoviesFromDb();
        if (result != null) {
            showData();
            setData(result);
        }
        Log.d("Fav", "getFavoutitesFromDatabase: " + result);
    }

    private void setData(List<PopularResult> result) {
        movieListAdapter = new MovieListAdapter(mContext, result);
        mRecyclerView.setAdapter(movieListAdapter);
    }

    private void showData() {
        progDialog.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        errorText.setVisibility(View.GONE);
    }

    public void showProgress() {
        progDialog.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        errorText.setVisibility(View.GONE);
    }
}
