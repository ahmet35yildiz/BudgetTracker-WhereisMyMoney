package com.amttech.budgettracker_whereismymoney.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity(tableName = "transactions")

data class Transactions(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "transaction_id") @NotNull var transactionId: Int,
    @ColumnInfo(name = "transaction_type") @NotNull var transactionType: String, //tekrar ediyor
    @ColumnInfo(name = "transaction_date") @NotNull var transactionDate: String,
    @ColumnInfo(name = "transaction_amount") @NotNull var transactionAmount: Double,
    @ColumnInfo(name = "transaction_category") @NotNull var transactionCategory: String, //tekrar ediyor
    @ColumnInfo(name = "transaction_description") @NotNull var transactionDescription: String) : Serializable {
}