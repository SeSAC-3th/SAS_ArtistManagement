package com.sas.companymanagement.ui

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
        setupJetpackNavigation()
        setAutoLoginDestination(sharedPref.getBoolean("autoLogin", false))
    }

    private fun setupJetpackNavigation() {
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.bottom_nav_host_fragment) as NavHostFragment? ?: return
        navController = host.navController
        binding.bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination == navController.findDestination(R.id.fragment_login)){
                binding.bottomNavigationView.visibility = View.GONE
            }else  binding.bottomNavigationView.visibility = View.VISIBLE
        }

    }

    /**
     * Set auto login destination
     *  자동로그인에 따라 시작하는 프래그먼트를 설정
     * @param autoLogin
     */
    private fun setAutoLoginDestination(autoLogin: Boolean) {
        val navGraph = navController.navInflater.inflate(R.navigation.bottom_nav_graph)
        if (autoLogin) navGraph.setStartDestination(R.id.fragment_main)
        else navGraph.setStartDestination(R.id.fragment_login)
        navController.setGraph(navGraph, null)
    }
}