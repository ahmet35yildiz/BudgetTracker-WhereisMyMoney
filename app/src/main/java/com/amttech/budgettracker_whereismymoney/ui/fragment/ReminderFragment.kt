package com.amttech.budgettracker_whereismymoney.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.amttech.budgettracker_whereismymoney.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReminderFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_reminder, container, false)
    }
}