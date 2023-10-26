package com.bhavya.countries.info.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bhavya.countries.info.repository.CountryDetailsRepository
import com.bhavya.countries.info.util.DispatcherProvider
import java.lang.IllegalArgumentException

class CountryDetailsViewModelFactory(private val repository: CountryDetailsRepository,  private  val dispatcherProvider: DispatcherProvider) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CountryDetailsViewModel::class.java)){
            return CountryDetailsViewModel(repository, dispatcherProvider) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}