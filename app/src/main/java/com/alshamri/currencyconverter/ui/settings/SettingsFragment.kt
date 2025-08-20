package com.alshamri.currencyconverter.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.alshamri.currencyconverter.CurrencyApplication
import com.alshamri.currencyconverter.R
import com.alshamri.currencyconverter.databinding.FragmentSettingsBinding
import com.alshamri.currencyconverter.di.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SettingsViewModel by viewModels {
        ViewModelFactory {
            SettingsViewModel(CurrencyApplication.appModule.currencyRepository)
        }
    }

    private lateinit var settingsAdapter: SettingsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupRecyclerView()
        setupObservers()
        setupClickListeners()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupRecyclerView() {
        settingsAdapter = SettingsAdapter()
        binding.ratesRecyclerView.adapter = settingsAdapter
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.rates.collectLatest { rates ->
                settingsAdapter.submitList(rates)
            }
        }
    }

    private fun setupClickListeners() {
        binding.saveButton.setOnClickListener {
            val updatedRates = settingsAdapter.getUpdatedRates()
            if (updatedRates.isNotEmpty()) {
                viewModel.updateRates(updatedRates)
                Snackbar.make(binding.root, getString(R.string.rates_updated_successfully), Snackbar.LENGTH_SHORT).show()
            }
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
