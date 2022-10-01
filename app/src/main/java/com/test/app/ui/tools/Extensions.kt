package com.test.app.ui.tools

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.AbsListView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.test.app.R
import com.test.app.ui.tools.ui.SimpleDividerItemDecoration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private var isScrolling = false

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