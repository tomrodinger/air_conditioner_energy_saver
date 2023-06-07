package com.app.imotion.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

/**
 * Created by hani.fakhouri on 2023-06-05.
 */

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> DropDownSelector(
    values: List<T>,
    currentValue: T? = null,
    valueStringProvider: (T) -> String,
    noSelectionText: String,
    onSelected: (T) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxWidth()) {
        ExposedDropdownMenuBox(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            val valueString = currentValue?.let {
                valueStringProvider(it)
            } ?: noSelectionText
            TextField(
                value = valueString,
                textStyle = MaterialTheme.typography.caption,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = MaterialTheme.colors.onBackground,
                ),
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                values.forEach { value ->
                    DropdownMenuItem(
                        content = {
                            Text(text = valueStringProvider(value))
                        },
                        onClick = {
                            expanded = false
                            onSelected(value)
                        },
                    )
                }
            }
        }
    }
}