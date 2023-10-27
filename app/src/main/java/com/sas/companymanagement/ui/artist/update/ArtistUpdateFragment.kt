package com.sas.companymanagement.ui.artist.update


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
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.MaterialDatePicker
import com.jakewharton.rxbinding4.view.clicks
import com.sas.companymanagement.R
import com.sas.companymanagement.databinding.FragmentArtistUpdateBinding
import com.sas.companymanagement.ui.artist.Artist
import com.sas.companymanagement.ui.artist.ArtistCategory
import com.sas.companymanagement.ui.artist.ArtistGender
import com.sas.companymanagement.ui.common.ViewBindingBaseFragment
import com.sas.companymanagement.ui.common.getRandomListToString
import com.sas.companymanagement.ui.common.toastMessage
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit

class ArtistUpdateFragment :
    ViewBindingBaseFragment<FragmentArtistUpdateBinding>(FragmentArtistUpdateBinding::inflate) {

    companion object {
        fun newInstance() = ArtistUpdateFragment()
    }


    private val artistArgs: ArtistUpdateFragmentArgs by navArgs()
    private val viewModel: ArtistUpdateViewModel by viewModels()
    private val compositeDisposable = CompositeDisposable()
    private var name = ""
    private var birth = ""
    private var gender = ""
    private var nickname = ""
    private var category = ""
    private var imageSrc = ""
    private var id = 0L
    private var imageUri: Uri? = null
    private var eval = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (artistArgs.artistId != -1L) getField()
        listenerSetup()
    }


    /**
     * Get field
     * 기존 Artist의 정보를 수정하려고 할 때
     */
    private fun getField() {
        viewModel.findArtist(artistArgs.artistId)
        viewModel.getSearchResults().observe(viewLifecycleOwner) { artist ->
            CoroutineScope(Dispatchers.Main).launch {
                with(binding) {
                    teArtistName.setText(artist.artistName)
                    teArtistNickname.setText(artist.artistNickname)
                    tvArtistBirth.setText(artist.artistBirth)
                    ibArtist.setImageURI(Uri.parse(artist.artistImage))
                    ibArtist.setBackgroundColor(Color.TRANSPARENT)
                    val checkId = when (artist.artistGender) {
                        ArtistGender.FEMALE.gender -> R.id.radioButtonFemale
                        else -> R.id.radioButtonMale
                    }
                    rgArtistGender.check(checkId)
                    for (position in ArtistCategory.entries.indices) {
                        if (spArtistJob.getItemAtPosition(position)
                                .toString() == artist.artistCategory
                        ) spArtistJob.setSelection(position)
                    }
                    imageSrc = artist.artistImage
                    imageUri = artist.artistImage.toUri()
                    id = artist.id
                    eval = artist.artistEval
                }
            }
        }
    }


    private fun listenerField() {
        // brith listener
        with(binding) {
            tvArtistBirth
                .clicks()
                .observeOn(Schedulers.io())
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    showDateTimePicker(
                        tvArtistBirth
                    )
                }, {
                    Log.e("RX_ERROR", compositeDisposable.toString())
                })
        }

        // image listener
        with(binding) {
            ibArtist
                .clicks()
                .observeOn(Schedulers.io())
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        getImageFromGallery(Manifest.permission.READ_MEDIA_IMAGES)
                    } else  getImageFromGallery(Manifest.permission.READ_EXTERNAL_STORAGE)
                }, {
                    Log.e("IB_ERROR", compositeDisposable.toString())
                })
        }

        with(binding) {
            // gender listener
            rgArtistGender.setOnCheckedChangeListener { group, checkdId ->
                gender = when (checkdId) {
                    R.id.radioButtonFemale -> ArtistGender.FEMALE.gender
                    else -> ArtistGender.MALE.gender
                }
            }
            // category listener
            spArtistJob.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(p: AdapterView<*>?) {}
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        category = when (parent!!.getItemAtPosition(position).toString()) {
                            ArtistCategory.ACTOR.job -> ArtistCategory.ACTOR.job
                            ArtistCategory.TALENT.job -> ArtistCategory.TALENT.job
                            else -> ArtistCategory.SINGER.job
                        }
                    }
                }
        }
    }

    private fun listenerSetup() {
        with(binding) {
            tbArtistUpdate.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            listenerField()
            tbArtistUpdate.setOnMenuItemClickListener { item ->
                if (item.itemId == R.id.menu_update) {
                    updateSet()
                    saveImage(artistArgs.artistId)
                    if (requireUpdate()) {
                        if (artistArgs.artistId != -1L) {
                            viewModel.updateArtist(
                                // Edit
                                Artist(
                                    artistName = name,
                                    artistNickname = nickname,
                                    artistImage = imageSrc,
                                    artistGender = gender,
                                    artistCategory = category,
                                    artistBirth = birth,
                                    artistEval = eval,
                                    id = id
                                )
                            )

                        } else {
                            // Insert, insert인 경우 image는 file로 저장해야 함
                            viewModel.insertArtist(
                                Artist(
                                    artistName = name,
                                    artistNickname = nickname,
                                    artistImage = imageSrc,
                                    artistGender = gender,
                                    artistCategory = category,
                                    artistBirth = birth,
                                    artistEval = getRandomListToString(),
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
     * Update set
     * 입력값(수정값)을 field 에 저장
     */
    private fun updateSet() {
        with(binding) {
            name = teArtistName.text.toString()
            nickname = teArtistNickname.text.toString()
            birth = tvArtistBirth.text.toString()
            gender = when (rgArtistGender.checkedRadioButtonId) {
                R.id.radioButtonFemale -> ArtistGender.FEMALE.gender
                else -> ArtistGender.MALE.gender
            }
        }
    }

    /**
     * Require update
     * 한 값이라도 공백일이면 추가 안됨
     * @return
     */
    private fun requireUpdate(): Boolean {
        with(binding) {
            if (teArtistName.text.toString() == "" ||
                teArtistNickname.text.toString() == "" ||
                tvArtistBirth.text.toString() == "" ||
                rgArtistGender.checkedRadioButtonId == -1 ||
                imageSrc == ""
            ) {

                toastMessage(resources.getString(R.string.error_message_empty), activity as Activity)

                return false
            }
        }
        return true
    }


    private fun showDateTimePicker(
        dateTextView: TextView
    ) {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        datePicker.addOnPositiveButtonClickListener { selectedDateValue ->
            val selectDate = Calendar.getInstance()
            selectDate.timeInMillis = selectedDateValue
            dateTextView.text =
                SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault()).format(selectDate.time)
        }
        datePicker.show(childFragmentManager, "time_picker_tag")
    }

    /**
     * 선택한 이미지 저장 수정일 경우 기존 파일 지우고 저장
     *
     * @param id artist의 id값
     * @author 윤성욱
     */
    @SuppressLint("SdCardPath")
    private fun saveImage(id: Long) {
        val imagesFolder = File(activity?.filesDir, "images")
        if (!imagesFolder.exists()) imagesFolder.mkdirs()
        if (id == -1L) {
            val deleteFile = File(imageSrc)
            if(deleteFile.exists())deleteFile.delete()
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
                if (data != -1) outputStream.write(buffer, 0, data)
            } while (data != -1)
            inputStream.close()
            outputStream.close()
        } catch (e: Exception) {
            Log.e("file", "파일이 만들어 지지 않음")
        }
    }


    /**
     * 갤러리에서 가져온 이미지를 보여주는 역할
     * @author 윤성욱
     */
    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                imageUri = it.data?.data                //uri 가져옴
                binding.ibArtist.setImageURI(imageUri) //그 uri 셋팅
                binding.ibArtist.setBackgroundColor(Color.TRANSPARENT)
            }
        }


    /**
     * 아티스트를 선택하고 돌아왔을 때 선택한 이미지가 남아있게 하기 위한 onResume 오버라이딩
     * @author 윤성욱
     */
    override fun onResume() {
        super.onResume()
        if (imageUri != null) {
            binding.ibArtist.setImageURI(imageUri)
            binding.ibArtist.setBackgroundColor(Color.TRANSPARENT)
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
    private fun getImageFromGallery(permission: String) {
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
        ActivityResultContracts.RequestPermission()) {
        when(it) {
            true -> {
                val intent = Intent(Intent.ACTION_PICK)
                intent.setDataAndType(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    "image/*"
                )
                startForResult.launch(intent)
            }
            false -> {
                toastMessage(resources.getString(R.string.permission_deny),requireActivity())
            }
        }
    }

}