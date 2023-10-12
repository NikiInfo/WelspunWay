package com.example.testapp

import android.app.AlertDialog
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class RecyclerDetailActivity : AppCompatActivity() {
    val driverList = ArrayList<Driver>()
    //private val driverList: ArrayList<Driver>()
    private lateinit var carName: TextView
    private lateinit var name: TextView
    private lateinit var tvcarNumber: TextView
    private lateinit var image: ImageView
    private var imageUrl: String = ""
    private var key = ""
    private lateinit var btnUpdateDriverDetailsActivity: Button
    private lateinit var btnDeleteDriver: Button


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_detail)
        btnUpdateDriverDetailsActivity = findViewById(R.id.btnUpdateDriverDetails)

        btnUpdateDriverDetailsActivity.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("Driver Id").toString(),
                intent.getStringExtra("Driver Name").toString()
            )
        }
//        initViews()
//        setValuesToViews()
        btnDeleteDriver = findViewById(R.id.btnDeleteDriverDetails)
        btnUpdateDriverDetailsActivity = findViewById(R.id.btnUpdateDriverDetails)
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

        btnDeleteDriver.setOnClickListener {
            val reference = FirebaseDatabase.getInstance().getReference("Driver")
            val storage = FirebaseStorage.getInstance()
            val storageReference = storage.getReferenceFromUrl(imageUrl)
            storageReference.delete().addOnSuccessListener {
                reference.child(key).removeValue()
                Toast.makeText(this,"Deleted", Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, AdminActivity::class.java))
                finish()
            }
                .addOnFailureListener {
                    Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show()
                }
        }
//        btnUpdateDriverDetailsActivity.setOnClickListener{
//            val intent = Intent(this,UpdateDriverDetailsActivity::class.java)
//                .putExtra("Car Name", carName.text.toString())
//                .putExtra("Driver Name",name.text.toString())
//                .putExtra("Car Number",carNumber.text.toString())
//                .putExtra("Image",imageUrl)
//                startActivity(intent)
//
//        }

    //}
}
    private fun openUpdateDialog(
        driverId: String,
        driverName: String
    ){
        val bundle = intent.extras
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.activity_update_driver_details,null)
         mDialog.setView(mDialogView)
        if (bundle != null) {
            imageUrl = bundle.getString("Image")?: ""
        }
        val image = findViewById<ImageView>(R.id.updateImage)
        val etDriverName = mDialogView.findViewById<TextInputEditText>(R.id.etDriverName)
        val etCarNumber = mDialogView.findViewById<TextInputEditText>(R.id.etCarNumber)
        val etCapacity = mDialogView.findViewById<TextInputEditText>(R.id.etCapacity)
        val etCarName = mDialogView.findViewById<TextInputEditText>(R.id.etCarName)
        val etPlant = mDialogView.findViewById<TextInputEditText>(R.id.etDriverPlant)
        val etVacancy = mDialogView.findViewById<TextInputEditText>(R.id.etVacancy)
        val btnUpdateDriver = mDialogView.findViewById<Button>(R.id.btnUpdateDriver)
//        Glide.with(this).load(imageUrl).into(image)
        etDriverName.setText(intent.getStringExtra("Driver Name"))
        etCarNumber.setText(intent.getStringExtra("Car Number"))
        etCapacity.setText(intent.getStringExtra("Capacity"))
        etCarName.setText(intent.getStringExtra("Car Name"))
        etPlant.setText(intent.getStringExtra("Plant"))
        etVacancy.setText(intent.getStringExtra("Availability"))

        mDialog.setTitle("Udpating $driverName Reconrd")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateDriver.setOnClickListener {
            updateDriverData(
                driverId,
                etDriverName.text.toString(),
                etCarNumber.text.toString(),
                etCapacity.text.toString().toInt(),
                etCarName.text.toString(),
                etPlant.text.toString(),
                etVacancy.text.toString()
            )
            Toast.makeText(this,"Driver data Updated Successfully",Toast.LENGTH_SHORT).show()
            alertDialog.dismiss()
        }



    }
    private fun updateDriverData(
        id: String,
        name: String,
        carNumber: String,
        capacity: Int,
        carName: String,
        plant: String,
        vacancy: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Driver").child(id)
        val imageUrl = intent.getStringExtra("Image")
        val driverInfo = Driver(id,name, carNumber, plant,capacity,carName,vacancy,imageUrl)
        dbRef.setValue(driverInfo)
    }
}