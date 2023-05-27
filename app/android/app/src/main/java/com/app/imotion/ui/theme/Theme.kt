package com.app.imotion.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = MainGreen,
    primaryVariant = MainGreen,
    secondary = LightGreen,
    secondaryVariant = LightGreen,
)

private val LightColorPalette = lightColors(

    // Color displayed most frequently across your app’s screens and components.
    primary = MainGreen,
    primaryVariant = MainGreen,
    onPrimary = Color.White,

    // Color provides more ways to accent and distinguish your product. Secondary colors are best for:
    //   - Floating action buttons
    //   - Selection controls, like checkboxes and radio buttons
    //   - Highlighting selected text
    //   - Links and headlines
    secondary = LightGreen,
    secondaryVariant = LightGreen,
    onSecondary = MotionBlack,

    // Background color appears behind scrollable content.
    background = MotionBackground,
    onBackground = MotionBlack,

    // Surface color is used on surfaces of components, such as cards, sheets and menus.
    surface = LightGreen,
    onSurface = MotionBlack,

    // Error color is used to indicate error within components, such as text fields.
    error = Color.Red,
    onError = Color.White,
)

@Composable
fun IMotionTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        //DarkColorPalette
        LightColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}