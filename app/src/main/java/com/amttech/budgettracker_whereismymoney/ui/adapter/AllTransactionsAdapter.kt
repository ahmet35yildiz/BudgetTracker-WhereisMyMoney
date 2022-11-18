package com.amttech.budgettracker_whereismymoney.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.amttech.budgettracker_whereismymoney.R
import com.amttech.budgettracker_whereismymoney.data.entity.Transactions
import com.amttech.budgettracker_whereismymoney.databinding.CardDesignAllTransactionsBinding

class AllTransactionsAdapter(
    private var allTransactionsList: List<Transactions>,
    private var onItemClick: (Transactions) -> Unit
) :
    RecyclerView.Adapter<AllTransactionsAdapter.CardDesignHolder>() {
    inner class CardDesignHolder(val binding: CardDesignAllTransactionsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(allTransactions: Transactions) {

            with(binding) {

                allTransactionsObject = allTransactions

                imageButtonDelete.setOnClickListener {
                    onItemClick(allTransactions)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardDesignHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: CardDesignAllTransactionsBinding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.card_design_all_transactions,
                parent,
                false
            )
        return CardDesignHolder(binding)
    }

    override fun onBindViewHolder(holder: CardDesignHolder, position: Int) =
        holder.bind(allTransactionsList[position])

    override fun getItemCount() = allTransactionsList.size
}