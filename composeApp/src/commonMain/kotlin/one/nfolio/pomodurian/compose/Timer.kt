package one.nfolio.pomodurian.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
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
    MainButton(onClick = onClick) {
      MainFontText(
        "START",
        fontSize = 30.sp,
        weight = FontWeight.ExtraBold
      )
    }
  }

  @Composable
  fun TimeView(timerTime: Duration, timerFlag: Boolean, onPlayClick: () -> Unit) {
    val playIconHeight = 50.0
    val playIconWidth = 2 * sqrt(5.0) / 5 * playIconHeight // 2√5/5 * y

    MainFontText( // time
      timerTime.format(),
      fontSize = 80.sp,
      weight = FontWeight.Medium,
      color = MaterialTheme.colorScheme.secondary
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

        val playPath = Path().apply {
          moveTo(0f, curveClearance)
          lineTo(0f, size.height - curveClearance)
          quadraticTo(controlPoint, size.height - controlPoint, curveClearance, size.height - curveClearance)
          lineTo(size.width - curveClearance / 2, size.height / 2 + curveClearance / 2)
          quadraticTo(
            size.width - controlPoint,
            size.height / 2,
            size.width - curveClearance / 2,
            size.height / 2 - curveClearance / 2
          )
          lineTo(curveClearance, curveClearance)
          quadraticTo(controlPoint, controlPoint, 0f, curveClearance)
          close()
        }

        val pausePath = Path().apply {
          moveTo(0f, 0f)
          lineTo(0f, size.height)
          lineTo(size.width / 3, size.height)
          lineTo(size.width / 3, 0f)
          close()

          moveTo(size.width / 3 * 2, 0f)
          lineTo(size.width / 3 * 2, size.height)
          lineTo(size.width, size.height)
          lineTo(size.width, 0f)
          close()
        }
        drawPath(if (timerFlag) playPath else pausePath, color = primaryColor)
      }
    }
  }
}