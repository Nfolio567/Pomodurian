package one.nfolio.pomodurian

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun MainButton(onClick: () -> Unit, content: @Composable (RowScope.() -> Unit)) {
  Button(
    onClick = onClick,
    border = BorderStroke(2.dp, MaterialTheme.colorScheme.secondary),
    shape = ButtonDefaults.shape,
    content = content
  )
}