package com.dicoding.ecobin.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.ecobin.data.response.DataOrganicWaste
import com.dicoding.ecobin.databinding.ItemRowWastetypeBinding
import com.dicoding.ecobin.ui.helper.WasteDiffCallback

class ListWasteTypeAdapter : RecyclerView.Adapter<ListWasteTypeAdapter.WasteViewHolder>(){
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
        holder.bind(listWaste[position])
    }
    override fun getItemCount(): Int {
        return listWaste.size
    }
    inner class WasteViewHolder(private val binding: ItemRowWastetypeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(organicWaste: DataOrganicWaste) {
            with(binding) {
                tvItemName.text = organicWaste.name
//                Glide.with(circleImageView)
//                    .load(fav.avatarUrl).into(circleImageView)
//                cardView.setOnClickListener{
//                    val intent = Intent(it.context, DetailUser::class.java)
//                    intent.putExtra(DetailUser.EXTRA_FAV, fav)
//                    it.context.startActivity(intent)
//                }
            }
        }
    }
}