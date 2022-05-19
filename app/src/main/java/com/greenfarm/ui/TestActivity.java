package com.greenfarm.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.greenfarm.R;
import com.greenfarm.ui.main.SearchActivity;

//import org.tensorflow.lite.examples.detection.DetectorActivity;
import org.tensorflow.lite.examples.detection.customview.OverlayView;
import org.tensorflow.lite.examples.detection.env.ImageUtils;
import org.tensorflow.lite.examples.detection.env.Logger;
import org.tensorflow.lite.examples.detection.env.Utils;
import org.tensorflow.lite.examples.detection.tflite.Classifier;
import org.tensorflow.lite.examples.detection.tflite.YoloV5Classifier;
import org.tensorflow.lite.examples.detection.tracking.MultiBoxTracker;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
//import java.util.Random;

public class TestActivity extends AppCompatActivity {

    public static final float MINIMUM_CONFIDENCE_TF_OD_API = 0.5f;
    private static String TF_OD_API_MODEL_FILE = "best-fp16-sesame-s.tflite";

    private static String TF_OD_API_LABELS_FILE = "file:///android_asset/ctest.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
/*
        cameraButton = findViewById(R.id.cameraButton);
        detectButton = findViewById(R.id.detectButton);*/
        imageView = findViewById(R.id.imageView);

//        cameraButton.setOnClickListener(v -> startActivity(new Intent(TestActivity.this, DetectorActivity.class)));

        /*detectButton.setOnClickListener(v -> {
            Handler handler = new Handler();

            new Thread(() -> {
                final List<Classifier.Recognition> results = detector.recognizeImage(cropBitmap);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        handleResult(cropBitmap, results);
                    }
                });
            }).start();
        });*/

        if(getIntent().getStringExtra("class").equals("sesame")){
            TF_OD_API_MODEL_FILE = "best-fp16-sesame-s.tflite";
            TF_OD_API_LABELS_FILE = "file:///android_asset/ctest.txt";
        }else if(getIntent().getStringExtra("class").equals("red-bean")){
            TF_OD_API_MODEL_FILE = "best-fp16.tflite";
            TF_OD_API_LABELS_FILE = "file:///android_asset/btest.txt";
        }else if(getIntent().getStringExtra("class").equals("bean")){
            // 모델 필요
        }
        this.sourceBitmap = Utils.getBitmapFromAsset(TestActivity.this, getIntent().getStringExtra("image"));

        this.cropBitmap = Utils.processBitmap(sourceBitmap, TF_OD_API_INPUT_SIZE);

        this.imageView.setImageBitmap(cropBitmap);



        initBox();
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();


        Handler handler = new Handler();

        new Thread(() -> {
            long startTime = System.currentTimeMillis();
            final List<Classifier.Recognition> results = detector.recognizeImage(cropBitmap);

            handler.post(new Runnable() {
                @Override
                public void run() {
                    handleResult(cropBitmap, results);
                    long endTime = System.currentTimeMillis();
                    Log.d("Model running time", String.valueOf(((endTime - startTime))));
                    // 모델 실행시간 약 0.9 ~ 1초
                }
            });


        }).start();

