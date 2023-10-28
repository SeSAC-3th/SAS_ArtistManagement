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

/**
* MainActivity
*
* @fileName             :MainActivity.kt
* @auther               :윤성욱, 이종윤, 박지혜
* @since                :2023-10-28
**/
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var navController: NavController

    /**
     * Androidx SplashScreen compat 라이브러리를 활용하여 SplashScreen 구현
     * @author 이종윤
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(binding.root)
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        setupJetpackNavigation(sharedPref.getBoolean(resources.getString(R.string.auto_login), false))
    }

    /**
     * 자동로그인 Preference값에 따른 네비게이션바 시작점 세팅
     *
     * @param autoLogin 자동로그인 값
     * @author 박지혜, 윤성욱
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
        //네비게이션 바가 보이는 곳과 보이지 않을 곳 설정
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