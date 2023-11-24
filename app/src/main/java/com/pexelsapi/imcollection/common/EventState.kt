package com.pexelsapi.imcollection.common

class EventState<T>(private val content: T) {
    private var isHandled = false

    @Synchronized
    fun getContentIfNotHandled(): T? {
        return takeIf { !isHandled }?.let {
            isHandled = true
            content
        }
    }
}