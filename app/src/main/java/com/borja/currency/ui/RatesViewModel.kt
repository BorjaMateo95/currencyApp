package com.borja.currency.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borja.currency.data.repository.RatesRepository
import com.borja.currency.domain.BaseResponse
import com.borja.currency.domain.Currency
import com.borja.currency.domain.fold
import com.borja.currency.utils.CurrencyUtil
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.launch

@ActivityScoped
class RatesViewModel @ViewModelInject constructor(
    private val repository: RatesRepository
) : ViewModel() {

    var currencySelected : String = ""

    lateinit var currencyList : MutableList<Currency>
    private val _uiModel: MutableLiveData<UiModel> = MutableLiveData()
    val uiModel: LiveData<UiModel>
        get() = _uiModel

    init {
        viewModelScope.launch {
            _uiModel.value = UiModel.LOADING

            repository.getBaseResponse().fold({
                getRates()
            }, {
                if (it != null) {
                    currencyList = CurrencyUtil.getCurrencyList(it.rates)
                    _uiModel.value = UiModel.SUCCESS(currencyList)
                } else {
                    getRates()
                }
            })


        }
    }


    private fun getRates() {
        viewModelScope.launch {
            repository.getRates().fold({
                _uiModel.value = UiModel.ERROR
            }, {
                currencyList = CurrencyUtil.getCurrencyList(it.rates)
                _uiModel.value = UiModel.SUCCESS(currencyList)
                saveBaseResponse(it)
            })
        }
    }

    private fun saveBaseResponse(baseResponse: BaseResponse) {
        viewModelScope.launch {
            repository.insertBaseResponse(baseResponse)
        }
    }


    fun getRatesByCurrency() {
        viewModelScope.launch {
            _uiModel.value = UiModel.LOADING

            repository.getRatesByCurrency(currencySelected).fold({
                _uiModel.value = UiModel.ERROR
            }, {
                currencyList = CurrencyUtil.getCurrencyList(it.rates)
                _uiModel.value = UiModel.SUCCESS_SELECT_CURRENCY(currencyList)
            })

        }
    }


    sealed class UiModel {
        class SUCCESS(val currencyList: MutableList<Currency>) : UiModel()
        class SUCCESS_SELECT_CURRENCY(val currencyList: MutableList<Currency>) : UiModel()
        object LOADING : UiModel()
        object ERROR : UiModel()
    }
}