package one.nfolio.pomodurian

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.painterResource

import pomodurian.composeapp.generated.resources.Res
import pomodurian.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
  MaterialTheme {
    var showContent by remember { mutableStateOf(false) }
    var isPlay by remember { mutableStateOf(false) }
    val bufferSize = remember{ 1024 }
    val play = remember { Play(bufferSize) }
    val sounds = remember { Sounds(true){ GenerateNoise.white(bufferSize) } }

    LaunchedEffect(isPlay){
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
    }

    Column(
      modifier = Modifier
        .background(MaterialTheme.colorScheme.primaryContainer)
        .safeContentPadding()
        .fillMaxSize(),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
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
        sounds.generator = { GenerateNoise.pink(bufferSize)}
      }) {
        Text("PINK")
      }
      Button(onClick = {
        isPlay = !isPlay
        sounds.generator = { GenerateNoise.brown(bufferSize) }
      }) {
        Text("BROWN")
      }
    }
  }
}