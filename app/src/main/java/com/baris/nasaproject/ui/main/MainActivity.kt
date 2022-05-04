package com.baris.nasaproject.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.viewpager2.widget.ViewPager2
import com.baris.nasaproject.R
import com.baris.nasaproject.databinding.ActivityMainBinding
import com.baris.nasaproject.ui.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewPager: ViewPager2 = binding.viewPager
        val viewPagerAdapter = ViewPagerAdapter(this, this)

        viewPager.adapter = viewPagerAdapter
        viewPager.currentItem = 0

        val tabs: TabLayout = binding.tabLayout

        TabLayoutMediator(tabs, viewPager){ tab, position ->
            tab.text = viewPagerAdapter.getTabTitle(position)
        }.attach()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.actions, menu)
        return true
    }
}