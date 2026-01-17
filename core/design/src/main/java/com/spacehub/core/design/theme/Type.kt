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
package com.spacehub.core.design.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.spacehub.core.design.R
import com.spacehub.core.design.theme.AppFonts.spaceGroteskMedium
import com.spacehub.core.design.theme.AppFonts.spaceGroteskRegular

val TYPOGRAPHY = Typography()

val Typography = Typography(
    displayLarge = TYPOGRAPHY.displayLarge.copy(
        fontFamily = spaceGroteskRegular,
        fontFeatureSettings = "ss02, dlig",
    ),
    displayMedium = TYPOGRAPHY.displayMedium.copy(
        fontFamily = spaceGroteskRegular,
        fontFeatureSettings = "ss02, dlig",
    ),
    displaySmall = TYPOGRAPHY.displaySmall.copy(
        fontFamily = spaceGroteskRegular,
        fontFeatureSettings = "ss02, dlig",
    ),
    headlineLarge = TYPOGRAPHY.headlineLarge.copy(
        fontFamily = spaceGroteskRegular,
        fontFeatureSettings = "ss02, dlig",
    ),
    headlineMedium = TYPOGRAPHY.headlineMedium.copy(
        fontFamily = spaceGroteskRegular,
        fontFeatureSettings = "ss02, dlig",
    ),
    headlineSmall = TYPOGRAPHY.headlineSmall.copy(
        fontFamily = spaceGroteskRegular,
        fontFeatureSettings = "ss02, dlig",
    ),
    titleLarge = TYPOGRAPHY.titleLarge.copy(
        fontFamily = spaceGroteskRegular,
        fontFeatureSettings = "ss02, dlig",
    ),
    titleMedium = TYPOGRAPHY.titleMedium.copy(
        fontFamily = spaceGroteskMedium,
        fontFeatureSettings = "ss02, dlig",
    ),
    titleSmall = TYPOGRAPHY.titleSmall.copy(
        fontFamily = spaceGroteskMedium,
        fontFeatureSettings = "ss02, dlig",
    ),
    bodyLarge = TYPOGRAPHY.bodyLarge.copy(
        fontFamily = spaceGroteskRegular,
        fontFeatureSettings = "ss02, dlig",
    ),
    bodyMedium = TYPOGRAPHY.bodyMedium.copy(
        fontFamily = spaceGroteskRegular,
        fontFeatureSettings = "ss02, dlig",
    ),
    bodySmall = TYPOGRAPHY.bodySmall.copy(
        fontFamily = spaceGroteskRegular,
        fontFeatureSettings = "ss02, dlig",
    ),
    labelLarge = TYPOGRAPHY.labelLarge.copy(
        fontFamily = spaceGroteskMedium,
        fontFeatureSettings = "ss02, dlig",
    ),
    labelMedium = TYPOGRAPHY.labelMedium.copy(
        fontFamily = spaceGroteskMedium,
        fontFeatureSettings = "ss02, dlig",
    ),

    labelSmall = TYPOGRAPHY.labelSmall.copy(
        fontFamily = spaceGroteskMedium,
        fontFeatureSettings = "ss02, dlig",
    ),
)

@OptIn(ExperimentalTextApi::class)
object AppFonts {
    // Primary font - Space Grotesk
    val spaceGroteskBold = FontFamily(Font(R.font.space_grotesk_bold))
    val spaceGrotesLight = FontFamily(Font(R.font.space_grotesk_light))
    val spaceGroteskMedium = FontFamily(Font(R.font.space_grotesk_medium))
    val spaceGroteskRegular = FontFamily(Font(R.font.space_grotesk_regular))
    val spaceGroteskSemibold = FontFamily(Font(R.font.space_grotesk_semibold))

    // Secondary font - Space Mono (for minor areas)
    val spaceMonoRegular = FontFamily(Font(R.font.space_mono_regular))
    val spaceMonoBold = FontFamily(Font(R.font.space_mono_bold))
    val spaceMonoItalic = FontFamily(Font(R.font.space_mono_italic))
    val spaceMonoBoldItalic = FontFamily(Font(R.font.space_mono_bold_italic))
}

// Space Mono TextStyles with letter spacing
object SpaceMonoStyle {
    val Regular = TextStyle(
        fontFamily = AppFonts.spaceMonoRegular,
        letterSpacing = 0.25.sp,
    )
    val Bold = TextStyle(
        fontFamily = AppFonts.spaceMonoBold,
        letterSpacing = 0.25.sp,
    )
    val Italic = TextStyle(
        fontFamily = AppFonts.spaceMonoItalic,
        letterSpacing = 0.25.sp,
    )
    val BoldItalic = TextStyle(
        fontFamily = AppFonts.spaceMonoBoldItalic,
        letterSpacing = 0.25.sp,
    )
}
