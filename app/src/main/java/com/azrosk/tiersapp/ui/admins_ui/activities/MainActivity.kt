package com.azrosk.tiersapp.ui.admins_ui.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.azrosk.tiersapp.helper.Constants
import com.azrosk.tiersapp.R
import com.azrosk.tiersapp.databinding.ActivityMainBinding
import com.azrosk.tiersapp.sharedpref.MySharedPreferences
import com.azrosk.tiersapp.ui.clients_ui.actitvity.ClientsActivity
import com.azrosk.tiersapp.ui.masters_ui.MastersActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    private var _binding : ActivityMainBinding?=null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val sp = MySharedPreferences(this)
        val lang = sp.getLanguage()
        val config = resources.configuration
        val locale = Locale(lang!!)
        Locale.setDefault(locale)
        config.setLocale(locale)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            createConfigurationContext(config)
        resources.updateConfiguration(config, resources.displayMetrics)
        setContentView(binding.root)

        sp.isFirstTimeLoad(this)
        val userType = sp.getLoggedInBy()
        if (userType == Constants.CLIENT){
            val intent = Intent(this, ClientsActivity::class.java)
            startActivity(intent)
            finish()
        }
        else if (userType == Constants.MASTER){
            val intent = Intent(this, MastersActivity::class.java)
            startActivity(intent)
            finish()
        }

        setBottomNavBar()
    }

    private fun setBottomNavBar() {
        val navController = findNavController(R.id.nav_host)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navHome, R.id.navOrders, R.id.navAdminLocation
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}