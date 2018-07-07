package moviesapp.com.myapplication.view;

import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;


import moviesapp.com.myapplication.R;
import moviesapp.com.myapplication.beans.PopularResult;
import moviesapp.com.myapplication.view.fragments.MovieDetailsFragment;

public class MovieDetailsActivity extends AppCompatActivity {

    private ActionBar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);


        toolbar = getSupportActionBar();
        if(toolbar != null){
            toolbar.setTitle("Movie Details");
        }


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Using getParcelableExtra(String key) method
        PopularResult movie =  getIntent().getParcelableExtra("movieList");
        Log.d("@@intent result", "onCreate: "+movie);

        loadFragment(movie);

    }

    private void loadFragment(PopularResult movie) {
        MovieDetailsFragment frag = MovieDetailsFragment.newInstance(movie);
        getSupportFragmentManager().beginTransaction().add(R.id.movieDetailsContainer, frag, frag.toString())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
