package com.example.rtotest.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.rtotest.R

class ExamOptionsAdapter(
        private val options: List<String>,
        private var selectedOption: Int?,
        private val onClick: ClickListener,
) : RecyclerView.Adapter<ExamOptionsAdapter.OptionsViewHolder>() {

    inner class OptionsViewHolder(private val view: View) :
            RecyclerView.ViewHolder(view) {

        @SuppressLint("SetTextI18n")
        fun bindData(option: String, SNo: Int) {
            val optionText = view.findViewById<TextView>(R.id.option_text)
            val optionNo = view.findViewById<TextView>(R.id.option_no)
            val optionBg = view.findViewById<CardView>(R.id.option_bg)

            optionNo.text = "$SNo."
            optionText.text = option

            markSelectedOption(option, optionBg)

            view.setOnClickListener {
                selectedOption = adapterPosition
                onClick.onClickRvOption(option, selectedOption)
            }
        }

        private fun markSelectedOption(option: String, optionBg: CardView) {
            selectedOption?.let {
                if (options[it] == option) {
                    optionBg.setCardBackgroundColor(Color.parseColor("#03DAC5"))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionsViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_option, parent, false)

        return OptionsViewHolder(v)
    }

    override fun onBindViewHolder(holder: OptionsViewHolder, position: Int) {
        holder.bindData(options[position], position + 1)
    }

    override fun getItemCount(): Int {
        return options.size
    }

    interface ClickListener {
        fun onClickRvOption(
                option: String,
                selectedOpt: Int?
        )
    }
}