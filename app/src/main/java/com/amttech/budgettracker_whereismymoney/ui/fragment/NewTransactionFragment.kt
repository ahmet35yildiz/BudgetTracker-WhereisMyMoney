package com.amttech.budgettracker_whereismymoney.ui.fragment

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
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
import com.amttech.budgettracker_whereismymoney.util.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class NewTransactionFragment : Fragment() {
    private lateinit var binding: FragmentNewTransactionBinding
    private val viewModel: NewTransactionsFragmentViewModel by viewModels()

    private lateinit var sharedPref: SharedPreferences

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

            sharedPref = requireActivity().getSharedPreferences(PREFS_FILENAME, 0)

            editTextMoneyboxName.setText(sharedPref.getString(PREF_KEY_MONEYBOX_NAME, ""))
            editTextMoneyboxAmount.setText(sharedPref.getString(PREF_KEY_MONEYBOX_AMOUNT, ""))

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
                editTextAmount.visibility = View.VISIBLE
                tvDate.visibility = View.VISIBLE
                imageButtonCalendar.visibility = View.VISIBLE
                editTextDescription.visibility = View.VISIBLE
                buttonSave.visibility = View.VISIBLE
                textInputLayoutMoneyboxName.visibility = View.GONE
                textInputLayoutMoneyboxAmount.visibility = View.GONE
                textInputLayoutMoneyboxNewPrice.visibility = View.GONE
                buttonSaveMoneybox.visibility = View.GONE
                buttonAddMoneybox.visibility = View.GONE

            }
            radioButtonExpense.setOnClickListener {
                chipGroupExpense.visibility = View.VISIBLE
                chipGroupIncome.visibility = View.GONE
                editTextAmount.visibility = View.VISIBLE
                tvDate.visibility = View.VISIBLE
                imageButtonCalendar.visibility = View.VISIBLE
                editTextDescription.visibility = View.VISIBLE
                buttonSave.visibility = View.VISIBLE
                textInputLayoutMoneyboxName.visibility = View.GONE
                textInputLayoutMoneyboxAmount.visibility = View.GONE
                textInputLayoutMoneyboxNewPrice.visibility = View.GONE
                buttonSaveMoneybox.visibility = View.GONE
                buttonAddMoneybox.visibility = View.GONE
            }
            radioButtonMoneybox.setOnClickListener {
                textInputLayoutMoneyboxName.visibility = View.VISIBLE
                textInputLayoutMoneyboxAmount.visibility = View.VISIBLE
                textInputLayoutMoneyboxNewPrice.visibility = View.VISIBLE
                buttonSaveMoneybox.visibility = View.VISIBLE
                buttonAddMoneybox.visibility = View.VISIBLE
                chipGroupExpense.visibility = View.GONE
                chipGroupIncome.visibility = View.GONE
                editTextAmount.visibility = View.GONE
                tvDate.visibility = View.GONE
                imageButtonCalendar.visibility = View.GONE
                editTextDescription.visibility = View.GONE
                buttonSave.visibility = View.GONE
            }

            buttonSave.setOnClickListener {

                var selectedType = ""
                when (radioGroup.checkedRadioButtonId) {
                    R.id.radioButtonIncome -> selectedType = getString(R.string.radioButtonIncome)
                    R.id.radioButtonExpense -> selectedType = getString(R.string.radioButtonExpense)
                }

                var selectedCategory = ""
                when (chipGroupIncome.checkedChipId) {
                    R.id.chipSalary -> selectedCategory = getString(R.string.chipSalary)
                    R.id.chipInvestment -> selectedCategory = getString(R.string.chipInvestment)
                    R.id.chipAllowance -> selectedCategory = getString(R.string.chipAllowance)
                    R.id.chipBonus -> selectedCategory = getString(R.string.chipBonus)
                }
                when (chipGroupExpense.checkedChipId) {
                    R.id.chipFood -> selectedCategory = getString(R.string.chipFood)
                    R.id.chipShopping -> selectedCategory = getString(R.string.chipShopping)
                    R.id.chipBill -> selectedCategory = getString(R.string.chipBill)
                    R.id.chipSelfDevelopment -> selectedCategory = getString(R.string.chipSelfDevelopment)
                    R.id.chipTransportationFee -> selectedCategory = getString(R.string.chipTransportationFee)
                    R.id.chipEntertainment -> selectedCategory = getString(R.string.chipEntertainment)
                    R.id.chipHealth -> selectedCategory = getString(R.string.chipHealth)
                    R.id.chipHoliday -> selectedCategory = getString(R.string.chipHoliday)
                    R.id.chipKids -> selectedCategory = getString(R.string.chipKids)
                    R.id.chipOther -> selectedCategory = getString(R.string.chipOther)
                }

                var enteredAmount =
                    if (editTextAmount.text.isNullOrEmpty()) {
                        0.0
                    } else {
                        if (selectedType == getString(R.string.radioButtonIncome)) editTextAmount.text.toString().toDouble()
                        else ("-" + editTextAmount.text.toString()).toDouble()
                    }

                var enteredDescription = editTextDescription.text.toString()

                hideKeyboard()
                if (enteredAmount == 0.0 || selectedType == "" || selectedCategory == "") {
                    showSnackbar(view, getString(R.string.fillRequiredFields))
                } else {
                    buttonSaveClick(
                        selectedType,
                        selectedDate,
                        enteredAmount,
                        selectedCategory,
                        enteredDescription
                    )
                    editTextAmount.text.clear()
                    radioGroup.clearCheck()
                    chipGroupExpense.clearCheck()
                    chipGroupIncome.clearCheck()
                    chipGroupExpense.visibility = View.GONE
                    chipGroupIncome.visibility = View.GONE
                    editTextDescription.text.clear()
                    selectedDate = "$day/${month + 1}/$year"
                    tvDate.text = selectedDate
                    editTextMoneyboxNewAmount.text?.clear()
                    showSnackbar(view, getString(R.string.savedNewTransaction))
                }
            }

            buttonSaveMoneybox.setOnClickListener {
                hideKeyboard()

                if (editTextMoneyboxName.text!!.isEmpty() || editTextMoneyboxAmount.text!!.isEmpty()) {
                    showSnackbar(view, getString(R.string.fillRequiredFields))
                } else {
                    sharedPref.edit()
                        .putString(PREF_KEY_MONEYBOX_NAME, editTextMoneyboxName.text.toString())
                        .apply()
                    sharedPref.edit()
                        .putString(PREF_KEY_MONEYBOX_AMOUNT, editTextMoneyboxAmount.text.toString())
                        .apply()
                    sharedPref.edit().putString(PREF_KEY_MONEYBOX_NEW_AMOUNT, "0").apply()

                    editTextAmount.text.clear()
                    radioGroup.clearCheck()
                    chipGroupExpense.clearCheck()
                    chipGroupIncome.clearCheck()
                    chipGroupExpense.visibility = View.GONE
                    chipGroupIncome.visibility = View.GONE
                    editTextDescription.text.clear()
                    selectedDate = "$day/${month + 1}/$year"
                    tvDate.text = selectedDate
                    editTextMoneyboxNewAmount.text?.clear()
                    buttonAddMoneybox.visibility = View.GONE
                    buttonSaveMoneybox.visibility = View.GONE
                    textInputLayoutMoneyboxName.visibility = View.GONE
                    textInputLayoutMoneyboxAmount.visibility = View.GONE
                    textInputLayoutMoneyboxNewPrice.visibility = View.GONE
                    showSnackbar(view, getString(R.string.savedNewMoneybox))
                }
            }

            buttonAddMoneybox.setOnClickListener {

                hideKeyboard()

                if (editTextMoneyboxNewAmount.text!!.isEmpty()) {
                    showSnackbar(view, getString(R.string.fillRequiredFields))
                } else {
                    var enteredNewAmount = editTextMoneyboxNewAmount.text.toString().toInt()
                    sharedPref.getString(PREF_KEY_MONEYBOX_NEW_AMOUNT, "0")?.toInt()?.let {
                        sharedPref.edit().putString(
                            PREF_KEY_MONEYBOX_NEW_AMOUNT,
                            (it + enteredNewAmount).toString()
                        ).apply()
                    }
                    editTextMoneyboxNewAmount.text?.clear()
                    editTextAmount.text.clear()
                    radioGroup.clearCheck()
                    chipGroupExpense.clearCheck()
                    chipGroupIncome.clearCheck()
                    chipGroupExpense.visibility = View.GONE
                    chipGroupIncome.visibility = View.GONE
                    editTextDescription.text.clear()
                    selectedDate = "$day/${month + 1}/$year"
                    tvDate.text = selectedDate
                    buttonAddMoneybox.visibility = View.GONE
                    buttonSaveMoneybox.visibility = View.GONE
                    textInputLayoutMoneyboxName.visibility = View.GONE
                    textInputLayoutMoneyboxAmount.visibility = View.GONE
                    textInputLayoutMoneyboxNewPrice.visibility = View.GONE
                    showSnackbar(view, getString(R.string.addedNewAmount))
                }
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

    companion object {
        const val PREFS_FILENAME = "com.amttech.budgettracker_whereismymoney.prefs"
        const val PREF_KEY_MONEYBOX_NAME = "moneyboxName"
        const val PREF_KEY_MONEYBOX_AMOUNT = "moneyboxAmount"
        const val PREF_KEY_MONEYBOX_NEW_AMOUNT = "moneyboxNewAmount"
    }
}