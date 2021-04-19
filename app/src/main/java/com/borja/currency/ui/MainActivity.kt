package com.borja.currency.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.borja.currency.R
import com.borja.currency.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var spinnerCurrencyAdapter: SpinnerAdapter
    private var promotionAdapter = CurrencyCalculateAdatper()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val viewModel: RatesViewModel = ViewModelProviders.of(this).get(RatesViewModel::class.java)

        spinnerCurrencyAdapter = SpinnerAdapter(this)
        binding.spinner.adapter = spinnerCurrencyAdapter

        binding.recicler.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )

        binding.recicler.adapter = promotionAdapter


        viewModel.uiModel.observe(this, {
            when (it) {
                is RatesViewModel.UiModel.ERROR -> {
                    binding.constraintLoading.visibility = View.GONE
                }
                is RatesViewModel.UiModel.SUCCESS -> {
                    binding.constraintLoading.visibility = View.GONE
                    spinnerCurrencyAdapter.plus(it.currencyList)
                    it.currencyList[0].currency.also { viewModel.currencySelected = it }
                }

                is RatesViewModel.UiModel.LOADING -> {
                    binding.constraintLoading.visibility = View.VISIBLE
                }

                is RatesViewModel.UiModel.SUCCESS_SELECT_CURRENCY -> {
                    binding.constraintLoading.visibility = View.GONE

                    binding.edAmount.text.toString().toDouble().let { amount ->
                        promotionAdapter.submitList(it.currencyList,
                            amount
                        )
                    }
                    binding.recicler.visibility = View.VISIBLE

                }
            }
        })

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.currencySelected = viewModel.currencyList[position].currency
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        binding.bCalculate.setOnClickListener {
            if (binding.edAmount.text.isEmpty()) {
                Toast.makeText(this, "Cantidad obligatoria", Toast.LENGTH_LONG).show();
                return@setOnClickListener
            }
            viewModel.getRatesByCurrency()
        }
    }



}