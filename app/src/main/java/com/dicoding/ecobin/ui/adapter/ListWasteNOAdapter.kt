package com.dicoding.ecobin.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.ecobin.R
import com.dicoding.ecobin.data.response.DataOrganicWaste
import com.dicoding.ecobin.databinding.ItemRowWastetypeBinding
import com.dicoding.ecobin.ui.helper.WasteDiffCallback

class ListWasteNOAdapter: RecyclerView.Adapter<ListWasteNOAdapter.WasteViewHolder>(){
    private val listWaste = ArrayList<DataOrganicWaste>()
    fun setListWaste(listWaste: List<DataOrganicWaste>) {
        val diffCallback = WasteDiffCallback(this.listWaste, listWaste)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listWaste.clear()
        this.listWaste.addAll(listWaste)
        diffResult.dispatchUpdatesTo(this)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):WasteViewHolder {
        val binding = ItemRowWastetypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WasteViewHolder(binding)
    }
    override fun onBindViewHolder(holder: WasteViewHolder, position: Int) {
        holder.bind(listWaste[position], position)
    }
    override fun getItemCount(): Int {
        return listWaste.size
    }
    inner class WasteViewHolder(private val binding: ItemRowWastetypeBinding) : RecyclerView.ViewHolder(binding.root) {
        val drawableImages = arrayOf(
            R.drawable.plastic,
            R.drawable.glass,
            R.drawable.electronic,
        )
        fun bind(organicWaste: DataOrganicWaste, position: Int) {
            with(binding) {
                tvItemName.text = organicWaste.name
                val drawableImage = ContextCompat.getDrawable(itemView.context,drawableImages[position % drawableImages.size])
                Glide.with(itemView.context)
                    .load(drawableImage)
                    .into(imgItemPhoto)
            }
        }
    }
}