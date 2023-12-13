package com.dicoding.ecobin.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.ecobin.data.response.DataOrder
import com.dicoding.ecobin.databinding.ItemRowRiwayatBinding
import com.dicoding.ecobin.ui.helper.OrderDiffCallback
class ListOrderAdapter : RecyclerView.Adapter<ListOrderAdapter.OrderViewHolder>(){
    private val listOrder = ArrayList<DataOrder>()
    fun setListOrder(listOrder: List<DataOrder>) {
        val diffCallback = OrderDiffCallback(this.listOrder, listOrder)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listOrder.clear()
        this.listOrder.addAll(listOrder)
        diffResult.dispatchUpdatesTo(this)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):OrderViewHolder {
        val binding = ItemRowRiwayatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }
    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(listOrder[position])
    }
    override fun getItemCount(): Int {
        return listOrder.size
    }
    inner class OrderViewHolder(private val binding: ItemRowRiwayatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(order: DataOrder) {
            with(binding) {
                nama.text = "Nama : "+order.name
                alamat.text ="Alamat : "+order.address
                waktu.text = "Waktu : "+order.date +" "+ order.time
                transID.text="Transaksi ID : "+order.pickupId
                notes.text = "Notes : "+order.note
            }
        }
    }
}