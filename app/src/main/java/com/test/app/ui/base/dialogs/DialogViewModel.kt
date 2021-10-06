package com.test.app.ui.base.dialogs

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class DialogViewModel : ViewModel() {

    val title = ObservableField<String>().apply {
        set("")
    }

    val message = ObservableField<String>().apply {
        set("")
    }

    fun setInfo(title : String, message : String){
        this.title.set(title)
        this.message.set(message)
    }

}