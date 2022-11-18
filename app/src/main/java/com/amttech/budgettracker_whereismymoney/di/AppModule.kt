package com.amttech.budgettracker_whereismymoney.di

import android.content.Context
import androidx.room.Room
import com.amttech.budgettracker_whereismymoney.data.repo.TransactionsDaoRepository
import com.amttech.budgettracker_whereismymoney.room.Database
import com.amttech.budgettracker_whereismymoney.room.TransactionsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

class AppModule {
    @Provides
    @Singleton
    fun provideBudgetDaoRepository(btDao: TransactionsDao): TransactionsDaoRepository {
        return TransactionsDaoRepository(btDao)
    }

    @Provides
    @Singleton
    fun provideTransactionsDao(@ApplicationContext context: Context): TransactionsDao {
        val db = Room.databaseBuilder(context, Database::class.java, "BudgetTracker.sqlite")
            .createFromAsset("BudgetTracker.sqlite").build()
        return db.getTransactionsDao()
    }
}