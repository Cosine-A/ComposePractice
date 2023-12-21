package kr.cosine.composepractice.instagram.data

import kr.cosine.composepractice.instagram.extension.format

data class Posting(
    val profile: Profile,
    private val imageDrawable: List<Int>,
    private val likeCount: Int,
    private val lore: List<String>,
    val time: String
) {

    fun getImageCount(): Int = imageDrawable.size

    fun getImageDrawable(index: Int): Int? = imageDrawable.getOrNull(index)

    fun getFormattedLikeCount(): String = likeCount.format()

    fun getLore(index: Int): String? = lore.getOrNull(index)
}