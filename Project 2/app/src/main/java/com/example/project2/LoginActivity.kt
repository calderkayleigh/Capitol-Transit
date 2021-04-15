package com.example.project2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    //declare variables
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var login: Button
    private lateinit var signup: Button
    private lateinit var progress: ProgressBar
    private lateinit var switch: Switch
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preferences = getSharedPreferences("transit-app", Context.MODE_PRIVATE)

        //set layout
        setContentView(R.layout.activity_login)

        //get firebase instance
        firebaseAuth = FirebaseAuth.getInstance()

        //connect variables to their GUI IDs
        email = findViewById(R.id.emailLogin)
        password = findViewById(R.id.passwordLogin)
        login = findViewById(R.id.loginButton)
        signup = findViewById(R.id.signUpButton)
        progress = findViewById(R.id.progressBarLogin)
        switch = findViewById(R.id.switchLogin)

        //initially disable the login button
        login.isEnabled = false

        //create on click listener for the search button
        signup.setOnClickListener{v: View? ->

            //open signup activity using an intent
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        var switchStatus = preferences.getString("switch", "")
        Log.e("LoginActivity", "switch status: $switchStatus")
        if(switchStatus == "yes")
        {
            login.isEnabled = true
            switch.isChecked = true
            val savedUsername = preferences.getString("username", "")
            val savedPassword = preferences.getString("password", "")
            email.setText(savedUsername)
            password.setText(savedPassword)
        }
        else{
            switch.isChecked = false
            email.setText("")
            password.setText("")
        }

        login.setOnClickListener{

            //Shared preferences
            val inputtedUsername: String = email.text.toString()
            val inputtedPassword: String = password.text.toString()

            //firebase for logging in
            firebaseAuth.signInWithEmailAndPassword(inputtedUsername, inputtedPassword).addOnCompleteListener{
                    task: Task<AuthResult> ->

                if(task.isSuccessful)
                {

                    val currentUser = firebaseAuth.currentUser!!
                    val currentEmail = currentUser.email
                    Toast.makeText(this, "Logged in successfully $currentEmail", Toast.LENGTH_LONG).show()

                    preferences.edit().putString("username", inputtedUsername)
                        .apply()
                    preferences.edit().putString("password", inputtedPassword)
                        .apply()

                    val intent = Intent(this,TransitActivity::class.java)
                    startActivity(intent)
                }
                else{

                    val exception = task.exception
                    Toast.makeText(this, "Failed to login $exception", Toast.LENGTH_LONG).show()
                }
            }
        }

        switch.setOnCheckedChangeListener(switchWatcher)
        email.addTextChangedListener(textWatcher)
        password.addTextChangedListener(textWatcher)
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?){}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int){}
        override fun onTextChanged(s: CharSequence?, start: Int, before:Int, count: Int)
        {
            val inputtedUsername: String = email.text.toString()
            val inputtedPassword: String = password.text.toString()
            val enableButton = inputtedUsername.isNotEmpty() && inputtedPassword.isNotEmpty()

            login.setEnabled(enableButton)
        }

    }

    private val switchWatcher =
        CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            val preferences = getSharedPreferences("transit-app", Context.MODE_PRIVATE)

            if(isChecked) {
                preferences.edit().putString("switch", "yes")
                    .apply()
                Log.e("LoginActivity", "Switch is checked")
            } else {
                preferences.edit().putString("switch", "no")
                    .apply()
                Log.e("LoginActivity", "Switch is not checked")
            }
        }
}