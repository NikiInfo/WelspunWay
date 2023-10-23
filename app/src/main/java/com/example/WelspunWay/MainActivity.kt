package com.example.WelspunWay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        //this is used to hide the action bar
        supportActionBar?.hide()
        auth = Firebase.auth


            val user = auth.currentUser
            if(user != null) {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()

            } else {
                val signup = findViewById<Button>(R.id.signup)
                signup.setOnClickListener{
                    val intent = Intent(this, SignUpActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                val signin = findViewById<Button>(R.id.login)
                signin.setOnClickListener {
                    val intent = Intent(this, SignInActivity::class.java)
                    startActivity(intent)
                    finish()

            }
                val admin = findViewById<Button>(R.id.btnAdminView)
                admin.setOnClickListener {
                    val intent = Intent(this, AdminActivity::class.java)
                    startActivity(intent)
                    finish()
                }


        }

        




//        Handler(Looper.getMainLooper()).postDelayed({
//
//
//        }3000) //
    }
    override fun onBackPressed() {
        //super.onBackPressed()
    }


}