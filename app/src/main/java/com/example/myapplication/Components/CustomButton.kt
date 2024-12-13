package com.example.myapplication.Components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Login
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.Utilities.Constants
import org.w3c.dom.Text

@Composable
fun CustomButton(
    buttonColor: Color,
    buttonText: String = "",
    buttonTextColor : Color,
    buttonIcon: ImageVector? = null,
    buttonHeight: Int,
    buttonWidth: Int,

    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(), // Tüm ekranı kapla
        contentAlignment = Alignment.Center // İçeriği yatay ve dikey ortala
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .height(buttonHeight.dp)
                .width(buttonWidth.dp)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(50),
                    ambientColor = Color.Black.copy(alpha = 0.3f),
                    spotColor = Color.Black.copy(alpha = 0.4f)
                ),
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonColor,
                contentColor = buttonTextColor
            )
        ) {
            Text(
                fontSize = 16.sp,
                text = buttonText
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Rounded.Login,
                contentDescription = "Icon",
                tint = Color.White
            )
        }
    }
}