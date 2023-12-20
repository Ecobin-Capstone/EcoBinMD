package com.dicoding.ecobin.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.ecobin.data.response.DataPickup
import com.dicoding.ecobin.databinding.ItemRowHistoryBinding

class ListHistoryAdapter: ListAdapter<DataPickup, ListHistoryAdapter.MyViewHolder>(DIFF_CALLBACK){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRowHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataPickup>() {
            override fun areItemsTheSame(oldItem: DataPickup, newItem: DataPickup): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: DataPickup, newItem: DataPickup): Boolean {
                return oldItem == newItem
            }
        }
    }
    class MyViewHolder(val binding: ItemRowHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataPickup){
            binding.transID.text = "Pickup ID : "+data.pickupId
            binding.penerima.text = "Penerima : "+data.partner
            binding.Kategori.text = "Kategori : "+data.category
            binding.waktu.text = "Waktu : "+data.timeAgo

        }
    }
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: DataPickup)
    }

}