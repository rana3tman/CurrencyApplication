package com.bm.currencyapplication.historicaldata.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bm.currencyapplication.R
import com.bm.currencyapplication.databinding.FragmentDetailsBinding


class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val detailsViewModel: HistoricalDataViewModel by activityViewModels()
    private var currencyFrom = ""
    private var currencyTo = ""
    private var symbols = listOf<String>()
    private var base = 0.0
    private lateinit var resultAdapter: ResultAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.detailsViewModel = detailsViewModel
        currencyFrom = requireArguments().getString("currency_from").toString()
        currencyTo = requireArguments().getString("currency_to").toString()
        base = requireArguments().getDouble("base")
        symbols = requireArguments().getStringArrayList("symbols")!!.toList()

        initObserver()
        return binding.root
    }

    private fun initObserver() {
        detailsViewModel.items.observe(viewLifecycleOwner) { state ->
            when (state) {
                is HistoricalDataState.Initializing -> {


                    detailsViewModel.onTriggerEvent(
                        HistoricalDataIntent.GetCurrencies(
                            currencyFrom,
                            currencyTo,
                            base,
                            symbols
                        )
                    )
                }

                is HistoricalDataState.Loading -> {
                    if (binding.progressBar.visibility != View.VISIBLE)
                        binding.progressBar.visibility = View.VISIBLE
                }

                is HistoricalDataState.DailyHistoricalData -> {
                    binding.progressBar.visibility = View.GONE
                    resultAdapter = ResultAdapter(requireContext(), state.result)
                    binding.listView.adapter = resultAdapter

                    binding.listView.visibility = View.VISIBLE

                    binding.title2.text =
                        String.format(getString(R.string.the_conversion_of_to), base, currencyFrom)
                    var message = ""
                    state.rates.map { entry ->
                        message += "${entry.key}= ${entry.value} \n\n"

                    }
                    binding.textViewResult.text = message
                    binding.textViewResult.visibility = View.VISIBLE

                }


                is HistoricalDataState.Error -> {
                    binding.progressBar.visibility = View.GONE

                    binding.textViewErrorHisto.text = String.format(
                        getString(R.string.please_try_other_currencies),
                        state.message
                    )
                    binding.textViewErrorHisto.visibility = View.VISIBLE


                }


                else -> {}
            }
        }
    }

    override fun onDestroy() {
        binding.unbind()
        super.onDestroy()
    }

}