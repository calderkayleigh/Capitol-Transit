package com.example.project2

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val intent = getIntent()


        //test lat and lons
        var lat1 = 38.8978168
        var lon1 = -77.0404246
        var lat2 = 38.8983144732
        var lon2 = -77.0280779971


        val potentialLat1 = intent.getStringExtra("lat1")
        if (!potentialLat1.isNullOrEmpty()){
            lat1 = potentialLat1.toDouble()
        }

        val potentialLon1 = intent.getStringExtra("lon1")
        if (!potentialLon1.isNullOrEmpty()){
            lon1 = potentialLon1.toDouble()
        }

        val potentialLat2 = intent.getStringExtra("lat2")
        if (!potentialLat2.isNullOrEmpty()){
            lat2 = potentialLat2.toDouble()
        }

        val potentialLon2 = intent.getStringExtra("lon2")
        if (!potentialLon2.isNullOrEmpty()){
            lon2 = potentialLon2.toDouble()
        }
        Log.e("MapsActivity", "First Result: $lat1, $lon1")
        Log.e("MapsActivity", "Second Result: $lat2, $lon2")

        // Add markers
        val location1 = LatLng(lat1, lon1)
        val location2 = LatLng(lat2, lon2)

        title = "Origin Station to Destination Station"
        mMap.addMarker(MarkerOptions().position(location1).title("Origin Station"))
        mMap.addMarker(MarkerOptions().position(location2).title("Destination Station"))

        val latLngBounds = LatLngBounds.Builder()
            .include(location1)
            .include(location2)
            .build()

        val padding = 175

        mMap.animateCamera(
            CameraUpdateFactory.newLatLngBounds(latLngBounds, padding))

    }
}