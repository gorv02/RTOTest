package com.example.rtotest.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rtotest.R
import com.example.rtotest.model.TrafficSigns
import com.example.rtotest.network.bindImageFromUrl

class TrafficSignsAdapter()
    : RecyclerView.Adapter<TrafficSignsAdapter.MyView>() {

    private var list: List<TrafficSigns> = emptyList()

    fun setList(list: List<TrafficSigns>){
        this.list = list
    }

    inner class MyView(private val view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("SetTextI18n")
        fun bindImages(sign: TrafficSigns) {
            val sNo = view.findViewById<TextView>(R.id.sNo_traffic_text)
            val trafficImg = view.findViewById<ImageView>(R.id.traffic_img)
            val trafficText = view.findViewById<TextView>(R.id.traffic_text)

            sNo.text = "${(adapterPosition+1)}."
            bindImageFromUrl(trafficImg , sign.signUrl)
            trafficText.text = sign.signName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {

        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_traffic_signs, parent, false)

        return MyView(itemView)
    }

    override fun onBindViewHolder(
        holder: MyView,
        position: Int
    ) {
        holder.bindImages(list[position])
    }

    override fun getItemCount(): Int = list.size
}