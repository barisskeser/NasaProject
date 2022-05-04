package com.baris.nasaproject.ui.adapter

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.baris.nasaproject.R
import com.baris.nasaproject.ui.main.ViewPagerFragment

private val TAB_TITLES = arrayOf(
    R.string.curiosity,
    R.string.opportunity,
    R.string.spirit
)

class ViewPagerAdapter(
    private val context: Context,
    activity: FragmentActivity
) : FragmentStateAdapter(activity) {

    fun getTabTitle(position: Int): String {
        return context.getString(TAB_TITLES[position])
    }

    override fun getItemCount(): Int {
        return TAB_TITLES.size
    }

    override fun createFragment(position: Int): Fragment {
        val fragment: Fragment = ViewPagerFragment()
        fragment.arguments = Bundle().apply {
            putInt("ROVER", TAB_TITLES[position])
        }
        return fragment
    }
}