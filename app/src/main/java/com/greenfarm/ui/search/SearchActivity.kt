package com.greenfarm.ui.main

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.greenfarm.R
import com.greenfarm.databinding.ActivityMainBinding
import com.greenfarm.databinding.ActivitySearchBinding
import com.greenfarm.ui.BaseActivity
import com.greenfarm.ui.TestActivity
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class SearchActivity: BaseActivity<ActivitySearchBinding>(ActivitySearchBinding::inflate) {
    var filepath : String? = null
    var isImageUploaded = false
    var className : String? = null
    override fun initAfterBinding() {
        // 뒤로가기
        binding.categoryBackIc.setOnClickListener{
            finish()
        }

        // 콩 검출
        binding.categoryBeanBt.setOnClickListener{
            binding.searchCategoryPartView.visibility = View.GONE
            binding.searchUploadPartView.visibility = View.VISIBLE
            className = "bean"
        }

        // 팥 검출
        binding.categoryRedbeanBt.setOnClickListener{
            binding.searchCategoryPartView.visibility = View.GONE
            binding.searchUploadPartView.visibility = View.VISIBLE
            className = "red-bean"
        }

        // 참깨 검출
        binding.categorySesameBt.setOnClickListener{
            binding.searchCategoryPartView.visibility = View.GONE
            binding.searchUploadPartView.visibility = View.VISIBLE
            className = "sesame"
        }


        // 2Step에서 뒤로가기
        binding.uploadBackIc.setOnClickListener{
            binding.searchUploadPartView.visibility = View.GONE
            binding.searchCategoryPartView.visibility = View.VISIBLE
            binding.uploadImage.setImageBitmap(null)
        }

        // 사진 촬영 클릭
        binding.uploadCameraBgIv.setOnClickListener{
            var check : Boolean = checkPermission()
            Log.d("permission","${check}")
            if(check == true){
                dispatchTakePictureIntent()
            }
            else {
                requestPermission()
            }
        }

        // 갤러리 가져오기 클릭
        binding.uploadGallaryBgIv.setOnClickListener{
            openGalleryForImage()
        }


        // 병해충 검출 시작 클릭
        binding.uploadStartTv.setOnClickListener{
            Log.d("start","start")
            if(isImageUploaded){
                val intent= Intent(this, TestActivity::class.java)
                intent.putExtra("image",filepath)
                intent.putExtra("class",className)
                startActivity(intent)
            }
            Log.d("end","end")
        }

    }


    // Camera

    // Camera 권한 요청
    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(READ_EXTERNAL_STORAGE, CAMERA),
            1)
    }

    // Camera 권한 체크
    private fun checkPermission() : Boolean {
        return (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
    }

    // Camera 권한요청 결과
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // 권한 Get
        }
        else {
            // 권한 Don't Get
        }
    }

    // Camera OPEN
    private fun dispatchTakePictureIntent() {
        Log.d("실행","실행")
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // 찍은 사진을 그림파일로 만들기
            val photoFile: File? =
                try {
                    createImageFile()
                } catch (ex: IOException) {
                    Log.d("TAG", "그림파일 만드는도중 에러생김")
                    null
                }

            // 그림파일을 성공적으로 만들었다면 onActivityForResult로 보내기
            photoFile?.also {
                val photoURI: Uri = FileProvider.getUriForFile(
                    this, "com.greenfarm.fileprovider", it
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, 1)
                isImageUploaded = true
            }
        }
    }

    // Camera 이미지 -> 파일로 저장
    private fun createImageFile() : File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            filepath = absolutePath
        }
    }


    // Gallery

    private fun openGalleryForImage(){
        var intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        intent.putExtra(Intent.EXTRA_INITIAL_INTENTS, true)
        intent.type = "image/*"
        intent.action = Intent.ACTION_OPEN_DOCUMENT

        startActivityForResult(intent, 2)
        isImageUploaded = true
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode){
            1 -> {
                if(requestCode == 1 && resultCode == Activity.RESULT_OK){

                    // 카메라로부터 받은 데이터가 있을경우에만
                    val file = File(filepath)
                    if (Build.VERSION.SDK_INT < 28) {
                        val bitmap = MediaStore.Images.Media
                            .getBitmap(contentResolver, Uri.fromFile(file))  //Deprecated
                        binding.uploadImage.setImageBitmap(bitmap)
                    }
                    else{
                        val decode = ImageDecoder.createSource(this.contentResolver,
                            Uri.fromFile(file))
                        val bitmap = ImageDecoder.decodeBitmap(decode)
                        binding.uploadImage.setImageBitmap(bitmap)
                    }
                }
            }

            2 -> {
                if (resultCode == Activity.RESULT_OK && requestCode == 2){
                    data?.data?.let { uri ->
                        val imageUri : Uri? = data?.data
                        if (imageUri != null){
                            contentResolver.takePersistableUriPermission(imageUri,Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            var bitmap : Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                            saveBitmapAsPNGFile(bitmap)
                        }
                    }
                    binding.uploadImage.setImageURI(data?.data) // handle chosen image
                }
            }
        }
    }

    private fun newPngFileName() : String {
        val sdf = SimpleDateFormat("yyyyMMdd__HHmmss")
        val filename = sdf.format(System.currentTimeMillis())
        return "${filename}.png"
    }

    private fun saveBitmapAsPNGFile(bitmap: Bitmap){
        val path = File(filesDir, "image")
        if(!path.exists()){
            path.mkdirs()
        }
        val photoName = newPngFileName()
        val file = File(path,photoName)
        var imageFile : OutputStream? = null
        try {
            file.createNewFile()
            imageFile = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 60, imageFile)
            imageFile.close()

            filepath = file.absolutePath.toString()
        }catch (e: Exception){

        }
    }



}