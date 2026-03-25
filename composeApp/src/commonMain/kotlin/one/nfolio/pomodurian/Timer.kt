package one.nfolio.pomodurian

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.sqrt
import kotlin.time.Duration

object Timer {
  @Composable
  fun StartView(onClick: () -> Unit) {
    Button(onClick = onClick) {
      Text(
        "Start",
        fontWeight = FontWeight.ExtraBold,
        fontSize = 30.sp
      )
    }
  }

  @Composable
  fun TimeView(timerTime: Duration,  onPlayClick: () -> Unit) {
    val playIconHeight = 50.0
    val playIconWidth = 2 * sqrt(5.0) / 5 * playIconHeight // 2√5/5 * y

    Text( // time
      timerTime.format(),
      modifier = Modifier,
      fontSize = 80.sp
    )
    Box( // button
      modifier = Modifier
        .clickable(
          interactionSource = remember { MutableInteractionSource() },
          indication = null,
          onClick = onPlayClick
        )
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
    }  }
}