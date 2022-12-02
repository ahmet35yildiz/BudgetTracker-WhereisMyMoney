package com.amttech.budgettracker_whereismymoney.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.amttech.budgettracker_whereismymoney.ui.fragment.HomepageFragment
import com.amttech.budgettracker_whereismymoney.ui.fragment.MoneyboxFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return HomepageFragment()
        }
        return MoneyboxFragment()
    }

    companion object {
        private const val NUM_TABS = 2
    }
}