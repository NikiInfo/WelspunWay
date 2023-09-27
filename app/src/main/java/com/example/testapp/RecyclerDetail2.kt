package com.example.testapp

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

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


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_detail2)

        btnConfirmBooking = findViewById(R.id.btnCofirmBooking)
        tvcarNumber = findViewById(R.id.detailCarNumber)
        image = findViewById(R.id.detailImage)
        carName = findViewById(R.id.detailCarName)
        name = findViewById(R.id.detailTitle)

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
            val databaseReference = FirebaseDatabase.getInstance().getReference("Driver")
            if (selectedDriverId != null) {
                databaseReference.child(selectedDriverId).child("availability").setValue("no")
                    .addOnSuccessListener {
                        Toast.makeText(this,"Booking has been confirmed succesfully",Toast.LENGTH_LONG).show()
                    }
            }
        }


    }


}