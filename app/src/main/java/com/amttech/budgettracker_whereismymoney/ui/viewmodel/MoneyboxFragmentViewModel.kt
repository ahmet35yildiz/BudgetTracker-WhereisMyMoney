package com.amttech.budgettracker_whereismymoney.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.amttech.budgettracker_whereismymoney.data.repo.TransactionsDaoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoneyboxFragmentViewModel @Inject constructor(private var btRepo: TransactionsDaoRepository) :
    ViewModel() {
    var allTransactionsList = btRepo.transactionsList
}