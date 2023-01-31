/*
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
 */

package com.lpirro.repository.mapper

import java.util.regex.Pattern

private const val YOUTUBE_VIDEO_ID_REGEX =
    "http(?:s)?:\\/\\/(?:m.)?(?:www\\.)?youtu(?:\\.be\\/|(?:be-nocookie|be)\\.com\\/(?:watch|[\\w]+\\?(?:feature=[\\w]+.[\\w]+\\&)?v=|v\\/|e\\/|embed\\/|user\\/(?:[\\w#]+\\/)+))([^&#?\\n]+)"

class YouTubeVideoIdParserImpl : YouTubeVideoIdParser {

    override fun getVideoId(videoUrl: String): String? {
        val pattern = Pattern.compile(YOUTUBE_VIDEO_ID_REGEX, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(videoUrl)
        if (matcher.find()) {
            return matcher.group(1)
        }
        return null // TODO: Add analytics if the mapper fails to map youtube video
    }
}
