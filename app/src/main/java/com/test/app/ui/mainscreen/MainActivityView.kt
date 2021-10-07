package com.test.app.ui.mainscreen

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.app.R
import com.test.app.databinding.ActivityMainBinding
import com.test.app.rest.state.StatusType
import com.test.app.ui.base.ActivityBase
import com.test.app.ui.mainscreen.adapters.beerAdapter
import com.test.app.ui.mainscreen.models.BeerModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivityView : ActivityBase() {

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this@MainActivityView, R.layout.activity_main )
    }
    private val vm: MainActivityViewModel by viewModels()
    lateinit var adapter: beerAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(binding.root)
        super.onCreate(savedInstanceState)
        vm.getCharacters()
    }

    override fun initUI() {
        binding.apply {
            viewModel = vm
            lifecycleOwner = this@MainActivityView
        }
    }

    override fun setListeners() {

    }

    override fun setObservers() {
        vm.charactersResponse.observe(this) {
            val resource = it ?: return@observe
            showLoading(false)
            when (resource.statusType){
                StatusType.SUCCESS -> {

                }
                StatusType.ERROR -> showInfoMessage(resource.title ?: "", resource.message ?: "")
                StatusType.LOADING -> showLoading(true)
            }
        }
    }
}
