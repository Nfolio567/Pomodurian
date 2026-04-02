package one.nfolio.pomodurian

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
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
import pomodurian.composeapp.generated.resources.pink_noise
import pomodurian.composeapp.generated.resources.white_noise
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.math.sqrt
import kotlin.time.Clock
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

val ColorScheme.transparent: Color get() = Color(0x00000000)

@Composable
@Preview
fun App() {

  val lightColorScheme = lightColorScheme(
    primary = Color(0xFFF2633D),
    secondary = Color(0xFF303E1C),
    secondaryContainer = Color(0xFFF5B6A8),
    primaryContainer = Color(0xFFE5B44D),
    background = Color(0xFFF5F2DE),
    onPrimary = Color(0xFFECD83A)
  )

  MaterialTheme(
    colorScheme = lightColorScheme
  ) {
    var isPomodoroStarted by remember { mutableStateOf(false) }

    var showContent by remember { mutableStateOf(false) }
    var isPlay by remember { mutableStateOf(false) }

    var isTimerStart by remember { mutableStateOf(false) }
    var isTimeOut by remember { mutableStateOf(false) }
    val workTime = 1.minutes + 1.seconds
    var timerTime by remember { mutableStateOf(workTime) }

    val startTime = remember { atomic(Clock.System.now()) }
    val elapsed = remember { atomic(Duration.ZERO) }

    val play = remember { Play(1024) }
    val sounds = remember { Sounds(true, 0.3f) { GenerateNoise.white() } }

    val secondary = MaterialTheme.colorScheme.secondary

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

            delay(10.milliseconds)
          }
        }
      } else {
        val now = Clock.System.now()
        elapsed.value += now - startTime.value
      }
    }

    Box() {
      Column(
        modifier = Modifier
          .background(MaterialTheme.colorScheme.primaryContainer)
          .safeContentPadding()
          .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        DecorationBar()
        DecorationBar()

        Box( // Timer
          modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(10.dp)
            .shadow(10.dp, RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.background),
          contentAlignment = Alignment.Center
        ) {
          CircularProgressIndicator( // Time progress
            progress = {
              (timerTime / workTime).toFloat()
            },
            modifier = Modifier
              .fillMaxSize()
              .padding(15.dp)
              .border(2.dp, MaterialTheme.colorScheme.secondary, CircleShape),
            strokeWidth = 15.dp,
            gapSize = (-10).dp
          )

          val strokeWidth = 2.dp
          Canvas(
            modifier = Modifier
              .fillMaxSize()
              .padding(30.dp)
          ) {
            drawCircle(
              color = secondary,
              style = Stroke(width = strokeWidth.toPx())
            )
          }
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
                isPlay = !isPlay
                isTimerStart = !isTimerStart
              }
            }
          }
        }
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .shadow(10.dp, RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.background),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Row {
            SelectedSound("WhiteNoise", painterResource(Res.drawable.white_noise))
            SelectedSound("PinkNoise", painterResource(Res.drawable.pink_noise))
          }

        }
        /*
      MainButton(onClick = {
        showContent = !showContent
        isPlay = !isPlay
        sounds.generator = { GenerateNoise.white() }
      }) {
        Text(
          "Click me!",
          fontWeight = FontWeight.ExtraBold
        )
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
      MainButton(onClick = {
        isPlay = !isPlay
        sounds.generator = { GenerateNoise.pink()}
      }) {
        Text("PINK")
      }
      MainButton(onClick = {
        isPlay = !isPlay
        sounds.generator = { GenerateNoise.brown() }
      }) {
        Text("BROWN")
      }*/
      }
    }
  }

  ScrollButton()

}

fun Duration.format(): String {
  val minutes = this.inWholeMinutes
  val seconds = this.inWholeSeconds % 60

  return "${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"
}
