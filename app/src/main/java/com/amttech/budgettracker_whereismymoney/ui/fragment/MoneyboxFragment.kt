package com.amttech.budgettracker_whereismymoney.ui.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.amttech.budgettracker_whereismymoney.R
import com.amttech.budgettracker_whereismymoney.databinding.FragmentMoneyboxBinding
import com.amttech.budgettracker_whereismymoney.ui.fragment.NewTransactionFragment.Companion.PREFS_FILENAME
import com.amttech.budgettracker_whereismymoney.ui.fragment.NewTransactionFragment.Companion.PREF_KEY_MONEYBOX_AMOUNT
import com.amttech.budgettracker_whereismymoney.ui.fragment.NewTransactionFragment.Companion.PREF_KEY_MONEYBOX_NEW_AMOUNT
import com.amttech.budgettracker_whereismymoney.ui.viewmodel.MoneyboxFragmentViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoneyboxFragment : Fragment() {

    private lateinit var binding: FragmentMoneyboxBinding
    private val viewModel: MoneyboxFragmentViewModel by viewModels()

    private lateinit var sharedPref: SharedPreferences

    var moneyboxName = ""
    var moneyboxAmount = ""
    var moneyboxNewAmount = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_moneybox, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            moneyboxObject = this@MoneyboxFragment

            val pieListMoneybox = ArrayList<PieEntry>()

            sharedPref = requireActivity().getSharedPreferences(PREFS_FILENAME, 0)

            moneyboxName = sharedPref.getString(NewTransactionFragment.PREF_KEY_MONEYBOX_NAME, "")!!
            moneyboxAmount = sharedPref.getString(PREF_KEY_MONEYBOX_AMOUNT, "0")!!
            moneyboxNewAmount = sharedPref.getString(PREF_KEY_MONEYBOX_NEW_AMOUNT, "0")!!

            if (moneyboxNewAmount.toInt() > moneyboxAmount.toInt()) {
                moneyboxNewAmount = moneyboxAmount
            }

            pieListMoneybox.add(
                PieEntry(
                    (moneyboxAmount.toFloat() - moneyboxNewAmount.toFloat()),
                    getString(R.string.deficientAmount)
                )
            )
            pieListMoneybox.add(
                PieEntry(
                    moneyboxNewAmount.toFloat(),
                    getString(R.string.intheMoneybox)
                )
            )

            val colorSetMoneybox = ArrayList<Int>()
            colorSetMoneybox.add(resources.getColor(R.color.purple_500))
            colorSetMoneybox.add(resources.getColor(R.color.teal_700))

            val pieDataSetMoneybox = PieDataSet(pieListMoneybox, "")
            pieDataSetMoneybox.colors = colorSetMoneybox

            val pieDataMoneybox = PieData(pieDataSetMoneybox)
            with(pieChartMoneybox) {
                data = pieDataMoneybox
                isDrawHoleEnabled = true
                description.isEnabled = false
                legend.isEnabled = false
                isClickable = false
                centerTextRadiusPercent = 0f
                animateY(1500, Easing.EaseInOutQuad)
            }
        }
    }
}