package com.example.project2

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.widget.Button
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
    private lateinit var cost: TextView
    private lateinit var duration: TextView
    private lateinit var favorites: Button
    private lateinit var delays: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        var durationString = ""
        var delayString = ""

        val checkBoxBoolean = intent.getBooleanExtra("checkBoxBoolean", false)

        //set layout
        setContentView(R.layout.activity_routes)

        origin = findViewById(R.id.originStationUserInput)
        destination = findViewById(R.id.destinationStationUserInput)
        resultsText = findViewById(R.id.routeText)
        cost = findViewById(R.id.costMetroInput)
        duration = findViewById(R.id.expectedDurationMetroInput)
        favorites = findViewById(R.id.addToFavorites)
        delays = findViewById(R.id.delayMetroInput)

        doAsync {
            // Geocoding should be done on a background thread - it involves networking
            // and has the potential to cause the app to freeze (Application Not Responding error)
            // if done on the UI Thread and it takes too long.
            val stationManager = StationEntranceManager()

            // In Kotlin, you can assign the result of a try-catch block. Both the "try" and
            // "catch" clauses need to yield a valid value to assign.


            originStation = stationManager.retrieveStation(lat1, lon1)
            Log.e("RoutesActivity", "$originStation")

            destinationStation = stationManager.retrieveStation(lat2, lon2)
            Log.e("RoutesActivity", "$destinationStation")

            costString = stationManager.retrieveMetroCost(originStation, destinationStation, checkBoxBoolean)
            durationString = stationManager.retrieveMetroDuration(originStation, destinationStation)
            delayString = stationManager.retrieveMetroDelays()

            val findRoute = connectionAlgorithm()
            val originStationName = stationManager.retrieveStationName(originStation)
            val destinationStationName = stationManager.retrieveStationName(destinationStation)


            // Move back to the UI Thread now that we have some results to show.
            // The UI can only be updated from the UI Thread.
            runOnUiThread {

                if((originStationName != "Error: Station not found") && (destinationStation != "Error: Station not found")) {
                    origin.text = originStationName
                    destination.text = destinationStationName
                    cost.text = costString + " US Dollars"
                    duration.text = durationString + " Minutes"
                    delays.text = delayString


                    Log.e("RoutesActivity", "Origin passed: $originStationName, Destination passed: $destinationStationName,")
                    //TODO- the following code is used to find the shortest path. This is in progress. For now our app is using the WMATA API
                    //TODO - therefore our app is currently limited to finding paths on the same line
      //              val originToPass: String = originStationName
      //              val destinationToPass: String = destinationStationName
      //              val root = findRoute.determinePath(originToPass, destinationToPass).split(";")
      //              var theRoot = "Algorithm is not working when passing string\n from kotlin to java.. \nInvestigating now"
//

//                    resultsText.text = theRoot

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
                                origin.text = root.first()
                                originName = root.first()
                                destination.text = root.last()
                                destName = root.last()

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
                    val toast = Toast.makeText(this@RoutesActivity, "One of the stations is wrong or the geocoder failed", Toast.LENGTH_LONG)
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
                    preferences.edit().putString("description$i", "The cost to travel between these two stations is $costString dollars and it will take approximately $durationString minutes to travel.").apply()

                    runOnUiThread{
                        val toast = Toast.makeText(
                                this@RoutesActivity,
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
                                this@RoutesActivity,
                                "Failed to add $originName to $destName to favorites. The maximum number of favorites is 10.",
                                Toast.LENGTH_LONG
                        )
                        toast.show()
                    }
                }
            }
        }
    }
}