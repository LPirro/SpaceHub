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

package com.lpirro.repository.mapper

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class YouTubeVideoIdParserImplTest {

    private lateinit var videoUrlsUnderTest: List<String>
    private lateinit var youTubeVideoIdParser: YouTubeVideoIdParser

    @Before
    fun setup() {
        youTubeVideoIdParser = YouTubeVideoIdParserImpl()
        videoUrlsUnderTest = listOf(
            "https://www.youtube.com/watch?v=DFYRQ_zQ-gk&feature=featured",
            "https://www.youtube.com/watch?v=DFYRQ_zQ-gk",
            "http://www.youtube.com/watch?v=DFYRQ_zQ-gk",
            "http://www.youtube.com/watch?v=DFYRQ_zQ-gk",
            "http://www.youtube.com/watch?v=DFYRQ_zQ-gk",
            "https://youtube.com/watch?v=DFYRQ_zQ-gk",
            "http://youtube.com/watch?v=DFYRQ_zQ-gk",
            "https://youtube.com/watch?v=DFYRQ_zQ-gk",
            "http://youtube.com/watch?v=DFYRQ_zQ-gk",
            "https://m.youtube.com/watch?v=DFYRQ_zQ-gk",
            "http://m.youtube.com/watch?v=DFYRQ_zQ-gk",
            "http://m.youtube.com/watch?v=DFYRQ_zQ-gk",
            "http://m.youtube.com/watch?v=DFYRQ_zQ-gk",
            "https://www.youtube.com/v/DFYRQ_zQ-gk?fs=1&hl=en_US",
            "http://www.youtube.com/v/DFYRQ_zQ-gk?fs=1&hl=en_US",
            "http://www.youtube.com/v/DFYRQ_zQ-gk?fs=1&hl=en_US",
            "http://www.youtube.com/v/DFYRQ_zQ-gk?fs=1&hl=en_US",
            "http://youtube.com/v/DFYRQ_zQ-gk?fs=1&hl=en_US",
            "https://www.youtube.com/embed/DFYRQ_zQ-gk?autoplay=1",
            "https://www.youtube.com/embed/DFYRQ_zQ-gk",
            "http://www.youtube.com/embed/DFYRQ_zQ-gk",
            "http://www.youtube.com/embed/DFYRQ_zQ-gk",
            "http://www.youtube.com/embed/DFYRQ_zQ-gk",
            "https://youtube.com/embed/DFYRQ_zQ-gk",
            "http://youtube.com/embed/DFYRQ_zQ-gk",
            "http://youtube.com/embed/DFYRQ_zQ-gk",
            "https://youtube.com/embed/DFYRQ_zQ-gk",
            "https://youtu.be/DFYRQ_zQ-gk?t=120",
            "https://youtu.be/DFYRQ_zQ-gk",
            "http://youtu.be/DFYRQ_zQ-gk",
            "http://youtu.be/DFYRQ_zQ-gk",
            "http://youtu.be/DFYRQ_zQ-gk",
            "https://www.youtube.com/HamdiKickProduction?v=DFYRQ_zQ-gk",
            "https://www.youtube.com/watch?v=DFYRQ_zQ-gk&t=67s",
            "http://www.youtube.com/watch?v=DFYRQ_zQ-gk&a=GxdCwVVULXctT2lYDEPllDR0LRTutYfW",
            "http://www.youtube.com/embed/DFYRQ_zQ-gk",
            "http://www.youtube.com/watch?v=DFYRQ_zQ-gk",
            "https://www.youtube.com/watch?v=DFYRQ_zQ-gk",
            "http://www.youtube.com/watch?v=DFYRQ_zQ-gk",
            "http://youtu.be/DFYRQ_zQ-gk",
            "http://www.youtube.com/v/DFYRQ_zQ-gk",
            "http://www.youtube.com/e/DFYRQ_zQ-gk",
            "http://www.youtube.com/watch?feature=player_embedded&v=DFYRQ_zQ-gk",
        )
    }

    @Test
    fun `YouTube Video Urls extract id Correctly`() {
        val expectedId = "DFYRQ_zQ-gk"
        videoUrlsUnderTest.forEach {
            assertEquals(expectedId, youTubeVideoIdParser.getVideoId(it))
        }
    }
}
