package com.amttech.budgettracker_whereismymoney.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.amttech.budgettracker_whereismymoney.R
import com.amttech.budgettracker_whereismymoney.data.entity.Transactions
import com.amttech.budgettracker_whereismymoney.databinding.CardDesignLastTransactionsBinding

class LastTransactionsAdapter(private var lastTransactionsList: List<Transactions>) :
    RecyclerView.Adapter<LastTransactionsAdapter.CardDesignHolder>() {

    inner class CardDesignHolder(val binding: CardDesignLastTransactionsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(lastTransactions: Transactions) {

            with(binding) {

                lastTransactionsObject = lastTransactions
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardDesignHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: CardDesignLastTransactionsBinding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.card_design_last_transactions,
                parent,
                false
            )
        return CardDesignHolder(binding)
    }

    override fun onBindViewHolder(holder: CardDesignHolder, position: Int) =
        holder.bind(lastTransactionsList[position])

    override fun getItemCount(): Int = lastTransactionsList.size
}
