package com.test.app.ui.mainscreen.fragments.ubicationregister

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.test.app.R
import com.test.app.databinding.FragmentUbicationRegisterBinding
import com.test.app.ui.base.FragmentBase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UbicationRegisterFragment : FragmentBase() {

    private val binding: FragmentUbicationRegisterBinding by lazy {
        DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.fragment_ubication_register, null, false)
    }

    private val vm: UbicationRegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.apply {
            viewModel = vm
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun setListeners() {
        
    }

    override fun setObservers() {
        
    }

    override fun cleanFields() {
        
    }

    override fun removeObservers() {
        
    }

    override fun initViewComponents() {
        
    }

    override fun changeToolbarParams() {
        
    }
}