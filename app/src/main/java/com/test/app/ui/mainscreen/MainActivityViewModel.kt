package com.test.app.ui.mainscreen

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewModelScope
import com.test.app.AppTest
import com.test.app.R
import com.test.app.rest.punkapi.RepositoryAPI
import com.test.app.ui.mainscreen.models.BeerModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor() : ViewModel() {

    val page = MutableLiveData<Int>()
    private val elementsByPage = MutableLiveData<Int>()
    var beerRetro = MutableLiveData<List<BeerModel>>()
    var busy = MutableLiveData<Integer>()
    var showNoInternet = MutableLiveData<Integer>()
    var showMainLayout = MutableLiveData<Integer>()
    var tagline = MutableLiveData<String>()
    var description = MutableLiveData<String>()
    var brewedDate = MutableLiveData<String>()
    var foodPairing = MutableLiveData<String>()
    var nameToolbar = MutableLiveData<String>()
    var imageURL = MutableLiveData<String>()

    private val TAG = "MainVM"
    private var pageAfter = AppTest.instance.resources.getInteger(R.integer.pageBegin)

    init {
        busy.value = View.VISIBLE as Integer
        showNoInternet.postValue(View.GONE as Integer)
        showMainLayout.postValue(View.VISIBLE as Integer)
        page.value = AppTest.instance.resources.getInteger(R.integer.pageBegin)
        elementsByPage.value = AppTest.instance.resources.getInteger(R.integer.elementsByPage)
        tagline.value = ""
        description.value = ""
        brewedDate.value = ""
        foodPairing.value = ""
        nameToolbar.value = AppTest.instance.getString(R.string.app_name)
    }

    lateinit var activityViewModel: ViewModelStoreOwner

    fun changeViews(noInternet: Boolean) {
        showNoInternet.value = if (noInternet) View.GONE as Integer else View.VISIBLE as Integer
        showMainLayout.value = if (noInternet) View.VISIBLE as Integer else View.GONE as Integer
    }

    fun updatePage(isUp: Boolean) {
        var pageN = page.value!!.toInt()
        if (isUp) {
            if (pageAfter > 1) {
                pageAfter--
                page.value = pageAfter
            }
        } else {
            pageN++
            page.value = pageN
        }
    }

    fun getBeersRetro() {
        busy.value = View.VISIBLE as Integer
        viewModelScope.launch(Dispatchers.IO) {
//            val chelas = RepositoryAPI.getBeers(
//                activityViewModel,
//                page.value!!.toInt(),
//                elementsByPage.value!!.toInt()
//            )
//            if (chelas == 44) {
//                showNoInternet.postValue(View.VISIBLE as Integer)
//                showMainLayout.postValue(View.GONE as Integer)
//                return@launch
//            } else {
//                showNoInternet.postValue(View.GONE as Integer)
//                showMainLayout.postValue(View.VISIBLE as Integer)
//                beerRetro.postValue(chelas as List<BeerModel>)
//            }
        }
    }

}