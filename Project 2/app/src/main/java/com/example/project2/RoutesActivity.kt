package com.example.project2

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.doAsync
import org.json.JSONObject

class RoutesActivity: AppCompatActivity() {

    private lateinit var origin: TextView
    private lateinit var destination: TextView

    private lateinit var resultsText: TextView

    private lateinit var originStation: String
    private lateinit var destinationStation: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = getIntent()
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
        Log.e("RoutesAcivity", "First Result: $lat1, $lon1")
        Log.e("RoutesAcivity", "Second Result: $lat2, $lon2")

        //set layout
        setContentView(R.layout.activity_routes)

        origin = findViewById(R.id.originStationUserInput)
        destination = findViewById(R.id.destinationStationUserInput)
        resultsText = findViewById(R.id.routeText)

        doAsync {
            // Geocoding should be done on a background thread - it involves networking
            // and has the potential to cause the app to freeze (Application Not Responding error)
            // if done on the UI Thread and it takes too long.
            val stationManager = StationEntranceManager()

            // In Kotlin, you can assign the result of a try-catch block. Both the "try" and
            // "catch" clauses need to yield a valid value to assign.

            try {
                originStation = stationManager.retrieveStation(lat1, lon1)
                Log.e("RoutesActivity", "$originStation")
            } catch (e: Exception){
                Log.e("RoutesActivity", "Station Name API Failed", e)
            }

            try {
                destinationStation = stationManager.retrieveStation(lat2, lon2)
                Log.e("RoutesActivity", "$destinationStation")
            } catch (e: Exception){
                Log.e("RoutesActivity", "Station Name API Failed", e)
            }

            // Move back to the UI Thread now that we have some results to show.
            // The UI can only be updated from the UI Thread.
            runOnUiThread {
                // need to implement: When JSON returns empty
                if((originStation != "Error: Station not found") && (destinationStation != "Error: Station not found")) {
                    origin.text = originStation
                    destination.text = destinationStation

                    doAsync {
                        val root: List<String> = try {
                            stationManager.retrieveRoute(
                                    originStation,
                                    destinationStation
                            )
                        } catch (e: Exception) {
                            Log.e("Routes Activity", "Path api failed", e)
                            listOf<String>()
                        }

                        // Move back to the UI Thread now that we have some results to show.
                        // The UI can only be updated from the UI Thread.
                        runOnUiThread {
                            if (root.isNotEmpty()) {
                                // Potentially, we could show all results to the user to choose from,
                                // but for our usage it's sufficient enough to just use the first result
                                val firstResult = root.first()
                                origin.text = root.first()
                                destination.text = root.last()

                                var theRoute = ""
                                for (station in root)
                                    theRoute = theRoute + station + "\n"
                                resultsText.text = theRoute

                            } else {
                                Log.d("Routes Activity", "Path api results failed!")

                            }
                        }
                    }
                } else {
                    //give the user an error
                    Log.d("RoutesActivity", "No results from station entrance api!")
                    val toast = Toast.makeText(this@RoutesActivity, "Dumb dumb you messed up one of your stations is wrong", Toast.LENGTH_LONG)
                    toast.show()
                }

            }
        }

    }
}