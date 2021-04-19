package com.borja.currency.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.borja.currency.R
import com.borja.currency.databinding.ItemCalculatedCurrencyBinding
import com.borja.currency.domain.Currency

class CurrencyCalculateAdatper :
    RecyclerView.Adapter<CurrencyCalculateAdatper.ViewHolder>() {

    var list: List<Currency> = arrayListOf()
    var cant: Double = 0.0

    init {
        setHasStableIds(true)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.currency = list[position]
        holder.binding.cant = this.cant
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemCalculatedCurrencyBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_calculated_currency,
            parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun getItemId(position: Int): Long = position.toLong()

    fun submitList(currencyList: List<Currency>, cant: Double) {
        list = currencyList
        this.cant = cant
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemCalculatedCurrencyBinding) :
        RecyclerView.ViewHolder(binding.root)

}