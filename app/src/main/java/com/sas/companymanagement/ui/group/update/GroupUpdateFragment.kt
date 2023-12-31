package com.sas.companymanagement.ui.group.update

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
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
import com.sas.companymanagement.ui.common.getRandomListToString
import com.sas.companymanagement.ui.common.toastMessage
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

/**
 * 그룹 추가 프래그먼트
 *
 * @fileName         : GroupUpdateFragment
 * @author           : 이원형, 박지혜, 이종윤, 이기영, 윤성욱
 * @Since            : 2023-10-17
 */
class GroupUpdateFragment :
    ViewBindingBaseFragment<FragmentGroupUpdateBinding>(FragmentGroupUpdateBinding::inflate) {


    companion object {
        fun newInstance() = GroupUpdateFragment()
    }

    private val groupArgs: GroupUpdateFragmentArgs by navArgs()
    private val viewModel: GroupUpdateViewModel by viewModels()
    private val artistUpdateViewModel: ArtistUpdateViewModel by viewModels()
    private val compositeDisposable = CompositeDisposable()
    private var imageSrc = ""
    private var imageUri: Uri? = null
    private var name = ""
    private var id = 0L
    private var artistId = ""
    private var selectedArtistIdList: MutableSet<Long> = mutableSetOf()
    private var tempSet: MutableSet<Long> = mutableSetOf()
    private var isFieldLoaded = false
    private var eval = ""
    var fragmentStackSize = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<MutableSet<Long>>("selectedArtistId")
            ?.observe(viewLifecycleOwner) {
                isFieldLoaded = false
                selectedArtistIdList = it
                if (groupArgs.groupId == -1L) {
                    setChip.invoke()
                }
            }

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (groupArgs.groupId != -1L) getField()
        listenerSetup()
    }

    /**
     * Group 수정시 기존 정보 가져온다.
     * @author 이원형, 이기영, 이종윤
     */
    private fun getField() {
        binding.tbGroupUpdate.title = resources.getString(R.string.group_update_artist)
        fragmentStackSize++
        viewModel.findGroup(groupArgs.groupId)
        viewModel.getSearchResults().observe(viewLifecycleOwner) { groupData ->
            CoroutineScope(Dispatchers.Main).launch {
                with(binding) {
                    teGroupName.setText(groupData.groupName)
                    ibGroup.setImageURI(Uri.parse(groupData.groupImage))
                    ibGroup.setBackgroundColor(Color.TRANSPARENT)
                    imageSrc = groupData.groupImage
                    imageUri = groupData.groupImage.toUri()
                    id = groupData.id
                    artistId = groupData.artistId
                    eval = groupData.groupEval
                }
                val artistList = groupData.artistId.split(",").map {
                    it.trim().toLong()
                }.toMutableSet()
                if (fragmentStackSize < 2) {
                    tempSet =
                        (artistList.toMutableSet() + selectedArtistIdList.toMutableSet()).toMutableSet()
                    editArtistChip(tempSet)
                } else {
                    tempSet = selectedArtistIdList
                    editArtistChip(tempSet)
                }

            }
        }
    }

    /**
     * chip 설정
     * @author 이종윤
     */
    private val setChip = {
        tempSet = selectedArtistIdList
        editArtistChip(tempSet)
    }
    /**
     * 선택한 이미지 저장 / 수정일 경우 기존 파일 지우고 저장
     *
     * @author 윤성욱
     */
    @SuppressLint("SdCardPath")
    private fun saveImage() {
        val imagesFolder = File(activity?.filesDir, "images")
        if (!imagesFolder.exists()) imagesFolder.mkdirs()
        if (groupArgs.groupId == -1L) {
            val deleteFile = File(imageSrc)
            if (deleteFile.exists()) deleteFile.delete()
            val imageName = System.currentTimeMillis().toString()
            imageSrc = "/data/data/com.sas.companymanagement/files/images/${imageName}.jpg"
        }
        val newFile = File(imageSrc)
        imageToFile(requireActivity(), imageUri!!, newFile)
    }
    /**
     * 이미지를 파일로 저장
     *
     * @param context contentResolver를 사용하기 위한 context
     * @param imageUri 이미지의 uri
     * @param newFile 파일 형식
     * @author 윤성욱
     */
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

    /**
     * chip을 사용해 아티스트 추가
     * @author 이종윤, 윤성욱
     */
    private fun editArtistChip(temp: MutableSet<Long>) {
        if (temp.isNotEmpty()) {
            binding.cgArtistUpdate.removeAllViews()
            temp.forEach { id ->
                binding.cgArtistUpdate.addView(Chip(context).apply {
                    artistUpdateViewModel.findArtistById(id).observe(viewLifecycleOwner) { artist ->
                        if (artist != null) {
                            text = artist.artistName
                            isCloseIconVisible = true
                        }
                    }
                    //chip에 있는 close 버튼을 클릭할 때, 삭제한 id 값을 기반을 selectedArtistIdList 값을 삭제함
                    setOnCloseIconClickListener {
                        temp.remove(id)
                        binding.cgArtistUpdate.removeView(this)
                    }
                })
            }
            binding.cgArtistUpdate.addView(binding.cAdd)
        }
    }

    /**
     * chip, imageButton click event
     * @author 이원형, 윤성욱
     */
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
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        getImageFromGallery(Manifest.permission.READ_MEDIA_IMAGES)
                    } else getImageFromGallery(Manifest.permission.READ_EXTERNAL_STORAGE)
                }, {
                    Log.e("IB_ERROR", compositeDisposable.toString())
                })
        }
    }

    /**
     * imageButton listenerSetup
     * @author 이원형, 윤성욱, 박지혜
     */
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
                        if (groupArgs.groupId != -1L) {
                            viewModel.updateGroup(
                                Group(
                                    artistId = tempSet.joinToString(),
                                    groupName = name,
                                    groupImage = imageSrc,
                                    groupEval = eval,
                                    id = id
                                )
                            )
                        } else {
                            viewModel.insertGroup(
                                Group(
                                    artistId = selectedArtistIdList.joinToString(),
                                    groupName = name,
                                    groupImage = imageSrc,
                                    groupEval = getRandomListToString()
                                )
                            )
                        }
                        findNavController().popBackStack()
                    } else {
                        toastMessage(resources.getString(R.string.error_message_empty), activity as Activity)
                    }
                }
                true
            }

        }
    }


    /**
     * group 수정시 editText 이름 설정
     * @author 이원형
     */
    private fun updateSet() {
        with(binding) {
            name = teGroupName.text.toString()
        }
    }

    /**
     * 아티스트를 선택하고 돌아왔을 때 선택한 이미지가 남아있게 하기 위한 onResume 오버라이딩
     * @author 윤성욱
     */
    override fun onResume() {
        super.onResume()
        if (imageUri != null) {
            binding.ibGroup.setImageURI(imageUri)
            binding.ibGroup.setBackgroundColor(Color.TRANSPARENT)
        }
    }

    /**
     * 공백이 있을 시 저장 불가
     * @author 이원형
     */
    private fun requireUpdate(): Boolean {
        with(binding) {
            if (teGroupName.text.toString() == "" ||
                imageSrc == ""
            ) return false
        }
        return true
    }

    /**
     * 갤러리에서 가져온 이미지를 보여주는 역할
     * @author 윤성욱
     */
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

    /**
     * Manifest 파일에 권한이 있으면 갤러리에서 이미지를 가져온다.
     * @param permission 권한
     * @author 윤성욱
     */
    private fun getImageFromGallery(permission : String) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermission.launch(permission)
        } else {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*"
            )
            startForResult.launch(intent)
        }
    }

    /**
     * Request permission 권한요청 팝업 이벤트 처리
     * @author 윤성욱
     */
    private val requestPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        when (it) {
            true -> {
                val intent = Intent(Intent.ACTION_PICK)
                intent.setDataAndType(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    "image/*"
                )
                startForResult.launch(intent)
            }
            false -> {
                toastMessage(resources.getString(R.string.permission_deny), requireActivity())
            }
        }
    }
}

