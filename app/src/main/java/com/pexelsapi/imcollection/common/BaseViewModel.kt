package com.pexelsapi.imcollection.common

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    protected fun addDisposable(disposable: () -> Disposable) {
        compositeDisposable.add(disposable.invoke())
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}