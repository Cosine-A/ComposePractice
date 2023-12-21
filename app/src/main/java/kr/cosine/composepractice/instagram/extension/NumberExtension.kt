package kr.cosine.composepractice.instagram.extension

import java.text.DecimalFormat

private val decimalFormat = DecimalFormat("#,##0.###")

fun Double.format(): String = decimalFormat.format(this)

fun Int.format(): String = decimalFormat.format(this)

fun Long.format(): String = decimalFormat.format(this)