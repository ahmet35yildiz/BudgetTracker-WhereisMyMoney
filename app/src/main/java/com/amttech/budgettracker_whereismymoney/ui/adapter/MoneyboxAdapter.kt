package com.amttech.budgettracker_whereismymoney.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.amttech.budgettracker_whereismymoney.R
import com.amttech.budgettracker_whereismymoney.data.entity.Transactions
import com.amttech.budgettracker_whereismymoney.databinding.CardDesignMoneyboxBinding

class MoneyboxAdapter(private var moneyboxList: List<Transactions>) :
    RecyclerView.Adapter<MoneyboxAdapter.CardDesignHolder>() {

    inner class CardDesignHolder(val binding: CardDesignMoneyboxBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(moneybox: Transactions) {

            with(binding) {

                moneyboxObject = moneybox
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardDesignHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: CardDesignMoneyboxBinding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.card_design_moneybox,
                parent,
                false
            )
        return CardDesignHolder(binding)
    }

    override fun onBindViewHolder(holder: CardDesignHolder, position: Int) =
        holder.bind(moneyboxList[position])

    override fun getItemCount(): Int = moneyboxList.size
}