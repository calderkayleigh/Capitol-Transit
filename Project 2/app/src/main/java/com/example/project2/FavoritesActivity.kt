package com.example.project2

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class FavoritesActivity: AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set layout
        setContentView(R.layout.activity_favorites)
        recyclerView = findViewById(R.id.recyler)

        // Create an empty but mutable list of favorites
        val favoritesList = mutableListOf<Favorite>()

        val preferences = getSharedPreferences("transit-app", Context.MODE_PRIVATE)
        val originStation = preferences.getString("origin", "")
        val destStation = preferences.getString("destination", "")
        //TODO - create the description
        val description = "Placeholder"

        //TODO - add condition where we cannot add duplicates to the list

        val favorite = Favorite(
                originStation = originStation,
                destinationStation = destStation,
                description = description
        )

        favoritesList.add(favorite)

        recyclerView.adapter = FavoritesAdapter(favoritesList)

        //tell if you want the list to be vertical or horizontal
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this@FavoritesActivity)


    }
}