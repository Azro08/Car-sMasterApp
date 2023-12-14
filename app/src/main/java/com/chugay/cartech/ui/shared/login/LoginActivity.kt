package com.chugay.cartech.ui.shared.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chugay.cartech.databinding.ActivityLoginBinding
import com.chugay.cartech.sharedpref.MySharedPreferences
import java.util.*

class LoginActivity : AppCompatActivity() {
    private var _binding : ActivityLoginBinding?=null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sp = MySharedPreferences(this)
        val lang = sp.getLanguage()
        val config = resources.configuration
        val locale = Locale(lang!!)
        Locale.setDefault(locale)
        config.setLocale(locale)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}