package com.test.app.ui.mainscreen.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.app.R
import com.test.app.ui.mainscreen.models.BeerModel


class beerAdapter(
    val context: Context,
    val listBeers: ArrayList<BeerModel>,
    val event: OnBeerTouchEvent
) : RecyclerView.Adapter<beerAdapter.BeerItem>() {

    inner class BeerItem(private val beerView: View) : RecyclerView.ViewHolder(beerView) {
        fun bindItems(beerModel: BeerModel) {

        }
    }

    interface OnBeerTouchEvent {
        fun onTouchItem(beerModel: BeerModel)
    }


    fun addItemsBefore(listBeers: List<BeerModel>) {
        repeat(listBeers.size) {
            this.listBeers.add(0, listBeers[listBeers.size - it -1])
            notifyItemInserted(0)
        }
    }

    fun addItemsAfter(listBeers: List<BeerModel>) {
        var rest = if (this.listBeers.size <= 1) 0 else this.listBeers.size - 1
        repeat(listBeers.size) {
            this.listBeers.add(rest, listBeers[it])
            notifyItemInserted(rest)
            rest++
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerItem =
        BeerItem(LayoutInflater.from(context).inflate(R.layout.item_picture, parent, false))

    override fun getItemCount(): Int =
        listBeers.size

    override fun onBindViewHolder(holder: BeerItem, position: Int) =
        holder.bindItems(listBeers[position])

}