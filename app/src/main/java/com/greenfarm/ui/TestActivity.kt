package com.greenfarm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.greenfarm.R
import android.app.ActivityManager
import android.graphics.*
import org.tensorflow.lite.examples.detection.tflite.Classifier.Recognition
import org.tensorflow.lite.examples.detection.tflite.Classifier
import org.tensorflow.lite.examples.detection.tflite.YoloV5Classifier
import android.widget.Toast
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.widget.ImageView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.greenfarm.data.entities.FirebaseViewModel
import com.google.firebase.database.DatabaseError
import com.greenfarm.data.nearby.NearbyUser
import com.greenfarm.data.nearby.NearbyUserResult
import com.greenfarm.utils.getJwt
import org.tensorflow.lite.examples.detection.env.Logger
import org.tensorflow.lite.examples.detection.env.Utils
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.HashSet

//import org.tensorflow.lite.examples.detection.DetectorActivity;
//import java.util.Random;
class TestActivity : AppCompatActivity() {
    private var filepath: String? = null
    private var detector: Classifier? = null
    private var sourceBitmap: Bitmap? = null
    private var cropBitmap: Bitmap? = null
    var imageView: ImageView? = null
    var userList = ArrayList<String>()
    var isLog : Boolean? = null
    val disease: MutableList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val configurationInfo = activityManager.deviceConfigurationInfo
        val handler = Handler()

        imageView = findViewById(R.id.test_iv)
        isLog = intent!!.getBooleanExtra("IsLog",false)

