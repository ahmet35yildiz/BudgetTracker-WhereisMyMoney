package com.amttech.budgettracker_whereismymoney.data.entity

data class Transactions(var transactionId : Int,
                        var transactionType: String,
                        var transactionDate: String,
                        var transactionAmount: Double,
                        var transactionCategory: String,
                        var transactionDescription: String) {
}