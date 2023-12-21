package kr.cosine.composepractice.instagram.data

data class Story(
    val profile: Profile,
    private val displayId: String? = null
) {

    fun getDisplayId(): String {
        return displayId ?: profile.getShortId()
    }
}