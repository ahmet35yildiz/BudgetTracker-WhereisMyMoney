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

        with(binding){
            radioButtonAll.setOnClickListener {
                observeAllTransactions()
            }

            radioButtonYear.setOnClickListener {
                observeYearlyTransactions()
            }

            radioButtonMonth.setOnClickListener {
                observeMonthlyTransactions()
            }
        }
    }

    private fun observeAllTransactions() {
        viewModel.lastTransactionsList.observe(viewLifecycleOwner) {
            setupRV(it)
        }
    }

    private fun observeYearlyTransactions() {
        viewModel.lastTransactionsListYear.observe(viewLifecycleOwner) {
            setupRV(it)
        }
    }

    private fun observeMonthlyTransactions() {
        viewModel.lastTransactionsListMonth.observe(viewLifecycleOwner) {
            setupRV(it)
        }
    }

    private fun setupRV(requireTransactionsList: List<Transactions>) {
        with(binding) {
            homepageFragment = this@HomepageFragment

            //Incomes Pie Chart
            val pieListIncomes = ArrayList<PieEntry>()
            var salary = 0f
            var investment = 0f
            var allowance = 0f
            var bonus = 0f

            for (i in requireTransactionsList) {
                when (i.transactionCategory) {
                    "Salary" -> salary += i.transactionAmount.toFloat()
                    "Investment" -> investment += i.transactionAmount.toFloat()
                    "Allowance" -> allowance += i.transactionAmount.toFloat()
                    "Bonus" -> bonus += i.transactionAmount.toFloat()
                }
            }
            with(pieListIncomes) {
                add(PieEntry(salary, "Salary"))
                add(PieEntry(investment, "Investment"))
                add(PieEntry(allowance, "Allowance"))
                add(PieEntry(bonus, "Bonus"))
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
                    "Food" -> food += i.transactionAmount.toFloat()
                    "Shopping" -> shopping += i.transactionAmount.toFloat()
                    "Bill" -> bill += i.transactionAmount.toFloat()
                    "Self Development" -> selfDevelopment += i.transactionAmount.toFloat()
                    "Transportation Fee" -> transportationFee += i.transactionAmount.toFloat()
                    "Entertainment" -> entertainment += i.transactionAmount.toFloat()
                    "Health" -> health += i.transactionAmount.toFloat()
                    "Holiday" -> holiday += i.transactionAmount.toFloat()
                    "Kids" -> kids += i.transactionAmount.toFloat()
                    "Other" -> other += i.transactionAmount.toFloat()
                }
            }

            with(pieListExpenses) {
                add(PieEntry(-food, "Food"))
                add(PieEntry(-shopping, "Shopping"))
                add(PieEntry(-bill, "Bill"))
                add(PieEntry(-selfDevelopment, "Self Development"))
                add(PieEntry(-transportationFee, "Transportation Fee"))
                add(PieEntry(-entertainment, "Entertainment"))
                add(PieEntry(-health, "Health"))
                add(PieEntry(-holiday, "Holiday"))
                add(PieEntry(-kids, "Kids"))
                add(PieEntry(-other, "Other"))
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
                if (i.transactionType == "Income") {
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