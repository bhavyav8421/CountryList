package com.bhavya.countries.info.api

import com.bhavya.countries.info.model.CountryDetailsData
import retrofit2.Response
import retrofit2.http.GET

interface CountryApi {
    @GET("32dcb892b06648910ddd40406e37fdab/raw/db25946fd77c5873b0303b858e861ce724e0dcd0/countries.json")
    suspend fun getCountryDetails() : Response<ArrayList<CountryDetailsData>>
}