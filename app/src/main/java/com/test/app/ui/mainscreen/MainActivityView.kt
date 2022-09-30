package com.test.app.ui.mainscreen

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.test.app.R
import com.test.app.databinding.ActivityMainV2Binding
import com.test.app.ui.base.ActivityBase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivityView : ActivityBase() {

    private val binding: ActivityMainV2Binding by lazy {
        DataBindingUtil.setContentView(this@MainActivityView, R.layout.activity_main_v2 )
    }
    private val vm: MainActivityViewModel by viewModels()
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            viewModel = vm
            lifecycleOwner = this@MainActivityView
        }
        setContentView(binding.root)
    }

    override fun initUI() {
        val navHostFragment = supportFragmentManager.findFragmentById(binding.navHostFragmentContainer.id) as NavHostFragment
        navController = navHostFragment.findNavController()
        navController.enableOnBackPressed(true)

        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.profileFragment, R.id.listMoviesFragment, R.id.ubicationListFragment, R.id.ubicationRegisterFragment, R.id.imageUploadFragment)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setupActionBarWithNavController(navController)
        }

        binding.bnvBar.setupWithNavController(navController)

    }

    override fun setListeners() {

    }

    override fun setObservers() {

    }
}
