package com.app.imotion.extensions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * Created by hani.fakhouri on 2023-05-29.
 */

// Helper function to collect flows in a lifecycle aware way
// See: https://medium.com/androiddevelopers/a-safer-way-to-collect-flows-from-android-uis-23080b1f8bda
inline fun <T> Flow<T>.collectWith(
    owner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline action: suspend CoroutineScope.(T) -> Unit): Job {
    return owner.lifecycleScope.launch {
        owner.repeatOnLifecycle(minActiveState) {
            collect {
                action(it)
            }
        }
    }
}