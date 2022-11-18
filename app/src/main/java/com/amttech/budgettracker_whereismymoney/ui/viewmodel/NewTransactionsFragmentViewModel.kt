package com.amttech.budgettracker_whereismymoney.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.amttech.budgettracker_whereismymoney.data.repo.TransactionsDaoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewTransactionsFragmentViewModel @Inject constructor(private var btRepo: TransactionsDaoRepository) :
    ViewModel() {

    fun saveTransaction(
        transactionType: String,
        transactionDate: String,
        transactionAmount: Double,
        transactionCategory: String,
        transactionDescription: String
    ) {
        btRepo.saveNewTransactions(
            transactionType,
            transactionDate,
            transactionAmount,
            transactionCategory,
            transactionDescription
        )
    }
}