package com.banchero_dam3.jm.unit4_ej3_mapa;

import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    private double latitud;
    private  double longitud;

    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;
    private DecimalFormat decimales = new DecimalFormat("0.0000");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.getUiSettings().setScrollGesturesEnabled(true);

        CameraUpdate camUpd1 =  CameraUpdateFactory.newLatLngZoom(new LatLng(28.4559911, -16.2855148), 15);
        mMap.moveCamera(camUpd1);

        LatLng cifp = new LatLng(28.455, -16.2836);

        mMap.addMarker(new MarkerOptions().position(cifp).title("CIFP César Manrique").snippet("DAM 3")
             .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.uni))));

        mMap.setOnMapClickListener(this);

        mMap.setOnMarkerClickListener(this);

/*este metodo no funciona en android antiguos
 en 4.4 sí y se puede borrar el marcador,
 despues actualizar el mapa para volver a añadir marcadores*/
        mMap.setOnMapLongClickListener(this);

    }

    /*show infor*/
    @Override
    public void onMapClick(LatLng latLng) {

        mMap.addMarker(new MarkerOptions().position(latLng).
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                    .snippet("latitud: " + decimales.format(latLng.latitude) + "\nlongitud: " +
                        decimales.format(latLng.longitude)).title("EJ3 DAM3 JM_B"));
    }


    /*edit marker long click*/
    @Override
    public void onMapLongClick(LatLng latLng) {

        removeMarker(mMap);

        Toast.makeText(MapsActivity.this, "Ahora puede Clicar en el Marcador que quiere eliminar\n " +
                "y volver a cargar el mapa para añadir marcadores", Toast.LENGTH_LONG).show();
    }


    @Override
    public boolean onMarkerClick(Marker marker) {

       // marker.showInfoWindow();

        LatLng latLng = marker.getPosition();

        Toast.makeText(MapsActivity.this,
                "Click\n" + "Lat: " + decimales.format(latLng.latitude) + "\n" +
                   "Lng: " +  decimales.format(latLng.longitude) + "\nMARKER pos: " + marker.getPosition() +
                     "\n TITULO: " + marker.getTitle() +"\nMARKER: "  +marker.getSnippet(),
                Toast.LENGTH_SHORT).show();

        return false;
    }

    /*add marker */
    public void addMarker(View view) {
        mMap.addMarker(new MarkerOptions().position(
                new LatLng(mMap.getCameraPosition().target.latitude, mMap.getCameraPosition().target.longitude)));
    }


    /*for remove selected marker */
    public void removeMarker(GoogleMap v){

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                marker.remove();
                return false;
            }
        });

    }
}