        if (isLog == false) {
            if (intent.getStringExtra("class") == "sesame") {
                TF_OD_API_MODEL_FILE = "best-fp16-sesame-s.tflite"
                TF_OD_API_LABELS_FILE = "file:///android_asset/sesame-label.txt"
            } else if (intent.getStringExtra("class") == "red-bean") {
                TF_OD_API_MODEL_FILE = "best-fp16-redbean.tflite"
                TF_OD_API_LABELS_FILE = "file:///android_asset/red-bean-label.txt"
            } else if (intent.getStringExtra("class") == "bean") {
                // 모델 필요
            }
            sourceBitmap =
                Utils.getBitmapFromAsset(this@TestActivity, intent.getStringExtra("image"))
            cropBitmap = Utils.processBitmap(sourceBitmap, TF_OD_API_INPUT_SIZE)
            imageView!!.setImageBitmap(cropBitmap)
            initBox()
            Thread {
                val startTime = System.currentTimeMillis()
                val results = detector!!.recognizeImage(cropBitmap)
                handler.post {
                    handleResult(cropBitmap, results)
                    val endTime = System.currentTimeMillis()
                    Log.d("Model running time", (endTime - startTime).toString())
                    // 모델 실행시간 약 0.9 ~ 1초
                }
            }.start()
            System.err.println(configurationInfo.glEsVersion.toDouble())
            System.err.println(configurationInfo.reqGlEsVersion >= 0x30000)
            System.err.println(String.format("%X", configurationInfo.reqGlEsVersion))
        }
        else {
            // 지난 기록 화면
        }
    }


    private fun initBox() {
        try {
            detector = YoloV5Classifier.create(
                assets,
                TF_OD_API_MODEL_FILE,
                TF_OD_API_LABELS_FILE,
                TF_OD_API_IS_QUANTIZED,
                TF_OD_API_INPUT_SIZE
            )
        } catch (e: IOException) {
            e.printStackTrace()
            LOGGER.e(e, "Exception initializing classifier!")
            val toast = Toast.makeText(
                applicationContext, "Classifier could not be initialized", Toast.LENGTH_SHORT
            )
            toast.show()
            finish()
        }
    }

    private fun handleResult(bitmap: Bitmap?, results: List<Recognition>) {
        val canvas = Canvas(bitmap!!)
        val paint = Paint()
        val textPaint = Paint()
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 1.0f
        textPaint.textSize = 20f
        textPaint.color = Color.BLUE
        textPaint.style = Paint.Style.FILL_AND_STROKE
        textPaint.isAntiAlias = false
        textPaint.alpha = 255
        if (results.size == 0) {
            // show image guide line
//            Intent intent = new Intent(this, SearchActivity.class);
//            startActivity(intent);
        } else {

            for (i in results.indices) {
                val result = results[i]
                val location = result.location
                Log.d("l", location.toString())
                if (intent.getStringExtra("class") == "sesame") {
                    if (location != null && result.confidence >= MINIMUM_CONFIDENCE_TF_OD_API) {
                        Log.d("title", result.title)
                        if (result.title == "세균성점무늬병") {
                            paint.color = Color.RED
                        } else if (result.title == "흰가루병") {
                            paint.color = Color.BLUE
                        } else {
                            paint.color = Color.CYAN
                        }
                        disease.add(result.title)
                        canvas.drawRect(location, paint)
                        val labelString = if (!TextUtils.isEmpty(result.title)) String.format(
                            "%s %.2f",
                            result.title,
                            100 * result.confidence
                        ) else String.format("%.2f", 100 * result.confidence)
                        canvas.drawText(labelString, location.left, location.top, textPaint)
                        //                cropToFrameTransform.mapRect(location);
                        //
                        //                result.setLocation(location);
                        //                Log.d("2",result.getLocation().toString());
                        //                mappedRecognitions.add(result);
                    }
                } else if (intent.getStringExtra("class") == "red-bean") {
                    if (location != null && result.confidence >= MINIMUM_CONFIDENCE_TF_OD_API) {
                        Log.d("title", result.title)
                        if (result.title == "rhizopus") {
                            paint.color = Color.RED
                        } else if (result.title == "세균잎마름병") {
                            paint.color = Color.BLUE
                        } else if (result.title == "흰가루병") {
                            paint.color = Color.GREEN
                        } else {
                            paint.color = Color.CYAN
                        }
                        disease.add(result.title)
                        canvas.drawRect(location, paint)
                        val labelString = if (!TextUtils.isEmpty(result.title)) String.format(
                            "%s %.2f",
                            result.title,
                            100 * result.confidence
                        ) else String.format("%.2f", 100 * result.confidence)
                        canvas.drawText(labelString, location.left, location.top, textPaint)
                    }
                }
            }
//            val diseaseSet = HashSet(disease)
            imageView!!.setImageBitmap(bitmap)
            // 서버에 병해충 이름 사진 등 전달
            // 일정 반경 내 유저 아이디 수신
            var userId = intent.getStringExtra("user-id").toString()
            var jwtToken = getJwt().toString()
            NearbyUser.getNearbyUser(this,userId)
        }
    }
    fun setUserList(nearbyUsers: List<String>) {
        for (i in nearbyUsers){
            userList.add(i)
        }

        val user = arrayOf("user1", "user2")
        val diseaseSet = HashSet(disease)
        // 파이어베이스 데이터베이스에서 해당 유저 아이디 토큰 받아옴
        val tokens: MutableList<String?> = ArrayList()
        val mDatabase: DatabaseReference = FirebaseDatabase.getInstance().reference
        val mUser = mDatabase.child("tokens")
        mUser.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (i in user.indices) {
                    Log.d(
                        "ds", nearbyUsers[i] + snapshot.child(nearbyUsers[i]).getValue(
                            String::class.java
                        )
                    )
                    tokens.add(snapshot.child(nearbyUsers[i]).getValue(String::class.java))
                    val firebaseViewModel = FirebaseViewModel(application)
                    // fcm서버에 해당 토큰에 대해 알림 요청
                    firebaseViewModel.sendNotification(
                        tokens[i]!!,
                        nearbyUsers[i],
                        "발견된 병해충: $diseaseSet"
                    )
                }
                Log.d("tokens", tokens.toString())
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
    private fun saveBitmapAsPNGFile(bitmap: Bitmap) {
        val path = File(this.filesDir, "image")
        if (!path.exists()) {
            path.mkdirs()
        }
        val photoName = newPngFileName()
        val file = File(path, photoName)
        var imageFile = null as OutputStream?
        try {
            file.createNewFile()
            imageFile = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 60, imageFile)
            imageFile!!.close()
            filepath = file.absolutePath.toString()
        } catch (var7: Exception) {
        }
    }

    private fun newPngFileName(): String {
        val sdf = SimpleDateFormat("yyyyMMdd__HHmmss")
        val filename = sdf.format(System.currentTimeMillis())
        return "$filename.png"
    }

    companion object {
        const val MINIMUM_CONFIDENCE_TF_OD_API = 0.5f
        private var TF_OD_API_MODEL_FILE = "best-fp16-sesame-m.tflite"
        private var TF_OD_API_LABELS_FILE = "file:///android_asset/ctest.txt"
        private val LOGGER = Logger()
        const val TF_OD_API_INPUT_SIZE = 640
        private const val TF_OD_API_IS_QUANTIZED = false

        // Minimum detection confidence to track a detection.
        private const val MAINTAIN_ASPECT = true
    }
}