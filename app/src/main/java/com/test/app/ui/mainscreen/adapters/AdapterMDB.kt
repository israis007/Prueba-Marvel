package com.test.app.ui.mainscreen.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.test.app.BuildConfig
import com.test.app.R
import com.test.app.databinding.ItemPictureBinding
import com.test.app.objects.Results
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AdapterMDB (
    val context: Context,
    val listResults: ArrayList<Results>,
    val event: OnResultTouchListener
) : RecyclerView.Adapter<AdapterMDB.ResultItem>() {

    lateinit var binding : ItemPictureBinding

    inner class ResultItem(private val pictureBinding: ItemPictureBinding) : RecyclerView.ViewHolder(pictureBinding.root) {
        fun setData(result: Results){
            with(pictureBinding){
                itemActvName.text = result.title
                itemActvOriginalTitle.text = result.original_title
                itemActvDescription.text = result.overview
                itemActvReleaseDate.text = result.release_date
                val path = "${BuildConfig.URL_IMGS}${result.poster_path}"
                updateImage(itemAcivBanner, path)

                itemAcivBanner.setOnClickListener { event.onTouchItem(path) }
                itemChipSeries.setOnClickListener { event.onChipAddFavoriteClickListener(result) }
                itemChipComics.setOnClickListener { event.onChipSimilarClickListener(result) }

                executePendingBindings()
            }
        }
    }

    interface OnResultTouchListener {
        fun onTouchItem(pathImage: String)
        fun onChipAddFavoriteClickListener(result: Results)
        fun onChipSimilarClickListener(result: Results)
    }


    fun addItemsBefore(list: List<Results>) {
        repeat(list.size) {
            this.listResults.add(0, list[list.size - it -1])
            notifyItemInserted(0)
        }
    }

    fun addItemsAfter(list: List<Results>) {
        var rest = if (this.listResults.size <= 1) 0 else this.listResults.size
        repeat(list.size) {
            this.listResults.add(rest, list[it])
            notifyItemInserted(rest)
            rest++
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultItem {
        binding = ItemPictureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ResultItem(binding)
    }

    override fun getItemCount(): Int =
        listResults.size

    override fun onBindViewHolder(holder: ResultItem, position: Int) =
        holder.setData(listResults[position])

    private fun updateImage(appCompatImageView: AppCompatImageView, path: String?) {
        Log.d("RECVIW", "url -> $path")
        GlobalScope.launch(Dispatchers.Main) {
            Glide.with(context)
                .load(path)
                .centerCrop()
                .placeholder(R.drawable.ic_sand_clock)
                .error(R.drawable.ic_no_photo)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(appCompatImageView)
        }
    }

}