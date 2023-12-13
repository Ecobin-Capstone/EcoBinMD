package com.dicoding.ecobin.ui.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.ecobin.R
import com.dicoding.ecobin.data.response.DataOrganicWaste
import com.dicoding.ecobin.data.response.WasteItem
import com.dicoding.ecobin.databinding.ItemRowWastetypeBinding
import com.dicoding.ecobin.ui.helper.WasteDiffCallback

class ListWasteNOAdapter: RecyclerView.Adapter<ListWasteNOAdapter.WasteViewHolder>(){
    private val listWaste = ArrayList<DataOrganicWaste>()
    private val wasteItemList = mutableListOf<WasteItem>()
    fun setListWaste(listWaste: List<DataOrganicWaste>) {
        val diffCallback = WasteDiffCallback(this.listWaste, listWaste)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listWaste.clear()
        this.listWaste.addAll(listWaste)
        diffResult.dispatchUpdatesTo(this)
    }
    fun getWasteItemList(): List<WasteItem> {
        return wasteItemList
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
        init {
            binding.beratText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val quantity = s.toString().toIntOrNull() ?: 0
                        val typeId = 1 + (position+1)
                        val wasteItem = WasteItem(typeId, quantity)

                        // Update the wasteItemList for the specific position
                        if (position < wasteItemList.size) {
                            wasteItemList[position] = wasteItem
                        } else {
                            wasteItemList.add(wasteItem)
                        }
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // Not used in this scenario
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // Not used in this scenario
                }
            })
        }
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