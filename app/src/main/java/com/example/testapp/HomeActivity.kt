package com.example.testapp

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.view.Window
import android.view.textclassifier.TextClassifierEvent.TextSelectionEvent.Builder
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
    private lateinit var binding: ActivityHomeBinding

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
            val intent = Intent(this, UserDetails::class.java)
            startActivity(intent)
        }

        binding.btnSignOut.setOnClickListener {
            auth.signOut()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }
        binding.btnSelectCar.setOnClickListener {
            if (checkSmsPermission()) {
                // Permission is granted, send the SMS
                sendSms()
            }
        }





    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_SMS_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, send the SMS
                sendSms()
            } else {
                // Permission denied, show an explanation or handle it accordingly
                showPermissionExplanation()
            }
        }
    }
    private fun checkSmsPermission(): Boolean {
        val sendSmsPermission =android.Manifest.permission.SEND_SMS
        if (ContextCompat.checkSelfPermission(this, sendSmsPermission) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this, arrayOf(sendSmsPermission), REQUEST_SMS_PERMISSION)
            return false
        }
        return true
    }
    private fun sendSms() {
        val phoneNumber = "+916370535302" // Replace with the recipient's phone number
        val message = "Hello, this is your SMS message!" // Replace with your message

        try {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            Toast.makeText(this, "SMS sent!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "SMS failed to send.", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }
    private fun showPermissionExplanation() {
        AlertDialog.Builder(this)
            .setMessage("SMS permission is required to send messages.")
            .setPositiveButton("OK") { dialog: DialogInterface?, _: Int ->
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.SEND_SMS),
                    REQUEST_SMS_PERMISSION
                )
                dialog?.dismiss()
            }
            .setNegativeButton("Cancel") { dialog: DialogInterface?, _: Int ->
                dialog?.dismiss()
            }
            .show()
    }

    }
