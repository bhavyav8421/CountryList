package com.bhavya.countries.info.repository

import com.bhavya.countries.info.api.CountryApiFactory
import com.bhavya.countries.info.model.CountryDetailsData
import com.bhavya.countries.info.util.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class CountryDetailsRepository(private val dispatcherProvider: DispatcherProvider) {

    fun getCountryDetails(): Flow<Response<ArrayList<CountryDetailsData>>> {
        return flow {dispatcherProvider.io
            emit(CountryApiFactory().create().getCountryDetails())
        }
    }

}