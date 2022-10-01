package com.test.app.ui.mainscreen.fragments.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.app.rest.responses.PopularPersonsResponse
import com.test.app.rest.state.Resource
import com.test.app.rest.usecases.GetPopularPersonsCaseUse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getPopularPersonsCaseUse: GetPopularPersonsCaseUse
): ViewModel() {

    private val _response = MutableLiveData<Resource<PopularPersonsResponse>>()
    val response: LiveData<Resource<PopularPersonsResponse>> get() = _response

    fun getPopularPersons(language: String, page: Int){
        viewModelScope.launch(Dispatchers.IO){
            getPopularPersonsCaseUse(language = language, page = page){
                _response.postValue(it)
            }
        }
    }

}