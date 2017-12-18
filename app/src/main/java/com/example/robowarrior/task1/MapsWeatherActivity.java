package com.example.robowarrior.task1;

import android.app.Dialog;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

public class MapsWeatherActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isGsAvailable()) {
            setContentView(R.layout.activity_maps_weather);
            startMap();
        }

    }

    private void startMap() {
        MapFragment mapFragment= (MapFragment) getFragmentManager().findFragmentById(R.id.mapfragment);
        mapFragment.getMapAsync(this);
    }

    public boolean isGsAvailable()
    {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int isAvaialble = apiAvailability.isGooglePlayServicesAvailable(this);
        if(isAvaialble == ConnectionResult.SUCCESS)
        {
            return true;
        }
        else if(apiAvailability.isUserResolvableError(isAvaialble)) {
            Dialog dialog = apiAvailability.getErrorDialog(this, isAvaialble, 0);
            dialog.show();
        }
        else
            Toast.makeText(this, "Unable to get GOOGLE SERVICES", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

    }
    private void movetoLocation(double lat, double lang,float x)
    {
        LatLng latLng = new LatLng(lat,lang);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,x);
        googleMap.animateCamera(cameraUpdate);
    }
    public void GO(View view) throws IOException
    {
        EditText editText = (EditText) findViewById(R.id.editText);
        String Location = editText.getText().toString();
        Geocoder geocoder = new Geocoder(this);
        List<Address> list = geocoder.getFromLocationName(Location,1);
        Address address = list.get(0);
        String locality = address.getLocality();
        Toast.makeText(this, locality, Toast.LENGTH_SHORT).show();
        Double lat = address.getLatitude();
        Double lang = address.getLongitude();
        movetoLocation(lat,lang,15);
    }
}
