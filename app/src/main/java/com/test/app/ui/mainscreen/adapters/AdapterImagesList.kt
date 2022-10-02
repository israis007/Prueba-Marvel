package com.test.app.ui.mainscreen.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.app.databinding.ItemImagesBinding
import com.test.app.ui.tools.updateImage

class AdapterImagesList (
    private val listImages: ArrayList<String>,
    private val onClickImage: (urlImage: String) -> Unit
): RecyclerView.Adapter<AdapterImagesList.ImageViewHolder>() {

    inner class ImageViewHolder(private val binding: ItemImagesBinding) : RecyclerView.ViewHolder(binding.root){
        fun setData(urlImage: String){
            with(binding){
                aivImage.updateImage(urlImage)
                aivImage.setOnClickListener {
                    onClickImage(urlImage)
                }
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder(ItemImagesBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.setData(listImages[position])
    }

    override fun getItemCount(): Int = listImages.size

    fun addItemsBefore(list: List<String>) {
        repeat(list.size) {
            this.listImages.add(0, list[list.size - it -1])
            notifyItemInserted(0)
        }
    }

    fun addItemsAfter(list: List<String>) {
        var rest = if (this.listImages.size <= 1) 0 else this.listImages.size
        repeat(list.size) {
            this.listImages.add(rest, list[it])
            notifyItemInserted(rest)
            rest++
        }
    }

}