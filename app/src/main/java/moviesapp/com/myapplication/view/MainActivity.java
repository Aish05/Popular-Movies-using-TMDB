package moviesapp.com.myapplication.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import moviesapp.com.myapplication.MovieItemClick;
import moviesapp.com.myapplication.R;
import moviesapp.com.myapplication.beans.PopularResult;
import moviesapp.com.myapplication.view.fragments.MovieDetailsFragment;
import moviesapp.com.myapplication.view.fragments.MoviesFragment;
import moviesapp.com.myapplication.view.fragments.MyFavouritesFragment;
import moviesapp.com.myapplication.view.utility.Util;

public class MainActivity extends AppCompatActivity implements MovieItemClick {

    private MoviesFragment movieFrag;
    private MyFavouritesFragment myFavouritesFragment;
    private ActionBar toolbar;
    BottomNavigationView navigation;
    private SensorManager mSensorManager;
    private ShakeEventListener mSensorListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = getSupportActionBar();
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if ((!Util.getSavedBooleanDataFromPref(this, "FIRST_LAUNCH_SHOWN"))) {
            launchHelpScreen();
        }

        // load the popular movies fragment by default
        if (savedInstanceState == null) {
            movieFrag = MoviesFragment.newInstance();
            toolbar.setTitle("Popular Movies");
            getSupportFragmentManager().beginTransaction().replace(R.id.movieContainer, movieFrag).commit();

        }

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorListener = new ShakeEventListener();

        mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {

            public void onShake() {
                toolbar.setTitle("My Favourites");
                myFavouritesFragment = new MyFavouritesFragment();
                loadFragment(myFavouritesFragment);
                navigation.setSelectedItemId(R.id.navigation_fav);
//                Toast.makeText(MainActivity.this, "Shake!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void launchHelpScreen() {
            Intent helpScreenIntent = new Intent(MainActivity.this,HelpScreenActivity.class);
            startActivity(helpScreenIntent);

    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    toolbar.setTitle("Popular Movies");
                    movieFrag = MoviesFragment.newInstance();
                    loadFragment(movieFrag);
                    return true;
                case R.id.navigation_fav:
                    toolbar.setTitle("My Favourites");
                    myFavouritesFragment = new MyFavouritesFragment();
                    loadFragment(myFavouritesFragment);
                    return true;


            }

            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.movieContainer, fragment).commit();
    }


    @Override
    public void onMoveItemClicked(int position, PopularResult popularResult) {

        Intent intent = new Intent(MainActivity.this, MovieDetailsActivity.class);
        intent.putExtra("movieList", popularResult);
        startActivity(intent);


    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("@@", "onResume called:");
        mSensorManager.registerListener(mSensorListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.maps:
                Intent mapIntent = new Intent(MainActivity.this, MapsActivity.class);
                this.startActivity(mapIntent);
                break;

            case R.id.help:
                Intent helpIntent = new Intent(MainActivity.this, HelpScreenActivity.class);
                this.startActivity(helpIntent);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}

