package moviesapp.com.myapplication.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.mapbox.mapboxsdk.Mapbox;

import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdate;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;


import java.util.ArrayList;

import moviesapp.com.myapplication.R;
import moviesapp.com.myapplication.beans.MapBean;

public class MapsActivity extends AppCompatActivity {

    MapView mapView;
    private ActionBar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.access_token));
        setContentView(R.layout.maps_activity);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar = getSupportActionBar();
        toolbar.setTitle("Map");

        //Create a arraylist of mapbean object and add place name, lat and lon
        ArrayList<MapBean> mMapBean = new ArrayList<MapBean>();
        mMapBean.add(new MapBean(getString(R.string.draw_marker_options_title), 13.0553683, 80.2557781));
        mMapBean.add(new MapBean(getString(R.string.draw_marker_luxe), 12.9907023, 80.2145297));
        mMapBean.add(new MapBean(getString(R.string.draw_marker_escape_cinemas), 13.0588762,80.2619595));
        mMapBean.add(new MapBean(getString(R.string.draw_marker_inox_cinemas), 13.0590392,80.1941066));
        mMapBean.add(new MapBean(getString(R.string.draw_marker_pvr_cinemas), 13.0596705,80.1941061));
        mMapBean.add(new MapBean(getString(R.string.draw_marker_ags_cinemas), 13.0599861,80.1941058));
        mMapBean.add(new MapBean(getString(R.string.draw_marker_devi_multiplex), 13.068344,80.2597384));
        mMapBean.add(new MapBean(getString(R.string.draw_marker_mayajal_multiplex), 12.8484017,80.2331251));
        mMapBean.add(new MapBean(getString(R.string.draw_marker_abirami_cinemas), 13.0893064,80.1856012));
        mMapBean.add(new MapBean(getString(R.string.draw_marker_sangam_cinemas), 13.0794685,80.2471046));


        //when map is ready
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {

                //focus on the lat lon of chennai
                LatLng coordinate = new LatLng(13.0827, 80.2707);
                CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                        coordinate, 10);
                mapboxMap.animateCamera(location);

                //add markers from the list to the map
                for (int i = 0; i < mMapBean.size(); i++) {
                    final LatLng latLng = new LatLng(mMapBean.get(i).getLat(), mMapBean.get(i).getLon());
                    mapboxMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title(mMapBean.get(i).getName()));
                }



//

            }
        });
       /* mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                mapboxMap.setStyleUrl(Style.LIGHT);
            }
        });*/
    }


    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
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

