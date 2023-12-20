package kr.cosine.composepractice.instagram

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kr.cosine.composepractice.R
import kr.cosine.composepractice.instagram.data.Posting
import kr.cosine.composepractice.instagram.data.Story
import kr.cosine.composepractice.instagram.extension.clickableWithoutEffect
import kr.cosine.composepractice.instagram.theme.Gray
import kr.cosine.composepractice.ui.theme.ComposePracticeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposePracticeTheme {
                Main()
            }
        }
    }
}

@Composable
private fun Main() {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxHeight()
    ) {
        LazyColumn {
            item {
                Head()
                StoryPage(
                    Story("stephen_curry", R.drawable.stephen_curry),
                    Story("klay_thompson", R.drawable.klay_thompson),
                    Story("lebron_james", R.drawable.lebron_james),
                    Story("kevin_durant", R.drawable.kevin_durant),
                    Story("james_harden", R.drawable.james_harden)
                )
                DrawLine()
            }
            items(
                items = listOf(
                    Posting(
                        "stephen_curry",
                        R.drawable.stephen_curry,
                        listOf(R.drawable.page1, R.drawable.page2)
                    )
                )
            ) { posting ->
                PostingElement(posting)
            }
        }
        QuickMenu()
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
            StoryPageElement(Story("내 스토리", R.drawable.my_profile), true)
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
                val profileDrawable = story.profileDrawable
                if (profileDrawable != null) {
                    CircleImage(profileDrawable, 70.dp)
                }
            }
            if (isOwner) {
                CircleImage(
                    R.drawable.my_story_plus,
                    25.dp,
                    Modifier.align(Alignment.BottomEnd)
                )
            }
        }
        Text(
            text = story.getShortId(),
            modifier = Modifier.paddingFromBaseline(top = 6.dp, bottom = 12.dp),
            fontSize = 12.sp
        )
    }
}

@Composable
private fun PostingElement(posting: Posting) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        PostingElementHead(posting)
        PostingElementImage(posting)
    }
}

@Composable
private fun PostingElementHead(posting: Posting) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 7.dp,
                horizontal = 12.dp
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircleImage(posting.profileDrawable, 33.dp)
            Text(
                text = posting.id,
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
private fun PostingElementImage(posting: Posting) {
    val imageCount = posting.getImageCount()
    val pagerState = rememberPagerState(pageCount = { imageCount })
    Box {
        HorizontalPager(
            state = pagerState
        ) { page ->
            val imageDrawable = posting.getImageDrawable(page) ?: return@HorizontalPager
            Image(
                painter = painterResource(imageDrawable),
                contentDescription = null
            )
        }
        Surface(
            color = Gray.copy(0.9f),
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

@Composable
private fun QuickMenu() {
    Column {
        DrawLine()
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 6.dp)
        ) {
            val size = 24.dp
            QuickMenuElement(R.drawable.filled_home, size)
            QuickMenuElement(R.drawable.magnifying_glass, size)
            QuickMenuElement(R.drawable.posting, size)
            QuickMenuElement(R.drawable.reels, size)
            CircleImage(
                R.drawable.my_profile, size + 2.dp, Modifier.border(
                    width = 2.dp,
                    color = Color.Gray,
                    shape = CircleShape
                )
            )
        }
    }
}

@Composable
private fun QuickMenuElement(drawable: Int, size: Dp) {
    Image(
        painter = painterResource(drawable),
        contentDescription = null,
        modifier = Modifier.size(size)
    )
}