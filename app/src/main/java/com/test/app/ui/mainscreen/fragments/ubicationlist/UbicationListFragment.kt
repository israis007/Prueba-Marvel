package com.test.app.ui.mainscreen.fragments.ubicationlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.test.app.R
import com.test.app.databinding.FragmentUbicationMapBinding
import com.test.app.ui.base.FragmentBase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UbicationListFragment : FragmentBase() {

    private val binding: FragmentUbicationMapBinding by lazy {
        DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.fragment_ubication_map, null ,false)
    }

    private val vm: UbicationListViewModel by viewModels()

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