package com.example.WelspunWay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdminActivity : AppCompatActivity() {
    private lateinit var fab: FloatingActionButton
    private lateinit var databaseReference: DatabaseReference
    private lateinit var eventListener: ValueEventListener
    private lateinit var recyclerView: RecyclerView
    private lateinit var driverList: MutableList<Driver>
    private lateinit var adapter: DriverAdapter
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        recyclerView = findViewById(R.id.recycleView)
        fab = findViewById(R.id.btnAddCarDetails)
        searchView = findViewById(R.id.adminSearch)
        searchView.clearFocus()


        val gridLayoutManager = GridLayoutManager(this, 1)
        recyclerView.layoutManager = gridLayoutManager

        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog = builder.create()
        dialog.show()

        driverList = ArrayList()
        adapter = DriverAdapter(this, driverList as ArrayList<Driver>)
        recyclerView.adapter = adapter

        databaseReference = FirebaseDatabase.getInstance().getReference("Driver")
        dialog.show()

        eventListener = databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                driverList.clear()
                for (itemSnapshot in snapshot.children) {
                    val driver = itemSnapshot.getValue(Driver::class.java)
                    driver?.let { (driverList as ArrayList<Driver>).add(it) }
                }
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }

            override fun onCancelled(error: DatabaseError) {
                dialog.dismiss()
            }
        })
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchList(newText)
                return true
            }
        })
            val fab = findViewById<FloatingActionButton>(R.id.btnAddCarDetails)

        fab.setOnClickListener {
            val intent = Intent(this,UploadDriverActivity::class.java)
            startActivity(intent)
        }

    }

    private fun searchList(text:String?) {
        if (text != null) {
            val searchList = ArrayList<Driver>()
            for (driver in driverList) {
                if (driver.name.toLowerCase().contains(text.toLowerCase())) {
                    searchList.add(driver)
                }
            }
            adapter.searchDataList(searchList)
        }
    }

}
