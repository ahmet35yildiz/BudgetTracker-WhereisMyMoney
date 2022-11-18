package com.amttech.budgettracker_whereismymoney.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.amttech.budgettracker_whereismymoney.data.entity.Transactions

@Database(entities = [Transactions::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun getTransactionsDao(): TransactionsDao
}