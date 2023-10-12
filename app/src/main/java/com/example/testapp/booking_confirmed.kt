package com.example.testapp

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.example.testapp.databinding.ActivityBookingConfirmedBinding
import com.example.testapp.databinding.ActivityUpdateDriverDetailsBinding
import com.example.testapp.databinding.ActivityUserDetailsBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class booking_confirmed : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var binding: ActivityBookingConfirmedBinding
    private val REQUEST_CALL_PERMISSION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingConfirmedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val callButton = findViewById<ImageButton>(R.id.imageButton)
        val endRide = findViewById<Button>(R.id.btnEndRide)
        val dialog = builder.create()
        val bookingId = intent.getStringExtra("BookingId")
        databaseReference = FirebaseDatabase.getInstance().getReference("Booking")
        if(bookingId!=null){
            getDriverData()
            dialog.dismiss()
        } else {
            Toast.makeText(this@booking_confirmed,"Error getting Booking Details", Toast.LENGTH_SHORT).show()
            dialog.dismiss()

        }
        // Button Click function for End Ride
        endRide.setOnClickListener {
            val selectedDriverId = intent.getStringExtra("DriverId")
            val databaseReference1 = FirebaseDatabase.getInstance().getReference("Driver")
            if (selectedDriverId != null) {
                databaseReference1.child(selectedDriverId).child("availability").setValue("yes")
                    .addOnSuccessListener {
                        Toast.makeText(
                            this,
                            "Ride has been ended Succesfully",
                            Toast.LENGTH_LONG
                        ).show()
                    }
            }
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
        }
        // Adding feature for call function on button click
        callButton.setOnClickListener{
            val phoneNumber = "+919979796053"

            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"))

            if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                startActivity(intent)
            } else{
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CALL_PHONE), REQUEST_CALL_PERMISSION)
            }
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == REQUEST_CALL_PERMISSION) {
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val phoneNumber = "+919979796053"

                val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"))

                startActivity(intent)
                } else {
                    Toast.makeText(this@booking_confirmed, "Call permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun getDriverData() {
        val bookingId = intent.getStringExtra("BookingId")
        val builder = AlertDialog.Builder(this)
        val carNumber = intent.getStringExtra("Car Number")
        val driverName = intent.getStringExtra("Driver Name")
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog = builder.create()
        dialog.show()
        binding.textView4.text = driverName
        binding.textView7.text = carNumber
        dialog.dismiss()
        }
    }
