package one.nfolio.pomodurian

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import pomodurian.composeapp.generated.resources.Guanine
import pomodurian.composeapp.generated.resources.Res
import kotlin.math.pow

@Composable
fun MainButton(onClick: () -> Unit, content: @Composable (RowScope.() -> Unit)) {
  Button(
    onClick = onClick,
    border = BorderStroke(2.dp, MaterialTheme.colorScheme.secondary),
    shape = ButtonDefaults.shape,
    content = content
  )
}

@Composable
fun MainFontText(text: String, fontSize: TextUnit, weight: FontWeight, color: Color = Color.Unspecified, modifier: Modifier = Modifier) {
  Text(
    modifier = modifier,
    text = text,
    fontSize = fontSize,
    fontFamily = FontFamily(Font(Res.font.Guanine, weight)),
    color = color
  )
}

@Composable
fun SelectedSound(text: String, soundImage: Painter) {
  Box(
    modifier = Modifier
      .padding(10.dp)
      .border(2.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(20.dp))
      .shadow(10.dp, RoundedCornerShape(20.dp))
      .background(MaterialTheme.colorScheme.primary)
  ) {
    Image(soundImage, null)
    MainFontText(text, 20.sp, FontWeight.Normal, MaterialTheme.colorScheme.secondary,
      modifier = Modifier
        .padding(10.dp)
    )
  }
}

@Composable
fun DecorationBar() {
  val primary = MaterialTheme.colorScheme.primary
  val secondary = MaterialTheme.colorScheme.secondary

  Canvas(
    modifier = Modifier
      .fillMaxWidth()
      .height(20.dp)
      .padding(top = 10.dp)
  ) {
    drawPath(
      Path().apply {
        moveTo(0f, 0f)
        lineTo(size.width, 0f)
        moveTo(0f, size.height)
        lineTo(size.width, size.height)
      },
      color = secondary,
      style = Stroke(width = 5f)
    )
    drawLine(
      color = primary,
      start = Offset(0f, size.height / 2),
      end = Offset(size.width, size.height / 2),
      strokeWidth = size.height
    )
  }
}

@Composable
fun ScrollButton() {
  val shape = CircleShape
  val buttonModifier = Modifier
    .fillMaxWidth()
    .aspectRatio(1f)
    .padding(5.dp)

  Column(
    modifier = Modifier
      .width(40.dp)
      .height(100.dp)
      .shadow(1.dp, RoundedCornerShape(20.dp))
      .background(Color.LightGray.copy(alpha = 0.9f)),
    verticalArrangement = Arrangement.SpaceBetween
  ) {
    Button(
      onClick = {

      },
      shape = shape,
      modifier = buttonModifier,
      contentPadding = PaddingValues(0.dp)
    ) {
      Text(
        "❮",
        modifier = Modifier
          .rotate(90f)
      )
    }
    Button(
      onClick = {

      },
      shape = shape,
      modifier = buttonModifier,
      contentPadding = PaddingValues(0.dp)
    ) {
      Text(
        "❯",
        modifier = Modifier
          .rotate(90f)
      )
    }
  }
}
