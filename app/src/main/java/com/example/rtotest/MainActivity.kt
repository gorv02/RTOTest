package com.example.rtotest

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val appBarConfiguration by lazy {
        AppBarConfiguration(
                setOf(R.id.homeFragment, R.id.practiceFragment,
                        R.id.examFragment, R.id.examScorecardFragment, R.id.examStatusFragment)
        )
    }

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.main_nav_host_fragment)
                as NavHostFragment
        navController = navHostFragment.findNavController()

        setSupportActionBar(topAppBar_toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        bottom_nav_bar.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> showBottomNav()
                R.id.practiceFragment -> showBottomNav()
                else -> hideBottomNav()
            }
        }
    }

    private fun showBottomNav() {
        bottom_nav_bar.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        bottom_nav_bar.visibility = View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}