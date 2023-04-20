package com.example.common.mvp

import androidx.annotation.CallSuper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

abstract class BasePresenter<V : MvpView> : MvpPresenter<V>,CoroutineScope {
    protected var view: V? = null
        private set

    override val coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.Main.immediate


    @CallSuper
    override fun attach(view: V) {
        this.view = view
    }

    @CallSuper
    override fun detach() {
        this.view = null
    }
}