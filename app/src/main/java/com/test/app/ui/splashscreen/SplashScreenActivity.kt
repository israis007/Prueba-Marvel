package com.test.app.ui.splashscreen

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.test.app.R
import com.test.app.databinding.ActivitySplashScreenBinding
import com.test.app.ui.base.ActivityBase
import com.test.app.ui.mainscreen.MainActivityView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreenActivity : ActivityBase() {

    private val viewModel: SplashScreenViewModel by viewModels()
    private val binding: ActivitySplashScreenBinding by lazy {
        DataBindingUtil.setContentView(this@SplashScreenActivity, R.layout.activity_splash_screen)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(binding.root)
        super.onCreate(savedInstanceState)
    }

    override fun initUI() {
        binding.apply {
            viewModel = this@SplashScreenActivity.viewModel
            lifecycleOwner = this@SplashScreenActivity
        }
    }

    override fun setListeners() {
        binding.actLavSplash.addAnimatorListener(viewModel.createAnimationListener())
    }

    override fun setObservers() {
        viewModel.animationFinished.observe(this) {
            val finished = it ?: return@observe
            if (finished) {
                launchActivity(MainActivityView::class.java)
                finish()
            }
        }
    }
}