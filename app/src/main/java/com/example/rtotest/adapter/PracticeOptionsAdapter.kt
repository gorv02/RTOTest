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

class PracticeOptionsAdapter(
    private val options: List<String>,
    private val answerId: Int,
    private val isAnswered: Boolean,
    private var selectedOption: Int?,
    private val onClick: ClickListener,
) : RecyclerView.Adapter<PracticeOptionsAdapter.OptionsViewHolder>() {

    inner class OptionsViewHolder(private val view: View):
        RecyclerView.ViewHolder(view){

        @SuppressLint("SetTextI18n")
        fun bindData(data : String, SNo : Int){
            val optionText = view.findViewById<TextView>(R.id.option_text)
            val optionNo = view.findViewById<TextView>(R.id.option_no)
            val optionBg = view.findViewById<CardView>(R.id.option_bg)

            optionText.text = data
            optionNo.text = "$SNo."

            checkCorrectAnswer(data,optionBg)

            view.setOnClickListener{
                selectedOption = adapterPosition
                onClick.onClickRvOption(data , selectedOption)
            }
        }

        private fun checkCorrectAnswer(option: String, optionBg: CardView){
            if (isAnswered){
                 if (option == options[answerId-1]){
                     optionBg.setCardBackgroundColor(Color.parseColor("#85e085"))
                 }
                else {
                     selectedOption?.let {
                         if (options[it] == option) {
                             optionBg.setCardBackgroundColor(Color.parseColor("#ff6666"))
                         }
                     }
                 }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionsViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_option, parent,false)

        return OptionsViewHolder(v)
    }

    override fun onBindViewHolder(holder: OptionsViewHolder, position: Int) {
        holder.bindData(options[position], position+1)
    }

    override fun getItemCount(): Int {
        return options.size
    }

    interface ClickListener{
        fun onClickRvOption(
            option: String,
            selectedOpt: Int?
        )
    }
}