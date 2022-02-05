package com.wizeline.heroes

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.wizeline.heroes.databinding.ActivityMainBinding
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)

        val remoteConfig = FirebaseRemoteConfig.getInstance()
        val isEnabled = remoteConfig.getBoolean("isAppBlocked")
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(3600)
            .setFetchTimeoutInSeconds(60)
            .build()
        remoteConfig.setConfigSettingsAsync(configSettings)

        val isAppEnable = FirebaseRemoteConfig.getInstance().getBoolean("isAppBlocked")
        Toast.makeText(
            this, "Is enabled: $isEnabled, isAppEnabled: $isAppEnable",
            Toast.LENGTH_SHORT
        ).show()

//        remoteConfig.fetchAndActivate()
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    val updated = task.result
//                    Log.d("TAG", "Config params updated: $updated")
//                    Toast.makeText(
//                        this@MainActivity, "Fetch and activate succeeded",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                } else {
//                    Toast.makeText(
//                        this@MainActivity, "Fetch failed",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//                displayWelcomeMessage()
//            }


        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.catalogFragment, R.id.searchFragment
            )
        )
        findViewById<Toolbar>(R.id.toolbar)
            .setupWithNavController(navController, appBarConfiguration)

        binding.bottomNavView.setupWithNavController(navController)
    }

//    private fun displayWelcomeMessage() {
//        setTheme(R.style.AppTheme)
//    }
}