        System.err.println(Double.parseDouble(configurationInfo.getGlEsVersion()));
        System.err.println(configurationInfo.reqGlEsVersion >= 0x30000);
        System.err.println(String.format("%X", configurationInfo.reqGlEsVersion));
    }

    private static final Logger LOGGER = new Logger();

    public static final int TF_OD_API_INPUT_SIZE = 640;

    private static final boolean TF_OD_API_IS_QUANTIZED = false;



    // Minimum detection confidence to track a detection.
    private static final boolean MAINTAIN_ASPECT = true;
    private Integer sensorOrientation = 90;

    private Classifier detector;

    private Matrix frameToCropTransform;
    private Matrix cropToFrameTransform;
    private MultiBoxTracker tracker;
    private OverlayView trackingOverlay;

    protected int previewWidth = 0;
    protected int previewHeight = 0;

    private Bitmap sourceBitmap;
    private Bitmap cropBitmap;

    private Button cameraButton, detectButton;
    private ImageView imageView;

    private void initBox() {
        previewHeight = TF_OD_API_INPUT_SIZE;
        previewWidth = TF_OD_API_INPUT_SIZE;
        frameToCropTransform =
                ImageUtils.getTransformationMatrix(
                        previewWidth, previewHeight,
                        TF_OD_API_INPUT_SIZE, TF_OD_API_INPUT_SIZE,
                        sensorOrientation, MAINTAIN_ASPECT);

        cropToFrameTransform = new Matrix();
        frameToCropTransform.invert(cropToFrameTransform);

        tracker = new MultiBoxTracker(this);
        trackingOverlay = findViewById(R.id.tracking_overlay);
        trackingOverlay.addCallback(
                canvas -> tracker.draw(canvas));

        tracker.setFrameConfiguration(TF_OD_API_INPUT_SIZE, TF_OD_API_INPUT_SIZE, sensorOrientation);

        try {
            detector =
                    YoloV5Classifier.create(
                            getAssets(),
                            TF_OD_API_MODEL_FILE,
                            TF_OD_API_LABELS_FILE,
                            TF_OD_API_IS_QUANTIZED,
                            TF_OD_API_INPUT_SIZE);
        } catch (final IOException e) {
            e.printStackTrace();
            LOGGER.e(e, "Exception initializing classifier!");
            Toast toast =
                    Toast.makeText(
                            getApplicationContext(), "Classifier could not be initialized", Toast.LENGTH_SHORT);
            toast.show();
            finish();
        }
    }

    private void handleResult(Bitmap bitmap, List<Classifier.Recognition> results) {
        final Canvas canvas = new Canvas(bitmap);
        final Paint paint = new Paint();
        final Paint textPaint = new Paint();

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1.0f);

        textPaint.setTextSize(20);
        textPaint.setColor(Color.BLUE);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        textPaint.setAntiAlias(false);
        textPaint.setAlpha(255);

        if(results.size() == 0){
            // show image guide line
//            Intent intent = new Intent(this, SearchActivity.class);
//            startActivity(intent);
        }

        for(int i =0 ;i<results.size();i++){
            final Classifier.Recognition result = results.get(i);
            final RectF location = result.getLocation();
            Log.d("l", String.valueOf(location));
            if (location != null && result.getConfidence() >= MINIMUM_CONFIDENCE_TF_OD_API) {
                Log.d("title",result.getTitle());
                if(result.getTitle().equals("rhizopus")){
                    paint.setColor(Color.RED);
                }else if(result.getTitle().equals("세균잎마름병") ){
                    paint.setColor(Color.BLUE);
                }else if(result.getTitle().equals("흰가루병")){
                    paint.setColor(Color.GREEN);
                }else{
                    paint.setColor(Color.CYAN);
                }
                canvas.drawRect(location, paint);
                String labelString =
                        !TextUtils.isEmpty(result.getTitle())
                                ? String.format("%s %.2f", result.getTitle(), (100 * result.getConfidence()))
                                : String.format("%.2f", (100 * result.getConfidence()));
                canvas.drawText(labelString, location.left,location.top,textPaint);
//                cropToFrameTransform.mapRect(location);
//
//                result.setLocation(location);
//                Log.d("2",result.getLocation().toString());
//                mappedRecognitions.add(result);
            }
        }
//       tracker.trackResults(mappedRecognitions, 1);
//        trackingOverlay.postInvalidate();
        imageView.setImageBitmap(bitmap);
        // 서버에 병해충 이름 사진 등 전달
        // 일정 반경 내 유저 아이디 수신
        String[] user = {"user1","user2"};
        // 파이어베이스 데이터베이스에서 해당 유저 아이디 토큰 받아옴
        List<String> tokens = new ArrayList<String>();
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mUser = mDatabase.child("tokens");

        mUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(int i = 0; i<user.length;i++){
                    Log.d("ds",user[i]+snapshot.child(user[i]).getValue(String.class));
                    tokens.add(snapshot.child(user[i]).getValue(String.class));
                }
                Log.d("tokens",tokens.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // fcm서버에 해당 토큰에 대해 알림 요청
    }
}
