package com.example.WelspunWay

import androidx.appcompat.app.AppCompatActivity

class UpdateDriverDetailsActivity : AppCompatActivity() {
//    private lateinit var updateImage: ImageView
//    private lateinit var binding: ActivityUpdateDriverDetailsBinding
//    private lateinit var updateButton: Button
//    private lateinit var updateDriverName: TextInputEditText
//    private lateinit var updateCapacity: TextInputEditText
//    private lateinit var updateCarName: TextInputEditText
//    private lateinit var updatePlant: TextInputEditText
//    private lateinit var updateVacancy: TextInputEditText
//    private lateinit var updateCarNumber: TextInputEditText
//    private  var carName: String =""
//    private  var name: String =""
//    private var capacity: Int = 0
//    private var plant: String =""
//    private var vacancy: String = ""
//    private  var carNumber: String =""
//    private  var oldimageUrl: String =""
//    private var imageUrl: String = ""
//    private var uri: Uri? = null
//    private lateinit var databaseReference: DatabaseReference
//    private lateinit var storageReference: StorageReference
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_update_driver_details)
//
//        updateButton = findViewById(R.id.btnUpdateDriver)
//        updateImage = findViewById(R.id.updateImage)
//        updateDriverName = findViewById(R.id.etDriverName)
//        updatePlant = findViewById(R.id.etDriverPlant)
//        updateCapacity = findViewById(R.id.etCapacity)
//        updateVacancy = findViewById(R.id.etVacancy)
//        updateCarNumber = findViewById(R.id.etCarNumber)
//        updateCarName = findViewById(R.id.etCarName)
//
//        val activityResultLauncher =
//            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {result: ActivityResult ->
//                if (result.resultCode == Activity.RESULT_OK){
//                    val data: Intent? = result.data
//                    uri = data?.data
//                    updateImage.setImageURI(uri)
//                } else {
//                    Toast.makeText(this, "No Image Selected", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//        val bundle = intent.extras
//        bundle?.let {
//            Glide.with(this).load(it.getString("Image")).into(updateImage)
//            updateDriverName.setText(it.getString("Driver Name"))
//            updateCarNumber.setText(it.getString("Car Number"))
//            updatePlant.setText(it.getString("Plant"))
//            updateCapacity.setText(it.getString("Capacity"))
//            updateCarName.setText(it.getString("Car Name"))
//            updateVacancy.setText(it.getString("Vacancy"))
//            oldimageUrl = it.getString("Image") ?: ""
//        }
//        databaseReference = FirebaseDatabase.getInstance().getReference("Driver").child(carNumber)
//
//        updateImage.setOnClickListener {
//            val photoPicker = Intent(Intent.ACTION_PICK)
//            photoPicker.type = "image/*"
//            activityResultLauncher.launch(photoPicker)
//        }
//
//        updateButton.setOnClickListener{
//            saveData()
//            val intent = Intent(this, AdminActivity::class.java)
//            startActivity(intent)
//        }
//
//    }
//    private fun saveData(){
//        storageReference = FirebaseStorage.getInstance().getReference("Car Images")
//            .child(uri?.lastPathSegment ?: "")
//        val builder = AlertDialog.Builder(this)
//        builder.setCancelable(false)
//        builder.setView(R.layout.progress_dialog)
//        val dialog = builder.create()
//        dialog.show()
//
//        storageReference.putFile(uri!!)
//            .addOnSuccessListener { taskSnaphot: UploadTask.TaskSnapshot ->
//                val uriTask: Task<Uri> = taskSnaphot.storage.downloadUrl
//                while (!uriTask.isComplete);
//            val urlImage: Uri? = uriTask.result
//                imageUrl = urlImage.toString()
//                updateData()
//                dialog.dismiss()
//            }
//            .addOnFailureListener{e: Exception ->
//                dialog.dismiss()
//            }
//    }
//    private fun updateData(){
//        carName = updateCarName.text.toString().trim()
//        name = updateDriverName.text.toString().trim()
//        carNumber = updateCarNumber.text.toString().trim()
//        val capacityText = updateCapacity.text.toString().trim()
//        // Convert the capacity input to an integer
//        if (capacityText.isNotEmpty()) {
//            capacity = capacityText.toInt()
//        } else {
//            Toast.makeText(this,"Enter only number",Toast.LENGTH_SHORT).show()
//        }
//        plant = updatePlant.text.toString().trim()
//        vacancy = updateVacancy.text.toString().trim()
//        val driver = Driver(name, carNumber, plant,capacity,carName,vacancy,imageUrl)
//        databaseReference.setValue(driver)
//            .addOnCompleteListener {
//                if(it.isSuccessful) {
//                    val reference: StorageReference = FirebaseStorage.getInstance().getReferenceFromUrl(oldimageUrl)
//                    reference.delete()
//                    Toast.makeText(this,"Updated",Toast.LENGTH_SHORT).show()
//                    finish()
//                }
//            }
//            .addOnFailureListener{
//                Toast.makeText(this,"Updated",Toast.LENGTH_SHORT).show()
//            }
//    }
}