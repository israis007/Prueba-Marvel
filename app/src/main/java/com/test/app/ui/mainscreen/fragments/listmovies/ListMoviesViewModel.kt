package com.test.app.ui.mainscreen.fragments.listmovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.app.rest.responses.PopularMoviesResponse
import com.test.app.rest.state.Resource
import com.test.app.rest.usecases.GetPopularMoviesCaseUse
import com.test.app.rest.usecases.LoginCaseUse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListMoviesViewModel @Inject constructor(
    private val getMarvelCaseUse: GetPopularMoviesCaseUse,
    private val loginCaseUse: LoginCaseUse
) : ViewModel() {

    private val _responsePopularMovies = MutableLiveData<Resource<PopularMoviesResponse>>()
    val responsePopularMovies: LiveData<Resource<PopularMoviesResponse>> get() = _responsePopularMovies

    private val _session = MutableLiveData<Resource<Boolean>>()
    val session: LiveData<Resource<Boolean>> get() = _session

    fun getPopularMovies(language: String, page: Int){
        viewModelScope.launch(Dispatchers.IO){
            getMarvelCaseUse.invoke(language = language, page = page){
                _responsePopularMovies.postValue(it)
            }
        }
    }

    fun login(){
        viewModelScope.launch(Dispatchers.IO){
            loginCaseUse.invoke {
                _session.postValue(it)
            }
        }
    }
}