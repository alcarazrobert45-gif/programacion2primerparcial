package com.example.comercioesq.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.comercioesq.R
import com.example.comercioesq.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // R4: Inflar el layout usando ViewBinding y establecer la vista
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // R7: Configurar la Toolbar
        setSupportActionBar(binding.toolbar)

        // R8: Obtener el NavController
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController

        // R7, R8: Conectar BottomNavigation y Toolbar con el NavController
        NavigationUI.setupWithNavController(binding.bottomNav, navController)

        // Esto hace que el título cambie automáticamente
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    // R8: Manejar el botón de retroceso (Up) en la Toolbar
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}