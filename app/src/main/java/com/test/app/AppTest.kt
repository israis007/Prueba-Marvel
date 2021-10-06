package com.test.app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import dagger.hilt.android.HiltAndroidApp

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

    fun setConnected(isConnected : Boolean){
        this@AppTest.isConnected.postValue(isConnected)
    }

    fun isConnectedObserve() = isConnected

}