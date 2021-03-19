package com.example.project2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class RailAdapter(val rails: List<Rail>) : RecyclerView.Adapter<RailAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        //Number of rows we want, size of our list
        return rails.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //Recycler needs a new row
        //Reads the XML file for row type
        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.context)
        val rootLayout: View = layoutInflater.inflate(R.layout.row_rail_route, parent, false)

        //Uses the new row to build a ViewHolder
        return ViewHolder(rootLayout)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //ready to display a new row on the screen in the indicated position
        //override UI elements with correct information

        //get current position
        val  currentRail = rails[position]

        holder.origin.text = currentRail.origin
        holder.destination.text = currentRail.destination
        holder.cost.text = currentRail.cost
        holder.duration.text = currentRail.duration
        holder.delays.text = currentRail.delays
        holder.times.text = currentRail.times

    }

    class ViewHolder(rootLayout: View): RecyclerView.ViewHolder(rootLayout){

        //connect variables to their IDs
        val origin: TextView = rootLayout.findViewById(R.id.originStation)
        val destination: TextView = rootLayout.findViewById(R.id.destStation)
        val cost: TextView = rootLayout.findViewById(R.id.cost)
        val duration: TextView = rootLayout.findViewById(R.id.durationOfTravel)
        val delays: TextView = rootLayout.findViewById(R.id.delays)
        val times: TextView = rootLayout.findViewById(R.id.times)
    }
}