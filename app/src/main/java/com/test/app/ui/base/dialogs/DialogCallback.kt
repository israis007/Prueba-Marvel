package com.test.app.ui.base.dialogs

import android.content.DialogInterface

interface DialogCallback {

    fun onAcceptClickListener(dialog: DialogInterface)

    fun onCancelClickListener(dialog: DialogInterface)

    fun onDismissListener(dialog: DialogInterface)

}