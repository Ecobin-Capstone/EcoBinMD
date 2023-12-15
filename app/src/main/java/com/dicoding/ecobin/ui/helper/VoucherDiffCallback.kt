package com.dicoding.ecobin.ui.helper

import androidx.recyclerview.widget.DiffUtil
import com.dicoding.ecobin.data.response.DataVoucher

class VoucherDiffCallback (private val oldWasteList: List<DataVoucher>, private val newWasteList: List<DataVoucher>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldWasteList.size
    override fun getNewListSize(): Int = newWasteList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldWasteList[oldItemPosition].name == oldWasteList[newItemPosition].name
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote = oldWasteList[oldItemPosition]
        val newNote = newWasteList[newItemPosition]
        return oldNote.name == newNote.name
    }
}