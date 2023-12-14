package com.chugay.cartech.ui.clients_ui.actitvity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.chugay.cartech.R
import com.chugay.cartech.databinding.ActivityClientsBinding
import com.chugay.cartech.sharedpref.MySharedPreferences
import java.util.*

class ClientsActivity : AppCompatActivity() {
    private var _binding : ActivityClientsBinding?=null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityClientsBinding.inflate(layoutInflater)
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

        setBottomNavBar()

    }

    private fun setBottomNavBar() {
        val navController = findNavController(R.id.nav_host_clients)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navClientsHome, R.id.navClientsOrders, R.id.navClientsLocation
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navViewClients.setupWithNavController(navController)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}