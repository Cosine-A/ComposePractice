package kr.cosine.composepractice.instagram.data

data class Posting(
    val id: String,
    val profileDrawable: Int,
    private val imageDrawable: List<Int>
) {

    fun getImageCount(): Int = imageDrawable.size

    fun getImageDrawable(index: Int): Int? = imageDrawable.getOrNull(index)
}