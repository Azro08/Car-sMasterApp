package com.chugay.cartech.ui.shared.lang

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.chugay.cartech.R
import com.chugay.cartech.databinding.FragmentLanguageDialogBinding
import com.chugay.cartech.helper.Constants
import com.chugay.cartech.sharedpref.MySharedPreferences
import com.chugay.cartech.ui.admins_ui.activities.MainActivity
import com.chugay.cartech.ui.clients_ui.actitvity.ClientsActivity
import java.util.*

class LanguageDialogFragment : DialogFragment() {
    private var _binding : FragmentLanguageDialogBinding?=null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentLanguageDialogBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(this.activity)
        builder.run { setView(binding.root) }

        val sp = MySharedPreferences(requireContext())
        val savedLang = sp.getLanguage()
        var curLanguage = "en"

        val curUser = sp.getLoggedInBy()

        if (savedLang != null){
            curLanguage = savedLang
        }

        val languageList = arrayListOf("en", "ru")

        val myAdapter = ArrayAdapter(
            requireContext(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            languageList
        )
        binding.spinnerLanguages.adapter = myAdapter

        binding.btnSelectLang.setOnClickListener {
            if (curUser != null){
                setLocale(curLanguage, curUser)
            }
        }

        return builder.create()
    }

    private fun setLocale(curLanguage: String, curUser : String) {
        val locale = Locale(curLanguage)
        if (binding.spinnerLanguages.selectedItem != curLanguage){
            val sp = MySharedPreferences(requireContext())
            sp.saveLanguage(binding.spinnerLanguages.selectedItem.toString())
            if (curUser == Constants.ADMIN || curUser == "empty"){
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            } else if (curUser == Constants.CLIENT){
                val intent = Intent(requireContext(), ClientsActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        } else {
            Toast.makeText(requireContext(), R.string.language_already_picked, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}