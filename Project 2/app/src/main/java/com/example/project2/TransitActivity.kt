package com.example.project2

import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import kotlin.properties.Delegates


class TransitActivity: AppCompatActivity() {

    private lateinit var origin: EditText
    private lateinit var destination: EditText
    private lateinit var search: Button
    private lateinit var favorites: Button
    private lateinit var checkBox: CheckBox
    private var checkBoxBoolean by Delegates.notNull<Boolean>()
    private lateinit var map: Button
    private lateinit var trainTimes: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preferences = getSharedPreferences("transit-app", Context.MODE_PRIVATE)
        var lat1 = 0.0
        var lon1 = 0.0
        var lat2 = 0.0
        var lon2 = 0.0


        setContentView(R.layout.activity_transit)

        origin = findViewById(R.id.origin)
        destination = findViewById(R.id.destination)
        search = findViewById(R.id.searchButton)
        favorites = findViewById(R.id.favoritesButton)
        checkBox = findViewById(R.id.checkBox)
        map = findViewById(R.id.mapButton)
        trainTimes= findViewById(R.id.viewTrainTimes)

        search.isEnabled = false
        map.isEnabled = false
        checkBoxBoolean = false
        trainTimes.isEnabled = false


        //create on click listener for the search button
        search.setOnClickListener{v: View? ->

            //get user inputs
            val locationName: String = origin.text.toString()
            val locationName2: String = destination.text.toString()

            if(locationName.isNotEmpty() && locationName2.isNotEmpty())
            {
                val intent = Intent(this, RoutesActivity::class.java)
                doAsync {

                    //create geocoder
                    val geocoder: Geocoder = Geocoder(this@TransitActivity)

                    //get lat and long
                    val firstResult: List<Address> = try {
                        geocoder.getFromLocationName(
                                locationName,
                                10
                        )
                    } catch (e: Exception) {
                        Log.e("TransitActivity", "Geocoder has Failed for first address", e)
                        listOf<Address>()
                    }

                    //get lat and long
                    val secondResult: List<Address> = try {
                        geocoder.getFromLocationName(
                                locationName2,
                                10
                        )
                    } catch (e: Exception) {
                        Log.e("TransitActivity", "Geocoder has Failed for second address", e)
                        listOf<Address>()
                    }

                    //move to UI thread
                    runOnUiThread {
                        if (firstResult.isNotEmpty()) {
                            //only get first result
                            val firstResult = firstResult.first()
                            lat1 = firstResult.latitude
                            lon1 = firstResult.longitude
                            Log.e("TransitAcivity", "First Result: $lat1, $lon1")
                        }
                        if (secondResult.isNotEmpty()) {
                            //only get first result
                            val secondResult = secondResult.first()
                            lat2 = secondResult.latitude
                            lon2 = secondResult.longitude
                            Log.e("TransitAcivity", "Second Result: $lat2, $lon2")
                        }
                        intent.putExtra("lat1", lat1.toString())
                        intent.putExtra("lon1", lon1.toString())
                        intent.putExtra("lat2", lat2.toString())
                        intent.putExtra("lon2", lon2.toString())
                        intent.putExtra("checkBoxBoolean", checkBoxBoolean)
                        Log.e("TransitActivity", "checkbox: $checkBoxBoolean")
                        startActivity(intent)
                    }
                }
            }

        }

        favorites.setOnClickListener {
            val intent = Intent(this, FavoritesActivity::class.java)
            startActivity(intent)
        }

        trainTimes.setOnClickListener {

            //get user inputs
            val locationName: String = origin.text.toString()

            if(locationName.isNotEmpty())
            {
                val intent = Intent(this, TrainTimesActivity::class.java)
                doAsync {

                    //create geocoder
                    val geocoder: Geocoder = Geocoder(this@TransitActivity)

                    //get lat and long
                    val firstResult: List<Address> = try {
                        geocoder.getFromLocationName(
                                locationName,
                                10
                        )
                    } catch (e: Exception) {
                        Log.e("TransitActivity", "Geocoder has Failed for first address", e)
                        listOf<Address>()
                    }

                    //move to UI thread
                    runOnUiThread {
                        if (firstResult.isNotEmpty()) {
                            //only get first result
                            val firstResult = firstResult.first()
                            lat1 = firstResult.latitude
                            lon1 = firstResult.longitude
                            Log.e("TransitAcivity", "First Result: $lat1, $lon1")
                        }
                        intent.putExtra("lat1", lat1.toString())
                        intent.putExtra("lon1", lon1.toString())
                        startActivity(intent)
                    }
                }
            }
        }

        map.setOnClickListener {

            //get user inputs
            val locationName: String = origin.text.toString()
            val locationName2: String = destination.text.toString()

            if(locationName.isNotEmpty() && locationName2.isNotEmpty())
            {
                val intent = Intent(this, MapsActivity::class.java)
                doAsync {

                    //create geocoder
                    val geocoder: Geocoder = Geocoder(this@TransitActivity)

                    //get lat and long
                    val firstResult: List<Address> = try {
                        geocoder.getFromLocationName(
                            locationName,
                            10
                        )
                    } catch (e: Exception) {
                        Log.e("TransitActivity", "Geocoder has Failed for first address", e)
                        listOf<Address>()
                    }

                    //get lat and long
                    val secondResult: List<Address> = try {
                        geocoder.getFromLocationName(
                            locationName2,
                            10
                        )
                    } catch (e: Exception) {
                        Log.e("TransitActivity", "Geocoder has Failed for second address", e)
                        listOf<Address>()
                    }

                    //move to UI thread
                    runOnUiThread {
                        if (firstResult.isNotEmpty()) {
                            //only get first result
                            val firstResult = firstResult.first()
                            lat1 = firstResult.latitude
                            lon1 = firstResult.longitude
                            Log.e("TransitAcivity", "First Result: $lat1, $lon1")
                        }
                        if (secondResult.isNotEmpty()) {
                            //only get first result
                            val secondResult = secondResult.first()
                            lat2 = secondResult.latitude
                            lon2 = secondResult.longitude
                            Log.e("TransitAcivity", "Second Result: $lat2, $lon2")
                        }
                        intent.putExtra("lat1", lat1.toString())
                        intent.putExtra("lon1", lon1.toString())
                        intent.putExtra("lat2", lat2.toString())
                        intent.putExtra("lon2", lon2.toString())
                        startActivity(intent)
                    }
                }
            }
        }

        origin.addTextChangedListener(textWatcher)
        destination.addTextChangedListener(textWatcher)
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?){}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int){}
        override fun onTextChanged(s: CharSequence?, start: Int, before:Int, count: Int)
        {
            val inputtedOrigin: String = origin.text.toString()
            val inputtedDestination: String = destination.text.toString()
            val enableButton = inputtedOrigin.isNotEmpty() && inputtedDestination.isNotEmpty()
            val enableTrainTimes = inputtedOrigin.isNotEmpty()

            search.setEnabled(enableButton)
            map.setEnabled(enableButton)
            trainTimes.setEnabled(enableTrainTimes)
        }

    }
    //used Android Developer Guide as a reference for this code: https://developer.android.com/guide/topics/ui/controls/checkbox
    fun onCheckboxClicked(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked

            when (view.id) {
                R.id.checkBox -> {
                    checkBoxBoolean = checked
                }
            }
        }
    }
}