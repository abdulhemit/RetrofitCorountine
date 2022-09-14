package com.example.retrofitcorountine.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitcorountine.databinding.RecyclerRowBinding
import com.example.retrofitcorountine.model.cryptoModel


class RecyclerAdapter(val cryptoList: ArrayList<cryptoModel>, private val listener: Listener):
    RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder>() {

    interface Listener{
        fun onItemClick(cryptoModel: cryptoModel)
    }

    private val colors : Array<String> = arrayOf("#31E580","#D4E531","#314CE5","#E57531","#31E56A","#31DAE5","#C931E5","#E53151")

    class RecyclerHolder(val binding : RecyclerRowBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(cryptoModel: cryptoModel,colors:Array<String>,position: Int,listenert: Listener){

            itemView.setOnClickListener {
                listenert.onItemClick(cryptoModel)
            }
            binding.currency.text = cryptoModel.currency
            binding.price.text = cryptoModel.price
            itemView.setBackgroundColor(Color.parseColor(colors[position % 8]))


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RecyclerHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {

        holder.bind(cryptoList[position],colors,position,listener)
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }
}