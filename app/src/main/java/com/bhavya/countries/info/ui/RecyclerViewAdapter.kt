package com.bhavya.countries.info.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bhavya.countries.info.R
import com.bhavya.countries.info.databinding.ListItemBinding
import com.bhavya.countries.info.model.CountryDetailsData

class RecyclerViewAdapter(private val countryListData: ArrayList<CountryDetailsData>) : RecyclerView.Adapter<MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return countryListData.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(countryListData[position])
    }

    fun setList(data: List<CountryDetailsData>) {
        countryListData.clear()
        countryListData.addAll(data)
        notifyDataSetChanged()
    }

}

class MyViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: CountryDetailsData) {
        binding.nameRegionText.text = "${data.name}, ${data.region}"
        binding.codeText.text = data.code
        binding.capitalText.text = data.capital
    }
}