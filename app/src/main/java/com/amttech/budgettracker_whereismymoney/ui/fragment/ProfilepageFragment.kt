package com.amttech.budgettracker_whereismymoney.ui.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.recreate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.amttech.budgettracker_whereismymoney.R
import com.amttech.budgettracker_whereismymoney.databinding.FragmentProfilepageBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ProfilepageFragment : Fragment() {
    private lateinit var binding: FragmentProfilepageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profilepage, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            profilepageFragment = this@ProfilepageFragment

            loadLanguage()
        }
    }

    fun showLanguageDialog() {
        val languageList = arrayOf("English", "Türkçe")

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Choose Language")
        builder.setItems(languageList) {dialog, which ->
            when(which) {
                0 -> {
                    setLocale("en")
                    recreate(requireActivity())
                }
                1 -> {
                    setLocale("tr")
                    recreate(requireActivity())
                }
            }
            dialog.dismiss()
        }
        val dialog:AlertDialog = builder.create()
        dialog.show()
    }

private fun setLocale(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config:Configuration = Configuration()
        config.setLocale(locale)
        requireContext().resources.updateConfiguration(config, requireContext().resources.displayMetrics)
        val editor:SharedPreferences.Editor = requireContext().getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", language)
        editor.apply()
    }

    private fun loadLanguage() {
        val pref:SharedPreferences = requireContext().getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val language:String? = pref.getString("My_Lang", "")
        if (language != null) {
            setLocale(language)
        }
    }
}

