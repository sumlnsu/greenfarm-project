package com.greenfarm.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.greenfarm.R
import com.greenfarm.ui.main.MainActivity
import java.util.*
import java.util.stream.DoubleStream.builder
import java.util.stream.IntStream.builder


class MyFirebaseMessagingService : FirebaseMessagingService() {
    companion object {
        const val TAG = "MessagingService"
        private const val CHANNEL_NAME = "Push Notification"
        private const val CHANNEL_DESCRIPTION = "Push Notification 을 위한 채널"
        private const val CHANNEL_ID = "Channel Id"
    }

    /* 토큰 생성 메서드 */
    override fun onNewToken(token: String) {
        super.onNewToken(token)
//                    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
//            val myRef = database.getReference("message")
//            myRef.setValue("hi")
    }


    /* 메세지 수신 메서드 */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        super.onMessageReceived(remoteMessage)
        Log.d(TAG, "onMessageReceived() - remoteMessage : $remoteMessage")
        Log.d(TAG, "onMessageReceived() - from : ${remoteMessage.from}")
        Log.d(TAG, "onMessageReceived() - notification : ${remoteMessage.notification?.body}")

        val type = remoteMessage.data["type"]?.let { NotificationType.valueOf(it) } ?: kotlin.run {
            NotificationType.NORMAL  //type 이 null 이면 NORMAL type 으로 처리
        }
        val title = remoteMessage.data["title"]
        val message = remoteMessage.data["message"]

        Log.d(TAG, "onMessageReceived() - type : $type")
        Log.d(TAG, "onMessageReceived() - title : $title")
        Log.d(TAG, "onMessageReceived() - message : $message")

        sendNotification(remoteMessage)
    }


    /* 알림 생성 메서드 */
    private fun sendNotification(remoteMessage: RemoteMessage) {
        // RequestCode, Id를 고유값으로 지정하여 알림이 개별 표시되도록 함
        val uniId: Int = (System.currentTimeMillis() / 7).toInt()

        // 일회용 PendingIntent
        // PendingIntent : Intent 의 실행 권한을 외부의 어플리케이션에게 위임한다.
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) // Activity Stack 을 경로만 남긴다. A-B-C-D-B => A-B
        val pendingIntent = PendingIntent.getActivity(this, uniId, intent, PendingIntent.FLAG_ONE_SHOT)

        // 알림 채널 이름
        val channelId = "channel id"

        // 알림 소리
//        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        // 알림에 대한 UI 정보와 작업을 지정한다.
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher) // 아이콘 설정
            .setContentTitle(remoteMessage.data["body"].toString()) // 제목
            .setContentText(remoteMessage.data["title"].toString()) // 메시지 내용
            .setAutoCancel(true)
//            .setSound(soundUri) // 알림 소리
            .setContentIntent(pendingIntent) // 알림 실행 시 Intent

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // 오레오 버전 이후에는 채널이 필요하다.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Notice", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        // 알림 생성
        notificationManager.notify(uniId, notificationBuilder.build())
    }


    /* 알림 설정 메서드 */
//    private fun createNotification(
//        type: NotificationType,
//        title: String?,
//        message: String?
//    ): Notification {
//
//        val intent = Intent(this, MainActivity::class.java).apply {
//            putExtra("notificationType", " ${type.title} 타입 ")
//            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
//        }
//        val pendingIntent = PendingIntent.getActivity(this, (System.currentTimeMillis()/100).toInt(), intent, FLAG_UPDATE_CURRENT)  //알림이 여러개 표시되도록 requestCode 를 추가
//
//        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
//            .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
//            .setContentTitle(title)
//            .setContentText(message)
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//            .setContentIntent(pendingIntent)  //알림 눌렀을 때 실행할 Intent 설정
//            .setAutoCancel(true)  //클릭 시 자동으로 삭제되도록 설정
//
//        //type 에 따라 style 설정
//        when (type) {
//            NotificationType.NORMAL -> Unit
//            NotificationType.EXPANDABLE -> {
//                notificationBuilder.setStyle(
//                    NotificationCompat.BigTextStyle()
//                        .bigText("$message \n 😀 😃 😄 😁 😆 😅 😂 🤣 🥲 ☺️ 😊 😇 🙂 🙃 😉 😌 😍 🥰 😘 😗 😙 😚 😋 😛 😝 😜 🤪 🤨 🧐 🤓 😎 🥸 🤩 🥳 😏 😒 😞 😔 😟 😕 🙁 ☹️ 😣 😖 😫 😩 🥺 😢 😭 😤 😠 😡 🤬 🤯 😳 🥵 🥶 😱 😨 😰 😥 😓 🤗 🤔 🤭 🤫 🤥 😶 😐 😑 😬 🙄 😯 😦 😧 😮 😲 🥱 😴 🤤 😪 😵 🤐 🥴 🤢 🤮 🤧 😷 🤒 🤕")
//                )
//            }
//            NotificationType.CUSTOM -> {
//                notificationBuilder.setStyle(
//                    NotificationCompat.DecoratedCustomViewStyle()
//                )
//                    .setCustomContentView(
//                        RemoteViews(
//                            packageName,
//                            R.layout.view_custom_notification
//                        ).apply {
//                            setTextViewText(R.id.tv_custom_title, title)
//                            setTextViewText(R.id.tv_custom_message, message)
//                        }
//                    )
//            }
//        }
//        return notificationBuilder.build()
//    }


}