package com.test.app.ui.mainscreen.fragments.listmovies

import android.database.DatabaseUtils
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.app.R
import com.test.app.databinding.FragmentListMoviesBinding
import com.test.app.objects.Results
import com.test.app.rest.state.StatusType
import com.test.app.ui.base.FragmentBase
import com.test.app.ui.mainscreen.MainActivityView
import com.test.app.ui.mainscreen.adapters.AdapterMDB
import com.test.app.ui.tools.CalendarUtils
import com.test.app.ui.tools.ui.SimpleDividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

const val LANGUAGE = "es-MX"

@AndroidEntryPoint
class ListMoviesFragment: FragmentBase() {

    private val binding: FragmentListMoviesBinding by lazy {
        DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.fragment_list_movies, null, false)
    }

    private val vm: ListMoviesViewModel by viewModels()
    private lateinit var activity: MainActivityView

    lateinit var adapter: AdapterMDB
    @Inject
    lateinit var calendarUtils: CalendarUtils
    private var isScrolling = false
    private lateinit var linearLayoutManager: LinearLayoutManager
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
        vm.responsePopularMovies.observe(this) {
            val resource = it ?: return@observe
            activity.showLoading(false)
            when (resource.statusType){
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
        vm.responsePopularMovies.removeObservers(viewLifecycleOwner)
    }

    override fun initViewComponents() {
        activity = requireActivity() as MainActivityView
        linearLayoutManager = LinearLayoutManager(requireActivity())
        adapter = AdapterMDB(requireContext(), ArrayList(), object : AdapterMDB.OnResultTouchListener{
            override fun onTouchItem(pathImage: String) {
                activity.showImageMessage(path = pathImage)
            }

            override fun onChipAddFavoriteClickListener(result: Results) {

            }

            override fun onChipSimilarClickListener(result: Results) {

            }
        })
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        binding.actRv.apply {
            layoutManager = linearLayoutManager
            adapter = this@ListMoviesFragment.adapter
            isNestedScrollingEnabled = true
            addItemDecoration(SimpleDividerItemDecoration(requireContext()))
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
        vm.getPopularMovies(language = LANGUAGE, 1)
    }

    override fun changeToolbarParams() {
        
    }
}