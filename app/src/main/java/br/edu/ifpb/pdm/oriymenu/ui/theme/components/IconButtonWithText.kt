package br.edu.ifpb.pdm.oriymenu.ui.theme.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun IconButtonWithText(
    onClick: () -> Unit,
    contentDescription: String,
    icon: ImageVector,
    buttonText: String
) {
    TextButton(onClick = onClick) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = contentDescription)
            Spacer(modifier = Modifier.size(4.dp))
            Text(text = buttonText)
        }
    }
}
