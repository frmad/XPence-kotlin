package sdu.mobile.xpence.ui.utils

import androidx.compose.ui.text.input.OffsetMapping

/**
 * CurrencyOffsetMapping is a class that maps offsets
 * between an original text and its formatted version.
 *
 * @param originalText The original unformatted text.
 * @param formattedText The formatted text, which has the same content
 *                      as originalText but with different
 *                      character positioning, due to added
 *                      or removed formatting characters.
 */
class CurrencyOffsetMapping(originalText: String, formattedText: String) : OffsetMapping {
    private val originalLength: Int = originalText.length
    private val indexes = findDigitIndexes(originalText, formattedText)

    /**
     * Find the indexes of digits in the original text with respect
     * to the formatted text.
     *
     * @param firstString The original unformatted text.
     * @param secondString The formatted text.
     * @return A list of indexes indicating the position of digits
     *         in the secondString (formatted text).
     *         The order of indexes corresponds to the order of digits
     *         in the original text.
     *         If a digit is not found in the secondString,
     *         an empty list is returned.
     */
    private fun findDigitIndexes(firstString: String, secondString: String): List<Int> {
        val digitIndexes = mutableListOf<Int>()
        var currentIndex = 0
        for (digit in firstString) {
            // Find the index of the digit in the second string
            val index = secondString.indexOf(digit, currentIndex)
            if (index != -1) {
                digitIndexes.add(index)
                currentIndex = index + 1
            } else {
                // If the digit is not found, return an empty list
                return emptyList()
            }
        }
        return digitIndexes
    }

    /**
     * Maps an offset from the original text to its corresponding position
     * in the formatted text.
     *
     * @param offset The offset in the original text.
     * @return The offset in the formatted text corresponding to the input
     *         offset.
     *         If the input offset is beyond the length of the original text,
     *         the last position in the formatted text is returned adding 1
     *         to set the caret after last digit.
     */
    override fun originalToTransformed(offset: Int): Int {
        /**
         * Example:
         * original 123
         * formatted $123
         * indexes [1,2,3]
         * caret position/offset is 1 which is here 1|23 in the original
         * in formatted text it will be offset=2 since all digits move by 1
         * because of the $ symbol at start
         * if caret is at the end of 123 we do not have index for it in indexes
         * so we take last value from indexes and add 1
         */
        if (indexes.isEmpty())
            return 0
        if (offset >= originalLength) {
            return indexes.last() + 1
        }
        return indexes[offset]
    }

    /**
     * Maps an offset from the formatted text to its corresponding position
     * in the original text.
     *
     * @param offset The offset in the formatted text.
     * @return The offset in the original text corresponding to the input
     *         offset.
     *         If the input offset is beyond the length of the formatted text,
     *         the length of the original text is returned.
     */
    override fun transformedToOriginal(offset: Int): Int {
        /**
         * Example 1:
         * original text 123
         * formatted text $123
         * indexes [1, 2, 3], index 0 is taken by $ symbol
         * if user tries to set caret before $ (offset = 0)
         * which is not allowed
         * we have to find the closest allowed caret position which in that
         * case will be 1
         *
         * Example 2:
         * original text 123
         * formatted text 123 USD
         * indexes [0,1,2] beyond that we have space and currency symbol
         * if user tries to set caret between U and S (offset=5)
         * which is not allowed
         * we have to find the closest allowed caret which we cannot in indexes.
         * Thus we take the length of original text to set caret after 123
         */
        return indexes.indexOfFirst { it >= offset }.takeIf { it != -1 } ?: originalLength
    }
}