package com.example.project2

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.anko.doAsync
import org.json.JSONObject

class StationEntranceManager {

    //https://developer.wmata.com/docs/services/5476364f031f590f38092507/operations/5476364f031f5909e4fe330f?

    val okHttpClient: OkHttpClient
    var station: String = ""

    init {
        val builder = OkHttpClient.Builder()

        //network setup
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(logging)

        okHttpClient = builder.build()
    }

    fun retrieveStation(Lat: Double, Lon: Double): String{

        val Radius = 500
        val apiKey = "bd86072718514a4ab76b0efce909c43e"

        Log.e("StationEntranceManager", "before request")


        // API declaration
        val request = Request.Builder()
                .get()
                .url("https://api.wmata.com/Rail.svc/json/jStationEntrances?Lat=$Lat&Lon=$Lon&Radius=$Radius")
                .header("api_key", "$apiKey")
                .build()
        Log.e("StationEntranceManager", "request executed")

        val response: Response = okHttpClient.newCall(request).execute()
        Log.e("StationEntranceManager", "request executed")

        // Get the JSON body
        val responseBody: String? = response.body?.string()
        Log.e("StationEntranceManager", "json body")

        // If the response is successful & body is not Null or blank, parse
        if (response.isSuccessful && !responseBody.isNullOrBlank()) {

            Log.e("StationEntranceManager", "response successful")

            // set up for parsing
            val json = JSONObject(responseBody)
            val entrances = json.getJSONArray("Entrances")

            // contents to list
            val curr = entrances.getJSONObject(0)
            Log.e("StationEntranceManager", "$curr")
            station = curr.getString("StationCode1")
        }
        Log.e("StationEntranceManager", "Station: $station")
        return station
    }
    fun retrieveRoute(origin: String, destination: String): List<String> {

        val apiKey = "bd86072718514a4ab76b0efce909c43e"

        val routesList = mutableListOf<String>()

        // API declaration
        val request = Request.Builder()
                .get()
                .url("https://api.wmata.com/Rail.svc/json/jPath?FromStationCode=$origin&ToStationCode=$destination")
                .header("api_key", "$apiKey")
                .build()

        // "Execute" the request
        val response: Response = okHttpClient.newCall(request).execute()

        // Get the JSON body
        val responseBody: String? = response.body?.string()

        // If the response is successful & body is not Null or blank, parse
        if (response.isSuccessful && !responseBody.isNullOrBlank()) {

            // set up for parsing
            val json = JSONObject(responseBody)
            val paths = json.getJSONArray("Path")
            var name = ""

            // contents to list
            for (i in 0 until paths.length()) {
                val curr = paths.getJSONObject(i)
                name = curr.getString("StationName")

                routesList.add(name)
            }
        } else {
            Log.e("StationEntranceManager", "Response was  not successful")
        }

        return routesList
    }


}