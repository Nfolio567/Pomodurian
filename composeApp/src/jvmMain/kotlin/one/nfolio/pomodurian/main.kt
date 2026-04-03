package one.nfolio.pomodurian

import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.Composable
import one.nfolio.pomodurian.compose.App
import org.jetbrains.compose.resources.painterResource

import pomodurian.composeapp.generated.resources.Res
import pomodurian.composeapp.generated.resources.durian
import java.awt.Toolkit
import java.awt.event.WindowEvent
import java.awt.event.WindowFocusListener

fun main() = application {
  var windowFlag by remember { mutableStateOf(true) }
  var contentSwitch: @Composable () -> Unit by remember { mutableStateOf(::App) }

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
      Item("About") {
        contentSwitch = ::About
        windowFlag = true
      }
      Item("Github"){}
      Item("How to use"){}
      Separator()
      Item("Quit Pomodurian", onClick = ::exitApplication)
    },
    onAction = {
      windowFlag = !windowFlag
    }
  )

  Window(
    onCloseRequest = {
      windowFlag = false
      contentSwitch = ::App
    },
    state = windowState,
    //visible = windowFlag,
    undecorated = true,
    transparent = true,
    resizable = false
  ) {
    window.toFront()
    window.requestFocus()

    window.addWindowFocusListener(object : WindowFocusListener {
      override fun windowGainedFocus(e: WindowEvent?) {
        // empty
      }
      override fun windowLostFocus(e: WindowEvent?) {
        windowFlag = false
      }
    })
    Box(
      modifier = Modifier.fillMaxSize().padding(20.dp)
    ) {
      Surface(
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 12.dp
      ) {
        contentSwitch()
      }
    }
  }
}