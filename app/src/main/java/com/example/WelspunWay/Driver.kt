package com.example.WelspunWay

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Driver(var driverId: String,
                  var name : String,
                  var carNumber : String? = "",
                  var plant : String ? = "" ,
                  var carSeatingCapacity : Int ? = 0,
                  var carName : String? = "",
                  var availability : String? = "",
                  var imageUrl : String ? = "",
                  var phnNumber : String ? = ""
  ){constructor() : this("","")}

//data class DataClass(
//    val name: String? = null,
//    val carNumber: String,
//    val plant: String? = null,
//    val carSeatingCapacity: Int? = null,
//    val carName: String? = null,
//    val availability: String,
//    val imageUrl: String? = null
//)

