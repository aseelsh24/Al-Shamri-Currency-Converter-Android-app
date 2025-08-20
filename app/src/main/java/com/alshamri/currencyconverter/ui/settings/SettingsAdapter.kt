package com.alshamri.currencyconverter.ui.settings

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alshamri.currencyconverter.data.local.CurrencyRate
import com.alshamri.currencyconverter.databinding.ItemRateSettingBinding
import com.alshamri.currencyconverter.util.Constants

class SettingsAdapter : ListAdapter<CurrencyRate, SettingsAdapter.RateViewHolder>(DiffCallback()) {

    private val updatedRates = mutableMapOf<String, Double>()

    fun getUpdatedRates(): Map<String, Double> = updatedRates

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {
        val binding = ItemRateSettingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        val rate = getItem(position)
        holder.bind(rate)
    }

    inner class RateViewHolder(private val binding: ItemRateSettingBinding) : RecyclerView.ViewHolder(binding.root) {

        private var textWatcher: TextWatcher? = null

        fun bind(rate: CurrencyRate) {
            binding.currencyNameText.text = rate.currencyName
            binding.currencyCodeText.text = rate.currencyCode
            binding.rateEditText.setText(rate.rate.toString())

            // Remove previous watcher to prevent unwanted updates on recycled views
            binding.rateEditText.removeTextChangedListener(textWatcher)

            if (rate.currencyCode == Constants.USD) {
                binding.rateEditText.isEnabled = false
            } else {
                binding.rateEditText.isEnabled = true
                textWatcher = object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                    override fun afterTextChanged(s: Editable?) {
                        s?.toString()?.toDoubleOrNull()?.let {
                            updatedRates[rate.currencyCode] = it
                        }
                    }
                }
                binding.rateEditText.addTextChangedListener(textWatcher)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<CurrencyRate>() {
        override fun areItemsTheSame(oldItem: CurrencyRate, newItem: CurrencyRate): Boolean {
            return oldItem.currencyCode == newItem.currencyCode
        }

        override fun areContentsTheSame(oldItem: CurrencyRate, newItem: CurrencyRate): Boolean {
            return oldItem == newItem
        }
    }
}
