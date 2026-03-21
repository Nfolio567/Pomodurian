package one.nfolio.pomodurian

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.painterResource

import pomodurian.composeapp.generated.resources.Res
import pomodurian.composeapp.generated.resources.compose_multiplatform
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds
import kotlin.math.pow
import kotlin.math.sqrt

val ColorScheme.transparent: Color get() = Color(0x00000000)

@Composable
@Preview
fun App() {

  val lightColorScheme = lightColorScheme(
    primary = Color(0xFFFFD84D),
    primaryContainer = Color(0xFFF5EEDC)
  )

  MaterialTheme(
    colorScheme = lightColorScheme
  ) {

    var showContent by remember { mutableStateOf(false) }
    var isPlay by remember { mutableStateOf(false) }

    var isTimerStart by remember { mutableStateOf(true) }
    var time by remember { mutableStateOf(25.minutes) }

    val bufferSize = remember { 1024 }
    val play = remember { Play(bufferSize) }
    val sounds = remember { Sounds(true, 0.3f){ GenerateNoise.white() } }

    val playIconHeight = remember { 50.0 }
    val playIconWidth = remember { 2 * sqrt(5.0) / 5 * playIconHeight } // 2√5/5 * y

    /*LaunchedEffect(isPlay){
      if (isPlay) {
        println("unko")
        withContext(Dispatchers.Default) {
          play.start()
          while (true) {
            play.fromPCMs(sounds)
          }
        }
      } else {
        println("not unko")
        play.stop()
      }
    }*/

    LaunchedEffect(isTimerStart) {
      if (isTimerStart) {
        withContext(Dispatchers.Default) {
          while (true) {
            time -= 1.seconds
            delay(1000)
          }
        }
      }
    }

    Column(
      modifier = Modifier
        .background(MaterialTheme.colorScheme.primaryContainer)
        .safeContentPadding()
        .fillMaxSize(),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Box( // Timer
        modifier = Modifier
          .fillMaxWidth()
          .aspectRatio(1f),
        contentAlignment = Alignment.Center
      ) {
        CircularProgressIndicator( // Time progress
          progress = 0.5f,
          modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
          strokeWidth = 15.dp
        )
        Column( // time & play stop button
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text( // time
            time.format(),
            modifier = Modifier,
            fontSize = 80.sp
          )
          Box( // button
            modifier = Modifier
              .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
              ) {
                println("PlayIcon click")
                isPlay = !isPlay
                isTimerStart = !isTimerStart
              }
          ) {
            val primaryColor = MaterialTheme.colorScheme.primary

            Canvas(
              modifier = Modifier
                .height(playIconHeight.dp)
                .width(playIconWidth.dp)
            ) {
              val curveClearance = 5f
              val controlPoint = 2f

              val path = Path().apply {
                moveTo(0f, curveClearance)
                lineTo(0f, size.height - curveClearance)
                quadraticTo(controlPoint, size.height - controlPoint, curveClearance, size.height - curveClearance)
                lineTo(size.width - curveClearance / 2, size.height / 2 + curveClearance / 2)
                quadraticTo(size.width - controlPoint, size.height / 2, size.width - curveClearance / 2, size.height / 2 - curveClearance / 2)
                lineTo(curveClearance, curveClearance)
                quadraticTo(controlPoint, controlPoint, 0f, curveClearance)
                close()
              }
              drawPath(path, color = primaryColor)
            }
          }
        }
      }
      Button(onClick = {
        showContent = !showContent
        isPlay = !isPlay
        sounds.generator = { GenerateNoise.white()}
      }) {
        Text("Click me!")
      }
      AnimatedVisibility(showContent) {
        val greeting = remember { Greeting().greet() }
        Column(
          modifier = Modifier.fillMaxWidth(),
          horizontalAlignment = Alignment.CenterHorizontally,
        ) {
          Image(painterResource(Res.drawable.compose_multiplatform), null)
          Text("Compose: $greeting")
        }
      }
      Button(onClick = {
        isPlay = !isPlay
        sounds.generator = { GenerateNoise.pink()}
      }) {
        Text("PINK")
      }
      Button(onClick = {
        isPlay = !isPlay
        sounds.generator = { GenerateNoise.brown() }
      }) {
        Text("BROWN")
      }
    }
  }
}

fun Duration.format(): String {
  val minutes = this.inWholeMinutes
  val seconds = this.inWholeSeconds % 60

  return "${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"
}
