package com.example.rickmorty_mvvm_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.view.ActionMode
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.rickmorty_mvvm_app.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navView: BottomNavigationView= binding.navigationBar
        val navController = findNavController(R.id.nav_container_fragment)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_characters_fragment, R.id.navigation_locations_fragment
            )
        )
        setupActionBarWithNavController(navController,appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        super.onSupportNavigateUp()
        return findNavController(R.id.nav_container_fragment).navigateUp()

    }
}