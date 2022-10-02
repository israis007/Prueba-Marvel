package com.test.app.ui.mainscreen.fragments.imageupload

import android.app.Activity
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.irisoftmex.imagechooser.ImagePicker
import com.journeyapps.barcodescanner.ScanIntentResult
import com.test.app.BuildConfig
import com.test.app.R
import com.test.app.databinding.FragmentImageUploadBinding
import com.test.app.rest.state.StatusType
import com.test.app.ui.base.FragmentBase
import com.test.app.ui.mainscreen.MainActivityView
import com.test.app.ui.mainscreen.adapters.AdapterImagesList
import com.test.app.ui.tools.REQUEST_SELECT_PICTURE
import com.test.app.ui.tools.checkPermissions
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

internal const val MAX_SIZE = 1024
internal const val DIR_SAVE = "AppTestImages"

@AndroidEntryPoint
class ImageUploadFragment: FragmentBase() {

    private val binding: FragmentImageUploadBinding by lazy {
        DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.fragment_image_upload, null, false)
    }

    private val vm: ImageUploadViewModel by viewModels()

    private val launchImagePicker = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            (requireActivity() as MainActivityView).showLoading(true)
            vm.uploadImage(result.data?.data)
        }
        else
            (requireActivity() as MainActivityView).showToastMessage(getString(R.string.err_no_image_choose))
    }

    private lateinit var activity: MainActivityView
    private lateinit var adapter: AdapterImagesList
    private var listUrls = ArrayList<String>()
    private var listImages = ArrayList<String>()

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
        with(binding){
            fabAdd.setOnClickListener {
                this@ImageUploadFragment.checkPermissions(){ granted ->
                    if (granted){
                        ImagePicker.with(this@ImageUploadFragment)
                            .galleryOnly()
                            .cropSquare()
                            .galleryMimeTypes(  //Exclude gif images
                                mimeTypes = arrayOf(
                                    "image/jpg",
                                    "image/jpeg",
                                    "image/png"
                                )
                            )
                            .compress(MAX_SIZE)
                            //  Path: /storage/sdcard0/Android/data/package/files
                            .saveDir(requireContext().getExternalFilesDir(null)!!)
                            //  Path: /storage/sdcard0/Android/data/package/files/DCIM
                            .saveDir(requireContext().getExternalFilesDir(Environment.DIRECTORY_DCIM)!!)
                            //  Path: /storage/sdcard0/Android/data/package/files/Download
                            .saveDir(requireContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!)
                            //  Path: /storage/sdcard0/Android/data/package/files/Pictures
                            .saveDir(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!)
                            //  Path: /storage/sdcard0/Android/data/package/files/Pictures/ImagePicker
                            .saveDir(
                                File(
                                    requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!,
                                    DIR_SAVE
                                )
                            )
                            //  Path: /storage/sdcard0/Android/data/package/files/ImagePicker
                            .saveDir(requireContext().getExternalFilesDir(DIR_SAVE)!!)
                            //  Path: /storage/sdcard0/Android/data/package/cache/ImagePicker
                            .saveDir(File(requireContext().externalCacheDir, DIR_SAVE))
                            //  Path: /data/data/package/cache/ImagePicker
                            .saveDir(File(requireContext().cacheDir, DIR_SAVE))
                            //  Path: /data/data/package/files/ImagePicker
                            .saveDir(File(requireContext().filesDir, DIR_SAVE))
                            .createIntent {
                                launchImagePicker.launch(it)
                            }
                    } else
                        onPermissionError()
                }
            }
        }
    }

    override fun setObservers() {
        vm.uploadSuccess.observe(viewLifecycleOwner){
            val resource = it?:return@observe
            activity.showLoading(false)
            when(resource.statusType){
                StatusType.SUCCESS -> {
                    activity.showToastMessage("La imagen ${resource.data} se subio correctamente...")

                }
                StatusType.ERROR -> activity.showInfoMessage(getString(R.string.title_error), resource.message)
                StatusType.LOADING -> activity.showLoading(true)
            }
        }
        vm.listImages.observe(viewLifecycleOwner){
            val resource = it ?: return@observe
            activity.showLoading(false)
            when(resource.statusType){
                StatusType.SUCCESS -> {
                    listUrls = ArrayList()
                    listImages = ArrayList<String>().apply {
                        addAll(resource.data ?: listOf())
                    }
                    listImages.forEach { url ->
                        vm.getUrlImage(url)
                    }
                }
                StatusType.ERROR -> activity.showInfoMessage(getString(R.string.title_error), resource.message)
                StatusType.LOADING -> activity.showLoading(true)
            }
        }
        vm.urlImage.observe(viewLifecycleOwner){
            val resource = it ?: return@observe
            activity.showLoading(false)
            when(resource.statusType){
                StatusType.SUCCESS -> {
                    listUrls.add(resource.data ?: "")
                    if (listUrls.size ==  listImages.size)
                        adapter.addItemsAfter(listUrls)
                }
                StatusType.ERROR -> activity.showInfoMessage(getString(R.string.title_error), resource.message)
                StatusType.LOADING -> activity.showLoading(true)
            }
        }
    }

    override fun cleanFields() {
        
    }

    override fun removeObservers() {
        with(vm) {
            uploadSuccess.removeObservers(viewLifecycleOwner)
            listImages.removeObservers(viewLifecycleOwner)
            urlImage.removeObservers(viewLifecycleOwner)
        }
    }

    override fun initViewComponents() {
        activity = requireActivity() as MainActivityView
        adapter = AdapterImagesList(ArrayList()){
            activity.showImageMessage(it)
        }
        binding.rvList.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = this@ImageUploadFragment.adapter
        }
        vm.getListImages()
    }

    override fun changeToolbarParams() {
        
    }

    private fun onPermissionError() {
        activity.showInfoMessage(getString(R.string.title_error), getString(R.string.err_missing_permissions))
    }
}