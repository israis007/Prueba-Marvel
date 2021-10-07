package com.test.app.ui.mainscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.app.rest.apimarvel.MarvelRepository
import com.test.app.rest.responses.CharactersResponse
import com.test.app.rest.state.Resource
import com.test.app.rest.usecases.GetMarvelCaseUse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getMarvelCaseUse: GetMarvelCaseUse
) : ViewModel() {

    private val _characterResponse = getMarvelCaseUse.getCharacters()
    val charactersResponse: LiveData<Resource<CharactersResponse>> get() = _characterResponse

    fun getCharacters(){
        viewModelScope.launch(Dispatchers.IO){
            getMarvelCaseUse.invoke(50)
        }
    }

}