package org.extractor.services.youtube;

import org.junit.Before;
import org.junit.Test;
import org.schabi.newpipe.Downloader;
import org.extractor.NewPipe;
import org.extractor.exceptions.ExtractionException;
import org.extractor.exceptions.ParsingException;
import org.extractor.stream.StreamExtractor;
import org.extractor.stream.StreamInfoItemCollector;
import org.extractor.stream.StreamType;
import org.extractor.stream.VideoStream;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.extractor.ServiceList.YouTube;

/*
 * Created by Christian Schabesberger on 30.12.15.
 *
 * Copyright (C) Christian Schabesberger 2015 <chris.schabesberger@mailbox.org>
 * YoutubeVideoExtractorDefault.java is part of NewPipe.
 *
 * NewPipe is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NewPipe is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NewPipe.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * Test for {@link StreamExtractor}
 */
public class YoutubeStreamExtractorDefaultTest {
    public static final String HTTPS = "https://";
    private StreamExtractor extractor;

    @Before
    public void setUp() throws Exception {
        NewPipe.init(Downloader.getInstance());
        extractor = YouTube.getService().getStreamExtractor("https://www.youtube.com/watch?v=YQHsXMglC9A");
    }

    @Test
    public void testGetInvalidTimeStamp() throws ParsingException {
        assertTrue(extractor.getTimeStamp() + "",
                extractor.getTimeStamp() <= 0);
    }

    @Test
    public void testGetValidTimeStamp() throws IOException, ExtractionException {
        StreamExtractor extractor = YouTube.getService().getStreamExtractor("https://youtu.be/FmG385_uUys?t=174");
        assertEquals(extractor.getTimeStamp() + "", "174");
    }

    @Test
    public void testGetTitle() throws ParsingException {
        assertTrue(!extractor.getName().isEmpty());
    }

    @Test
    public void testGetDescription() throws ParsingException {
        assertTrue(extractor.getDescription() != null);
    }

    @Test
    public void testGetUploaderName() throws ParsingException {
        assertTrue(!extractor.getUploaderName().isEmpty());
    }

    @Test
    public void testGetLength() throws ParsingException {
        assertTrue(extractor.getLength() > 0);
    }

    @Test
    public void testGetViewCount() throws ParsingException {
        assertTrue(Long.toString(extractor.getViewCount()),
                extractor.getViewCount() > /* specific to that video */ 1224000074);
    }

    @Test
    public void testGetUploadDate() throws ParsingException {
        assertTrue(extractor.getUploadDate().length() > 0);
    }

    @Test
    public void testGetUploaderUrl() throws ParsingException {
        assertTrue(extractor.getUploaderUrl().length() > 0);
    }

    @Test
    public void testGetThumbnailUrl() throws ParsingException {
        assertTrue(extractor.getThumbnailUrl(),
                extractor.getThumbnailUrl().contains(HTTPS));
    }

    @Test
    public void testGetUploaderAvatarUrl() throws ParsingException {
        assertTrue(extractor.getUploaderAvatarUrl(),
                extractor.getUploaderAvatarUrl().contains(HTTPS));
    }

    @Test
    public void testGetAudioStreams() throws IOException, ExtractionException {
        assertTrue(!extractor.getAudioStreams().isEmpty());
    }

    @Test
    public void testGetVideoStreams() throws IOException, ExtractionException {
        for (VideoStream s : extractor.getVideoStreams()) {
            assertTrue(s.url,
                    s.url.contains(HTTPS));
            assertTrue(s.resolution.length() > 0);
            assertTrue(Integer.toString(s.format),
                    0 <= s.format && s.format <= 4);
        }
    }

    @Test
    public void testStreamType() throws ParsingException {
        assertTrue(extractor.getStreamType() == StreamType.VIDEO_STREAM);
    }

    @Test
    public void testGetDashMpd() throws ParsingException {
        assertTrue(extractor.getDashMpdUrl(),
                extractor.getDashMpdUrl() != null || !extractor.getDashMpdUrl().isEmpty());
    }

    @Test
    public void testGetRelatedVideos() throws ExtractionException, IOException {
        StreamInfoItemCollector relatedVideos = extractor.getRelatedVideos();
        assertFalse(relatedVideos.getItemList().isEmpty());
        assertTrue(relatedVideos.getErrors().isEmpty());
    }
}
