package com.test.app

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import dagger.hilt.android.HiltAndroidApp
import java.text.SimpleDateFormat
import java.util.*

@HiltAndroidApp
class AppTest: MultiDexApplication() {

    companion object {
        lateinit var instance: AppTest
            private set
    }

    init {
        @JvmStatic
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        Firebase.initialize(this@AppTest)
    }

    private var isConnected = MutableLiveData<Boolean>().apply {
        value = false
    }

    private var currentDate = 0L

    fun setConnected(isConnected : Boolean){
        this@AppTest.isConnected.postValue(isConnected)
    }

    fun isConnectedObserve() = isConnected

    fun getCurrentDateFormatted() : String {
        val locale = Locale.US

        val sdf = SimpleDateFormat("EEE',' dd MMM yyyy HH:mm:ss z", locale)
        val date = sdf.format(Calendar.getInstance(locale).apply {
            time.time = currentDate
        }.time)
        Log.d("AppTest", "fecha formateada -> $date")
        Log.d("AppTest", "fecha en millis -> $currentDate")
        return date
    }

    fun setCurrentDate(date : Date) {
        currentDate = date.time
    }

    fun getCurrentDate() = currentDate

}