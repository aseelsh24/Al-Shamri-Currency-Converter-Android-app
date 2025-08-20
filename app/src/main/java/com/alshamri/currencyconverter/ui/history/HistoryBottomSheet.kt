package com.alshamri.currencyconverter.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.alshamri.currencyconverter.CurrencyApplication
import com.alshamri.currencyconverter.databinding.BottomSheetHistoryBinding
import com.alshamri.currencyconverter.di.ViewModelFactory
import com.alshamri.currencyconverter.ui.main.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HistoryBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels {
        ViewModelFactory {
            MainViewModel(
                CurrencyApplication.appModule.currencyRepository,
                CurrencyApplication.appModule.historyRepository,
                CurrencyApplication.appModule.convertCurrencyUseCase
            )
        }
    }

    private lateinit var historyAdapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        historyAdapter = HistoryAdapter()
        binding.historyRecyclerView.adapter = historyAdapter
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.history.collectLatest { historyList ->
                if (historyList.isEmpty()) {
                    binding.emptyHistoryText.visibility = View.VISIBLE
                    binding.historyRecyclerView.visibility = View.GONE
                } else {
                    binding.emptyHistoryText.visibility = View.GONE
                    binding.historyRecyclerView.visibility = View.VISIBLE
                    historyAdapter.submitList(historyList)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "HistoryBottomSheet"
    }
}
