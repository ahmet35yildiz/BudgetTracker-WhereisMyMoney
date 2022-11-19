package com.amttech.budgettracker_whereismymoney.ui.fragment

import android.graphics.Color
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
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
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

                val pieList = ArrayList<PieEntry>()
                var incomes = 0f
                var expenses = 0f
                for (i in it) {
                    if (i.transactionType == "Income") {
                        incomes += i.transactionAmount.toFloat()
                    } else {
                        expenses += i.transactionAmount.toFloat()
                    }
                }
                pieList.add(PieEntry(incomes, "Incomes"))
                pieList.add(PieEntry(-expenses, "Expenses"))

                val colorSet = ArrayList<Int>()
                colorSet.add(resources.getColor(R.color.purple_500))
                colorSet.add(resources.getColor(R.color.purple_200))

                val pieDataSet = PieDataSet(pieList,"")
                pieDataSet.colors = colorSet

                val pieData = PieData(pieDataSet)
                pieChart.data = pieData
                pieChart.isDrawHoleEnabled = true
                pieChart.description.isEnabled = false
                pieChart.legend.isEnabled=false
                pieChart.isClickable = false
                pieChart.centerTextRadiusPercent=0f
                pieChart.animateY(1500, Easing.EaseInOutQuad)

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