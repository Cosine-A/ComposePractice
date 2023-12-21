package kr.cosine.composepractice.instagram.extension

private const val REGULAR_SPACE_CHARACTER = " "
private const val NON_BREAKABLE_SPACE_UNICODE = "\u00A0"

fun String.replaceSpace(): String {
    return replace(REGULAR_SPACE_CHARACTER, NON_BREAKABLE_SPACE_UNICODE)
}