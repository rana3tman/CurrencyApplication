package com.bm.currencyapplication.historicaldata.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.bm.currencyapplication.R
import com.bm.currencyapplication.convertcurrency.domain.HistoricalRateModel


class ResultAdapter(private val context: Context, private val list: List<HistoricalRateModel>) :
    BaseAdapter() {
    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {

        return list.size
    }

    override fun getItem(p0: Int): Any {
        return list[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

        val rowView = inflater.inflate(R.layout.list_view_item, p2, false)
        val dateTextView = rowView.findViewById<TextView>(R.id.date)
        val baseTextView = rowView.findViewById<TextView>(R.id.base)
        val rateTextView = rowView.findViewById<TextView>(R.id.rate)

        val item = getItem(p0) as HistoricalRateModel

        dateTextView.text = context.getString(R.string.date, item.date)
        baseTextView.text = context.getString(R.string.base, item.base)
        rateTextView.text = context.getString(R.string.s_rate, item.rates)
        return rowView
    }

}