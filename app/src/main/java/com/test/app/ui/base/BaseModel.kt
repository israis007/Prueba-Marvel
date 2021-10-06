package com.test.app.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.app.ui.base.models.NotServiceResponse

open class BaseModel: ViewModel() {

    val notServiceResponse: MutableLiveData<NotServiceResponse> by lazy {
        MutableLiveData<NotServiceResponse>()
    }

    val isConnected: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
}