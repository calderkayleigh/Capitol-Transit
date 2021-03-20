package com.example.project2

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.doAsync

class RoutesActivity: AppCompatActivity() {

    private lateinit var origin: TextView
    private lateinit var destination: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set layout
        setContentView(R.layout.activity_routes)

        origin = findViewById(R.id.originStationUserInput)
        destination = findViewById(R.id.destinationStationUserInput)


        //TODO - get lat and long for origin and destination
        val Lat1 = 38.8978168
        val Lon1 = -77.0404246
        val Lat2 = 38.8983144732
        val Lon2 = -77.0280779971

        val stationManager = StationEntranceManager()

        var originStation = stationManager.retrieveStation(Lat1, Lon1)
        Log.e("RoutesActivity", "$originStation")
        var destinationStation = stationManager.retrieveStation(Lat2, Lon2)
        Log.e("RoutesActivity", "$destinationStation")

        //var route = stationManager.retrieveRoute(originStation, destinationStation)

        origin.text = originStation
        destination.text = destinationStation




    }
}