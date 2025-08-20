package com.alshamri.currencyconverter.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.alshamri.currencyconverter.CurrencyApplication
import com.alshamri.currencyconverter.R
import com.alshamri.currencyconverter.data.local.CurrencyRate
import com.alshamri.currencyconverter.databinding.FragmentMainBinding
import com.alshamri.currencyconverter.di.ViewModelFactory
import com.alshamri.currencyconverter.ui.history.HistoryBottomSheet
import com.alshamri.currencyconverter.util.Formatter
import com.alshamri.currencyconverter.util.ThemeManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory {
            MainViewModel(
                CurrencyApplication.appModule.currencyRepository,
                CurrencyApplication.appModule.historyRepository,
                CurrencyApplication.appModule.convertCurrencyUseCase
            )
        }
    }

    private lateinit var themeManager: ThemeManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        themeManager = ThemeManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupListeners()
        observeViewModel()
    }

    private fun setupToolbar() {
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.theme_light -> {
                    themeManager.setTheme(ThemeManager.ThemeMode.LIGHT)
                    true
                }
                R.id.theme_dark -> {
                    themeManager.setTheme(ThemeManager.ThemeMode.DARK)
                    true
                }
                R.id.theme_system -> {
                    themeManager.setTheme(ThemeManager.ThemeMode.SYSTEM)
                    true
                }
                else -> false
            }
        }
    }

    private fun setupListeners() {
        binding.amountEditText.doAfterTextChanged { text ->
            viewModel.onAmountChange(text.toString())
        }

        binding.swapButton.setOnClickListener {
            viewModel.swapCurrencies()
        }

        binding.settingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_settingsFragment)
        }

        binding.historyButton.setOnClickListener {
            HistoryBottomSheet().show(childFragmentManager, HistoryBottomSheet.TAG)
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest { state ->
                    updateSpinners(state.allRates, state.fromCurrencyCode, state.toCurrencyCode)
                    updateResult(state.convertedAmount, state.toCurrencyCode)
                    // Save conversion to history after a successful conversion
                    if (state.convertedAmount != null) {
                        viewModel.saveCurrentConversion()
                    }
                }
            }
        }
    }

    private fun updateResult(convertedAmount: Double?, toCode: String) {
        binding.resultTextView.text = Formatter.formatAmount(convertedAmount)
        binding.toCurrencyCodeText.text = toCode
    }

    private fun updateSpinners(rates: List<CurrencyRate>, fromCode: String, toCode: String) {
        if (rates.isEmpty()) return

        val currencyCodes = rates.map { it.currencyCode }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, currencyCodes).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        // Setup for "From" spinner
        binding.fromCurrencySpinner.adapter = adapter
        val fromPosition = rates.indexOfFirst { it.currencyCode == fromCode }
        if (fromPosition != -1) {
            binding.fromCurrencySpinner.setSelection(fromPosition)
        }
        binding.fromCurrencySpinner.onItemSelectedListener =
            createSpinnerListener(viewModel::onFromCurrencyChange)

        // Setup for "To" spinner
        binding.toCurrencySpinner.adapter = adapter
        val toPosition = rates.indexOfFirst { it.currencyCode == toCode }
        if (toPosition != -1) {
            binding.toCurrencySpinner.setSelection(toPosition)
        }
        binding.toCurrencySpinner.onItemSelectedListener =
            createSpinnerListener(viewModel::onToCurrencyChange)
    }

    private fun createSpinnerListener(onSelected: (String) -> Unit): android.widget.AdapterView.OnItemSelectedListener {
        return object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: android.widget.AdapterView<*>?, view: View?, position: Int, id: Long) {
                onSelected(parent?.getItemAtPosition(position) as String)
            }
            override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {}
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
