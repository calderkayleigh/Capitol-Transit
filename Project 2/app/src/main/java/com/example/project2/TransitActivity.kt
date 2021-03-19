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
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find


class TransitActivity: AppCompatActivity() {

    private lateinit var origin: EditText
    private lateinit var destination: EditText
    private lateinit var search: Button
    private lateinit var favorites: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preferences = getSharedPreferences("transit-app", Context.MODE_PRIVATE)

        setContentView(R.layout.activity_transit)

        origin = findViewById(R.id.origin)
        destination = findViewById(R.id.destination)
        search = findViewById(R.id.searchButton)
        favorites = findViewById(R.id.favoritesButton)

        search.isEnabled = false


        //create on click listener for the search button
        search.setOnClickListener{v: View? ->

            //get user inputs
            val locationName: String = origin.text.toString()
            val locationName2: String = destination.text.toString()

            if(locationName.isNotEmpty() && locationName2.isNotEmpty())
            {
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
                            val lat = firstResult.latitude
                            val long = firstResult.longitude
                            Log.e("TransitAcivity", "First Result: $lat, $long")
                        }
                        if (secondResult.isNotEmpty()) {
                            //only get first result
                            val secondResult = secondResult.first()
                            val lat = secondResult.latitude
                            val long = secondResult.longitude
                            Log.e("TransitAcivity", "Second Result: $lat, $long")
                        }
                    }
                }
            }

            //open routes activity using an intent
            val intent = Intent(this, RoutesActivity::class.java)
            startActivity(intent)
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

            search.setEnabled(enableButton)
        }

    }
}