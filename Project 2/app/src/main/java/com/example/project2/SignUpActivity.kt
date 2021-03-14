package com.example.project2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity: AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var passwordVerification: EditText
    private lateinit var signup: Button
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preferences = getSharedPreferences("transit-app", Context.MODE_PRIVATE)

        //set layout
        setContentView(R.layout.activity_signup)

        //assign variables to their GUI IDs
        email = findViewById(R.id.emailSignUp)
        password = findViewById(R.id.passwordSignUp)
        passwordVerification = findViewById(R.id.passwordVerification)
        signup = findViewById(R.id.signUpButtonSignUpPage)

        //get firebase instance
        firebaseAuth = FirebaseAuth.getInstance()

        //diable signup button
        signup.isEnabled = false


        //get strings
        val passwordError: String = getString(R.string.passwordDoesNotMatch)
        val signUpSuccessful: String = getString(R.string.signUpSuccessful)

        //on click listener for sign up
        signup.setOnClickListener{
            //get username and password that the user inputs
            val inputtedUsername: String = email.text.toString()
            val inputtedPassword: String = password.text.toString()
            val inputtedPasswordVerification: String = passwordVerification.text.toString()

            if(inputtedPassword == inputtedPasswordVerification)
            {
                firebaseAuth.createUserWithEmailAndPassword(inputtedUsername,inputtedPassword)
                    .addOnCompleteListener{
                            task: Task<AuthResult> ->
                        if(task.isSuccessful)
                        {
                            val currentUser = firebaseAuth.currentUser!!
                            val currentEmail = currentUser.email
                            Toast.makeText(this, "Registered successfully $currentEmail", Toast.LENGTH_LONG).show()

                            preferences.edit().putString("username",inputtedUsername).apply()

                            //if successful, send user to the login screen
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                        }
                        else{
                            val exception = task.exception
                            Toast.makeText(this, "Failed to register $exception", Toast.LENGTH_LONG).show()
                        }
                    }
            }
            else
            {
                runOnUiThread{
                    val toast = Toast.makeText(
                        this@SignUpActivity,
                        "$passwordError",
                        Toast.LENGTH_LONG
                    )
                    toast.show()
                }
            }
        }

        //text changed listeners for user inputs (to enable and disable the signup button)
        email.addTextChangedListener(textWatcher)
        password.addTextChangedListener(textWatcher)
        passwordVerification.addTextChangedListener(textWatcher)
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?){}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int){}
        override fun onTextChanged(s: CharSequence?, start: Int, before:Int, count: Int)
        {
            val inputtedUsername: String = email.text.toString()
            val inputtedPassword: String = password.text.toString()
            val inputtedPasswordVerification: String = passwordVerification.text.toString()
            val enableButton = inputtedUsername.isNotEmpty() && inputtedPassword.isNotEmpty() && inputtedPasswordVerification.isNotEmpty()

            signup.setEnabled(enableButton)
        }

    }
}