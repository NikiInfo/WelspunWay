package com.example.testapp

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.Date

class RecyclerDetail2 : AppCompatActivity() {
    val driverList = ArrayList<Driver>()
    //private val driverList: ArrayList<Driver>()
    private lateinit var carName: TextView
    private lateinit var name: TextView
    private lateinit var tvcarNumber: TextView
    private lateinit var image: ImageView
    private var imageUrl: String = ""
    private var key = ""
    private lateinit var btnConfirmBooking: Button
    private lateinit var databaseReference: DatabaseReference
    private val REQUEST_SMS_PERMISSION = 123



    override fun onCreate(savedInstanceState: Bundle?) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Booking")


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_detail2)
        btnConfirmBooking = findViewById(R.id.btnCofirmBooking)
        tvcarNumber = findViewById(R.id.detailCarNumber)
        image = findViewById(R.id.detailImage)
        carName = findViewById(R.id.detailCarName)
        name = findViewById(R.id.detailTitle)
        val bookingId = databaseReference.push().key ?: ""
        val driverName = intent.getStringExtra("Driver Name")
        val carNumber = intent.getStringExtra("Car Number")

        val bundle = intent.extras
        if(bundle != null){
            tvcarNumber.text = bundle.getString("Car Number")
            carName.text = bundle.getString("Car Name")
            key = bundle.getString("Driver Id") ?: ""
            name.text = bundle.getString("Driver Name")

            imageUrl = bundle.getString("Image")?: ""
            Glide.with(this).load(imageUrl).into(image)

        }
        btnConfirmBooking.setOnClickListener {
            val selectedDriverId = intent.getStringExtra("Driver Id")
            val vacancy = intent.getStringExtra("Availability")
            if (vacancy == "yes") {
                saveBookingData()
                val databaseReference2 = FirebaseDatabase.getInstance().getReference("Driver")
                if (selectedDriverId != null) {
                    databaseReference2.child(selectedDriverId).child("availability").setValue("no")
                        .addOnSuccessListener {
                            Toast.makeText(
                                this,
                                "Booking has been confirmed succesfully",
                                Toast.LENGTH_LONG
                            ).show()

                        }
                }
                val intent = Intent(this, booking_confirmed::class.java).apply {
                    putExtra("Driver Name",driverName)
                    putExtra("Car Number",carNumber)
                    putExtra("BookingId",bookingId)
                }
                startActivity(intent)
            } else  {
                Toast.makeText(
                    this,
                    "Sorry! This has been booked by someone else",
                    Toast.LENGTH_LONG
                ).show()
            }
                        if (checkSmsPermission()) {
                // Permission is granted, send the SMS
                sendSms()
            }

        }


    }

    private fun saveBookingData() {
        val driverName = intent.getStringExtra("Driver Name")
       val dataHolder = DataHolder.getInstance()
        val to = dataHolder.toDestination
        val from = dataHolder.fromDestination
        val purpose = dataHolder.purpose
        val empId = dataHolder.empId
        val empName = dataHolder.empName

        val carNumber = intent.getStringExtra("Car Number")
        val date  = Date()
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val timeStamp = formatter.format(date)

        val selectedDriverId = intent.getStringExtra("Driver Id")
        val bookingId = databaseReference.push().key ?: ""
        val booking =
                    Booking(
                        selectedDriverId,
                        driverName,
                        to,
                        from,
                        empId,
                        purpose,
                        carNumber,
                        null,
                        empName,
                        timeStamp,
                        null,
                        bookingId
                        )



        if (bookingId != null) {
            databaseReference.child(bookingId).setValue(booking)
                .addOnCompleteListener {
                    Toast.makeText(
                        this@RecyclerDetail2,
                        "Details saved successfully",
                        Toast.LENGTH_SHORT
                    ).show()

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
        val dataHolder = DataHolder.getInstance()
        val empName = dataHolder.empName
        val to = dataHolder.toDestination
        val from = dataHolder.fromDestination
        val phoneNumber = "+919979796053" // Replace with the recipient's phone number
        val message = "Your Ride has Started with $empName Location from $from to $to" // Replace with your message

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
        androidx.appcompat.app.AlertDialog.Builder(this)
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