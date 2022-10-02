package com.test.app.ui.mainscreen.fragments.imageupload

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.app.rest.state.Resource
import com.test.app.rest.usecases.GetListOfImagesCaseUse
import com.test.app.rest.usecases.GetUrlImagesCaseUse
import com.test.app.rest.usecases.UploadImageToStorageCaseUse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageUploadViewModel @Inject constructor(
    private val uploadImageToStorageCaseUse: UploadImageToStorageCaseUse,
    private val getUrlImagesCaseUse: GetUrlImagesCaseUse,
    private val getListOfImagesCaseUse: GetListOfImagesCaseUse
): ViewModel(){

    private val _uploadSuccess = MutableLiveData<Resource<String>>()
    val uploadSuccess: LiveData<Resource<String>> get() = _uploadSuccess

    private val _listImages = MutableLiveData<Resource<List<String>>>()
    val listImages: LiveData<Resource<List<String>>> get() = _listImages

    private val _urlImage = MutableLiveData<Resource<String>>()
    val urlImage: LiveData<Resource<String>> get() = _urlImage

    fun uploadImage(uriPath: Uri?){
        viewModelScope.launch(Dispatchers.IO){
            uploadImageToStorageCaseUse.invoke(uri = uriPath){
                _uploadSuccess.postValue(it)
            }
        }
    }

    fun getListImages(){
        viewModelScope.launch(Dispatchers.IO){
            getListOfImagesCaseUse.invoke {
                _listImages.postValue(it)
            }
        }
    }

    fun getUrlImage(name: String){
        viewModelScope.launch(Dispatchers.IO){
            getUrlImagesCaseUse.invoke(name){
                _urlImage.postValue(it)
            }
        }
    }
}