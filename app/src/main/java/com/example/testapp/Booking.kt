package com.example.testapp
data class Booking(

                    var driverId: String?="",
                  var driverName : String?="",
                   var to: String,
                   var from: String,
                   var empId: String,
                   var purpose:String? = "",
                  var carNumber : String? = "",
                  var plant : String ? = "" ,
                    var empName: String? = "",
                   var bookingStartTime: String?= "",
                    var bookingEndTime: String? = "",
                    var bookingId: String? = ""
    ){constructor() : this("","","","","")}

