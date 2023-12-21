package kr.cosine.composepractice.instagram.data

import kr.cosine.composepractice.instagram.extension.format

data class Posting(
    val user: User,
    private val imageDrawable: List<Int>,
    private val likeCount: Int,
    val lore: String,
    val comments: List<Comment>,
    val time: String
) {

    private companion object {
        const val FIRST_LORE_LIMIT = 100//31
    }

    private val splitedLore = when {
        lore.length <= FIRST_LORE_LIMIT -> listOf(lore)
        else -> listOf(
            lore.substring(0, FIRST_LORE_LIMIT),
            lore.substring(FIRST_LORE_LIMIT)
        )
    }

    fun getImageCount(): Int = imageDrawable.size

    fun getImageDrawable(index: Int): Int? = imageDrawable.getOrNull(index)

    fun getFormattedLikeCount(): String = likeCount.format()

    fun getLore(index: Int): String? = splitedLore.getOrNull(index)
}