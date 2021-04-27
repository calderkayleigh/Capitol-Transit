package com.example.project2

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.doAsync

class TrainTimesActivity: AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val apiKey = getString(R.string.wmata_api_key)

        //set layout
        setContentView(R.layout.activity_traintimes)
        recyclerView = findViewById(R.id.recyler2)

        val preferences = getSharedPreferences("transit-app", Context.MODE_PRIVATE)

        val stationEntranceManager = StationEntranceManager()

        doAsync {

            var lat1 = intent.getStringExtra("lat1")?.toDouble()!!
            var lon1 = intent.getStringExtra("lon1")?.toDouble()!!

            val originStation = stationEntranceManager.retrieveStation(lat1, lon1, apiKey)
            val trainTimes: List<TrainTime> = stationEntranceManager.retrieveTrainTimes(originStation, apiKey)

            runOnUiThread{
                recyclerView.adapter = TrainTimesAdapter(trainTimes)

                //tell if you want the list to be vertical or horizontal
                recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this@TrainTimesActivity)
            }
        }

    }
}