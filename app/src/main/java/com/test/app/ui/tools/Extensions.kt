package com.test.app.ui.tools

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AbsListView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.test.app.AppTest
import com.test.app.R
import com.test.app.ui.tools.ui.SimpleDividerItemDecoration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream
import java.nio.file.Files
import java.nio.file.Paths

private var isScrolling = false
private val TAG = "Extensions"

fun RecyclerView.setUpScrollingListener(context: Context, adapter: RecyclerView.Adapter<*>, addItemDecoration: Boolean = true, lastElement: (watchLastElement: Boolean, isScrolling: Boolean) -> Unit){
    val linearLayoutManager = LinearLayoutManager(context)
    linearLayoutManager.orientation = RecyclerView.VERTICAL

    this.layoutManager = linearLayoutManager
    this.adapter = adapter
    this.isNestedScrollingEnabled = true

    if (addItemDecoration)
        this.addItemDecoration(SimpleDividerItemDecoration(context))

    this.addOnScrollListener(object : RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
                lastElement(false, isScrolling)
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (isScrolling && (linearLayoutManager.itemCount - linearLayoutManager.findFirstVisibleItemPosition() - linearLayoutManager.childCount == 0)){
                isScrolling = false
                lastElement(true, isScrolling)
            }
        }
    })
}

fun AppCompatImageView.updateImage(path: String){
    GlobalScope.launch(Dispatchers.Main){
        Glide.with(this@updateImage)
            .load(path)
            .centerCrop()
            .placeholder(R.drawable.ic_sand_clock)
            .error(R.drawable.ic_no_photo)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(this@updateImage)
    }
}

fun <T> ChipGroup.createChip(layoutInflater: LayoutInflater, textChip: String?, data: T, clicked: (data: T) -> Unit){
    val chip = layoutInflater.inflate(R.layout.chip_item_inversed, null, false) as Chip
    chip.apply {
        id = View.generateViewId()
        text = textChip
        setOnClickListener {
            clicked(data)
        }
    }
    this.addView(chip)
}

private fun getRealPathOfImage(imageUri: Uri): String? {
    val filePath: String?
    val path = arrayOf(MediaStore.Images.Media.DATA)
    val cursor2 = AppTest.instance.contentResolver.query(imageUri, path, null, null, null)
    if (cursor2 == null){
        Log.d(TAG, "Path not found: ${imageUri.path}")
        filePath = imageUri.path
    } else {
        cursor2.moveToFirst()
        val idx = cursor2.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        filePath = cursor2.getString(idx)
        Log.d(TAG, "Real Path Select: ${cursor2.getString(idx)}")
        cursor2.close()
    }
    return filePath
}

fun Uri.convertToByteArray(): ByteArray? {
    var byteArray: ByteArray?
    //Convert by NIO
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        byteArray = Files.readAllBytes(Paths.get(getRealPathOfImage(this)!!))
    else {
        var fileInputStream: FileInputStream? = null
        try {
            val file = File(getRealPathOfImage(this)!!)
            fileInputStream = FileInputStream(file)
            byteArray = ByteArray(file.length().toInt())
            fileInputStream.read(byteArray)
        } catch (e: Exception){
            byteArray = null
        } finally {
            fileInputStream!!.close()
        }
    }
    return byteArray
}

fun Fragment.checkPermissions(granted: (granted: Boolean) -> Unit) {
    val permissionsGroup = arrayListOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
//        permissionsGroup.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
//        permissionsGroup.add(Manifest.permission.MANAGE_EXTERNAL_STORAGE)

    for (cad in permissionsGroup) {
        if (ContextCompat.checkSelfPermission(this.requireContext(), cad) == PackageManager.PERMISSION_DENIED) {
            this.requireActivity().requestPermissions(
                arrayOf(cad),
                RQC
            )
        }
    }
    var t = false
    //Checking
    for (cad in permissionsGroup){
        t = ContextCompat.checkSelfPermission(this.requireContext(), cad) == PackageManager.PERMISSION_GRANTED
    }
    granted(t)
}