package com.example.testapp

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
        }


    }

    private fun saveBookingData() {
        val driverName = intent.getStringExtra("Driver Name")
       val dataHolder = DataHolder.getInstance()
        val to = dataHolder.toDestination
        val from = dataHolder.fromDestination
        val purpose = dataHolder.purpose

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
                        null,
                        purpose,
                        carNumber,
                        null,
                        null,
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


}