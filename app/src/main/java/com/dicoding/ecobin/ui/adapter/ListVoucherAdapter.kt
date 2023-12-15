package com.dicoding.ecobin.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.ecobin.R
import com.dicoding.ecobin.data.response.DataVoucher
import com.dicoding.ecobin.databinding.ItemRowVoucherBinding
import com.dicoding.ecobin.ui.helper.VoucherDiffCallback

class ListVoucherAdapter (private val listener: VoucherClickListener): RecyclerView.Adapter<ListVoucherAdapter.VoucherViewHolder>(){
    private val listVoucher= ArrayList<DataVoucher>()
    fun setListVoucher(listVoucher: List<DataVoucher>) {
        val diffCallback = VoucherDiffCallback(this.listVoucher, listVoucher)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listVoucher.clear()
        this.listVoucher.addAll(listVoucher)
        diffResult.dispatchUpdatesTo(this)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):VoucherViewHolder {
        val binding = ItemRowVoucherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VoucherViewHolder(binding)
    }
    override fun onBindViewHolder(holder: VoucherViewHolder, position: Int) {
        holder.bind(listVoucher[position])
    }
    override fun getItemCount(): Int {
        return listVoucher.size
    }

    fun getItem(position: Int): DataVoucher {
        return listVoucher[position]
    }

    interface VoucherClickListener {
        fun onRedeem(voucherID: Int)
    }
    inner class VoucherViewHolder(private val binding: ItemRowVoucherBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.redeemButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val voucher = getItem(position)
                    listener.onRedeem(voucher.id!!)
                }
            }
        }
        val drawableImages = arrayOf(
            R.drawable.coffee,
            R.drawable.bus,
            R.drawable.gojek,
            R.drawable.tokopedia,
            R.drawable.lombok,
        )
        fun bind(voc: DataVoucher) {
            with(binding) {
                tvItemName.text = voc.name
                desc.text = voc.description
                point.text = "Point Needed : "+voc.point
                val drawableImage = ContextCompat.getDrawable(itemView.context,drawableImages[position % drawableImages.size])
                Glide.with(itemView.context)
                    .load(drawableImage)
                    .into(imgItemPhoto)
            }
        }
    }
}