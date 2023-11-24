package sdu.mobile.xpence.ui.utils

import kotlinx.serialization.Serializable

@Serializable
data class Quadruple<out A, out B, out C, out D>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D
) {
    override fun toString(): String = "($first, $second, $third)"
}

fun <T> Quadruple<T, T, T, T>.toList(): List<T> = listOf(first, second, third, fourth)