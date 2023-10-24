package com.sas.companymanagement.ui

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.sas.companymanagement.R
import com.sas.companymanagement.databinding.ActivityMainBinding
import com.sas.companymanagement.databinding.FragmentArtistBinding
import com.sas.companymanagement.databinding.FragmentArtistUpdateBinding
import com.sas.companymanagement.databinding.FragmentGroupBinding
import com.sas.companymanagement.databinding.FragmentGroupUpdateBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        setContentView(binding.root)

        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        if (sharedPref.getBoolean("autoLogin",false)){
            setupJetpackNavigation()
        }else{

        }


    }

    private fun setupJetpackNavigation() {
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.bottom_nav_host_fragment) as NavHostFragment? ?: return
        navController = host.navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}