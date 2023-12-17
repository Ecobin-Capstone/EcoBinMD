package com.dicoding.ecobin.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.ecobin.data.response.DataReceipt
import com.dicoding.ecobin.databinding.ItemRowReceiptBinding
import com.dicoding.ecobin.ui.helper.ReceiptDiffCallback

class ListReceiptAdapter(private val listener: ListReceiptAdapter.VoucherClickListener): RecyclerView.Adapter<ListReceiptAdapter.VoucherViewHolder>(){
    private val listVoucher= ArrayList<DataReceipt>()
    fun setListVoucher(listVoucher: List<DataReceipt>) {
        val diffCallback = ReceiptDiffCallback(this.listVoucher, listVoucher)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listVoucher.clear()
        this.listVoucher.addAll(listVoucher)
        diffResult.dispatchUpdatesTo(this)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):VoucherViewHolder {
        val binding = ItemRowReceiptBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VoucherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VoucherViewHolder, position: Int) {
        holder.bind(listVoucher[position])
    }
    override fun getItemCount(): Int {
        return listVoucher.size
    }
    interface VoucherClickListener {
        fun checkCode(userID: String)
    }
    fun getItem(position: Int): DataReceipt {
        return listVoucher[position]
    }

    inner class VoucherViewHolder(private val binding: ItemRowReceiptBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.redeemButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val voucher = getItem(position)
                    listener.checkCode(voucher.id!!.toString())
                }
            }
        }
        fun bind(voc: DataReceipt) {
            with(binding) {
                tvItemName.text = voc.voucher
            }
        }
    }
}