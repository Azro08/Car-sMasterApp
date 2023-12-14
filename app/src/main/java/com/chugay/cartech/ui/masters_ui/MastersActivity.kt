package com.chugay.cartech.ui.masters_ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import com.chugay.cartech.R
import com.chugay.cartech.databinding.ActivityMastersBinding
import com.chugay.cartech.sharedpref.MySharedPreferences
import com.chugay.cartech.ui.shared.lang.LanguageDialogFragment
import com.chugay.cartech.ui.shared.login.LoginActivity

class MastersActivity : AppCompatActivity() {
    private var _binding: ActivityMastersBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMastersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setMenu()

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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}