package com.example.rtotest

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.rtotest.fragments.DashboardFragment
import com.example.rtotest.fragments.HomeFragment
import com.example.rtotest.fragments.PracticeFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var homeFragment: Fragment
    private lateinit var practiceFragment: PracticeFragment
    private lateinit var dashboardFragment: DashboardFragment

    private lateinit var bottomNavBar: BottomNavigationView
    private lateinit var navController: NavController
    private lateinit var toolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        homeFragment = HomeFragment()
        practiceFragment = PracticeFragment()
        dashboardFragment = DashboardFragment()

        bottomNavBar = findViewById(R.id.bottom_nav_bar)
        toolbar = findViewById(R.id.topAppBar_toolbar)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment)
                as NavHostFragment
        navController = navHostFragment.findNavController()

        appBarConfiguration = AppBarConfiguration(
                setOf(R.id.homeFragment , R.id.practiceFragment, R.id.dashboardFragment)
        )

        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController , appBarConfiguration)

        bottomNavBar.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> showBottomNav()
                R.id.practiceFragment -> showBottomNav()
                R.id.dashboardFragment -> showBottomNav()
                else -> hideBottomNav()
            }
        }
    }

    private fun showBottomNav() {
        bottomNavBar.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        bottomNavBar.visibility = View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}