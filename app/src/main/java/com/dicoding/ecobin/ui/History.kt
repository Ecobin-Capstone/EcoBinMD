package com.dicoding.ecobin.ui

import android.content.Intent
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.dicoding.ecobin.R
import com.dicoding.ecobin.databinding.ActivityHistoryBinding
import com.dicoding.ecobin.ui.adapter.SectionPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class History : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2,
            R.string.tab_text_3,
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    val homeIntent = Intent(this@History, Dashboard::class.java)
                    startActivity(homeIntent)
                    true
                }

                R.id.scan -> {
                    val dashboardIntent = Intent(this@History, UploadActivity::class.java)
                    startActivity(dashboardIntent)
                    true
                }
                R.id.history -> {
                    val dashboardIntent = Intent(this@History, History::class.java)
                    startActivity(dashboardIntent)
                    true
                }
                R.id.profile -> {
                    val dashboardIntent = Intent(this@History, Profile::class.java)
                    startActivity(dashboardIntent)
                    true
                }
                // Add cases for other menu items if needed
                else -> false
            }
        }

        val sectionsPagerAdapter = SectionPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }
}