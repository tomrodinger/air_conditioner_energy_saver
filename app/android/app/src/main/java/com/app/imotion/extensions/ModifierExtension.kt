package com.app.imotion.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Created by hani@fakhouri.eu
 */
@Composable
fun Modifier.applyIf(
    condition: Boolean,
    modifierFunction: @Composable Modifier.() -> Modifier
) = this.run {
    if (condition) {
        this.modifierFunction()
    } else {
        this
    }
}