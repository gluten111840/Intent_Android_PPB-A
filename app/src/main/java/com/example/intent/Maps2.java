package com.example.intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Maps2 extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.frag_maps1);
//        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        Button go = findViewById(R.id.btn_loc);
        go.setOnClickListener(op);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng ITS = new LatLng(-7.2819705, 112.795323);
        mMap.addMarker(new MarkerOptions().position(ITS).title("Marker in ITS"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ITS,15));
    }

    View.OnClickListener op = view -> {
        if (view.getId() == R.id.btn_loc) {
            hideKeyboard(view);
            goToLokasi();
        }
    };

    private void goToLokasi() {
        EditText lat = findViewById(R.id.ed_lati);
        EditText lng = findViewById(R.id.ed_longi);
        EditText zoom = findViewById(R.id.ed_zoom);

        Double dbllat = Double.parseDouble(lat.getText().toString());
        Double dbllng = Double.parseDouble(lng.getText().toString());
        float dblzoom = Float.parseFloat(zoom.getText().toString());

        Toast.makeText(this, "Move to Lat: "+dbllat+" Long: "+dbllng,Toast.LENGTH_LONG).show();
        gotopeta(dbllat,dbllng,dblzoom);

    }

    private void hideKeyboard(View v) {
        InputMethodManager a = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        a.hideSoftInputFromWindow(v.getWindowToken(),0);
    }

    private void gotopeta(Double lat, Double lng, float z) {
        LatLng lokasiBaru = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(lokasiBaru).title("Marker in "+lat+": "+lng));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lokasiBaru,z));
    }
}