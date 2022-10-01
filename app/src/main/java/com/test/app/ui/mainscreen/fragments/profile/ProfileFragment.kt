package com.test.app.ui.mainscreen.fragments.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.google.gson.Gson
import com.test.app.R
import com.test.app.databinding.FragmentProfileBinding
import com.test.app.objects.KnowFor
import com.test.app.rest.state.StatusType
import com.test.app.ui.base.FragmentBase
import com.test.app.ui.mainscreen.MainActivityView
import com.test.app.ui.mainscreen.adapters.AdapterPopularPersons
import com.test.app.ui.mainscreen.fragments.listmovies.LANGUAGE
import com.test.app.ui.tools.setUpScrollingListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment: FragmentBase() {

    private val binding: FragmentProfileBinding by lazy {
        DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.fragment_profile, null, false)
    }

    private val vm: ProfileViewModel by viewModels()
    private lateinit var activity: MainActivityView
    private lateinit var adapter: AdapterPopularPersons
    private var nextPage = 1
    private var limit = false

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
        vm.response.observe(viewLifecycleOwner){
            val resource = it ?: return@observe
            activity.showLoading(false)
            when(resource.statusType){
                StatusType.SUCCESS -> {
                    val response = resource.data!!
                    nextPage++
                    if (nextPage > response.total_pages)
                        limit = true
                    adapter.addItemsAfter(response.results)
                }
                StatusType.ERROR -> activity.showInfoMessage(getString(R.string.title_error), resource.message)
                StatusType.LOADING -> activity.showLoading(true)
            }
        }
    }

    override fun cleanFields() {
        
    }

    override fun removeObservers() {
        vm.response.removeObservers(viewLifecycleOwner)
    }

    override fun initViewComponents() {
        activity = requireActivity() as MainActivityView
        adapter = AdapterPopularPersons(requireContext(), layoutInflater, ArrayList(), object: AdapterPopularPersons.OnResultTouchListener{
            override fun onTouchItem(pathImage: String) {
                activity.showImageMessage(pathImage)
            }

            override fun onChipClicked(result: KnowFor) {
                Log.d("Chip", Gson().toJson(result))
            }
        })
        binding.actRv.setUpScrollingListener(requireContext(), adapter, true){ lastItem, _ ->
            if (lastItem)
                vm.getPopularPersons(LANGUAGE, nextPage)
        }
        vm.getPopularPersons(LANGUAGE, 1)
    }

    override fun changeToolbarParams() {
        
    }
}