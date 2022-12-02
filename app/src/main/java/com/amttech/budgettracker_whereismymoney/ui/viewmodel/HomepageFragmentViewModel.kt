package com.amttech.budgettracker_whereismymoney.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amttech.budgettracker_whereismymoney.data.entity.Transactions
import com.amttech.budgettracker_whereismymoney.data.repo.TransactionsDaoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomepageFragmentViewModel @Inject constructor(private var btRepo: TransactionsDaoRepository) :
    ViewModel() {

    var lastTransactionsList = MutableLiveData<List<Transactions>>()
    var lastTransactionsListMonth = MutableLiveData<List<Transactions>>()
    var lastTransactionsListYear = MutableLiveData<List<Transactions>>()

    init {
        installLastTransactions()
    }

    fun installLastTransactions() {
        btRepo.showTransactions()
        lastTransactionsList = btRepo.transactionsList
        lastTransactionsListMonth = btRepo.transactionsListMonthly
        lastTransactionsListYear = btRepo.transactionsListYearly
    }
}
