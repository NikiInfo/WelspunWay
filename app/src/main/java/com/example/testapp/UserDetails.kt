package com.example.testapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.testapp.databinding.ActivityHomeBinding
import com.example.testapp.databinding.ActivityUserDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserDetails : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var  binding: ActivityUserDetailsBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var employee: Employee
    private lateinit var uid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        val button = findViewById<Button>(R.id.btnAddUserDetails)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog = builder.create()

        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()

        databaseReference = FirebaseDatabase.getInstance().getReference("Employees")
        if(uid.isNotEmpty()){

            getUserData()

        }

        binding.btnAddUserDetails.setOnClickListener {
            val intent = Intent(this,AddUserDetails::class.java)
            startActivity(intent)
            //button.isEnabled = false
//            button.visibility = View.GONE
        }
    }

    private fun getUserData() {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog = builder.create()
        dialog.show()
        databaseReference.child(uid).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                 val employee = snapshot.getValue(Employee::class.java)
                if(employee!=null ) {
                    binding.tvEmpName.text = employee.name
                    binding.tvEmpId.text = "${employee.empId}"
                    binding.tvEmpDesignation.text = employee.designation
                    binding.tvEmpPlant.text = employee.plant
                    dialog.dismiss()
                } else {
                    val intent = Intent(this@UserDetails,AddUserDetails::class.java)
                    startActivity(intent)
                    Toast.makeText(this@UserDetails,"Please Udpate user details first",Toast.LENGTH_SHORT).show()
                }


            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@UserDetails,"Error getting Profile Details",Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }

        })
    }
}