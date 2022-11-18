package com.amttech.budgettracker_whereismymoney.data.repo

import androidx.lifecycle.MutableLiveData
import com.amttech.budgettracker_whereismymoney.data.entity.Transactions
import com.amttech.budgettracker_whereismymoney.room.TransactionsDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionsDaoRepository(var btDao: TransactionsDao) {
    var transactionsList = MutableLiveData<List<Transactions>>()

    fun showTransactions() {
        val job = CoroutineScope(Dispatchers.Main).launch() {
            transactionsList.value = btDao.getTransactions()
        }
    }

    fun saveNewTransactions(
        transactionType: String,
        transactionDate: String,
        transactionAmount: Double,
        transactionCategory: String,
        transactionDescription: String
    ) {
        val job = CoroutineScope(Dispatchers.Main).launch() {
            val newTransaction = Transactions(
                0,
                transactionType,
                transactionDate,
                transactionAmount,
                transactionCategory,
                transactionDescription
            )
            btDao.addTransaction(newTransaction)
        }
    }

    fun deleteTransaction(transaction: Transactions) {
        val job = CoroutineScope(Dispatchers.Main).launch() {
            val deletedTransaction = Transactions(
                transaction.transactionId,
                transaction.transactionType,
                transaction.transactionDate,
                transaction.transactionAmount,
                transaction.transactionCategory,
                transaction.transactionDescription
            )
            btDao.deleteTransaction(deletedTransaction)
            showTransactions()
        }
    }

    fun searchTransaction(searchWord: String) {
        val job = CoroutineScope(Dispatchers.Main).launch() {
            transactionsList.value = btDao.searchTransaction(searchWord)
        }
    }
}