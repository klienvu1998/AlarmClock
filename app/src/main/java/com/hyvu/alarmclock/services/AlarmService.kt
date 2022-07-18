package com.hyvu.alarmclock.services

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.*
import androidx.core.app.NotificationCompat
import com.hyvu.alarmclock.AlarmClockApplication
import com.hyvu.alarmclock.R
import com.hyvu.alarmclock.broadcasts.AlarmBroadcastReceiver
import com.hyvu.alarmclock.views.RingActivity

class AlarmService: Service() {

    private var mediaPlayer: MediaPlayer? = null
    private var vibrator: Vibrator? = null

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.alarm)
        mediaPlayer?.isLooping = true

        @Suppress("DEPRECATION")
        vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notificationIntent = Intent(this, RingActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        val alarmTitle = intent?.getStringExtra(AlarmBroadcastReceiver.TITLE) ?: ""

        val notification = NotificationCompat.Builder(this, AlarmClockApplication.ALARM_SERVICE_CHANNEL_ID)
            .setContentTitle(alarmTitle)
            .setContentText("Ring ....")
            .setSmallIcon(R.drawable.ic_alarm_list)
            .setContentIntent(pendingIntent)
            .build()

        mediaPlayer?.start()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator?.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            val pattern = longArrayOf(0, 100, 1000)
            vibrator?.vibrate(pattern, 0)
        }

        startForeground(1, notification)

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.stop()
        vibrator?.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}