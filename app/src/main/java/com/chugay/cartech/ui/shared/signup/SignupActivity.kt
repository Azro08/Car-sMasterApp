package com.chugay.cartech.ui.shared.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.chugay.cartech.helper.Constants
import com.chugay.cartech.databinding.ActivitySignupBinding
import com.chugay.cartech.model.Client
import com.chugay.cartech.sharedpref.MySharedPreferences
import com.chugay.cartech.ui.clients_ui.actitvity.ClientsActivity
import com.chugay.cartech.ui.shared.signup.viewmodel.SignUpViewModel
import java.util.*

class SignupActivity : AppCompatActivity() {
    private var _binding : ActivitySignupBinding?=null
    private val binding get() = _binding!!
    private lateinit var viewModel : SignUpViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sp = MySharedPreferences(this)
        val lang = sp.getLanguage()
        val config = resources.configuration
        val locale = Locale(lang!!)
        Locale.setDefault(locale)
        config.setLocale(locale)
        _binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[SignUpViewModel::class.java]

        binding.btnSignup.setOnClickListener {
            signup()
        }

        binding.tvLogin.setOnClickListener{
            onBackPressed()
        }
    }

    private fun signup() {

        when{
            TextUtils.isEmpty(binding.etSignupEmail.text.toString().trim{it <= ' '}) -> {
                binding.etSignupEmail.error = "Enter your email"
                //                Toast.makeText(this, "Please enter email.", Toast.LENGTH_LONG).show()
            }
            TextUtils.isEmpty(binding.etSignupPassword.text.toString().trim{it <= ' '}) -> {
                binding.etSignupPassword.error = "Enter your password"
                Toast.makeText(this, "Please enter password.", Toast.LENGTH_LONG).show()
            }

            else -> {
                val email = binding.etSignupEmail.text.toString()
                val password = binding.etSignupPassword.text.toString()
                viewModel.readAllClients.observe(this){
                    checkIfTaken(it, email, password)
                }
            }

        }

    }

    private fun checkIfTaken(list: List<Client>, email: String, password: String) {
        val sp = MySharedPreferences(this)
        var isTaken = 0
        if (list.isEmpty()){
            viewModel.addClient(Client(email = email, password = password))
            sp.saveEmail(email)
            sp.saveUserType(Constants.CLIENT)
            val intent = Intent(this, ClientsActivity::class.java)
            startActivity(intent)
            finish()
        } else{
            for (i in list){
                if (i.email == email){
                    isTaken = 1
                }
            }
            if (isTaken == 0){
                viewModel.addClient(Client(email = email, password = password))
                sp.saveEmail(email)
                sp.saveUserType(Constants.CLIENT)
                val intent = Intent(this, ClientsActivity::class.java)
                startActivity(intent)
                finish()
            } else if (isTaken == 1 && sp.getEmail() == "empty"){
                Toast.makeText(this, "user already exists", Toast.LENGTH_LONG).show()
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}