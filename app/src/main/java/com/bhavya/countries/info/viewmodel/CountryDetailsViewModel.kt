package com.bhavya.countries.info.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhavya.countries.info.model.CountryDetailsData
import com.bhavya.countries.info.repository.CountryDetailsRepository
import com.bhavya.countries.info.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import retrofit2.Response

class CountryDetailsViewModel(private val repository: CountryDetailsRepository, private  val dispatcherProvider: DispatcherProvider) : ViewModel(){
    private val _uiState = MutableStateFlow<UiState<ArrayList<CountryDetailsData>>>(UiState.Loading)
    val uiState: StateFlow<UiState<ArrayList<CountryDetailsData>>> = _uiState

    fun getCountryDetails() {
        viewModelScope.launch {dispatcherProvider.main
            repository.getCountryDetails()
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }
                .collect{
                    print("********${it}")
                    onResponseCollected(it)
                }
        }
    }

    /**
     * invokes when http response is not null. response can be success or error
     */
    private fun onResponseCollected(it: Response<ArrayList<CountryDetailsData>>) {
        if (it.isSuccessful) {
            val countryDetails = it.body();
            countryDetails?.let { model ->
                _uiState.value = UiState.Success(model)
            }
        } else {
            _uiState.value = UiState.Error(it.message())
        }
    }
}

