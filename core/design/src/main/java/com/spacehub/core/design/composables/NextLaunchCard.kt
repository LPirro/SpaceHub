/*
 *
 *  * SpaceHub - Designed and Developed by LPirro (Leonardo Pirro)
 *  * Copyright (C) 2023 Leonardo Pirro
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.spacehub.core.design.composables

import android.os.CountDownTimer
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.spacehub.core.design.R
import com.spacehub.core.design.theme.SpacehubTheme
import java.util.concurrent.TimeUnit

@Composable
fun NextLaunchCard(
    modifier: Modifier = Modifier,
    title: String,
    provider: String,
    location: String,
    launchImageUrl: String?,
    targetDateMillis: Long,
    onClick: () -> Unit,
) {
    val imageRequest = rememberImageRequest(launchImageUrl)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(262.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() },
    ) {
        AsyncImage(
            model = imageRequest,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            MaterialTheme.colorScheme.surfaceContainerHigh.copy(alpha = 0.9f),
                        ),
                        startY = 0f,
                        endY = 700f,
                    ),
                ),
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
        ) {
            NextLaunchBadge()
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                maxLines = 1,
            )

            Text(
                text = "$provider  •  $location",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Spacer(modifier = Modifier.height(12.dp))

            CountdownCard(targetDateMillis = targetDateMillis)

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun NextLaunchBadge() {
    Row(
        modifier = Modifier
            .background(
                color = Color(0xFF1A194B),
                shape = RoundedCornerShape(4.dp),
            )
            .padding(horizontal = 6.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.rocket_launch_outline),
            contentDescription = null,
            modifier = Modifier.size(12.dp),
            tint = Color(0xFF0084FF),
        )
        Text(
            text = "Next Launch",
            style = MaterialTheme.typography.labelMedium,
            color = Color(0xFF0084FF),
        )
    }
}

@Composable
private fun CountdownCard(
    targetDateMillis: Long,
    modifier: Modifier = Modifier,
) {
    var days by remember { mutableLongStateOf(0L) }
    var hours by remember { mutableLongStateOf(0L) }
    var minutes by remember { mutableLongStateOf(0L) }
    var seconds by remember { mutableLongStateOf(0L) }
    var timer by remember { mutableStateOf<CountDownTimer?>(null) }

    LaunchedEffect(targetDateMillis) {
        val currentMillis = System.currentTimeMillis()
        val remainingMillis = targetDateMillis - currentMillis

        timer?.cancel()
        timer = object : CountDownTimer(remainingMillis, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                val timeDifference = targetDateMillis - System.currentTimeMillis()
                days = TimeUnit.MILLISECONDS.toDays(timeDifference)
                hours = TimeUnit.MILLISECONDS.toHours(timeDifference) % 24
                minutes = TimeUnit.MILLISECONDS.toMinutes(timeDifference) % 60
                seconds = TimeUnit.MILLISECONDS.toSeconds(timeDifference) % 60
            }

            override fun onFinish() {
                this.cancel()
            }
        }.start()
    }

    DisposableEffect(Unit) {
        onDispose {
            timer?.cancel()
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(61.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Row (
            modifier = Modifier.background(
                color = MaterialTheme.colorScheme.background.copy(alpha = 0.5f),
                shape = RoundedCornerShape(16.dp),
            )
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(16.dp),
                )
        ) {
            CountdownTMinusLabel()
            VerticalDivider(color = MaterialTheme.colorScheme.outline)
            CountdownSegment(value = days.toInt(), label = "DAYS")
            VerticalDivider()
            CountdownSegment(value = hours.toInt(), label = "HOURS")
            VerticalDivider()
            CountdownSegment(value = minutes.toInt(), label = "MINS")
            VerticalDivider()
            CountdownSegment(value = seconds.toInt(), label = "SECS")
        }
    }
}

@Composable
private fun CountdownTMinusLabel(

) {
    Box(
        modifier = Modifier
            .height(61.dp)
            .width(60.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "T-",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.15.sp,
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun CountdownSegment(
    value: Int,
    label: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .height(61.dp)
            .width(70.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = value.toString().padStart(2, '0'),
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.15.sp,
            ),
            color = MaterialTheme.colorScheme.onBackground,
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.5.sp,
            ),
            color = MaterialTheme.colorScheme.outline,
        )
    }
}

@Composable
private fun rememberImageRequest(data: String?): ImageRequest {
    val context = LocalContext.current
    val tintColor = MaterialTheme.colorScheme.inverseOnSurface.toArgb()

    val placeholderDrawable = remember(tintColor) {
        AppCompatResources.getDrawable(
            context,
            R.drawable.image_placeholder,
        )?.apply {
            setTint(tintColor)
        }
    }

    return remember(data, placeholderDrawable) {
        ImageRequest.Builder(context)
            .data(data)
            .crossfade(true)
            .error(placeholderDrawable)
            .placeholder(placeholderDrawable)
            .build()
    }
}

@Preview
@Composable
private fun NextLaunchCardPreview() {
    SpacehubTheme {
        NextLaunchCard(
            title = "Starlink 6-1",
            provider = "SpaceX",
            location = "Cape Canaveral, FL",
            launchImageUrl = null,
            targetDateMillis = System.currentTimeMillis() + TimeUnit.DAYS.toMillis(4) +
                TimeUnit.HOURS.toMillis(2) + TimeUnit.MINUTES.toMillis(45),
            onClick = {},
        )
    }
}
