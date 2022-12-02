package com.amttech.budgettracker_whereismymoney.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.amttech.budgettracker_whereismymoney.data.entity.Transactions

@Dao
interface TransactionsDao {
    @Query("SELECT * FROM transactions")
    suspend fun getTransactions(): List<Transactions>

    @Insert
    suspend fun addTransaction(transaction: Transactions)

    @Delete
    suspend fun deleteTransaction(transaction: Transactions)

    @Query(
        "SELECT * FROM transactions WHERE transaction_category like '%' || :searchWord || '%' " +
                "OR transaction_description like '%' || :searchWord || '%' " +
                "OR transaction_type like '%' || :searchWord || '%' " +
                "OR transaction_date like '%' || :searchWord || '%' " +
                "OR transaction_amount like '%' || :searchWord || '%'"
    )
    suspend fun searchTransaction(searchWord: String): List<Transactions>
}