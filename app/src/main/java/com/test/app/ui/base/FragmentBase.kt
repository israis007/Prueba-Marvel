package com.test.app.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class FragmentBase : Fragment() {

    abstract fun initUI()

    abstract fun setListeners()

    abstract fun setObservers()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        setListeners()
        setObservers()
    }
}