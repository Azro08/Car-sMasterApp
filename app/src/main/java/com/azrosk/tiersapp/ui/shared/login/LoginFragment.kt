package com.azrosk.tiersapp.ui.shared.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.azrosk.tiersapp.R
import com.azrosk.tiersapp.databinding.FragmentLoginBinding
import com.azrosk.tiersapp.helper.Constants
import com.azrosk.tiersapp.model.Admin
import com.azrosk.tiersapp.model.Client
import com.azrosk.tiersapp.model.Master
import com.azrosk.tiersapp.sharedpref.MySharedPreferences
import com.azrosk.tiersapp.ui.admins_ui.activities.MainActivity
import com.azrosk.tiersapp.ui.clients_ui.actitvity.ClientsActivity
import com.azrosk.tiersapp.ui.masters_ui.MastersActivity
import com.azrosk.tiersapp.ui.shared.login.admin_viewmodel.AdminViewModel
import com.azrosk.tiersapp.ui.shared.login.clients_viewmodel.ClientsViewModel
import com.azrosk.tiersapp.ui.shared.signup.SignupActivity

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AdminViewModel
    private lateinit var clientViewModel: ClientsViewModel
    private val spinnerOptions = arrayOf("Admin", "User", "Master")
    private val admin = Admin(email = "admin@admin.com", password = "admin")
    private var userIsFound = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity())[AdminViewModel::class.java]
        clientViewModel = ViewModelProvider(requireActivity())[ClientsViewModel::class.java]

        val myAdapter = ArrayAdapter(
            requireContext(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            spinnerOptions
        )
        binding.spinnerLoginAs.adapter = myAdapter

        //if default admin account doesn't exist create new one
        addDefaultAdmin()

        setMenu()

        binding.btnLogin.setOnClickListener {
            login()
        }

        binding.tvReg.setOnClickListener {
            val intent = Intent(requireContext(), SignupActivity::class.java)
            startActivity(intent)
        }

    }

    private fun setMenu() {
        val sp = MySharedPreferences(requireContext())
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.login_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.itemLoginLang -> {
                        findNavController().navigate(R.id.nav_login_lang)
                    }
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun addDefaultAdmin() {
        val sp = MySharedPreferences(requireContext())
        val adminExists = sp.adminExists()

        if (adminExists == 0) {
            viewModel.addAdmin(admin)
            sp.saveAdmin(1)
        }

    }

    private fun login() {

        when {
            TextUtils.isEmpty(binding.etLoginEmail.text.toString().trim { it <= ' ' }) -> {
                binding.etLoginEmail.error = "Enter your email"
                //                Toast.makeText(this, "Please enter email.", Toast.LENGTH_LONG).show()
            }

            TextUtils.isEmpty(binding.etLoginPassword.text.toString().trim { it <= ' ' }) -> {
                binding.etLoginPassword.error = "Enter your password"
                Toast.makeText(requireContext(), "Please enter password.", Toast.LENGTH_LONG).show()
            }

            else -> {
                val email = binding.etLoginEmail.text.toString()
                val password = binding.etLoginPassword.text.toString()
                if (binding.spinnerLoginAs.selectedItem.toString() == "Admin") {
                    viewModel.readAllAdmins.observe(requireActivity()) {
                        loginAsAdmin(it, email, password)
                    }

                } else if (binding.spinnerLoginAs.selectedItem.toString() == "User") {
                    clientViewModel.readAllClients.observe(requireActivity()) {
                        loginAsClient(it, email, password)
                    }
                } else if (binding.spinnerLoginAs.selectedItem.toString() == "Master") {
                    viewModel.readAllMasters.observe(requireActivity()) {
                        loginAsMaster(it, email, password)
                    }
                } else {
                    Toast.makeText(requireContext(), "something went wrong! ", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }

    }

    private fun loginAsMaster(masterList: List<Master>?, email: String, password: String) {
        Log.d("MasterDetails", masterList.toString())

        for (master in masterList!!) {
            Log.d("MasterDetails", master.toString())
            if (master.email == email && master.password == password) {
                userIsFound = 1
                val sp = MySharedPreferences(requireContext())
                sp.saveEmail(email)
                sp.saveUserType(Constants.MASTER)

                val intent = Intent(requireContext(), MastersActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }
        if (userIsFound == 0) Toast.makeText(
            requireContext(),
            "master doesn't exist or wrong password!!",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun loginAsAdmin(i: Admin, email: String, password: String) {
        val sp = MySharedPreferences(requireContext())
        if (i.email == email && i.password == password) {
            userIsFound = 1
            sp.saveEmail(email)
            sp.saveUserType(Constants.ADMIN)
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        } else {
            if (sp.getEmail() == "empty") {
                Toast.makeText(
                    requireContext(),
                    "admin doesn't exist or wrong password!!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }

    private fun loginAsClient(list: List<Client>, email: String, password: String) {
        for (i in list) {
            if (i.email == email && i.password == password) {
                userIsFound = 1
                val sp = MySharedPreferences(requireContext())
                sp.saveEmail(email)
                sp.saveUserType(Constants.CLIENT)

                val intent = Intent(requireContext(), ClientsActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }
        if (userIsFound == 0) Toast.makeText(
            requireContext(),
            "client doesn't exist or wrong password!!",
            Toast.LENGTH_LONG
        ).show()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}