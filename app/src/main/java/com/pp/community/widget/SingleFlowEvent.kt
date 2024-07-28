package com.pp.community.widget

import kotlinx.coroutines.flow.MutableSharedFlow

open class SingleFlowEvent<T> {
    private val mutableSharedFlow = MutableSharedFlow<T>()

    suspend fun emit(value: T) {
        mutableSharedFlow.emit(value)
    }

    val flow = mutableSharedFlow
}
