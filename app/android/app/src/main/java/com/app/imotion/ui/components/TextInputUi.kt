package com.app.imotion.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.app.imotion.ui.theme.MotionGrey

/**
 * Created by hani.fakhouri on 2023-06-08.
 */

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextInputUi(
    title: String,
    hint: String,
    userInput: String,
    isInFocus: Boolean,
    clearFocusWhenDone: Boolean = false,
    onInputChange: (String) -> Unit,
    onDoneClick: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    if (isInFocus) {
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.W700,
            color = MaterialTheme.colors.onBackground,
        )
        VerticalSpacer(space = 4.dp)
        OutlinedTextField(
            value = userInput,
            onValueChange = onInputChange,
            label = {
                if (userInput.isEmpty()) {
                    Text(
                        text = hint,
                        style = MaterialTheme.typography.body2,
                        fontWeight = FontWeight.W400,
                        color = MotionGrey,
                    )
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = MaterialTheme.colors.onBackground,
                focusedBorderColor = MaterialTheme.colors.onBackground.copy(alpha = 0.4F),
                cursorColor = MaterialTheme.colors.primary.copy(alpha = 0.5F),
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (clearFocusWhenDone) {
                        focusManager.clearFocus()
                    }
                    keyboardController?.hide()
                    onDoneClick()
                }
            ),
            trailingIcon = {
                if (userInput.isNotEmpty()) {
                    Image(
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                onInputChange("")
                            },
                        colorFilter = ColorFilter.tint(color = MotionGrey),
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
        )
    }
}