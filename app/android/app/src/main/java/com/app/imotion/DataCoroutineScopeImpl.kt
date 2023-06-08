package com.app.imotion

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Created by hani.fakhouri on 2023-06-08.
 */
class DataCoroutineScopeImpl @Inject constructor() : DataCoroutineScope {
    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job
}