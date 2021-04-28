package com.example.project2

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.doAsync

class RoutesBusActivity: AppCompatActivity() {
    private lateinit var origin: TextView
    private lateinit var destination: TextView
    private lateinit var resultsText: TextView
    private lateinit var originStation: String
    private lateinit var destinationStation: String
    private lateinit var cost: TextView
    private lateinit var favorites: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val apiKey = getString(R.string.wmata_api_key)

        val intent = getIntent()
        val preferences = getSharedPreferences("transit-app", Context.MODE_PRIVATE)

        //test lat and lons
        var lat1 = 38.8978168
        var lon1 = -77.0404246
        var lat2 = 38.8983144732
        var lon2 = -77.0280779971


        lat1 = intent.getStringExtra("lat1")?.toDouble()!!
        lon1 = intent.getStringExtra("lon1")?.toDouble()!!
        lat2 = intent.getStringExtra("lat2")?.toDouble()!!
        lon2 = intent.getStringExtra("lon2")?.toDouble()!!

        Log.e("RoutesAcivity", "First Result: $lat1, $lon1")
        Log.e("RoutesAcivity", "Second Result: $lat2, $lon2")

        var originName = ""
        var destName = ""
        var costString = ""

        val checkBoxBoolean = intent.getBooleanExtra("checkBoxBoolean", false)

        //set layout
        setContentView(R.layout.activity_routes_bus)

        origin = findViewById(R.id.originStationUserInput)
        destination = findViewById(R.id.destinationStationUserInput)
        resultsText = findViewById(R.id.routeText)
        cost = findViewById(R.id.costMetroInput)
        favorites = findViewById(R.id.addToFavorites)

        doAsync {
            // Geocoding should be done on a background thread - it involves networking
            // and has the potential to cause the app to freeze (Application Not Responding error)
            // if done on the UI Thread and it takes too long.
            val stationManager = StationEntranceManager()

            // In Kotlin, you can assign the result of a try-catch block. Both the "try" and
            // "catch" clauses need to yield a valid value to assign.


            //from origin to station 1
            //from station 2 to destination
            // call function pass in 1) origin lat/lon and destination lat/lon
            val busRoute = determineBusRoute(stationManager, lat1, lon1, lat2, lon2, apiKey)

            // Move back to the UI Thread now that we have some results to show.
            // The UI can only be updated from the UI Thread.
            runOnUiThread {

                if (busRoute != getString(R.string.noBusRoute)) {
                    val busInfo = busRoute.split(";")
                    origin.text = busInfo[0]
                    originName = busInfo[0]
                    destination.text = busInfo[1]
                    destName = busInfo[1]
                    cost.text = "2 US Dollars"
                    costString = "2 US Dollars"
                    resultsText.text = busInfo[2]
                } else {
                    Log.d("Routes Activity", "Path api results failed!")
                    val toast = Toast.makeText(this@RoutesBusActivity, "No routes found", Toast.LENGTH_LONG)
                    toast.show()
                }

            }
        }

        favorites.setOnClickListener {

            for(i in 1..10)
            {
                if(preferences.getString("origin$i", "").isNullOrEmpty() && preferences.getString("destination$i", "").isNullOrEmpty())
                {

                    preferences.edit().putString("origin$i","$originName").apply()
                    preferences.edit().putString("destination$i", "$destName").apply()
                    preferences.edit().putString("description$i", "The cost to travel between these two stations is $costString dollars.").apply()

                    runOnUiThread{
                        val toast = Toast.makeText(
                                this@RoutesBusActivity,
                                "Added $originName to $destName to Favorites",
                                Toast.LENGTH_LONG
                        )
                        toast.show()
                    }
                    return@setOnClickListener
                }
                else if(i == 10 && !preferences.getString("origin$i", "").isNullOrEmpty())
                {
                    runOnUiThread{
                        val toast = Toast.makeText(
                                this@RoutesBusActivity,
                                "Failed to add $originName to $destName to favorites. The maximum number of favorites is 10.",
                                Toast.LENGTH_LONG
                        )
                        toast.show()
                    }
                }
            }
        }
    }
    fun determineBusRoute(stationManager: StationEntranceManager, originLat: Double, originLon: Double, destLat: Double, destLon: Double, apiKey: String): String{
        //get bus station and routes for origin
        val originBusStation = stationManager.retrieveBusStop(originLat, originLon, apiKey)
        val originBusStationLines = stationManager.retrieveBusLinesAtStop(originLat, originLon, apiKey).split(";")

        Log.e("StationEntranceManager", "originBusStation: $originBusStation")
        Log.e("StationEntranceManager", "originBusLines: $originBusStationLines")
        //get bus station nd routes for destnation
        val destBusStation = stationManager.retrieveBusStop(destLat, destLon, apiKey)
        val destBusStationLines = stationManager.retrieveBusLinesAtStop(destLat, destLon, apiKey).split(";")

        Log.e("StationEntranceManager", "destBusStation: $destBusStation")
        Log.e("StationEntranceManager", "destBusLines: $destBusStationLines")
        //check if
        // either is empty, is yes then return "No bus connection"
        // both have the same line, if not return "No bus connection"
        //      if yes, return take bus line X from origin station to destination station
        if ((originBusStationLines.size == 0) or (destBusStationLines.size == 0)){

            return getString(R.string.noBusRoute)
        } else {
            for (originLine in originBusStationLines) {
                for (destLine in destBusStationLines) {
                    if ((originLine == destLine) and (originLine != "")) {
                        return originBusStation + ";" + destBusStation + ";" + getString(R.string.takeBusLine) + " " + originLine + "\n" + getString(R.string.from) + " " + originBusStation + "\n" + getString(R.string.to) + " " + destBusStation
                    }
                }
            }
        }
        return getString(R.string.noBusRoute)
    }
}
