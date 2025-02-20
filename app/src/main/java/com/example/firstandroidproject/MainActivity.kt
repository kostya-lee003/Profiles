//package com.example.composelayouts
package com.example.firstandroidproject


import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.size
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
//import com.example.composelayouts.ui.theme.ComposeMasterTheme

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.firstandroidproject.R
import com.example.firstandroidproject.ui.theme.FirstAndroidProjectTheme

//import com.airbnb.lottie.compose.*


/**
 * Main Activity class that serves as the entry point of the application.
 * Demonstrates basic Compose concepts including layouts, state, and Material Design components.
 */

enum class Race {
    HUMAN, VAMPIRE, ALIEN, MONSTER, UNKNOWN
}

data class Person(
    val name: String,
    val profileImage: Int,
    val id: String,
    val email: String,
    val race: Race,
    val favoriteEmoji: String,
    val preferredColorScheme: Color?
)

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Enable edge-to-edge display
        enableEdgeToEdge()

        // Set the content of the activity using Compose
        setContent {
            // Apply the app theme to all composables
            FirstAndroidProjectTheme {
                // Scaffold provides the basic material design layout structure
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    // TopAppBar demonstrates the use of Material3 components
//                    containerColor = Color.Black,
                    topBar = {
                        TopAppBar(
                            title = { Text("Kostya Demo") },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.inversePrimary
                            )
                        )
                    }
                ) { padding ->
                    // Pass the padding from Scaffold to maintain proper layout
                    MainContent(modifier = Modifier.padding(padding))
                }
            }
        }
    }
}

/**
 * Main content composable that demonstrates various Compose layout concepts.
 *
 * @param modifier Modifier to be applied to the content
 */
@Composable
fun MainContent(modifier: Modifier = Modifier) {
    var currentPerson by remember { mutableStateOf<Person?>(
        Person("Konstantin Lee", R.drawable.kostya, "U210187", "kostyalee003@gmail.com", Race.HUMAN, "🗿", Color.White)
    ) }

    val people = arrayOf(
        Person("Konstantin Lee", R.drawable.kostya, "U210187", "kostyalee003@gmail.com", Race.HUMAN, "🗿", Color.White),
        Person("Frima Khan", R.drawable.frima, "U2110169", "frima@example.com", Race.VAMPIRE, "🦄", Color.Magenta),
        Person("Elon Musk", R.drawable.elon, "999", "elon@example.com", Race.ALIEN, "👽", Color.Blue),
        Person("Stuart", R.drawable.stuart, "123", "stuart@example.com", Race.MONSTER, "🍌", Color.Yellow),
        Person("Unknown", R.drawable.unknown, "000", "unknown@example.com", Race.UNKNOWN, "❓", Color.Black)
    )

    var isPlaying by remember { mutableStateOf(false) }

    var currentEmojiFileName by remember { mutableStateOf("moai") } // Default emoji

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Welcome to Android dev",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(8.dp),
            textAlign = TextAlign.Center
        )

        PersonCarousel(people.toList()) { person ->
            currentPerson = person
            // Update the emoji file name when a new person is selected
            currentEmojiFileName = when (person.favoriteEmoji) {
                "🗿" -> "moai"
                "🦄" -> "pony"
                "👽" -> "alien"
                "🍌" -> "banana"
                else -> "question"
            }
            println("Selected Person: ${person.name}, Emoji: ${person.favoriteEmoji}, File: $currentEmojiFileName")

            // Reset isPlaying to ensure the new animation doesn't start automatically
            isPlaying = false
        }

        if (currentPerson != null) {
            PersonCard(currentPerson!!)
        }

        Spacer(modifier = Modifier.height(16.dp))

//        ShowAnimatedEmoji(
//            emoji = currentPerson!!.favoriteEmoji,
//            isPlaying = isPlaying,
//            onAnimationEnd = { isPlaying = false } // Reset isPlaying when animation ends
//        )

        key(currentEmojiFileName) {
            AnimatedEmojiView(
                emojiFileName = currentEmojiFileName,
                isPlaying = isPlaying,
                onAnimationEnd = { isPlaying = false } // Reset isPlaying when animation ends
            )
        }

            Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Start the animation when the button is tapped
                isPlaying = true
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = "Animate",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun PersonCarousel(people: List<Person>, onPersonClick: (Person) -> Unit) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(people) { person ->
            PersonItem(person, onPersonClick)
        }
    }
}

@Composable
fun PersonItem(person: Person, onPersonClick: (Person) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(100.dp)
            .clickable { onPersonClick(person) }
    ) {
        Image(
            painter = painterResource(id = person.profileImage),
            contentDescription = person.name,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = person.name,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun PersonCard(person: Person) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = person.profileImage),
                    contentDescription = person.name,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = person.name,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = person.id,
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = person.email
//                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Favorite Emoji: " + person.favoriteEmoji,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.outline
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Race: " + person.race.toString(),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}

@Composable
fun ShowAnimatedEmoji(emoji: String, isPlaying: Boolean, onAnimationEnd: () -> Unit) {
    val emojiMap = mapOf(
        "🗿" to "moai",
        "🦄" to "pony",
        "👽" to "alien",
        "🍌" to "banana",
        "❓" to "question"
    )
    emojiMap[emoji]?.let {
        AnimatedEmojiView(
            emojiFileName = it,
            isPlaying = isPlaying,
            onAnimationEnd = onAnimationEnd
        )
    }
}

@Composable
fun AnimatedEmojiView(
    emojiFileName: String,
    isPlaying: Boolean,
    onAnimationEnd: () -> Unit,
    modifier: Modifier = Modifier.size(100.dp)
) {
    println("Rendering Lottie Animation: $emojiFileName")

    val context = LocalContext.current
    val rawResId = remember { getRawResId(context, emojiFileName) }
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(rawResId))

    // Control animation progress
    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = isPlaying, // Play only when isPlaying is true
        iterations = 1, // Play only once
        restartOnPlay = false, // Do not restart automatically
        speed = 1f // Normal speed
    )

    // Detect when the animation completes
    LaunchedEffect(progress) {
        if (progress >= 1f) { // Animation is complete
            onAnimationEnd()
        }
    }

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier
    )
}

// Helper function to get raw resource ID
fun getRawResId(context: Context, fileName: String): Int {
    return context.resources.getIdentifier(fileName, "raw", context.packageName)
}

/**
 * Preview function for the MainContent composable.
 * Allows viewing the layout in Android Studio's preview pane.
 */
@Preview(showBackground = true)
@Composable
fun MainContentPreview() {
    FirstAndroidProjectTheme {
        MainContent()
    }
}
