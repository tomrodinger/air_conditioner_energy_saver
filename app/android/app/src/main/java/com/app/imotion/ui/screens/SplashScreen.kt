package com.app.imotion.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.imotion.R
import com.app.imotion.ui.components.VerticalSpacer
import com.app.imotion.ui.theme.PreviewTheme

/**
 * Created by hani@fakhouri.eu on 2023-05-22.
 */

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 44.dp),
            contentScale = ContentScale.Crop,
            painter = painterResource(R.drawable.logo),
            contentDescription = "Logo"
        )
        Column(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Text(
                text = "Energy Saver",
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.W500,
                color = MaterialTheme.colors.onPrimary,
            )
            VerticalSpacer(space = 30.dp)
            LinearProgressIndicator(
                modifier = Modifier
                    .width(142.dp)
                    .height(10.dp),
                color = MaterialTheme.colors.secondary,
                backgroundColor = MaterialTheme.colors.onPrimary,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {
    PreviewTheme {
        SplashScreen()
    }
}