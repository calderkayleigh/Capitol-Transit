package com.example.project2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TrainTimesAdapter(val trainTimes: List<TrainTime>) : RecyclerView.Adapter<TrainTimesAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        //Number of rows we want, size of our list
        return trainTimes.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //Recycler needs a new row
        //Reads the XML file for row type
        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.context)
        val rootLayout: View = layoutInflater.inflate(R.layout.traintime_layout, parent, false)

        //Uses the new row to build a ViewHolder
        return ViewHolder(rootLayout)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //ready to display a new row on the screen in the indicated position

        //get current position
        val currentTrain = trainTimes[position]

        //get title, source, and description
        holder.origin.text = currentTrain.originStation
        holder.destination.text = currentTrain.destination
        holder.time.text = currentTrain.time.toString()
        holder.line.text = currentTrain.line

    }

    class ViewHolder(rootLayout: View): RecyclerView.ViewHolder(rootLayout){

        //connect variables to their IDs
        val origin: TextView = rootLayout.findViewById(R.id.originTrain)
        val destination: TextView = rootLayout.findViewById(R.id.destinationUserInput)
        val time: TextView = rootLayout.findViewById(R.id.minutesTilTrain)
        val line: TextView = rootLayout.findViewById(R.id.lineUserInput)

    }
}