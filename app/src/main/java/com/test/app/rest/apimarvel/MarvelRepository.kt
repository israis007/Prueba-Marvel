package com.test.app.rest.apimarvel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.test.app.AppTest
import com.test.app.BuildConfig
import com.test.app.rest.responses.CharactersResponse
import com.test.app.rest.state.Resource
import com.test.app.ui.tools.SignCreator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import java.util.*
import javax.inject.Inject
import javax.inject.Named


class MarvelRepository @Inject constructor(
    private val providesMarvelAPI: ProvidesMarvelAPI,
    @Named(RETROFIT_MARVEL) private val retrofit: Retrofit,
    private val signCreator: SignCreator
) {

    private val _response = MutableLiveData<Resource<CharactersResponse>>()
    fun getResponse() : LiveData<Resource<CharactersResponse>> = _response

    fun getCharacters(limit: Int) {
        _response.postValue(Resource.loading())
        val timeStamp = Calendar.getInstance(Locale.getDefault()).time.time
        AppTest.instance.setCurrentDate(Date().apply {
            time = timeStamp
        })
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val sign = signCreator.createSign(timeStamp)
                Log.d("Marvel", "Firma -> '$sign'")
                val response = providesMarvelAPI.marvelAPI(retrofit).getCharacters(
                    "name",
                    limit,
                    timeStamp,
                    BuildConfig.PUBLIC_KEY,
                    sign
                )
                if (response.isSuccessful && response.code() == 200)
                    _response.postValue(Resource.success(response.body()!!))
                else
                    _response.postValue(Resource.error("No se pudo comunicar con el servidor de Marvel", response.message()))
            } catch (e: Exception){
                _response.postValue(Resource.error("No se pudo comunicar con el servidor de Marvel", e.message ?: e.stackTraceToString()))
            }
        }
    }

}