package com.sas.companymanagement.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.sas.companymanagement.R
import com.sas.companymanagement.databinding.ActivityMainBinding

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
        setupJetpackNavigation(sharedPref.getBoolean(resources.getString(R.string.auto_login), false))
    }

    /**
     * Setup jetpack navigation
     *  자동로그인 결과값에 따른 네비게이션 세팅
     * @param autoLogin
     */
    private fun setupJetpackNavigation(autoLogin: Boolean) {
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.bottom_nav_host_fragment) as NavHostFragment? ?: return
        navController = host.navController
        binding.bottomNavigationView.setupWithNavController(navController)
        val navGraph = navController.navInflater.inflate(R.navigation.bottom_nav_graph)
        if (autoLogin) navGraph.setStartDestination(R.id.fragment_main)
        else navGraph.setStartDestination(R.id.fragment_login)
        navController.setGraph(navGraph, null)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination == navController.findDestination(R.id.fragment_main) ||
                destination == navController.findDestination(R.id.fragment_artist) ||
                destination == navController.findDestination(R.id.fragment_schedule) ||
                destination == navController.findDestination(R.id.fragment_group) ||
                destination == navController.findDestination(R.id.fragment_setting)
            ) {
                binding.bottomNavigationView.visibility = View.VISIBLE
            } else binding.bottomNavigationView.visibility = View.GONE
        }
    }
}