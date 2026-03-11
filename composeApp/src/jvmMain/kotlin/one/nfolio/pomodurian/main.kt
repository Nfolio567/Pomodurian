package one.nfolio.pomodurian

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.jetbrains.compose.resources.painterResource

import pomodurian.composeapp.generated.resources.Res
import pomodurian.composeapp.generated.resources.durian
import java.awt.Toolkit

fun main() = application {
  var windowFlag by remember { mutableStateOf(true) }

  val screenSize = Toolkit.getDefaultToolkit().screenSize
  val windowWidth = 400.dp
  val windowHeight = 600.dp
  val windowState = with(LocalDensity.current) {
      rememberWindowState(
      width = windowWidth,
      height = windowHeight,
      position = WindowPosition((screenSize.width - windowWidth.toPx()).dp, 0.dp)
    )
  }

  Tray(
    painterResource(Res.drawable.durian),
    menu = {
      Item("Go to Timer") {
        windowFlag = true
      }
      Item("Setting"){}
      Separator()
      Item("About"){}
      Item("Github"){}
      Item("How to use"){}
      Separator()
      Item("Quit Pomodurian"){}
    },
    onAction = {
      windowFlag = !windowFlag
    }
  )

  Window(
    onCloseRequest = { windowFlag = false },
    state = windowState,
    visible = windowFlag,
    title = "pomodurian",
    undecorated = true,
    transparent = true,
    resizable = false
  ) {
    Surface(
      modifier = Modifier.fillMaxSize().padding(20.dp),
      shape = RoundedCornerShape(12.dp)
    ) {
      Surface(
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 12.dp
      ) {
        App()
      }
    }
  }
}