package com.dicoding.ecobin.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.ecobin.data.response.DataOrder
import com.dicoding.ecobin.databinding.ItemRowRiwayatBinding
import com.dicoding.ecobin.ui.helper.OrderDiffCallback
class ListOrderAdapter(private val listener: OrderClickListener): RecyclerView.Adapter<ListOrderAdapter.OrderViewHolder>(){
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

    fun getItem(position: Int): DataOrder {
        return listOrder[position]
    }

    interface OrderClickListener {
        fun onAcceptOrder(orderId: Int)
        fun onDeclineOrder(orderId: Int)
    }
    inner class OrderViewHolder(private val binding: ItemRowRiwayatBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.acceptButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val order = getItem(position)
                    listener.onAcceptOrder(order.pickupId!!)
                }
            }

            binding.DeclineButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val order = getItem(position)
                    listener.onDeclineOrder(order.pickupId!!)
                }
            }
        }
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