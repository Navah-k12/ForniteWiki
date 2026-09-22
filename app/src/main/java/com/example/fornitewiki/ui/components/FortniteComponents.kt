package com.example.fornitewiki.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fornitewiki.ui.theme.FortniteCardBg
import com.example.fornitewiki.ui.theme.FortniteCyan
import com.example.fornitewiki.ui.theme.FortniteDarkBg
import com.example.fornitewiki.ui.theme.FortniteYellow

/**
 * Modificador interactivo que anima los elementos con un efecto táctil
 * elástico estilo "Fortnite Arcade" al tocarlos.
 */
@Composable
fun Modifier.fortniteClickable(
    enabled: Boolean = true,
    scaleDown: Float = 0.92f,
    onClick: () -> Unit
): Modifier {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) scaleDown else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "fortniteScaleAnimation"
    )

    return this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .clickable(
            interactionSource = interactionSource,
            indication = null,
            enabled = enabled,
            onClick = onClick
        )
}

/**
 * Encabezado HUD temático de Fortnite Battle Royale para todas las pantallas
 */
@Composable
fun FortniteHUDHeader(
    tag: String = "BATTLE ROYALE",
    title: String,
    subtitle: String? = null,
    rightBadge: String? = null,
    badgeColor: Color = FortniteCyan,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .shadow(16.dp, RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
            .border(
                1.5.dp,
                Brush.horizontalGradient(
                    listOf(badgeColor.copy(alpha = 0.6f), Color.White.copy(alpha = 0.12f))
                ),
                RoundedCornerShape(20.dp)
            ),
        color = FortniteCardBg
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(badgeColor)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = tag.uppercase(),
                        color = badgeColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.4.sp
                    )
                }

                Text(
                    text = title.uppercase(),
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.5.sp
                )

                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        color = Color.LightGray.copy(alpha = 0.8f),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            if (rightBadge != null) {
                Surface(
                    color = badgeColor.copy(alpha = 0.18f),
                    shape = CutCornerShape(topStart = 6.dp, bottomEnd = 6.dp),
                    border = BorderStroke(1.dp, badgeColor.copy(alpha = 0.5f))
                ) {
                    Text(
                        text = rightBadge,
                        color = badgeColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }
        }
    }
}

/**
 * Chip de rareza estilo Fortnite con corte angular
 */
@Composable
fun FortniteRarityBadge(
    rarityText: String,
    rarityColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        color = rarityColor.copy(alpha = 0.22f),
        shape = CutCornerShape(topStart = 8.dp, bottomEnd = 8.dp),
        border = BorderStroke(1.dp, rarityColor),
        modifier = modifier
    ) {
        Text(
            text = rarityText.uppercase(),
            color = rarityColor,
            fontSize = 9.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 0.8.sp,
            modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
        )
    }
}
