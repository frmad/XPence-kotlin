package sdu.mobile.xpence.ui.components.addexpenseform

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import sdu.mobile.xpence.ui.utils.rememberCurrencyVisualTransformation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmallCurrencyInput(
    currency: String,
    disabled: Boolean,
    value: String,
    onSetValue: (String) -> Unit
) {
    val currencyVisualTransformation = rememberCurrencyVisualTransformation(currency = currency)
    val interactionSource = remember { MutableInteractionSource() }
    BasicTextField(
        value = value,
        onValueChange = { newValue ->
            val trimmed = newValue.trimStart('0').trim { it.isDigit().not() }
            if (trimmed.isEmpty() || trimmed.toInt() <= Int.MAX_VALUE) {
                onSetValue(trimmed)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        interactionSource = interactionSource,
        textStyle = MaterialTheme.typography.labelLarge,
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.onSecondary,
                shape = RoundedCornerShape(20) //rounded corners
            )
            .indicatorLine(
                enabled = true,
                isError = false,
                interactionSource = interactionSource,
                colors = TextFieldDefaults.colors(),
                focusedIndicatorLineThickness = 0.dp,  //to hide the indicator line
                unfocusedIndicatorLineThickness = 0.dp //to hide the indicator line
            )
            .height(40.dp),
        visualTransformation = currencyVisualTransformation,
        enabled = !disabled
    ) {
        TextFieldDefaults.DecorationBox(
            value = value,
            innerTextField = it,
            enabled = !disabled,
            singleLine = true,
            visualTransformation = currencyVisualTransformation,
            interactionSource = interactionSource,
            placeholder = {
                Text(
                    text = "0 $currency",
                )
            },
            trailingIcon = { /* ... */ },
            contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                top = 0.dp,
                bottom = 0.dp,
            ),
        )
    }
}