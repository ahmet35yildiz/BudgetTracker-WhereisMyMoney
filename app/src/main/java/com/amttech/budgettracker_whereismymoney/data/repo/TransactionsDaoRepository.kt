package com.amttech.budgettracker_whereismymoney.data.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.amttech.budgettracker_whereismymoney.data.entity.Transactions
import com.amttech.budgettracker_whereismymoney.room.TransactionsDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class TransactionsDaoRepository(var btDao: TransactionsDao) {
    var transactionsList = MutableLiveData<List<Transactions>>()
    var transactionsListMonthly = MutableLiveData<List<Transactions>>()
    var transactionsListYearly = MutableLiveData<List<Transactions>>()

    private val calendar = Calendar.getInstance()
    private val currentMonth = calendar.get(Calendar.MONTH) + 1
    private val currentYear = calendar.get(Calendar.YEAR)

    fun showTransactions() {
        val job = CoroutineScope(Dispatchers.Main).launch() {
            transactionsList.value = btDao.getTransactions()
            val yearList = mutableListOf<Transactions>()
            for (i in transactionsList.value!!) {
                if (i.transactionDate.takeLast(4).toInt() == currentYear) {
                    yearList.add(i)
                }
            }
            transactionsListYearly.value = yearList

            val monthList = mutableListOf<Transactions>()
            for (i in transactionsList.value!!) {
                if (i.transactionDate.takeLast(4).toInt() == currentYear) {
                    if (i.transactionDate.substringAfter("/").substringBeforeLast("/").toInt() == currentMonth) {
                        Log.e("month", i.transactionDate.substringAfter("/").substringBeforeLast("/").toInt().toString()) //neden 2 kez basıyor?
                        monthList.add(i)
                    }
                }
            }
            transactionsListMonthly.value = monthList
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