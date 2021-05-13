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
import com.example.rtotest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val appBarConfiguration by lazy {
        AppBarConfiguration(
                setOf(R.id.homeFragment, R.id.practiceFragment,
                        R.id.examFragment, R.id.examScorecardFragment, R.id.examStatusFragment)
        )
    }
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.main_nav_host_fragment)
                as NavHostFragment
        navController = navHostFragment.findNavController()

        setSupportActionBar(binding.topAppBarToolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.bottomNavBar.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> showBottomNav()
                R.id.practiceFragment -> showBottomNav()
                else -> hideBottomNav()
            }
        }
    }

    private fun showBottomNav() {
        binding.bottomNavBar.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        binding.bottomNavBar.visibility = View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}