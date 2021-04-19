package com.borja.currency.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.borja.currency.domain.Currency

class SpinnerAdapter(context: Context) :
    ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, emptyList<String>()) {

    var items: MutableList<Currency> = mutableListOf()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View =
        (convertView ?: LayoutInflater.from(context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)).apply {
            (this as TextView).text = items[position].currency
        }

    operator fun plus(itemsToAdd: MutableList<Currency>) {
        items = itemsToAdd
        notifyDataSetChanged()
    }


    override fun getCount(): Int = items.size
    override fun getItem(position: Int): String = items[position].currency


}