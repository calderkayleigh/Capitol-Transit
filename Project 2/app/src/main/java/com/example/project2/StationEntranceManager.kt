package com.example.project2

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject

class StationEntranceManager {

    //https://developer.wmata.com/docs/services/5476364f031f590f38092507/operations/5476364f031f5909e4fe330f?

    val okHttpClient: OkHttpClient

    init {
        val builder = OkHttpClient.Builder()

        //network setup
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(logging)

        okHttpClient = builder.build()
    }

    fun retrieveRoutes(): List<Rail> {

        //TODO - get lat and long
        val Lat = 39
        val Lon = -76.9
        val Radius = 5000
        val apiKey = "bd86072718514a4ab76b0efce909c43e"

        // API declaration
        val request = Request.Builder()
            .get()
            .url("https://api.wmata.com/Rail.svc/json/jStationEntrances?Lat=$Lat&Lon=$Lon&Radius=$Radius")
            .header("api_key", "$apiKey")
            .build()

        // "Execute" the request
        val response: Response = okHttpClient.newCall(request).execute()

        // Create an empty but mutable list of routes
        val routesList = mutableListOf<Rail>()

        // Get the JSON body
        val responseBody: String? = response.body?.string()

        // If the response is successful & body is not Null or blank, parse
        if (response.isSuccessful && !responseBody.isNullOrBlank()) {

            // set up for parsing
            val json = JSONObject(responseBody)
            val entrances = json.getJSONArray("Entrances")

            // contents to list
            for (i in 0 until entrances.length()) {
                val curr = entrances.getJSONObject(i)
                val description = curr.getString("Description")
                val stationID = curr.getString("StationCode1")

                //TODO - fix these
                val dest = "Fix"
                val cost = "fix"
                val delays = "fix"
                val duration = "fix"
                val times = "fix"

                val route = Rail(
                    origin = description,
                    destination = dest,
                    cost = cost,
                    delays = delays,
                    duration = duration,
                    times = times
                )

                routesList.add(route)
            }
        } else {

        }

        return routesList
    }
}