package com.test.app.ui.mainscreen

import android.os.Bundle
import android.widget.AbsListView
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.app.R
import com.test.app.databinding.ActivityMainBinding
import com.test.app.objects.Results
import com.test.app.rest.state.StatusType
import com.test.app.ui.base.ActivityBase
import com.test.app.ui.mainscreen.adapters.MarvelAdapter
import com.test.app.ui.tools.CalendarUtils
import com.test.app.ui.tools.ui.SimpleDividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivityView : ActivityBase() {

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this@MainActivityView, R.layout.activity_main )
    }
    private val vm: MainActivityViewModel by viewModels()
    lateinit var adapter: MarvelAdapter
    @Inject lateinit var calendarUtils: CalendarUtils
    private var isScrolling = false
    private val linearLayoutManager = LinearLayoutManager(this)

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

            override fun onChipSeriesClickListener(result: Results) {

            }

            override fun onChipComicsClickListener(result: Results) {

            }

            override fun onChipStoriesClickListener(result: Results) {

            }

            override fun onChipEventsClickListener(result: Results) {

            }

            override fun onChipUrlsClickListener(result: Results) {

            }
        }, calendarUtils)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        binding.actRv.apply {
            layoutManager = linearLayoutManager
            adapter = this@MainActivityView.adapter
            isNestedScrollingEnabled = true
            addItemDecoration(SimpleDividerItemDecoration(this@MainActivityView))
        }
        binding.actRv.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                    isScrolling = true
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (isScrolling && (linearLayoutManager.itemCount - linearLayoutManager.findFirstVisibleItemPosition() - linearLayoutManager.childCount == 0)){
                    isScrolling = false
                    vm.getCharacters()
                }
            }
        })
    }

    override fun setListeners() {

    }

    override fun setObservers() {
        vm.charactersResponse.observe(this) {
            val resource = it ?: return@observe
            showLoading(false)
            when (resource.statusType){
                StatusType.SUCCESS -> {
                    adapter.addItemsAfter(resource.data!!.data.results)
                }
                StatusType.ERROR -> showInfoMessage(resource.title ?: "", resource.message ?: "")
                StatusType.LOADING -> showLoading(true)
            }
        }
    }
}
