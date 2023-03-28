package com.azrosk.tiersapp.ui.shared.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.azrosk.tiersapp.helper.Constants
import com.azrosk.tiersapp.databinding.ActivityLoginBinding
import com.azrosk.tiersapp.model.Admin
import com.azrosk.tiersapp.model.Client
import com.azrosk.tiersapp.ui.shared.login.admin_viewmodel.AdminViewModel
import com.azrosk.tiersapp.ui.shared.login.clients_viewmodel.ClientsViewModel
import com.azrosk.tiersapp.sharedpref.MySharedPreferences
import com.azrosk.tiersapp.ui.admins_ui.activities.MainActivity
import com.azrosk.tiersapp.ui.clients_ui.actitvity.ClientsActivity
import com.azrosk.tiersapp.ui.shared.signup.SignupActivity
import java.util.*
import kotlin.collections.ArrayList

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