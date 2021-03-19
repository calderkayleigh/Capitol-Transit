package com.example.project2

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.doAsync

class RoutesActivity: AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set layout
        setContentView(R.layout.activity_routes)

        recyclerView = findViewById(R.id.recycler)

        val stationManager = StationEntranceManager()
        doAsync{
            try{
                val routes = stationManager.retrieveRoutes()

                runOnUiThread{
                    if(routes.isEmpty())
                    {
                        val toast = Toast.makeText(
                            this@RoutesActivity,
                            "no results",
                            Toast.LENGTH_LONG
                        )
                        toast.show()
                    }
                    recyclerView.adapter = RailAdapter(routes)

                    //tell if you want the list to be vertical or horizontal
                    recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this@RoutesActivity)

                }
            }
            catch(exception: Exception){
                Log.e("RoutesActivity", "fail")
            }
        }


    }
}