package com.hyvu.alarmclock.utils

// out: can set superType : ex: Production<Food> = FoodStore()
open class Event<out T>(private val content: T) {
    var hasBeenHandled = false
        private set //allow external only read value

    // Single Live Data (support multiple observers)
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    // Like normal livedata
    fun peekContent(): T = content
}