package kr.cosine.composepractice.instagram

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kr.cosine.composepractice.R
import kr.cosine.composepractice.instagram.data.Instagram.localUser
import kr.cosine.composepractice.instagram.data.Instagram.postings
import kr.cosine.composepractice.instagram.data.Instagram.stories
import kr.cosine.composepractice.instagram.data.Posting
import kr.cosine.composepractice.instagram.data.Story
import kr.cosine.composepractice.instagram.extension.clickableWithoutEffect
import kr.cosine.composepractice.instagram.extension.format
import kr.cosine.composepractice.instagram.extension.replaceSpace
import kr.cosine.composepractice.instagram.theme.defaultPageColor
import kr.cosine.composepractice.instagram.theme.ProfileBorderColor
import kr.cosine.composepractice.instagram.theme.gray
import kr.cosine.composepractice.instagram.theme.selectedPageColor
import kr.cosine.composepractice.ui.theme.ComposePracticeTheme

class InstagramMainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposePracticeTheme {
                Main()
            }
        }
    }

    @Composable
    private fun Main() {
        Column {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                item {
                    Head()
                    StoryPage(*stories.toTypedArray())
                    DrawLine()
                }
                items(postings) { posting ->
                    PostingElement(posting)
                }
            }
            QuickMenu(Modifier.weight(0.0625f)) // 0.07
        }
    }

    @Composable
    private fun DrawLine() {
        Divider(
            color = Color.LightGray,
            modifier = Modifier.fillMaxWidth()
        )
    }

    @Composable
    private fun Head() {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.instagram_word_logo),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(0.35f)//.size(144.dp)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(26.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.empty_heart),
                    contentDescription = null,
                    modifier = Modifier.size(26.dp)
                )
                Image(
                    painter = painterResource(R.drawable.direct_message),
                    contentDescription = null,
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    }

    @Composable
    private fun StoryPage(vararg stories: Story) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            item {
                StoryPageElement(Story(localUser, "내 스토리"), true)
            }
            items(items = stories) { story ->
                StoryPageElement(story)
            }
        }
    }

    @Composable
    private fun StoryPageElement(
        story: Story,
        isOwner: Boolean = false
    ) {
        var isChecked by rememberSaveable { mutableStateOf(false) }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.clickableWithoutEffect {
                        isChecked = !isChecked
                    }
                ) {
                    val background = if (isChecked) {
                        R.drawable.friend_story_checked
                    } else {
                        R.drawable.friend_story_activated
                    }
                    Image(
                        painter = painterResource(background),
                        contentDescription = null,
                        modifier = Modifier.size(85.dp)
                    )
                    CircleImage(story.user.profileDrawable, 70.dp)
                }
                if (isOwner) {
                    CircleImage(
                        R.drawable.story_plus,
                        25.dp,
                        Modifier.align(Alignment.BottomEnd)
                    )
                }
            }
            Text(
                text = story.getDisplayId(),
                fontSize = 12.sp,
                modifier = Modifier.paddingFromBaseline(6.dp, 12.dp),
            )
        }
    }

    @Composable
    private fun PostingElement(posting: Posting) {
        var currentPage by rememberSaveable { mutableIntStateOf(0) }
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            PostingElementHead(posting)
            PostingElementImage(posting) {
                currentPage = it
            }
            Column(
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {
                PostingElementInteract(posting, currentPage)
                PostingElementLike(posting)
                PostingElementNameAndLore(posting)
                PostingElementComment(posting)
                Spacer(6.dp)
                PostingElementCommentPlus()
                PostingElementTime(posting)
            }
            Spacer(6.dp)
        }
    }

    @Composable
    private fun PostingElementHead(posting: Posting) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp, 7.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val profile = posting.user
                CircleImage(profile.profileDrawable, 33.dp)
                Text(
                    text = profile.id,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.paddingFromBaseline(bottom = 9.dp)
                )
            }
            Image(
                painter = painterResource(R.drawable.more_info),
                contentDescription = null,
                modifier = Modifier.size(25.dp)
            )
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun PostingElementImage(posting: Posting, currentPageScope: (Int) -> Unit) {
        val imageCount = posting.getImageCount()
        val pagerState = rememberPagerState(pageCount = { imageCount })
        currentPageScope(pagerState.currentPage)
        Box {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(4f / 5f)
            ) { page ->
                val imageDrawable = posting.getImageDrawable(page) ?: return@HorizontalPager
                Image(
                    painter = painterResource(imageDrawable),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                    // modifier = Modifier.fillMaxWidth().height(515.dp)
                )
            }
            if (imageCount > 1) {
                Surface(
                    color = gray.copy(0.9f),
                    shape = RoundedCornerShape(13.dp),
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.TopEnd)
                ) {
                    Text(
                        text = "${pagerState.currentPage + 1}/$imageCount",
                        fontSize = 12.sp,
                        color = Color.White,
                        modifier = Modifier.padding(10.dp, 4.dp)
                    )
                }
            }
        }
    }

    @Composable
    private fun PostingElementInteract(posting: Posting, currentPage: Int) {
        Box {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            ) {
                val size = 22.dp
                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SwitchImage(R.drawable.filled_heart, R.drawable.empty_heart, size)
                    DefaultImage(R.drawable.comment, size)
                    DefaultImage(R.drawable.direct_message, size)
                }
                SwitchImage(R.drawable.filled_bookmark, R.drawable.empty_bookmark, size)
            }
            Row(
                modifier = Modifier.align(Alignment.Center)
            ) {
                repeat(posting.getImageCount()) { index ->
                    val color = if (currentPage == index) {
                        selectedPageColor
                    } else {
                        defaultPageColor
                    }
                    Circle(8.dp, color)
                }
            }
        }
    }

    @Composable
    private fun PostingElementLike(posting: Posting) {
        Text(
            text = "좋아요 ${posting.getFormattedLikeCount()}개",
            fontSize = 13.sp,
            fontWeight = FontWeight.W600
        )
    }

    @Composable
    private fun PostingElementNameAndLore(posting: Posting) {
        Box(
            modifier = Modifier.padding(vertical = 3.dp)
        ) {
            val userId = posting.user.id
            Text(
                text = buildAnnotatedString {
                    append(
                        AnnotatedString(
                            text = userId,
                            spanStyle = SpanStyle(fontWeight = FontWeight.SemiBold)
                        )
                    )
                    append(" ${posting.lore}".replaceSpace())
                },
                fontSize = 13.sp,
                lineHeight = 20.sp,
            )
            Text(
                text = userId,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }

    @Composable
    private fun PostingElementComment(posting: Posting) {
        val commentCount = posting.getCommentCount()
        if (commentCount != 0) {
            Text(
                text = "댓글 ${commentCount.format()}개 모두 보기",
                color = Color.Gray,
                fontSize = 13.sp
            )
        }
    }

    @Composable
    private fun PostingElementCommentPlus() {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BorderProfile(R.drawable.local_profile, 27.dp)
            Text(
                text = "댓글 추가...",
                color = Color.Gray,
                fontSize = 12.sp,
                modifier = Modifier.paddingFromBaseline(bottom = 7.dp)
            )
        }
    }

    @Composable
    private fun PostingElementTime(posting: Posting) {
        Text(
            text = posting.time,
            color = Color.Gray,
            fontSize = 11.sp,
            modifier = Modifier.padding(vertical = 3.dp)
        )
    }

    @Composable
    private fun Circle(size: Dp, color: Color) {
        Canvas(modifier = Modifier.size(size)) {
            val canvasSize = this.size
            val canvasWidth = canvasSize.width
            val canvasHeight = canvasSize.height
            drawCircle(
                color = color,
                center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                radius = canvasSize.minDimension / 4
            )
        }
    }

    @Composable
    private fun QuickMenu(modifier: Modifier = Modifier) {
        Surface(
            color = Color.White,
            modifier = modifier
        ) {
            Column(modifier) {
                DrawLine()
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                ) {
                    val size = 24.dp
                    DefaultImage(R.drawable.filled_home, size)
                    DefaultImage(R.drawable.magnifying_glass, size)
                    DefaultImage(R.drawable.posting, size)
                    DefaultImage(R.drawable.reels, size)
                    BorderProfile(R.drawable.local_profile, size + 2.dp)
                }
            }
        }
    }

    @Composable
    private fun BorderProfile(drawable: Int, size: Dp) {
        CircleImage(
            drawable, size, Modifier.border(
                width = 1.dp,
                color = ProfileBorderColor,
                shape = CircleShape
            )
        )
    }

    @Composable
    private fun Spacer(height: Dp) {
        Spacer(modifier = Modifier.height(height))
    }

    @Composable
    private fun SwitchImage(filledDrawable: Int, emptyDrawable: Int, size: Dp) {
        var isAviliable by rememberSaveable { mutableStateOf(false) }
        val drawable = if (isAviliable) {
            filledDrawable
        } else {
            emptyDrawable
        }
        DefaultImage(
            drawable,
            size,
            Modifier.clickableWithoutEffect {
                isAviliable = !isAviliable
            }
        )
    }

    @Composable
    private fun DefaultImage(drawable: Int, size: Dp, modifier: Modifier = Modifier) {
        Image(
            painter = painterResource(drawable),
            contentDescription = null,
            modifier = modifier.size(size)
        )
    }

    @Composable
    private fun CircleImage(drawable: Int, size: Dp, modifier: Modifier = Modifier) {
        Image(
            painter = painterResource(drawable),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(size)
                .clip(CircleShape)
        )
    }
}