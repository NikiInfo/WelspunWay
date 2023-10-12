package com.example.testapp

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.view.Window
import android.view.textclassifier.TextClassifierEvent.TextSelectionEvent.Builder
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.testapp.databinding.ActivityHomeBinding
import com.example.testapp.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase


class HomeActivity : AppCompatActivity() {
    private lateinit var empList: ArrayList<Employee>
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityHomeBinding
    private lateinit var employee: Employee
    private lateinit var uid: String



    override fun onResume() {
        super.onResume()
        val plants = resources.getStringArray(R.array.Plants)
        val arrayAdapter = ArrayAdapter(this,R.layout.dropdown_item,plants)
        binding.etFrom.setAdapter(arrayAdapter)
        binding.etTo.setAdapter(arrayAdapter)
    }

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

        databaseReference = FirebaseDatabase.getInstance().getReference("Booking")
        binding.btnSelectCar.setOnClickListener {

            saveData()

        }





    }

    private fun saveData() {

        // Get the user's UID
        uid = auth.currentUser?.uid.toString()

        // Retrieve input data
        val empId = uid
        val from = binding.etFrom.text.toString()
        val to = binding.etTo.text.toString()
        val purpose = binding.tvpurpose.text.toString()

        val databaseReference2 = FirebaseDatabase.getInstance().getReference("Employees")
        // Retrieve user details and then save booking
        databaseReference2.child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val empName = snapshot.child("name").getValue(String::class.java)
                val plant = snapshot.child("plant").getValue(String::class.java)
                val dataHolder = DataHolder.getInstance()
                dataHolder.toDestination = to
                dataHolder.fromDestination = from
                dataHolder.purpose = purpose
                dataHolder.empId = empId
                dataHolder.empName = empName


                if (uid != null) {
                    if(empName==null && plant==null){
                        Toast.makeText(this@HomeActivity, "Please update Your Details Under Profile", Toast.LENGTH_SHORT).show()

                    } else {
                        val intent = Intent(this@HomeActivity,CarSelectActivity::class.java).apply {
                        putExtra("plant",plant)

                        }
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(this@HomeActivity, "User data not found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@HomeActivity, "Please update Your Details Under Profile", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
