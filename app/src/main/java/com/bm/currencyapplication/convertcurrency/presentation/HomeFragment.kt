package com.bm.currencyapplication.convertcurrency.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

import com.bm.currencyapplication.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val convertCurrencyViewModel: ConvertCurrencyViewModel by activityViewModels()

    private lateinit var rates: HashMap<String, Double>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.mainViewModel = convertCurrencyViewModel


        binding.btnShowDetails.setOnClickListener {


        }
        binding.btnSwap.setOnClickListener {
            val valueFrom = binding.spinnerFrom.selectedItemPosition
            val valueTo = binding.spinnerTo.selectedItemPosition
            binding.spinnerFrom.setSelection(valueTo)
            binding.spinnerTo.setSelection(valueFrom)
            convertCurrencyViewModel.onTriggerEvent(
                ConvertCurrencyIntent.SwapRates(
                    valueFrom,
                    valueTo
                )
            )

        }

        initObserver()
        return binding.root
    }


    private fun initObserver() {
        convertCurrencyViewModel.items.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ConvertCurrencyState.Initializing -> {
                    convertCurrencyViewModel.onTriggerEvent(ConvertCurrencyIntent.GetAvailableRates)
                }

                is ConvertCurrencyState.Loading -> {
                    binding.progresBar.visibility = View.VISIBLE
                }

                is ConvertCurrencyState.AllRatesAvailable -> {
                    binding.progresBar.visibility = View.GONE
                    rates = state.data.rates
                    setupSpinners(rates)

                }

                is ConvertCurrencyState.AmountConverted -> {
                    binding.result.setText(state.result)
                }

                is ConvertCurrencyState.ResultConverted -> {
                    binding.amount.setText(state.amount)
                }

                is ConvertCurrencyState.AmountsUpdated -> {
                    binding.amount.setText(state.newAmount)
                    binding.result.setText(state.newResult)
                }

                is ConvertCurrencyState.Error -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()

                }


            }
        }
    }

    private fun setupSpinners(rates: HashMap<String, Double>) {
        val ratesKeys = rates.map { rate ->
            rate.key
        }

        val ratesFrom = listOf("From").plus(ratesKeys)
        val ratesTo = listOf("To").plus(ratesKeys)
        val adapterFrom = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item, ratesFrom
        )
        val adapterTo = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item, ratesTo
        )


        binding.spinnerFrom.adapter = adapterFrom
        binding.spinnerTo.adapter = adapterTo

    }



}