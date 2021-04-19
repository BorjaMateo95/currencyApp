package com.borja.currency.domain

sealed class Failure{
    sealed class ApiFailure : Failure() {
        data class GenericError(val message: String = "Generic error") : ApiFailure()
    }
}
