package com.test.app.ui.mainscreen

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.app.R
import com.test.app.databinding.ActivityMainBinding
import com.test.app.objects.Results
import com.test.app.rest.state.StatusType
import com.test.app.ui.base.ActivityBase
import com.test.app.ui.mainscreen.adapters.MarvelAdapter
import com.test.app.ui.tools.ui.SimpleDividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivityView : ActivityBase() {

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this@MainActivityView, R.layout.activity_main )
    }
    private val vm: MainActivityViewModel by viewModels()
    lateinit var adapter: MarvelAdapter

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
        adapter = MarvelAdapter(this, ArrayList(), object : MarvelAdapter.OnResultTouchListener{
            override fun onTouchItem(result: Results) {
                showImageMessage("${result.thumbnail.path}.${result.thumbnail.extension}")
            }
        })
        binding.actRv.apply {
            layoutManager = LinearLayoutManager(this@MainActivityView)
            adapter = this@MainActivityView.adapter
            isNestedScrollingEnabled = true
            addItemDecoration(SimpleDividerItemDecoration(this@MainActivityView))
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
                    adapter.addItemsBefore(resource.data!!.data.results)
                }
                StatusType.ERROR -> showInfoMessage(resource.title ?: "", resource.message ?: "")
                StatusType.LOADING -> showLoading(true)
            }
        }
    }
}
