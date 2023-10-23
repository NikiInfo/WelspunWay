package com.example.testapp

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.app.Instrumentation
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.testapp.databinding.ActivityAddUserDetailsBinding
import com.example.testapp.databinding.ActivityUploadDriverBinding
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlin.system.exitProcess

class UploadDriverActivity : AppCompatActivity() {
    private lateinit var binding : ActivityUploadDriverBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var dialog : Dialog
    private lateinit var firebaseStorage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    private var uri: Uri ? = null
    private lateinit var imageURL: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadDriverBinding.inflate(layoutInflater)
        setContentView(binding.root)

       val activityResultLauncher = registerForActivityResult(
           ActivityResultContracts.StartActivityForResult()
       ) { result: androidx.activity.result.ActivityResult ->
           if (result.resultCode == Activity.RESULT_OK) {
               val data = result.data
               uri = data?.data ?: return@registerForActivityResult
               binding.uploadImage.setImageURI(uri)
           } else {
               Toast.makeText(this, "No Image Selected",Toast.LENGTH_SHORT).show()
           }
       }
        binding.uploadImage.setOnClickListener {
            val photoPicker = Intent(Intent.ACTION_PICK)
            photoPicker.type = "image/*"
            activityResultLauncher.launch(photoPicker)
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Driver")
        binding.btnSaveDriverDetails.setOnClickListener{ saveData()
        }

    }
    private fun saveData() {
        val lastPathSegment = uri?.lastPathSegment
        storageReference = FirebaseStorage.getInstance().reference.child("Car Images")
        .child(lastPathSegment ?: "defaultFilename.jpg")
        val builder = AlertDialog.Builder(this@UploadDriverActivity)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_dialog)
        val dialog = builder.create()
        dialog.show()

        uri?.let {
            storageReference.putFile(it)
                .addOnSuccessListener { taskSnapshot ->
                    val uriTask = taskSnapshot.storage.downloadUrl
                    uriTask.addOnCompleteListener { uriTask ->
                        if (uriTask.isSuccessful) {
                            val urlImage = uriTask.result
                            imageURL = urlImage.toString()
                            uploadData()
                            dialog.dismiss()
                        }
                    }
                }
                .addOnFailureListener { e ->
                    dialog.dismiss()
                }
        }?: run {
            Toast.makeText(this, "Please Select image", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }




    }
    private fun uploadData(){
        val urlImage = imageURL
        val driverName = binding.etDriverName.editText?.text.toString()
        val carNumber = binding.etCarNumber.editText?.text.toString()
        val capacityText = binding.etCapacity.editText?.text.toString()
        val capacity: Int = capacityText.toInt()
        val carName = binding.etCarName.editText?.text.toString()
        val plant = binding.etDriverPlant.editText?.text.toString()
        val vacancy = binding.etVacancy.editText?.text.toString()
        val phnNumber = binding.tvphnNumber.editText?.text.toString()

        val driverId = databaseReference.push().key!!


        val driver = Driver(driverId,driverName,carNumber,plant,capacity,carName,vacancy,urlImage,phnNumber)
        if(checkAllField()) {
            databaseReference.child(driverId).setValue(driver)
                .addOnCompleteListener {
                    Toast.makeText(this, "Driver details saved successfully", Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(this,AdminActivity::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
        }


    }
    private fun checkAllField(): Boolean{
        val driverName = binding.etDriverName.editText?.text.toString()
        val carNumber = binding.etCarNumber.editText?.text.toString()
        val capacityText = binding.etCapacity.editText?.text.toString()
        val capacity: Int = capacityText.toInt()
        val carName = binding.etCarName.editText?.text.toString()
        val plant = binding.etDriverPlant.editText?.text.toString()
        val vacancy = binding.etVacancy.editText?.text.toString()
        val yes:String = "yes"
        val no:String = "no"

        if (driverName.isEmpty()){
            binding.etDriverName.error = "Please enter the name"
            return false
        }
        if(carNumber.length != 10){
            binding.etCarNumber.error = "Car number should be exactly 10 characters"
            Toast.makeText(this, "Example GJ12FD2340",Toast.LENGTH_SHORT).show()
            return false
        }
        if(capacity != 4 && capacity != 6){
            binding.etCapacity.error = "Only 4 or 6"
            return false
        }
//        if(vacancy.compareTo(yes) || vacancy.compareTo(no)){
//            binding.etVacancy.error = "Only yes or no"
//            return false
       // }
        return true
    }
}