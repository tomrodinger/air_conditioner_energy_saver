package com.app.imotion.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.imotion.R
import com.app.imotion.ui.theme.PreviewTheme

/**
 * Created by hani@fakhouri.eu on 2023-05-23.
 */

@Composable
fun IMotionSurface(content: @Composable () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.primary,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier.size(90.dp, 193.dp),
                painter = painterResource(R.drawable.bg1),
                contentDescription = ""
            )
            Image(
                modifier = Modifier.size(167.dp, 340.dp),
                painter = painterResource(R.drawable.bg2),
                contentDescription = ""
            )
            Image(
                modifier = Modifier
                    .size(370.dp, 905.dp)
                    .align(Alignment.TopEnd),
                contentScale = ContentScale.Crop,
                painter = painterResource(R.drawable.bg3),
                contentDescription = ""
            )
        }
        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun IMotionSurfacePreview() {
    PreviewTheme {
        IMotionSurface {
            Text("This is a test!")
        }
    }
}