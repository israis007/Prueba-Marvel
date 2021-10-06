package com.test.app.ui.base

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.test.app.R

abstract class ActivityBase : AppCompatActivity() {

    private val dialogLoading by lazy {
        val dialogAlertDialog = AlertDialog.Builder(this, R.style.CustomDialog)
        dialogAlertDialog.setView(R.layout.dialog_loading)
        dialogAlertDialog.setCancelable(false)
        dialogAlertDialog.create()
    }

    fun showLoading(loading: Boolean) {
        if (loading) {
            if (!dialogLoading.isShowing)
                dialogLoading.show()
        } else {
            if (dialogLoading.isShowing)
                dialogLoading.dismiss()
        }
    }

}