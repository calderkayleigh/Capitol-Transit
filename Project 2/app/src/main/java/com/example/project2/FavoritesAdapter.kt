package com.example.project2

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class FavoritesAdapter(val favorites: List<Favorite>) : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>()  {

    override fun getItemCount(): Int {
        //Number of rows we want, size of our list
        return favorites.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //Recycler needs a new row
        //Reads the XML file for row type
        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.context)
        val rootLayout: View = layoutInflater.inflate(R.layout.favorite_layout, parent, false)

        //Uses the new row to build a ViewHolder
        return ViewHolder(rootLayout)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //ready to display a new row on the screen in the indicated position

        //get current position
        val  currentFavorite = favorites[position]

        //get title, source, and description
        holder.origin.text = currentFavorite.originStation
        holder.destination.text = currentFavorite.destinationStation
        holder.description.text = currentFavorite.description


    }

    class ViewHolder(rootLayout: View): RecyclerView.ViewHolder(rootLayout){

        //connect variables to their IDs
        val origin: TextView = rootLayout.findViewById(R.id.originFavorite)
        val destination: TextView = rootLayout.findViewById(R.id.destinationFavorite)
        val description: TextView = rootLayout.findViewById(R.id.favoriteDescription)

    }
}