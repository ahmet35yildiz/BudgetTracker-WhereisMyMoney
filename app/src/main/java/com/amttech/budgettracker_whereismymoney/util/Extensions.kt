package com.amttech.budgettracker_whereismymoney.util

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun showSnackbar(view: View, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
}