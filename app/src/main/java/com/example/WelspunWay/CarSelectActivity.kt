package com.example.WelspunWay

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CarSelectActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var eventListener: ValueEventListener
    private lateinit var recyclerView: RecyclerView
    private lateinit var driverList: MutableList<Driver>
    private lateinit var adapter: SelectCarAdapter
    private lateinit var reload: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_select)
        reload = findViewById(R.id.btnRefresh)
        recyclerView = findViewById(R.id.recycleView)


        val gridLayoutManager = GridLayoutManager(this,1)
        recyclerView.layoutManager = gridLayoutManager

        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog = builder.create()
        dialog.show()

        driverList = ArrayList()
        adapter = SelectCarAdapter(this,driverList as ArrayList<Driver>)
        recyclerView.adapter = adapter

        databaseReference = FirebaseDatabase.getInstance().getReference("Driver")
        dialog.show()

        reload.setOnClickListener {
            recreate()
        }

        eventListener = databaseReference.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                driverList.clear()
                for(itemSnapshot in snapshot.children){

                    val driver = itemSnapshot.getValue(Driver::class.java)
                    driver?.let { (driverList as ArrayList<Driver>).add(it) }


                }
                val employeePlant = intent.getStringExtra("plant")
                if (employeePlant != null) {
                    adapter.filterDataList(employeePlant, "yes")
                    adapter.notifyDataSetChanged()
                    dialog.dismiss()
                }
                dialog.dismiss()

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@CarSelectActivity,"No cars Found, Please Contact Administrator",Toast.LENGTH_LONG).show()
                dialog.dismiss()
                val intent = Intent(this@CarSelectActivity,HomeActivity::class.java)
                startActivity(intent)

            }

        })

    }
}