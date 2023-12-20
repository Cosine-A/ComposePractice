package kr.cosine.composepractice.instagram.data

data class Story(
    val id: String,
    val profileDrawable: Int? = null,
    var isShowed: Boolean = false
) {

    private companion object {
        const val MAX_NAME_SIZE = 13
    }

    fun getShortId(): String {
        return if (id.length > MAX_NAME_SIZE) {
            id.slice(0..MAX_NAME_SIZE - 2) + "..."
        } else {
            id
        }
    }
}