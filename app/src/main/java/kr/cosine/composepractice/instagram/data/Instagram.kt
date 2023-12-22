package kr.cosine.composepractice.instagram.data

import kr.cosine.composepractice.R

object Instagram {

    val localUser = User("cosine_a", R.drawable.local_profile)
    private val stephenCurryUser = User("stephen_curry", R.drawable.stephen_curry)
    private val klayThompsonUser = User("klay_thompson", R.drawable.klay_thompson)
    private val lebronJamesUser = User("lebron_james", R.drawable.lebron_james)
    private val kevinDurantUser = User("kevin_durant", R.drawable.kevin_durant)
    private val jamesHardenUser = User("james_harden", R.drawable.james_harden)

    val stories = listOf(
        Story(stephenCurryUser),
        Story(klayThompsonUser),
        Story(lebronJamesUser),
        Story(kevinDurantUser),
        Story(jamesHardenUser)
    )

    val postings = listOf(
        Posting(
            stephenCurryUser,
            listOf(R.drawable.page1, R.drawable.page2),
            120958,
            // "Today's Curry record is a total of 33 points, 3-point shots (6 points), and 6 assists.",
            "오늘자 기록입니다. 총 33점, 3점 슛 2개, 어시스트 6개를 달성하였습니다.",
            listOf(Comment(klayThompsonUser, "ㅎㅇ")),
            "1시간 전"
        ),
        Posting(
            stephenCurryUser,
            listOf(R.drawable.page3, R.drawable.page4),
            4827,
            "테스트111",
            emptyList(),
            "1일 전"
        )
    )
}