package com.example.testapp

import android.content.Context
import android.content.Intent
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.Driver
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideContext
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule


import kotlinx.coroutines.withContext
@GlideModule
class SelectCarAdapter(private val context: Context, private var driverlist: ArrayList<Driver>) : RecyclerView.Adapter<SelectCarAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder,position: Int) {
        val dataItem = driverlist[position]
        //Glide.with(holder.recImage.context).load(dataItem.imageUrl).into(holder.recImage)
        Glide.with(context).load(dataItem.imageUrl).into(holder.recImage)
        holder.recTitle.text = dataItem.name
        holder.recDesc.text = dataItem.carName
        holder.recPlant.text = dataItem.plant
        holder.recCard.setOnClickListener{
            val intent = Intent(context, RecyclerDetail2::class.java).apply {
                putExtra("Driver Id",driverlist[position].driverId)
                putExtra("Image",driverlist[position].imageUrl)
                putExtra("Plant",driverlist[position].plant)
                putExtra("Capacity",driverlist[position].carSeatingCapacity)
                putExtra("Car Name",driverlist[position].carName)
                putExtra("Driver Name",driverlist[position].name)
                putExtra("Car Number",driverlist[position].carNumber)
                putExtra("Availability",driverlist[position].availability)

            }
            context.startActivity(intent)
        }



    }


    override fun getItemCount(): Int {
        return driverlist.size
    }
    fun searchDataList(searchList: ArrayList<Driver>) {
        driverlist = searchList
        notifyDataSetChanged()
    }
    fun filterDataList(plantName: String) {
        val filteredList = ArrayList<Driver>()

        for (driver in driverlist) {
            if (driver.plant == plantName) {
                filteredList.add(driver)
            }
        }

        searchDataList(filteredList)
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recImage: ImageView = itemView.findViewById(R.id.recImage)
        val recCard: CardView = itemView.findViewById(R.id.recCard)
        val recDesc: TextView = itemView.findViewById(R.id.recDesc)
        val recPlant: TextView = itemView.findViewById(R.id.recPlant)
        val recTitle: TextView = itemView.findViewById(R.id.recTitle)

    }
    fun filterDataList(plantName: String, availability: String) {
        val filteredList = ArrayList<Driver>()

        for (driver in driverlist) {
            if (driver.plant == plantName && driver.availability == availability) {
                filteredList.add(driver)
            }
        }

        searchDataList(filteredList)
        notifyDataSetChanged()
    }

}
