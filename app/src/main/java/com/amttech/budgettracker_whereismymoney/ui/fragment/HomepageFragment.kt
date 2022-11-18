package com.amttech.budgettracker_whereismymoney.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.amttech.budgettracker_whereismymoney.R
import com.amttech.budgettracker_whereismymoney.databinding.FragmentHomepageBinding
import com.amttech.budgettracker_whereismymoney.ui.adapter.LastTransactionsAdapter
import com.amttech.budgettracker_whereismymoney.ui.viewmodel.HomepageFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomepageFragment : Fragment() {

    private lateinit var binding: FragmentHomepageBinding
    private val viewModel: HomepageFragmentViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_homepage, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.lastTransactionsList.observe(viewLifecycleOwner) {

            with(binding) {
                homepageFragment = this@HomepageFragment

                val adapter = LastTransactionsAdapter(it)
                lastTransactionsAdapter = adapter
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.installLastTransactions()
    }
}