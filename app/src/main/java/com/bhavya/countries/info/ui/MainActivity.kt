package com.bhavya.countries.info.ui

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhavya.countries.info.R
import com.bhavya.countries.info.databinding.ActivityMainBinding
import com.bhavya.countries.info.model.CountryDetailsData
import com.bhavya.countries.info.repository.CountryDetailsRepository
import com.bhavya.countries.info.util.DefaultDispatcherProvider
import com.bhavya.countries.info.util.DispatcherProvider
import com.bhavya.countries.info.viewmodel.CountryDetailsViewModel
import com.bhavya.countries.info.viewmodel.CountryDetailsViewModelFactory
import com.bhavya.countries.info.viewmodel.UiState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding;
    private lateinit var countryDetailsViewModel: CountryDetailsViewModel
    private lateinit var adapter: RecyclerViewAdapter
    private var dispatchProvider:DispatcherProvider = DefaultDispatcherProvider()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        val repository = CountryDetailsRepository(dispatchProvider)
        val factory = CountryDetailsViewModelFactory(repository, dispatchProvider)
        countryDetailsViewModel = ViewModelProvider(this,factory)[CountryDetailsViewModel::class.java];
        initRecyclerView()
        fetchData();
        setUpDataObserver()
    }

    fun fetchData() {
        countryDetailsViewModel.getCountryDetails();
    }

    private fun setUpDataObserver() {
        val job = lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                countryDetailsViewModel.uiState.collectLatest {
                    when (it) {
                        is UiState.Success -> {
                            updateUI(it.data)
                        }

                        is UiState.Loading -> {
                        }

                        is UiState.Error -> {
                            Toast.makeText(applicationContext, it.message,Toast.LENGTH_LONG).show()
                            showAlert(it.message)
                        }

                        else -> {}
                    }
                }
            }
        }

    }

    private fun initRecyclerView(){
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val divider = DividerItemDecoration(this,DividerItemDecoration.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(this,R.drawable.divider)!!)
        binding.recyclerView.addItemDecoration(divider)
        adapter = RecyclerViewAdapter(ArrayList())
        binding.recyclerView.adapter = adapter
    }

    private fun updateUI(data: ArrayList<CountryDetailsData>) {
        adapter.setList(data)

    }

    fun showAlert(data:String) {
        AlertDialog.Builder(this,  R.style.AlertDialogCustom)
        .setTitle("Alert !")
        .setMessage("Error: $data")
        .setCancelable(false)
        .setPositiveButton("Retry",
            DialogInterface.OnClickListener { dialog: DialogInterface?, which: Int ->
                fetchData()
                dialog?.dismiss()
            } as DialogInterface.OnClickListener)
        .setNegativeButton("Cancel",
            DialogInterface.OnClickListener { dialog: DialogInterface, which: Int ->
                dialog?.dismiss()
                finish()
            } as DialogInterface.OnClickListener).create().show()

    }
}