package com.azrosk.tiersapp.ui.masters_ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.azrosk.tiersapp.R
import com.azrosk.tiersapp.databinding.ActivityMastersBinding
import com.azrosk.tiersapp.model.Master
import com.azrosk.tiersapp.model.Work
import com.azrosk.tiersapp.sharedpref.MySharedPreferences
import com.azrosk.tiersapp.ui.shared.lang.LanguageDialogFragment
import com.azrosk.tiersapp.ui.shared.login.LoginActivity

class MastersActivity : AppCompatActivity() {
    private var _binding: ActivityMastersBinding? = null
    private val binding get() = _binding!!
    lateinit var mastersViewModel: MastersViewModel
    private val worksList = mutableListOf<Work>()
    private var rvAdapter: WorksRvAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMastersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setMenu()
        mastersViewModel = ViewModelProvider(this)[MastersViewModel::class.java]
        getMaster()

    }

    private fun setMenu() {
        val sp = MySharedPreferences(this)
        this.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.logout_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.item_logout -> {
                        sp.removeUserEmail()
                        val intent = Intent(this@MastersActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                    R.id.clientItemLang -> {
                        val dialogLanguage = LanguageDialogFragment()
                        dialogLanguage.show(supportFragmentManager, "Language")
                    }
                }
                return true
                // Handle option Menu Here
            }
        }, this, Lifecycle.State.RESUMED)
    }

    private fun getMaster() {
        val sp = MySharedPreferences(this)
        val email = sp.getEmail() ?: ""
        mastersViewModel.getMasterById(email)
        mastersViewModel.readMaster.observe(this) { master ->
            if (master != null) getMasterWork(master)
            else Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getMasterWork(master: Master) {
        mastersViewModel.readAllOrders.observe(this) { orders ->
            for (order in orders) {
                if (order.picked_master == master.name) worksList.add(
                    Work(
                        order.order_time,
                        order.service_title
                    )
                )
            }
            rvAdapter = WorksRvAdapter(worksList)
            binding.rvWorks.setHasFixedSize(true)
            binding.rvWorks.layoutManager = LinearLayoutManager(this)
            binding.rvWorks.adapter = rvAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        rvAdapter = null
    }

}