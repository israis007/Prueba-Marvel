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
import com.test.app.ui.mainscreen.adapters.AdapterMDB
import com.test.app.ui.tools.CalendarUtils
import com.test.app.ui.tools.ui.SimpleDividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

const val LANGUAGE = "es-MX"

@AndroidEntryPoint
class MainActivityView : ActivityBase() {

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this@MainActivityView, R.layout.activity_main )
    }
    private val vm: MainActivityViewModel by viewModels()
    lateinit var adapter: AdapterMDB
    @Inject lateinit var calendarUtils: CalendarUtils
    private var isScrolling = false
    private val linearLayoutManager = LinearLayoutManager(this)
    private var nextPage = 1
    private var limit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(binding.root)
        super.onCreate(savedInstanceState)
        vm.getPopularMovies(language = LANGUAGE, 1)
    }

    override fun initUI() {
        binding.apply {
            viewModel = vm
            lifecycleOwner = this@MainActivityView
        }
        adapter = AdapterMDB(this, ArrayList(), object : AdapterMDB.OnResultTouchListener{
            override fun onTouchItem(pathImage: String) {
                showImageMessage(path = pathImage)
            }

            override fun onChipAddFavoriteClickListener(result: Results) {

            }

            override fun onChipSimilarClickListener(result: Results) {

            }
        })
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
                    if (!limit)
                        vm.getPopularMovies(language = LANGUAGE, nextPage)
                }
            }
        })
    }

    override fun setListeners() {

    }

    override fun setObservers() {
        vm.responsePopularMovies.observe(this) {
            val resource = it ?: return@observe
            showLoading(false)
            when (resource.statusType){
                StatusType.SUCCESS -> {
                    val response = resource.data!!
                    nextPage++
                    if (nextPage > response.total_pages)
                        limit = true
                    adapter.addItemsAfter(response.results)
                }
                StatusType.ERROR -> showInfoMessage(getString(R.string.title_error), resource.message)
                StatusType.LOADING -> showLoading(true)
            }
        }
    }
}
