package com.example.gourmetsearcher

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.gourmetsearcher.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

/** メイン画面 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
    }

    /** ActionBarの設定 */
    private fun setupActionBar() {
        setSupportActionBar(binding.toolBar)

        val hostFragment = binding.navHostFragment.getFragment<NavHostFragment>()
        navController = hostFragment.navController

        val appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    /** ActionBarの戻るボタンを押したときの処理 */
    override fun onSupportNavigateUp(): Boolean = navController.navigateUp()
}
