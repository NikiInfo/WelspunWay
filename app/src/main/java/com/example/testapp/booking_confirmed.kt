package com.example.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingConfirmedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog = builder.create()
        val bookingId = intent.getStringExtra("BookingId")
        databaseReference = FirebaseDatabase.getInstance().getReference("Booking")
        if(bookingId!=null){
            getDriverData()
            dialog.dismiss()
        }
    }

    private fun getDriverData() {
        val bookingId = intent.getStringExtra("BookingId")
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog = builder.create()
        dialog.show()
        if (bookingId != null) {
            databaseReference.child(bookingId).addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val booking = snapshot.getValue(Booking::class.java)
                    if (booking != null) {
                        binding.textView4.text = booking.driverName
                        binding.textView7.text = booking.carNumber
                        dialog.dismiss()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@booking_confirmed,"Error getting Booking Details", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }

            })
        }
    }
}