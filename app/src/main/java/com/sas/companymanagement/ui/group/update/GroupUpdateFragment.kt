package com.sas.companymanagement.ui.group.update

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.jakewharton.rxbinding4.view.clicks
import com.sas.companymanagement.R
import com.sas.companymanagement.databinding.FragmentGroupUpdateBinding
import com.sas.companymanagement.ui.common.ViewBindingBaseFragment
import com.sas.companymanagement.ui.group.Group
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.concurrent.TimeUnit

class GroupUpdateFragment :
    ViewBindingBaseFragment<FragmentGroupUpdateBinding>(FragmentGroupUpdateBinding::inflate) {


    companion object {
        fun newInstance() = GroupUpdateFragment()
    }

    private val viewModel: GroupUpdateViewModel by viewModels()
    private val compositeDisposable = CompositeDisposable()
    private var imageSrc = ""
    private var imageUri: Uri? = null
    private var name = ""

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
           /* val imagePath = "DB에 있는 주소"
            val bitmap = BitmapFactory.decodeFile(imagePath)
            ibGroup.setImageBitmap(bitmap)*/
        }

        with(compositeDisposable) {
            with(binding) {
                cAdd
                    .clicks()
                    .observeOn(Schedulers.io())
                    .throttleFirst(500, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        addArtistChip()
                    }, {
                        Log.e("RX_ERROR", compositeDisposable.toString())
                    })

            }
            with(binding){
                ibGroup
                    .clicks()
                    .observeOn(Schedulers.io())
                    .throttleFirst(500, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        val intent = Intent(Intent.ACTION_PICK)
                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*")
                        startForResult.launch(intent)
                    }, {
                        Log.e("IB_ERROR", compositeDisposable.toString())
                    })
            }
        }

        listenerSetup()
        observerSetup()
    }

    private fun clearFields() {
        with(binding) {
            teGroupName.setText("")
        }
    }

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){
            imageUri = it.data?.data                //uri 가져옴
            binding.ibGroup.setImageURI(imageUri) //그 uri 셋팅
        }
    }

    //이미지 저장
    private fun saveImage(){
        val imagesFolder = File(activity?.filesDir, "images")
        if (!imagesFolder.exists()) {
            imagesFolder.mkdirs()
        }
        val imageName = System.currentTimeMillis().toString()
        imageSrc = "${activity?.filesDir}/images/${imageName}.jpg"
        val newFile = File(imageSrc)
        imageToFile(requireActivity() ,imageUri!!, newFile)
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
                if (data != -1){
                    outputStream.write(buffer,0,data)
                }
            }while(data != -1)
            inputStream.close()
            outputStream.close()
        } catch (e: Exception) {
            Log.e("file","파일이 만들어 지지 않음")
        }
    }

    private fun addArtistChip() {
        /*
        var chipName = "이원형"

        binding.cgArtistUpdate.removeView(binding.cAdd)

        binding.cgArtistUpdate.addView(Chip(context).apply {
            chipIcon = ContextCompat.getDrawable(context, R.drawable.ic_check_24)
            text = chipName
            isCloseIconVisible = true
            setOnCloseIconClickListener { binding.cgArtistUpdate.removeView(this) }
        })
        binding.cgArtistUpdate.addView(binding.cAdd)
        */
        val action = GroupUpdateFragmentDirections.actionGroupUpdateFragmentToFragmentArtist()
        findNavController().navigate(action)

    }

    @SuppressLint("SetTextI18n")
    private fun listenerSetup() {
        binding.tbGroupUpdate.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.menu_update) {
                name = binding.teGroupName.text.toString()

                if (name.isNotEmpty()) {
                    viewModel.getAllGroups()
                    viewModel.insertGroup(Group(name,
                        imageSrc))
                    saveImage()

                    clearFields()
                }
            }
            true
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observerSetup() {
        viewModel.getAllGroups()?.observe(viewLifecycleOwner) { Groups ->
            for (item in Groups.indices) Log.e("Insert", Groups.get(item).id.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }
}