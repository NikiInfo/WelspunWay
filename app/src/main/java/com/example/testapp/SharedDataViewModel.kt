package com.example.testapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
class DataHolder private constructor() {

    var toDestination: String? = null
    var fromDestination: String? = null
    var purpose: String? = null
    var empId: String? = null
    var empName: String?= null

    companion object {
        // Singleton instance
        @Volatile
        private var instance: DataHolder? = null

        fun getInstance(): DataHolder {
            return instance ?: synchronized(this) {
                instance ?: DataHolder().also { instance = it }
            }
        }
    }
}

//class SharedDataViewModel: ViewModel() {
//
//    var to: String = ""
//    var from: String? = null
//    var purpose: String ? = null
//}