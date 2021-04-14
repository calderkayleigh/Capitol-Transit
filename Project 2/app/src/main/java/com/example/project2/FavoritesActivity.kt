package com.example.project2

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class FavoritesActivity: AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    // Create an empty but mutable list of favorites
    var favoritesList = mutableListOf<Favorite>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set layout
        setContentView(R.layout.activity_favorites)
        recyclerView = findViewById(R.id.recyler)

        val preferences = getSharedPreferences("transit-app", Context.MODE_PRIVATE)


        //TODO - add condition where we cannot add duplicates to the list
        //TODO - make sure list remembers previous (potentially use string set?)

        for(i in 1..10)
        {
            if(!preferences.getString("origin$i", "").isNullOrBlank())
            {
                val originStation = preferences.getString("origin$i", "")
                val destStation = preferences.getString("destination$i", "")
                val description = preferences.getString("description$i", "")

                val favorite = Favorite(
                        originStation = originStation,
                        destinationStation = destStation,
                        description = description
                )

                favoritesList.add(favorite)
            }
        }


        var favSize = favoritesList.size

        Log.e("FavoritesActivity", " list size: $favSize")
        recyclerView.adapter = FavoritesAdapter(favoritesList)

        //tell if you want the list to be vertical or horizontal
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this@FavoritesActivity)


    }
}