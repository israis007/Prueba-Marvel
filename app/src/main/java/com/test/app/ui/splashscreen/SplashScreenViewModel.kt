package com.test.app.ui.splashscreen

import android.animation.Animator
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor() : ViewModel() {

    private val _animationFinised = MutableLiveData<Boolean>()
    val animationFinished: LiveData<Boolean> get() = _animationFinised


    fun createAnimationListener() = object : Animator.AnimatorListener{
        override fun onAnimationStart(p0: Animator?) {
            _animationFinised.postValue(false)
        }

        override fun onAnimationEnd(p0: Animator?) {
            _animationFinised.postValue(true)
        }

        override fun onAnimationCancel(p0: Animator?) {
            _animationFinised.postValue(true)
        }

        override fun onAnimationRepeat(p0: Animator?) {
            _animationFinised.postValue(false)
        }
    }

}