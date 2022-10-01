package com.test.app.ui.mainscreen.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.app.BuildConfig
import com.test.app.R
import com.test.app.databinding.ItemPeopleBinding
import com.test.app.objects.KnowFor
import com.test.app.objects.ResultsPersons
import com.test.app.ui.tools.createChip
import com.test.app.ui.tools.updateImage

class AdapterPopularPersons (
    private val context: Context,
    private val layoutInflater: LayoutInflater,
    private val listResults: ArrayList<ResultsPersons>,
    private val event: OnResultTouchListener
): RecyclerView.Adapter<AdapterPopularPersons.ResultItem>() {

    interface OnResultTouchListener {
        fun onTouchItem(pathImage: String)
        fun onChipClicked(result: KnowFor)
    }

    inner class ResultItem(private val binding: ItemPeopleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setData(result: ResultsPersons){
            with(binding){
                itemActvName.text = result.name
                itemActvOriginalTitle.text = result.known_for_department
                itemActvDescription.text = context.getString(if (result.gender != 1) R.string.male else R.string.female)
                itemActvReleaseDate.text = result.popularity.toString()
                val path = "${BuildConfig.URL_IMGS}${result.profile_path}"
                itemAcivBanner.updateImage(path)
                itemAcivBanner.setOnClickListener { event.onTouchItem(path) }
                result.known_for.forEach { item ->
                    itemCgGroup.createChip(layoutInflater, item.title ?: context.getString(R.string.no_data), item){
                        event.onChipClicked(it)
                    }
                }

                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultItem =
        ResultItem(ItemPeopleBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ResultItem, position: Int) {
        holder.setData(listResults[position])
    }

    override fun getItemCount(): Int =
        listResults.size

    fun addItemsBefore(list: List<ResultsPersons>) {
        repeat(list.size) {
            this.listResults.add(0, list[list.size - it -1])
            notifyItemInserted(0)
        }
    }

    fun addItemsAfter(list: List<ResultsPersons>) {
        var rest = if (this.listResults.size <= 1) 0 else this.listResults.size
        repeat(list.size) {
            this.listResults.add(rest, list[it])
            notifyItemInserted(rest)
            rest++
        }
    }
}