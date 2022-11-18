package com.amttech.budgettracker_whereismymoney.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.amttech.budgettracker_whereismymoney.data.entity.Transactions
import com.amttech.budgettracker_whereismymoney.data.repo.TransactionsDaoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllTransactionsFragmentViewModel @Inject constructor(private var btRepo: TransactionsDaoRepository) :
    ViewModel() {
    var allTransactionsList = btRepo.transactionsList

    init {
        installAllTransactions()
    }

    fun installAllTransactions() {
        btRepo.showTransactions()
        allTransactionsList = btRepo.transactionsList
    }

    fun delete(transaction: Transactions) {
        btRepo.deleteTransaction(transaction)
    }

    fun search(searchWord: String) {
        btRepo.searchTransaction(searchWord)
    }
}