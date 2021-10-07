package com.test.app.ui.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.test.app.R
import com.test.app.databinding.DialogImageBinding
import com.test.app.databinding.DialogInfoBinding
import com.test.app.ui.base.dialogs.DialogCallback
import com.test.app.ui.base.dialogs.DialogViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

const val ARG_EXTRAS = "argsExtras"

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

    fun showInfoMessage(title: String, message: String, dialogCallback: DialogCallback) {
        val dialog = AlertDialog.Builder(this@ActivityBase, R.style.CustomDialogInfo)
        val vm = ViewModelProvider(this@ActivityBase)[DialogViewModel::class.java]
        val binding: DialogInfoBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this@ActivityBase),
            R.layout.dialog_info,
            null,
            false
        )
        binding.apply {
            viewModel = vm
            lifecycleOwner = this@ActivityBase
        }
        dialog.setView(binding.root)
        dialog.setCancelable(false)
        dialog.setPositiveButton(R.string.general_accept) { dial, _ ->
            dialogCallback.onAcceptClickListener(dial)
        }
        vm.setInfo(title, message)
        dialog.setOnDismissListener {
            dialogCallback.onDismissListener(it)
        }
        dialog.create().show()
    }

    fun showInfoMessage(title: String, message: String) {
        val dialog = AlertDialog.Builder(this@ActivityBase, R.style.CustomDialogInfo)
        val vm = ViewModelProvider(this@ActivityBase)[DialogViewModel::class.java]
        val binding: DialogInfoBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this@ActivityBase),
            R.layout.dialog_info,
            null,
            false
        )
        binding.apply {
            viewModel = vm
            lifecycleOwner = this@ActivityBase
        }
        dialog.setView(binding.root)
        dialog.setCancelable(false)
        dialog.setPositiveButton(R.string.general_accept) { dial, _ ->
            dial.dismiss()
        }
        vm.setInfo(title, message)
        dialog.create().show()
    }

    fun showImageMessage(path: String) {
        val dialog = AlertDialog.Builder(this@ActivityBase, R.style.CustomDialogInfo)
        val vm = ViewModelProvider(this@ActivityBase)[DialogViewModel::class.java]
        val binding: DialogImageBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this@ActivityBase),
            R.layout.dialog_image,
            null,
            false
        )
        binding.apply {
            viewModel = vm
            lifecycleOwner = this@ActivityBase
        }
        dialog.setView(binding.root)
        dialog.setCancelable(false)
        dialog.setPositiveButton(R.string.general_accept) { dial, _ ->
            dial.dismiss()
        }

        GlobalScope.launch(Dispatchers.Main) {
            Glide.with(this@ActivityBase)
                .load(path)
                .optionalFitCenter()
                .placeholder(R.drawable.ic_sand_clock)
                .error(R.drawable.ic_no_photo)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(binding.dialogImage)
        }

        dialog.create().show()
    }

    fun <T> launchActivity(clazz: Class<T>) {
        val intent = Intent(this, clazz).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        startActivity(intent)
    }

    fun <T> launchActivity(clazz: Class<T>, bundle: Bundle) {
        val intent = Intent(this, clazz).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }

        intent.putExtra(ARG_EXTRAS, bundle)
        startActivity(intent)
    }

    fun showToastMessage(message: String, duration: Int) {
        Toast.makeText(this@ActivityBase, message, duration).show()
    }

    fun showToastMessage(message: String) {
        Toast.makeText(this@ActivityBase, message, Toast.LENGTH_LONG).show()
    }

    abstract fun initUI()

    abstract fun setListeners()

    abstract fun setObservers()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
        setListeners()
        setObservers()
    }
}