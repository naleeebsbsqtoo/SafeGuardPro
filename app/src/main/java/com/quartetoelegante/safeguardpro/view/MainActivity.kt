package com.quartetoelegante.safeguardpro.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.quartetoelegante.safeguardpro.R
import com.quartetoelegante.safeguardpro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //criar a toolbar
    private lateinit var appBarConfiguration: AppBarConfiguration

    //criar a navegaçao
    private lateinit var navController: NavController

    //criar o binding
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = _binding!!

    val menuScreens = listOf(R.id.inicialFragment, R.id.funcionariosFragment, R.id.inventarioFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //configura o binding
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //configura a navegaçao e a toolbar
        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        binding.bottomNavigation.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, _ ->
            controller.currentDestination?.let {
                if (it.id == R.id.loginFragment){
                    binding.bottomNavigation.visibility = View.GONE
                } else{
                    binding.bottomNavigation.visibility = View.VISIBLE
                }

                if (it.id in menuScreens){
                    destination.parent?.setStartDestination(it.id)
                }
            }
        }
    }
}