package com.example.testapp

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.textclassifier.TextClassifierEvent.TextSelectionEvent.Builder
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.testapp.databinding.ActivityHomeBinding
import com.example.testapp.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class HomeActivity : AppCompatActivity() {
    private val REQUEST_SMS_PERMISSION = 123
    private lateinit var auth: FirebaseAuth
    private lateinit var  binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        auth = Firebase.auth
            //CODE TO CHANGE STATUS BAR COLOR
//        val window: Window = this.window
//        window.statusBarColor = this.resources.getColor(R.color.Welspun)


        binding.btnProfile.setOnClickListener {
            val intent = Intent(this,UserDetails::class.java)
            startActivity(intent)
        }

        binding.btnSignOut.setOnClickListener {
            auth.signOut()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }



    }
//    private fun requestSmsPermission(){
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS), REQUEST_SMS_PERMISSION)
//        }
//    }
}