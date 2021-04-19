package com.borja.currency.utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.borja.currency.domain.Currency

@RequiresApi(Build.VERSION_CODES.N)
class CurrencyUtil {

    companion object {
        fun getCurrencyList(rates: HashMap<String, Double>) : MutableList<Currency> {
            val currencyList : MutableList<Currency> = arrayListOf()
            rates.forEach { (currency, value) ->
                currencyList.add(Currency(currency, value))
            }

            return currencyList
        }
    }

}