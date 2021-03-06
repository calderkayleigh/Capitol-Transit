package com.example.project2

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
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
    } fun retrieveStation(Lat: Double, Lon: Double, apiKey: String): String{

        val Radius = 10000

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
            if (entrances.length() != 0){
                val curr = entrances.getJSONObject(0)
                station = curr.getString("StationCode1")
                Log.e("StationEntranceManager", "Station: $station")
                return station
            }
        }
        Log.e("StationEntranceManager", "Station not found!!")
        return "Error: Station not found"
    } fun retrieveStationLon(Lat: Double, Lon: Double, apiKey: String): Double{

        val Radius = 10000
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
            if (entrances.length() != 0){
                val curr = entrances.getJSONObject(0)
                val lon = curr.getDouble("Lon")
                Log.e("StationEntranceManager", "Station: $station")
                return lon
            }
        }
        Log.e("StationEntranceManager", "Station not found!!")
        return 0.0
    } fun retrieveStationLat(Lat: Double, Lon: Double, apiKey: String): Double{

        val Radius = 10000
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
            if (entrances.length() != 0){
                val curr = entrances.getJSONObject(0)
                val lat = curr.getDouble("Lat")
                Log.e("StationEntranceManager", "Station: $station")
                return lat
            }
        }
        Log.e("StationEntranceManager", "Station not found!!")
        return 0.0
    } fun retrieveRoute(origin: String, destination: String, apiKey: String): List<String> {
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
            //TODO - make RoutesActivity point to the shortest path algorithm rather than this method
            Log.e("StationEntranceManager", "Response was  not successful")
        }

        return routesList
    } fun retrieveMetroCost(origin: String, destination: String, checkBoxBoolean: Boolean, apiKey: String): String {
        var timeOfDay = ""

        if(checkBoxBoolean == false)
        {
            timeOfDay = "OffPeakTime"
        }
        else{
            timeOfDay = "PeakTime"
        }

        var cost = 0.0

        // API declaration
        val request = Request.Builder()
            .get()
            .url("https://api.wmata.com/Rail.svc/json/jSrcStationToDstStationInfo?FromStationCode=$origin&ToStationCode=$destination")
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
            val stationInfo = json.getJSONArray("StationToStationInfos")
            // contents to list
            if (stationInfo.length() != 0){
                val curr = stationInfo.getJSONObject(0)
                val costArray = curr.getJSONObject("RailFare")
                if(costArray.length() != 0)
                {
                    cost = costArray.getDouble("$timeOfDay")
                    Log.e("StationEntranceManager", "Cost: $cost")
                    return cost.toString()
                }
                else{
                    Log.e("StationEntranceManager", "Cost response was  not successful")
                    return "Error: Cost not found"
                }
            }

        } else {
            Log.e("StationEntranceManager", "Cost response was  not successful")
        }
        return "Error: Cost not found"
    } fun retrieveMetroDuration(origin: String, destination: String, apiKey: String): String{
        var duration = 0.0

        // API declaration
        val request = Request.Builder()
                .get()
                .url("https://api.wmata.com/Rail.svc/json/jSrcStationToDstStationInfo?FromStationCode=$origin&ToStationCode=$destination")
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
            val stationInfo = json.getJSONArray("StationToStationInfos")
            // contents to list
            if (stationInfo.length() != 0){
                val curr = stationInfo.getJSONObject(0)
                var duration = curr.getInt("RailTime")
                Log.e("StationEntranceManager", "Duration: $duration")
                return duration.toString()
            }

        } else {
            Log.e("StationEntranceManager", "Duration response was  not successful")
        }
        return "Error: Duration not found"
    } fun retrieveStationName(stationCode: String, apiKey: String): String{
        Log.e("StationEntranceManager", "before request")


        // API declaration
        val request = Request.Builder()
                .get()
                .url("https://api.wmata.com/Rail.svc/json/jStationInfo?StationCode=$stationCode")
                .header("api_key", "$apiKey")
                .build()
        Log.e("StationEntranceManager", "request executed")

        val response: Response = okHttpClient.newCall(request).execute()
        Log.e("StationEntaneManager", "request executed")

        // Get the JSON body
        val responseBody: String? = response.body?.string()
        Log.e("StationEntranceManager", "json body")

        // If the response is successful & body is not Null or blank, parse
        if (response.isSuccessful && !responseBody.isNullOrBlank()) {

            Log.e("StationEntranceManager", "response successful")

            // set up for parsing
            val json = JSONObject(responseBody)
            val result = json.getString("Name")

            // contents to list
            if (result.isNotEmpty()){
                Log.e("StationNameManager", "Station: $station")
                return result
            }
        }
        Log.e("StationNameManager", "Station not found!!")
        return "Error: Station not found"
    } fun retrieveMetroDelays(apiKey: String): String {
        val delayList = mutableListOf<String>()
        var delayListString = ""

        // API declaration
        val request = Request.Builder()
                .get()
                .url("https://api.wmata.com/Incidents.svc/json/Incidents")
                .header("api_key", "$apiKey")
                .build()

        val response: Response = okHttpClient.newCall(request).execute()

        // Get the JSON body
        val responseBody: String? = response.body?.string()

        // If the response is successful & body is not Null or blank, parse
        if (response.isSuccessful && !responseBody.isNullOrBlank()) {

            Log.e("StationEntranceManager", "delay API response successful")

            // set up for parsing
            val json = JSONObject(responseBody)
            val incidents = json.getJSONArray("Incidents")
            var delay = ""

            // contents to list
            for (i in 0 until incidents.length()) {
                val curr = incidents.getJSONObject(i)
                delay = curr.getString("Description")

                delayList.add(delay+"\n\n")
                delayListString = delayList.joinToString()
            }
        }
        else{
            Log.e("StationNameManager", "No Delays Found")
        }
        return delayListString
    } fun retrieveTrainTimes(originStation: String, apiKey: String): List<TrainTime> {
        val trainList = mutableListOf<TrainTime>()

        // API declaration
        val request = Request.Builder()
                .get()
                .url("https://api.wmata.com/StationPrediction.svc/json/GetPrediction/$originStation")
                .header("api_key", "$apiKey")
                .build()

        val response: Response = okHttpClient.newCall(request).execute()

        // Get the JSON body
        val responseBody: String? = response.body?.string()

        // If the response is successful & body is not Null or blank, parse
        if (response.isSuccessful && !responseBody.isNullOrBlank()) {

            // set up for parsing
            val json = JSONObject(responseBody)
            val trains = json.getJSONArray("Trains")

            // contents to list
            for (i in 0 until trains.length()) {
                val curr = trains.getJSONObject(i)
                val lineString = curr.getString("Line")
                var minutesString = curr.getString("Min")
                val destString = curr.getString("DestinationName")
                val orgString = curr.getString("LocationName")

                if (minutesString != "ARR") {
                    minutesString += " minutes"
                }

                val trainTime = TrainTime(
                        originStation = orgString,
                        destination = destString,
                        line = lineString,
                        time = minutesString
                )

                trainList.add(trainTime)
            }
        } else {
            Log.e("StationNameManager", "No Trains Found")
        }
        return trainList
    } fun retrieveBusStop(Lat: Double, Lon: Double, apiKey: String): String {
        val Radius = 10000

        Log.e("StationEntranceManager", "before request")


        // API declaration
        val request = Request.Builder()
            .get()
            .url("https://api.wmata.com/Bus.svc/json/jStops?Lat=$Lat&Lon=$Lon&Radius=$Radius\n")
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
            val stops = json.getJSONArray("Stops")

            // contents to list
            if (stops.length() != 0) {
                val curr = stops.getJSONObject(0)
                station = curr.getString("Name")
                Log.e("StationEntranceManager", "Station: $station")
                return station
            }
        }
        Log.e("StationEntranceManager", "Station not found!!")
        return "Error: Station not found"
    } fun retrieveBusLinesAtStop(Lat: Double, Lon: Double, apiKey: String): String{
        val Radius = 10000
        Log.e("StationEntranceManager", "before request")


        // API declaration
        val request = Request.Builder()
                .get()
                .url("https://api.wmata.com/Bus.svc/json/jStops?Lat=$Lat&Lon=$Lon&Radius=$Radius\n")
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
            val stops = json.getJSONArray("Stops")

            // contents to list
            if (stops.length() != 0){
                val curr = stops.getJSONObject(0)
                var lines = curr.getJSONArray("Routes")
                var allLines = ""
                for (i in 1..lines.length()){
                    allLines = allLines + lines[i-1] + ";"
                }
                Log.e("StationEntranceManager", "Station: $station")
                return allLines
            }
        }
        Log.e("StationEntranceManager", "Station not found!!")
        return "Error: Station not found"
    }
}