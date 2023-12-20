package com.dicoding.ecobin.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.ecobin.ui.Dashboard
import com.dicoding.ecobin.ui.HistoryFragment

class SectionPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var id: String = Dashboard.id
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = HistoryFragment()
        fragment.arguments = Bundle().apply {
            putInt(HistoryFragment.ARG_SECTION_NUMBER, position + 1)
            putString(HistoryFragment.ARG_ID, id)
        }
        return fragment
    }
}