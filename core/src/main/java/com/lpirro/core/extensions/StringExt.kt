/*
 *
 * SpaceHub - Designed and Developed by LPirro (Leonardo Pirro)
 * Copyright (C) 2023 Leonardo Pirro
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.lpirro.core.extensions

fun Double?.asMeters(): String {
    if (this == null) return "N/A"

    return "$this m"
}

fun Long?.asKiloMeters(): String {
    if (this == null) return "N/A"

    return "$this km"
}

fun Long?.asKilograms(): String {
    if (this == null) return "N/A"

    return "$this kg"
}

fun Long?.asKiloNewton(): String {
    if (this == null) return "N/A"

    return "$this kN"
}

fun String.countryCodeToEmoji(): String {
    return emoji[this] ?: ""
}

val emoji: Map<String, String> = mapOf(
    "IND" to "🇮🇳",
    "NGA" to "🇳🇬",
    "KEN" to "🇰🇪",
    "ITA" to "🇮🇹",
    "USA" to "🇺🇸",
    "CUB" to "🇨🇺",
    "GBR" to "🇬🇧",
    "ZAF" to "🇿🇦",
    "POL" to "🇵🇱",
    "GHA" to "🇬🇭",
    "MAR" to "🇲🇦",
    "IDN" to "🇮🇩",
    "ESP" to "🇪🇸",
    "BRA" to "🇧🇷",
    "DEU" to "🇩🇪",
    "FRA" to "🇫🇷",
    "ARE" to "🇦🇷",
    "CMR" to "🇨🇲",
    "MEX" to "🇲🇪",
    "UZB" to "🇺🇿",
    "IRN" to "🇮🇷",
    "SAU" to "🇸🇦",
    "TUR" to "🇹🇷",
    "CHL" to "🇨🇱",
    "TZA" to "🇹🇿",
    "UKR" to "🇺🇦",
    "NPL" to "🇳🇵",
    "PAK" to "🇵🇰",
    "PER" to "🇵🇪",
    "UGA" to "🇺🇬",
    "COL" to "🇨🇴",
    "AUS" to "🇦🇺",
    "AUT" to "🇦🇹",
    "ARG" to "🇦🇷",
    "ETH" to "🇪🇹",
    "CHN" to "🇨🇳",
    "RUS" to "🇷🇺",
    "PHL" to "🇵🇭",
    "VEN" to "🇻🇪",
    "MYS" to "🇲🇾",
    "GRC" to "🇬🇷",
    "CAN" to "🇨🇦",
    "BGD" to "🇧🇩",
    "ZWE" to "🇿🇼",
    "JPN" to "🇯🇵",
    "THA" to "🇹🇭",
    "LKA" to "🇱🇰",
    "ROU" to "🇷🇴",
    "ISR" to "🇮🇱",
    "VNM" to "🇻🇳",
    "NLD" to "🇳🇱",
    "EGY" to "🇪🇬",
    "ECU" to "🇪🇨",
    "SGP" to "🇸🇬",
    "DOM" to "🇩🇴",
    "GEO" to "🇬🇪",
    "DZA" to "🇩🇿",
    "KAZ" to "🇰🇿",
    "KOR" to "🇰🇷",
    "BEL" to "🇧🇪",
    "LBN" to "🇱🇧",
    "CZE" to "🇨🇿",
    "QAT" to "🇶🇦",
    "TJK" to "🇹🇯",
    "MDV" to "🇲🇻",
    "IRQ" to "🇮🇶",
    "HKG" to "🇭🇰",
    "SWE" to "🇸🇪",
    "TWN" to "🇹🇼",
    "AZE" to "🇦🇿",
    "TUN" to "🇹🇳",
    "ARM" to "🇦🇲",
    "KHM" to "🇰🇭",
    "KWT" to "🇰🇼",
    "NOR" to "🇳🇴",
    "HRV" to "🇭🇷",
    "MDA" to "🇲🇩",
    "FIN" to "🇫🇮",
    "CHE" to "🇨🇭",
    "ALB" to "🇦🇱",
)
