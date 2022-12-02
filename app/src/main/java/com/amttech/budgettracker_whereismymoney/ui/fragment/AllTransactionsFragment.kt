package com.amttech.budgettracker_whereismymoney.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.amttech.budgettracker_whereismymoney.R
import com.amttech.budgettracker_whereismymoney.data.entity.Transactions
import com.amttech.budgettracker_whereismymoney.databinding.FragmentAllTransactionsBinding
import com.amttech.budgettracker_whereismymoney.ui.adapter.AllTransactionsAdapter
import com.amttech.budgettracker_whereismymoney.ui.viewmodel.AllTransactionsFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllTransactionsFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentAllTransactionsBinding
    private val viewModel: AllTransactionsFragmentViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_all_transactions, container, false)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarAllTransactions)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){

            radioButtonAllTransactions.setOnClickListener {
                observeAllTransactions()
            }

            radioButtonYearlyTransactions.setOnClickListener {
                observeYearlyTransactions()
            }

            radioButtonMonthlyTransactions.setOnClickListener {
                observeMonthlyTransactions()
            }
        }
    }

    private fun observeAllTransactions(){
        viewModel.allTransactionsList.observe(viewLifecycleOwner) {
            setupRV(it)
        }
    }

    private fun observeYearlyTransactions(){
        viewModel.transactionsListYear.observe(viewLifecycleOwner){
            setupRV(it)
        }
    }

    private fun observeMonthlyTransactions(){
        viewModel.transactionsListMonth.observe(viewLifecycleOwner){
            setupRV(it)
        }
    }

    private fun setupRV(requireTransactionsList: List<Transactions>) {
        with(binding) {
            allTransactionsFragment = this@AllTransactionsFragment

            val adapter = AllTransactionsAdapter(requireTransactionsList) {
                deleteSelectedTransaction(
                    Transactions(
                        it.transactionId,
                        it.transactionType,
                        it.transactionDate,
                        it.transactionAmount,
                        it.transactionCategory,
                        it.transactionDescription
                    )
                )
            }
            allTransactionsAdapter = adapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        viewModel.search(query)
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        viewModel.search(newText)
        return true
    }

    override fun onResume() {
        super.onResume()
        viewModel.installAllTransactions()
    }

    private fun deleteSelectedTransaction(transaction: Transactions) {
        Snackbar.make(
            requireView(),
            getText(R.string.deleteTransaction),
            Snackbar.LENGTH_SHORT
        ).setAction(getText(R.string.yes)) {
            viewModel.delete(transaction)
        }.show()
    }
}