package com.app.imotion.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.imotion.R
import com.app.imotion.ui.theme.PreviewTheme


/**
 * Created by hani@fakhouri.eu on 2023-05-23.
 */

data class HeaderIconAction(
    @DrawableRes val iconRes: Int,
    val action: () -> Unit,
)

@Composable
fun MainPageHeader(
    onSchedulingClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp),
    ) {
        Column(
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Text(
                text = "Welcome",
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.W500,
                color = MaterialTheme.colors.onPrimary,
            )
            Image(
                modifier = Modifier.size(width = 80.dp, height = 34.dp),
                painter = painterResource(R.drawable.logo_main_header),
                contentDescription = "back"
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(36.dp)
                .clickable { onSchedulingClick() }
                .background(
                    color = MaterialTheme.colors.secondary.copy(alpha = 0.3F),
                    shape = RoundedCornerShape(4.dp)
                ),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                modifier = Modifier.size(20.dp),
                painter = painterResource(R.drawable.schedule),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary),
                contentDescription = "action 2"
            )
        }
    }
}

@Composable
fun Header(
    title: String,
    iconAction1: HeaderIconAction? = null,
    iconAction2: HeaderIconAction? = null,
    onBack: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 18.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.0F),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                modifier = Modifier
                    .clickable { onBack() }
                    .padding(8.dp)
                    .size(24.dp),
                painter = painterResource(R.drawable.back),
                contentDescription = "back"
            )
            HorizontalSpacer(space = 8.dp)
            Text(
                text = title,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.W700,
                color = MaterialTheme.colors.onPrimary,
            )
        }
        if (iconAction1 != null || iconAction2 != null) {
            Box(
                contentAlignment = Alignment.Center,
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    iconAction1?.let { iconAction1 ->
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clickable { iconAction1.action() }
                                .background(
                                    color = MaterialTheme.colors.secondary.copy(alpha = 0.3F),
                                    shape = RoundedCornerShape(4.dp)
                                ),
                            contentAlignment = Alignment.Center,
                        ) {
                            Image(
                                modifier = Modifier.size(22.dp),
                                painter = painterResource(iconAction1.iconRes),
                                colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary),
                                contentDescription = "action 1"
                            )
                        }
                    }
                    iconAction2?.let { iconAction2 ->
                        HorizontalSpacer(space = 12.dp)
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clickable { iconAction2.action() }
                                .background(
                                    color = MaterialTheme.colors.secondary.copy(alpha = 0.3F),
                                    shape = RoundedCornerShape(4.dp)
                                ),
                            contentAlignment = Alignment.Center,
                        ) {
                            Image(
                                modifier = Modifier.size(22.dp),
                                painter = painterResource(iconAction2.iconRes),
                                colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary),
                                contentDescription = "action 2"
                            )
                        }
                    }
                }
            }
        }
    }

}

@Preview(showBackground = true, heightDp = 86)
@Composable
private fun HeaderPreview() {
    PreviewTheme {
        IMotionSurface {
            Header(
                title = "Add Device Using Scan Code",
                iconAction1 = HeaderIconAction(
                    iconRes = R.drawable.refresh,
                    action = {}
                ),
                iconAction2 = HeaderIconAction(
                    iconRes = R.drawable.add,
                    action = {}
                ),
                onBack = {}
            )
        }
    }
}

@Preview(showBackground = true, heightDp = 86)
@Composable
private fun MainPageHeaderPreview() {
    PreviewTheme {
        IMotionSurface {
            MainPageHeader(
                onSchedulingClick = {}
            )
        }
    }
}