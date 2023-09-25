package com.example.testapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.testapp.databinding.ActivityHomeBinding
import com.example.testapp.databinding.ActivityUserDetailsBinding
import com.google.firebase.auth.FirebaseAuth

class UserDetails : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var  binding: ActivityUserDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        val button = findViewById<Button>(R.id.btnAddUserDetails)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddUserDetails.setOnClickListener {
            val intent = Intent(this,AddUserDetails::class.java)
            startActivity(intent)
            //button.isEnabled = false
//            button.visibility = View.GONE
        }
    }
}