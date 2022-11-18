package com.amttech.budgettracker_whereismymoney.ui.fragment

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.amttech.budgettracker_whereismymoney.R
import com.amttech.budgettracker_whereismymoney.databinding.FragmentNewTransactionBinding
import com.amttech.budgettracker_whereismymoney.ui.viewmodel.NewTransactionsFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class NewTransactionFragment : Fragment() {
    private lateinit var binding: FragmentNewTransactionBinding
    private val viewModel: NewTransactionsFragmentViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_new_transaction, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            newTransactionObject = this@NewTransactionFragment

            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            var selectedDate = "$day/${month + 1}/$year"
            tvDate.text = selectedDate

            imageButtonCalendar.setOnClickListener {
                val datePickerDialog =
                    DatePickerDialog(requireContext(), { view, year, month, dayOfMonth ->
                        selectedDate = "$dayOfMonth/${month + 1}/$year"
                        tvDate.text = selectedDate
                    }, year, month, day)
                datePickerDialog.show()
            }

            radioButtonIncome.setOnClickListener {
                chipGroupExpense.visibility = View.GONE
                chipGroupIncome.visibility = View.VISIBLE
            }
            radioButtonExpense.setOnClickListener {
                chipGroupExpense.visibility = View.VISIBLE
                chipGroupIncome.visibility = View.GONE
            }

            buttonSave.setOnClickListener {

                var selectedType = ""
                when (radioGroup.checkedRadioButtonId) {
                    R.id.radioButtonIncome -> selectedType = "Income"
                    R.id.radioButtonExpense -> selectedType = "Expense"
                }

                var selectedCategory = ""
                when (chipGroupIncome.checkedChipId) {
                    R.id.chipSalary -> selectedCategory = "Salary"
                    R.id.chipInvestment -> selectedCategory = "Investment"
                    R.id.chipAllowance -> selectedCategory = "Allowance"
                    R.id.chipBonus -> selectedCategory = "Bonus"
                }
                when (chipGroupExpense.checkedChipId) {
                    R.id.chipFood -> selectedCategory = "Food"
                    R.id.chipShopping -> selectedCategory = "Shopping"
                    R.id.chipBill -> selectedCategory = "Bill"
                    R.id.chipSelfDevelopment -> selectedCategory = "Self Development"
                    R.id.chipTransportationFee -> selectedCategory = "Transportation Fee"
                    R.id.chipEntertainment -> selectedCategory = "Enterainment"
                    R.id.chipHealth -> selectedCategory = "Health"
                    R.id.chipHoliday -> selectedCategory = "Holiday"
                    R.id.chipKids -> selectedCategory = "Kids"
                    R.id.chipOther -> selectedCategory = "Other"
                }

                val enteredAmount =
                    if (editTextAmount.text.isNullOrEmpty()) {
                        0.0
                    } else {
                        if (selectedType == "Income") editTextAmount.text.toString().toDouble()
                        else ("-" + editTextAmount.text.toString()).toDouble()
                    }

                val enteredDescription = editTextDescription.text.toString()

                hideKeyboard()
                buttonSaveClick(
                    selectedType,
                    selectedDate,
                    enteredAmount,
                    selectedCategory,
                    enteredDescription
                )
            }
        }
    }

    private fun buttonSaveClick(
        transactionType: String,
        transactionDate: String,
        transactionAmount: Double,
        transactionCategory: String,
        transactionDescription: String
    ) {

        viewModel.saveTransaction(
            transactionType,
            transactionDate,
            transactionAmount,
            transactionCategory,
            transactionDescription
        )
    }

    private fun hideKeyboard() {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }
}