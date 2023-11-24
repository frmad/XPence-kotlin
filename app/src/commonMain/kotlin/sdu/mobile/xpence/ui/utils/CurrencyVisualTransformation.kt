package sdu.mobile.xpence.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import sdu.mobile.xpence.formatDecimal

/**
 * Visual filter for currency values. Formats values without fractions
 * adding currency symbol
 * based on the provided currency code and default Locale.
 * @param currencyCode the ISO 4217 code of the currency
 */
class CurrencyVisualTransformation(
    val currencyCode: String
) : VisualTransformation {

    fun isInteger(str: String?) = str?.toIntOrNull()?.let { true } ?: false

    override fun filter(text: AnnotatedString): TransformedText {
        /**
         * First we need to trim typed text in case there are any spaces.
         * What can by typed is also handled on TextField itself,
         * see SampleUse code.
         */
        val originalText = text.text.trim()

        if (originalText.isEmpty()) {
            /**
             * If user removed the values there is nothing to format.
             * Calling numberFormatter would cause exception.
             * So we can return text as is without any modification.
             * OffsetMapping.Identity tell system that the number
             * of characters did not change.
             */
            return TransformedText(text, OffsetMapping.Identity)
        }

        if (!isInteger(originalText)) {
            /**
             * As mentioned before TextField should validate entered data
             * but here we also protect the app from crashing if it doesn't
             * and log warning.
             * Then return same TransformedText like above.
             */
            return TransformedText(text, OffsetMapping.Identity)
        }

        /**
         * Here is our TextField value transformation to formatted value.
         * EditText operates on String, so we have to change it to Int.
         * It's safe at this point because we eliminated cases where
         * value is empty or contains non-digits characters.
         */
        val formattedText = (originalText.toDouble() / 100).formatDecimal(2) + " " + currencyCode

        /**
         * CurrencyOffsetMapping is where the magic happens. See you there :)
         */
        return TransformedText(
            AnnotatedString(formattedText),
            CurrencyOffsetMapping(originalText, formattedText)
        )
    }
}

/**
 * Helper function prevents creating CurrencyVisualTransformation
 * on every re-composition and use inspection mode
 * in case you don't want to use visual filter in Previews.
 * Currencies were displayed for me in Preview but I don't trust them
 * so that's how you could deal with it by returning VisualTransformation.None
 */
@Composable
fun rememberCurrencyVisualTransformation(currency: String): VisualTransformation {
    val inspectionMode = LocalInspectionMode.current
    return remember(currency) {
        if (inspectionMode) {
            VisualTransformation.None
        } else {
            CurrencyVisualTransformation(currency)
        }
    }
}
