package com.example.testapp

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import android.widget.Button
import com.example.testapp.databinding.ActivityAddUserDetailsBinding
import com.google.android.gms.common.internal.Objects.ToStringHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddUserDetails : AppCompatActivity() {

    private lateinit var binding : ActivityAddUserDetailsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var dialog : Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val uid = auth.currentUser?.uid

        databaseReference = FirebaseDatabase.getInstance().getReference("Employees")
        binding.btnAddUser.setOnClickListener {
            showProgressBar()
            val name = binding.textInputLayoutName.editText?.text.toString()
            val empIdText = binding.textInputLayoutEmployeeID.editText?.text.toString()
            val empId: Int = empIdText.toInt()
            val plant = binding.textInputLayoutPlant.editText?.text.toString()
            val designation = binding.textInputLayoutDesignation.editText?.text.toString()


            val employee = Employee(name,empId,plant, designation)

            if (uid != null){
                databaseReference.child(uid).setValue(employee).addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(this@AddUserDetails,"Successfully updated profile",Toast.LENGTH_SHORT).show()
                        hideProgressBar()
                        val intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)

                    } else {
                        hideProgressBar()
                        Toast.makeText(this@AddUserDetails,"Failed to update profile",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }





    }
    private fun showProgressBar(){
        dialog = Dialog(this@AddUserDetails)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE )
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
    private fun hideProgressBar(){
        dialog.dismiss()
    }
}