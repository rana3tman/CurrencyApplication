package com.bm.currencyapplication.convertcurrency.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.bm.currencyapplication.R

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
            if (convertCurrencyViewModel.currentRate != 0.0 && convertCurrencyViewModel.currencyFrom.value != 0 && convertCurrencyViewModel.currencyTo.value != 0 && convertCurrencyViewModel.amountCurrentValue.value!!.isNotEmpty()) {
                addDetailsFragment(
                    convertCurrencyViewModel.fromKeys[convertCurrencyViewModel.currencyFrom.value!!],
                    convertCurrencyViewModel.toKeys[convertCurrencyViewModel.currencyTo.value!!],
                    listOf(
                        "USD",
                        "JPY",
                        "GBP",
                        "CNY",
                        "AUD",
                        "CAD",
                        "CHF",
                        "HKD",
                        "SGD",
                        "SEK",
                        "KRW"
                    ),
                    convertCurrencyViewModel.amountCurrentValue.value!!.toDouble()
                )
            } else Toast.makeText(
                requireContext(),
                getString(R.string.please_select_currencies_or_enter_amount),
                Toast.LENGTH_LONG
            ).show()

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

    private fun addDetailsFragment(
        currencyFrom: String,
        currencyTo: String,
        symbols: List<String>,
        base: Double
    ) {

        val bundle = Bundle()
        bundle.putString("currency_from", currencyFrom)
        bundle.putString("currency_to", currencyTo)
        bundle.putDouble("base", base)
        bundle.putStringArrayList("symbols", ArrayList(symbols))


        NavHostFragment.findNavController(this)
            .navigate(R.id.action_homeFragment_to_fragment_details, bundle)

    }

}