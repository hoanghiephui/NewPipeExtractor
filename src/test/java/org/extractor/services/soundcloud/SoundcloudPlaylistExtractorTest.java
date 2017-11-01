package org.extractor.services.soundcloud;

import org.junit.Before;
import org.junit.Test;
import org.schabi.newpipe.Downloader;
import org.extractor.NewPipe;
import org.extractor.exceptions.ExtractionException;
import org.extractor.playlist.PlaylistExtractor;

import static org.junit.Assert.*;
import static org.extractor.ServiceList.SoundCloud;

/**
 * Test for {@link PlaylistExtractor}
 */

public class SoundcloudPlaylistExtractorTest {
    private PlaylistExtractor extractor;

    @Before
    public void setUp() throws Exception {
        NewPipe.init(Downloader.getInstance());
        extractor = SoundCloud.getService()
                .getPlaylistExtractor("https://soundcloud.com/liluzivert/sets/the-perfect-luv-tape-r");
    }

    @Test
    public void testGetDownloader() throws Exception {
        assertNotNull(NewPipe.getDownloader());
    }

    @Test
    public void testGetId() throws Exception {
        assertEquals(extractor.getId(), "246349810");
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals(extractor.getName(), "THE PERFECT LUV TAPE®️");
    }

    @Test
    public void testGetThumbnailUrl() throws Exception {
        assertTrue(extractor.getThumbnailUrl(), extractor.getThumbnailUrl().contains("https://"));
    }

    @Test
    public void testGetUploaderUrl() throws Exception {
        assertEquals(extractor.getUploaderUrl(), "http://soundcloud.com/liluzivert");
    }

    @Test
    public void testGetUploaderName() throws Exception {
        assertEquals(extractor.getUploaderName(), "LIL UZI VERT");
    }

    @Test
    public void testGetUploaderAvatarUrl() throws Exception {
        assertTrue(extractor.getUploaderAvatarUrl(), extractor.getUploaderAvatarUrl().contains("https://"));
    }

    @Test
    public void testGetStreamsCount() throws Exception {
        assertEquals(extractor.getStreamCount(), 10);
    }

    @Test
    public void testGetStreams() throws Exception {
        assertTrue("no streams are received", !extractor.getStreams().getItemList().isEmpty());
    }

    @Test
    public void testGetStreamsErrors() throws Exception {
        assertTrue("errors during stream list extraction", extractor.getStreams().getErrors().isEmpty());
    }

    @Test
    public void testHasMoreStreams() throws Exception {
        // Setup the streams
        extractor.getStreams();
        assertTrue("extractor didn't have more streams", !extractor.hasMoreStreams());
    }

    @Test(expected = ExtractionException.class)
    public void testGetNextStreamsNonExistent() throws Exception {
        // Setup the streams
        extractor.getStreams();

        // This playlist don't have more streams, it should throw an error
        extractor.getNextStreams();

        fail("Expected exception wasn't thrown");
    }
}
