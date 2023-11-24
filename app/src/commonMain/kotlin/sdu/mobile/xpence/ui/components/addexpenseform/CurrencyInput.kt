package sdu.mobile.xpence.ui.components.addexpenseform

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import sdu.mobile.xpence.ui.utils.rememberCurrencyVisualTransformation

@Composable
fun CurrencyInput(
    currency: String,
    value: Int,
    onSetValue: (Int) -> Unit
) {
    val currencyVisualTransformation = rememberCurrencyVisualTransformation(currency = currency)

    TextField(
        value = if (value == 0) "" else value.toString(),
        onValueChange = { newValue ->
            val trimmed = newValue.trimStart('0').trim { it.isDigit().not() }
            if (trimmed.isEmpty()) {
                onSetValue(0)
            } else {
                onSetValue(trimmed.toInt())
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        visualTransformation = currencyVisualTransformation,
        placeholder = { Text("0 $currency") }
    )
}