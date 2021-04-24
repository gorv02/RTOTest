package com.example.rtotest.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rtotest.R
import com.example.rtotest.model.TrafficSigns


class HorizontalAdapter(val list: List<TrafficSigns> ,val clickListener: ClickListener) : RecyclerView.Adapter<HorizontalAdapter.MyView>() {

    inner class MyView(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bindImages(sign: TrafficSigns) {
            val imageView1 = view.findViewById<ImageView>(R.id.imageView1)
            var textView1 = view.findViewById<TextView>(R.id.textView1)

            imageView1.setImageResource(sign.signId)
            textView1.text = sign.signName

            view.setOnClickListener {
                clickListener.onClickRvHorizontal(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {

        val itemView: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.grid_item, parent, false)

        return MyView(itemView)
    }

    override fun onBindViewHolder(
        holder: MyView,
        position: Int
    ) {
        holder.bindImages(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface ClickListener{
        fun onClickRvHorizontal(position: Int)
    }
}