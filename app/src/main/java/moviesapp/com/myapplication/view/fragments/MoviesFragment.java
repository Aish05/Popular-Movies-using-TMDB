package moviesapp.com.myapplication.view.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
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

import com.google.gson.Gson;

import org.json.JSONException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import moviesapp.com.myapplication.R;
import moviesapp.com.myapplication.beans.PopularMain;

import moviesapp.com.myapplication.beans.PopularResult;
import moviesapp.com.myapplication.view.utility.Response;
import moviesapp.com.myapplication.view.adapter.MovieListAdapter;
import moviesapp.com.myapplication.view.utility.Util;

public class MoviesFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private ProgressBar progDialog;
    private TextView errorText;
    private Context mContext;
    MovieListAdapter movieListAdapter;
    int count = 0;
    GridLayoutManager manager;
    List<PopularResult> resultsList;


    public static MoviesFragment newInstance() {
        return new MoviesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        setupViews(view);


        getMoviesData();

        return view;
    }

    private void getMoviesData() {

        if (Util.isOnline(mContext)) {
            int pageNumber = count + 1;
            new AsyncTaskMovies().execute(getString(R.string.base_url) + "&page=" + pageNumber);
        } else {
            showError();
        }
    }

    private void showError() {
        errorText.setVisibility(View.VISIBLE);
        errorText.setText(R.string.no_internet);
        progDialog.setVisibility(View.GONE);
    }


    private void setupViews(View view) {
        // Calling the RecyclerView
        mRecyclerView = view.findViewById(R.id.recycler_view_movies);


        // The number of Columns
        manager = new GridLayoutManager(mContext, 2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);

        progDialog = view.findViewById(R.id.loadingDialog);
        movieListAdapter = new MovieListAdapter(mContext,null);

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
                int visibleItemCount = manager.getChildCount();
                int totalItemCount = manager.getItemCount();
                int pastVisibleItems = manager.findFirstVisibleItemPosition();
                if (pastVisibleItems + visibleItemCount >= totalItemCount) {
                    //End of list
                    count++;
                    if(count <= 3){
                        getMoviesData();
                    }

                }
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    private class AsyncTaskMovies extends AsyncTask<String, String, PopularMain> {

        private String response;
        private PopularMain main;

        @Override
        protected PopularMain doInBackground(String... params) {

            try {
                String url = params[0];
                URL baseUrl = new URL(url);
                this.response = new Response().getResponse(baseUrl);

                if (this.response != null) {
                    Log.d("Movies data", this.response);
                    main = parseData(this.response);
                }
                return main;


            } catch (Exception e) {
                e.printStackTrace();
                response = e.getMessage();
            }
            return main;
        }


        @Override
        protected void onPreExecute() {
            progDialog.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(PopularMain popularMain) {
            super.onPostExecute(popularMain);
            progDialog.setVisibility(View.GONE);
            setData(popularMain);
        }


        @Override
        protected void onProgressUpdate(String... text) {

            // Things to be done while execution of long running operation is in
            // progress. For example updating ProgessDialog
        }
    }

    private void setData(PopularMain result) {

        if(resultsList == null){
            resultsList  = new ArrayList<>();
            resultsList = result.getResults();
            movieListAdapter = new MovieListAdapter(mContext, resultsList);
        }else {
            resultsList.addAll(result.getResults());
            movieListAdapter.setMovieResults(resultsList);
            movieListAdapter.notifyDataSetChanged();
        }

        Log.d("@@result", "setData: " + result);

       /* movieListAdapter = new MovieListAdapter(mContext, result.getResults());
        movieListAdapter = new MovieListAdapter(mContext, resultsList);*/
        mRecyclerView.setAdapter(movieListAdapter);
    }

    private PopularMain parseData(String response) throws JSONException {
        Gson gson = new Gson();
        PopularMain popularMain = gson.fromJson(response, PopularMain.class);
        Log.d("@@parsed data", "parseData: " + popularMain.getPage() + " " + popularMain.getResults().get(0).getTitle());

        return popularMain;
    }

}
