package kr.cosine.composepractice.instagram.data

data class Story(
    val user: User,
    private val displayId: String? = null
) {

    fun getDisplayId(): String {
        return displayId ?: user.getShortId()
    }
}