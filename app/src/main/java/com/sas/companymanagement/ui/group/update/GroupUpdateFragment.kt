package com.sas.companymanagement.ui.group.update

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.chip.Chip
import com.jakewharton.rxbinding4.view.clicks
import com.sas.companymanagement.R
import com.sas.companymanagement.databinding.FragmentGroupUpdateBinding
import com.sas.companymanagement.ui.artist.update.ArtistUpdateViewModel
import com.sas.companymanagement.ui.common.ViewBindingBaseFragment
import com.sas.companymanagement.ui.group.Group
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.concurrent.TimeUnit

class GroupUpdateFragment :
    ViewBindingBaseFragment<FragmentGroupUpdateBinding>(FragmentGroupUpdateBinding::inflate) {


    companion object {
        fun newInstance() = GroupUpdateFragment()
        //    private var tempImage = "/data/data/com.sas.companymanagement/files/images/${imageName}.jpg"

    }

    private val groupArgs: GroupUpdateFragmentArgs by navArgs()
    private val viewModel: GroupUpdateViewModel by viewModels()
    private val artistUpdateViewModel: ArtistUpdateViewModel by viewModels()
    private val compositeDisposable = CompositeDisposable()
    private var imageSrc = ""
    private var imageUri: Uri? = null
    private var name = ""
    private var id = 0L
    private lateinit var selectedArtistIdList: LongArray

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<LongArray>("selectedArtistId")
            ?.observe(viewLifecycleOwner) {
                selectedArtistIdList = it
                addArtistChip()
            }

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //    binding.ibGroup.setImageURI(tempImage)
        if (groupArgs.groupId != -1) getField()
        listenerSetup()
    }

    private fun getField() {
        viewModel.findGroup(groupArgs.groupId)
        viewModel.getSearchResults().observe(viewLifecycleOwner) { groups ->

            val groupData = groups[0]
            CoroutineScope(Dispatchers.Main).launch {
                with(binding) {
                    teGroupName.setText(groupData.groupName)
                    ibGroup.setImageURI(Uri.parse(groupData.groupImage))
                    imageSrc = groupData.groupImage
                    imageUri = groupData.groupImage.toUri()
                    id = groupData.id
                }
            }
        }
    }


    //이미지 저장
    @SuppressLint("SdCardPath")
    private fun saveImage() {
        val imagesFolder = File(activity?.filesDir, "images")
        if (!imagesFolder.exists()) imagesFolder.mkdirs()
        if (groupArgs.groupId == -1) {
            val imageName = System.currentTimeMillis().toString()
            imageSrc = "/data/data/com.sas.companymanagement/files/images/${imageName}.jpg"

        }
        val newFile = File(imageSrc)
        imageToFile(requireActivity(), imageUri!!, newFile)
    }

    private fun imageToFile(context: Context, imageUri: Uri, newFile: File) {
        var inputStream: InputStream? = null
        var outputStream: FileOutputStream? = null

        try {
            inputStream = context.contentResolver.openInputStream(imageUri)
            outputStream = FileOutputStream(newFile)

            val buffer = ByteArray(1024)
            var data: Int
            do {
                data = inputStream!!.read(buffer)
                if (data != -1) {
                    outputStream.write(buffer, 0, data)
                }
            } while (data != -1)
            inputStream.close()
            outputStream.close()
        } catch (e: Exception) {
            Log.e("file", "파일이 만들어 지지 않음")
        }
    }

    private fun addArtistChip() {
        binding.cgArtistUpdate.removeView(binding.cAdd)

        selectedArtistIdList.forEach { id ->
            binding.cgArtistUpdate.addView(Chip(context).apply {
                artistUpdateViewModel.findArtistById(id)
                    .observe(viewLifecycleOwner) { artist ->
                        text = artist.artistName
                        isCloseIconVisible = true
                    }
                setOnCloseIconClickListener {
                    selectedArtistIdList = selectedArtistIdList.filter { value ->
                        value != id
                    }.toLongArray()

                    binding.cgArtistUpdate.removeView(this)
                }
            })
        }

        binding.cgArtistUpdate.addView(binding.cAdd)

    }

    private fun listenerField() {
        with(binding) {
            cAdd
                .clicks()
                .observeOn(Schedulers.io())
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val action =
                        GroupUpdateFragmentDirections.actionGroupUpdateFragmentToArtistSelectFragment(
                            "group"
                        )
                    findNavController().navigate(action)
                }, {
                    Log.e("RX_ERROR", compositeDisposable.toString())
                })

        }
        with(binding) {
            ibGroup
                .clicks()
                .observeOn(Schedulers.io())
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.setDataAndType(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        "image/*"
                    )
                    startForResult.launch(intent)
                }, {
                    Log.e("IB_ERROR", compositeDisposable.toString())
                })
        }
    }

    private fun listenerSetup() {

        with(binding) {
            tbGroupUpdate.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            listenerField()
            tbGroupUpdate.setOnMenuItemClickListener { item ->
                if (item.itemId == R.id.menu_update) {
                    updateSet()
                    saveImage()
                    if (requireUpdate()) {
                        if (groupArgs.groupId != -1) {
                            viewModel.updateGroup(
                                Group(
                                    artistId = selectedArtistIdList.joinToString(),
                                    groupName = name,
                                    groupImage = imageSrc,
                                    id = id
                                )
                            )
                        } else {
                            viewModel.insertGroup(
                                Group(
                                    artistId = selectedArtistIdList.joinToString(),
                                    groupName = name,
                                    groupImage = imageSrc,
                                )
                            )
                        }
                        findNavController().popBackStack()
                    } else {
                    }
                }
                true
            }

        }
    }

    //    binding.cgArtistUpdate.addView(binding.cAdd)
    private fun updateChip() {
        with(binding) {
//            selectedArtistIdList = cgArtistUpdate
        }
    }

    private fun updateSet() {
        with(binding) {
            name = teGroupName.text.toString()
        }
    }

    private fun requireUpdate(): Boolean {
        with(binding) {
            if (teGroupName.text.toString() == "" ||
                imageSrc == ""
            ) return false
        }
        return true
    }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                imageUri = it.data?.data
                binding.ibGroup.setImageURI(imageUri)
                binding.ibGroup.setBackgroundColor(Color.TRANSPARENT)

            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }
}


