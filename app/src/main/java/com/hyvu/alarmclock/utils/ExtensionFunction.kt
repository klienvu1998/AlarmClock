package com.hyvu.alarmclock.utils

import android.util.Log

fun logD(tag: String, function: String, message: String) {
    Log.d(tag, "[$function]----$message")
}

fun logE(tag: String, function: String, message: String) {
    Log.e(tag, "[$function]----$message")
}