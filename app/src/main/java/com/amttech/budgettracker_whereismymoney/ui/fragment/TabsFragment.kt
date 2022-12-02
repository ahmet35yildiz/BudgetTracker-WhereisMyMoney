package com.amttech.budgettracker_whereismymoney.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.amttech.budgettracker_whereismymoney.R
import com.amttech.budgettracker_whereismymoney.databinding.FragmentTabsBinding
import com.amttech.budgettracker_whereismymoney.ui.adapter.ViewPagerAdapter
import com.amttech.budgettracker_whereismymoney.ui.viewmodel.TabsFragmentViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TabsFragment : Fragment() {
    private lateinit var binding: FragmentTabsBinding
    private val viewModel: TabsFragmentViewModel by viewModels()

    private val fragmentsArray = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tabs, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentsArray.add(getString(R.string.transactionsTab))
        fragmentsArray.add(getString(R.string.moneyboxTab))

        with(binding) {
            tabsFragment = this@TabsFragment

            val adapter = ViewPagerAdapter(childFragmentManager, lifecycle)
            viewPagerTabs.adapter = adapter

            TabLayoutMediator(tabLayoutTabs, viewPagerTabs) { tab, position ->
                tab.text = fragmentsArray[position]
            }.attach()
        }
    }
}