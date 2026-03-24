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
import kotlinx.atomicfu.atomic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.painterResource

import pomodurian.composeapp.generated.resources.Res
import pomodurian.composeapp.generated.resources.compose_multiplatform
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.math.sqrt
import kotlin.time.Clock
import kotlin.time.Duration.Companion.seconds

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
    var isPomodoroStarted by remember { mutableStateOf(false) }

    var showContent by remember { mutableStateOf(false) }
    var isPlay by remember { mutableStateOf(false) }

    var isTimerStart by remember { mutableStateOf(false) }
    var isTimeOut by remember { mutableStateOf(false) }
    var timerTime by remember { mutableStateOf(Duration.ZERO) }
    val workTime = 25.minutes + 1.seconds

    val startTime = remember { atomic(Clock.System.now()) }
    val elapsed = remember { atomic(Duration.ZERO) }

    val play = remember { Play(1024) }
    val sounds = remember { Sounds(true, 0.3f){ GenerateNoise.white() } }

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
        startTime.value = Clock.System.now()
        withContext(Dispatchers.Default) {
          while (!isTimeOut) {
            val now = Clock.System.now()
            timerTime = workTime - (now - startTime.value) - elapsed.value

            if (timerTime <= Duration.ZERO) isTimeOut = true

            delay(10)
          }
        }
      } else {
        val now = Clock.System.now()
        elapsed.value += now - startTime.value
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
          progress = {
            (timerTime / workTime).toFloat()
          },
          modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
          strokeWidth = 15.dp
        )
        Column( // time & play stop button
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          if (!isPomodoroStarted) {
            Timer.StartView {
              isPomodoroStarted = true
              isTimerStart = true
            }
          } else {
            Timer.TimeView(timerTime) {
              println("PlayIcon click")
              isPlay = !isPlay
              isTimerStart = !isTimerStart
            }
          }
        }
      }
      Button(onClick = {
        showContent = !showContent
        isPlay = !isPlay
        sounds.generator = { GenerateNoise.white() }
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
