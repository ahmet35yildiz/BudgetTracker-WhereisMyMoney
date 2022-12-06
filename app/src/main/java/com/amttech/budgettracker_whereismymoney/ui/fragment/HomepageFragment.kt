package com.amttech.budgettracker_whereismymoney.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.amttech.budgettracker_whereismymoney.R
import com.amttech.budgettracker_whereismymoney.data.entity.Transactions
import com.amttech.budgettracker_whereismymoney.databinding.FragmentHomepageBinding
import com.amttech.budgettracker_whereismymoney.ui.adapter.LastTransactionsAdapter
import com.amttech.budgettracker_whereismymoney.ui.viewmodel.HomepageFragmentViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.*
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

        binding.homepageFragment = this@HomepageFragment
    }

    fun observeAllTransactions() {
        viewModel.lastTransactionsList.observe(viewLifecycleOwner) {
            setupRV(it)
        }
    }

    fun observeYearlyTransactions() {
        viewModel.lastTransactionsListYear.observe(viewLifecycleOwner) {
            setupRV(it)
        }
    }

    fun observeMonthlyTransactions() {
        viewModel.lastTransactionsListMonth.observe(viewLifecycleOwner) {
            setupRV(it)
        }
    }

    private fun setupRV(requireTransactionsList: List<Transactions>) {
        with(binding) {

            //Incomes Pie Chart
            val pieListIncomes = ArrayList<PieEntry>()
            var salary = 0f
            var investment = 0f
            var allowance = 0f
            var bonus = 0f

            for (i in requireTransactionsList) {
                when (i.transactionCategory) {
                    getString(R.string.chipSalary) -> salary += i.transactionAmount.toFloat()
                    getString(R.string.chipInvestment) -> investment += i.transactionAmount.toFloat()
                    getString(R.string.chipAllowance) -> allowance += i.transactionAmount.toFloat()
                    getString(R.string.chipBonus) -> bonus += i.transactionAmount.toFloat()
                }
            }
            with(pieListIncomes) {
                add(PieEntry(salary, getString(R.string.chipSalary)))
                add(PieEntry(investment, getString(R.string.chipInvestment)))
                add(PieEntry(allowance, getString(R.string.chipAllowance)))
                add(PieEntry(bonus, getString(R.string.chipBonus)))
            }

            val colorSetIncomes = ArrayList<Int>()
            with(colorSetIncomes) {
                add(resources.getColor(R.color.purple_500))
                add(resources.getColor(R.color.purple_200))
                add(resources.getColor(R.color.purple_700))
                add(resources.getColor(R.color.black))
            }

            val pieDataSetIncomes = PieDataSet(pieListIncomes, "")
            pieDataSetIncomes.colors = colorSetIncomes

            val pieDataIncomes = PieData(pieDataSetIncomes)
            with(pieChartIncomes) {
                data = pieDataIncomes
                isDrawHoleEnabled = true
                description.isEnabled = false
                legend.isEnabled = false
                isClickable = false
                centerTextRadiusPercent = 0f
                animateY(1500, Easing.EaseInOutQuad)
            }

            //Expenses Pie Chart
            val pieListExpenses = ArrayList<PieEntry>()
            var food = 0f
            var shopping = 0f
            var bill = 0f
            var selfDevelopment = 0f
            var transportationFee = 0f
            var entertainment = 0f
            var health = 0f
            var holiday = 0f
            var kids = 0f
            var other = 0f

            for (i in requireTransactionsList) {
                when (i.transactionCategory) {
                    getString(R.string.chipFood) -> food += i.transactionAmount.toFloat()
                    getString(R.string.chipShopping) -> shopping += i.transactionAmount.toFloat()
                    getString(R.string.chipBill) -> bill += i.transactionAmount.toFloat()
                    getString(R.string.chipSelfDevelopment) -> selfDevelopment += i.transactionAmount.toFloat()
                    getString(R.string.chipTransportationFee) -> transportationFee += i.transactionAmount.toFloat()
                    getString(R.string.chipEntertainment) -> entertainment += i.transactionAmount.toFloat()
                    getString(R.string.chipHealth) -> health += i.transactionAmount.toFloat()
                    getString(R.string.chipHoliday) -> holiday += i.transactionAmount.toFloat()
                    getString(R.string.chipKids) -> kids += i.transactionAmount.toFloat()
                    getString(R.string.chipOther) -> other += i.transactionAmount.toFloat()
                }
            }

            with(pieListExpenses) {
                add(PieEntry(-food, getString(R.string.chipFood)))
                add(PieEntry(-shopping, getString(R.string.chipShopping)))
                add(PieEntry(-bill, getString(R.string.chipBill)))
                add(PieEntry(-selfDevelopment, getString(R.string.chipSelfDevelopment)))
                add(PieEntry(-transportationFee, getString(R.string.chipTransportationFee)))
                add(PieEntry(-entertainment, getString(R.string.chipEntertainment)))
                add(PieEntry(-health, getString(R.string.chipHealth)))
                add(PieEntry(-holiday, getString(R.string.chipHoliday)))
                add(PieEntry(-kids, getString(R.string.chipKids)))
                add(PieEntry(-other, getString(R.string.chipOther)))
            }

            val colorSetExpenses = ArrayList<Int>()
            with(colorSetExpenses) {
                add(resources.getColor(R.color.purple_500))
                add(resources.getColor(R.color.purple_200))
                add(resources.getColor(R.color.purple_700))
                add(resources.getColor(R.color.black))
                add(resources.getColor(R.color.teal_200))
                add(resources.getColor(R.color.teal_700))
                add(resources.getColor(R.color.colorAccent))
                add(resources.getColor(R.color.colorPrimary))
                add(resources.getColor(R.color.colorPrimaryDark))
                add(resources.getColor(R.color.white))
            }

            val pieDataSetExpenses = PieDataSet(pieListExpenses, "")
            pieDataSetExpenses.colors = colorSetExpenses

            val pieDataExpenses = PieData(pieDataSetExpenses)
            with(pieChartExpenses) {
                data = pieDataExpenses
                isDrawHoleEnabled = true
                description.isEnabled = false
                legend.isEnabled = false
                isClickable = false
                centerTextRadiusPercent = 0f
                animateY(1500, Easing.EaseInOutQuad)
            }

            //Horizontal Bar Chart
            val barList = ArrayList<BarEntry>()
            var incomes = 0f
            var expenses = 0f
            for (i in requireTransactionsList) {
                if (i.transactionType == getString(R.string.radioButtonIncome)) {
                    incomes += i.transactionAmount.toFloat()
                } else {
                    expenses += i.transactionAmount.toFloat()
                }
            }
            barList.add(BarEntry(incomes, incomes))
            barList.add(BarEntry(-expenses, -expenses))

            val barColorSet = ArrayList<Int>()
            barColorSet.add(resources.getColor(R.color.purple_500))
            barColorSet.add(resources.getColor(R.color.purple_200))

            val barDataSet = BarDataSet(barList, "")
            barDataSet.colors = barColorSet

            val barData = BarData(barDataSet)
            with(horizontalBarChart) {
                data = barData
                isClickable = false
                description.isEnabled = false
                legend.isEnabled = false
                animateY(1500, Easing.EaseInOutQuad)
                axisLeft.isEnabled = true
                axisRight.isEnabled = true
                xAxis.isEnabled = false
                data.barWidth = 50f
                setDrawBarShadow(true)
            }

            val adapter = LastTransactionsAdapter(requireTransactionsList)
            lastTransactionsAdapter = adapter
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.installLastTransactions()
    }
}