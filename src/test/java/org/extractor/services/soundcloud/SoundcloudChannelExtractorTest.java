package org.extractor.services.soundcloud;

import org.junit.Before;
import org.junit.Test;
import org.schabi.newpipe.Downloader;
import org.extractor.ListExtractor;
import org.extractor.NewPipe;
import org.extractor.channel.ChannelExtractor;

import static org.junit.Assert.*;
import static org.extractor.ServiceList.SoundCloud;

/**
 * Test for {@link ChannelExtractor}
 */

public class SoundcloudChannelExtractorTest {

    ChannelExtractor extractor;

    @Before
    public void setUp() throws Exception {
        NewPipe.init(Downloader.getInstance());
        extractor = SoundCloud.getService()
                .getChannelExtractor("https://soundcloud.com/liluzivert");
    }

    @Test
    public void testGetDownloader() throws Exception {
        assertNotNull(NewPipe.getDownloader());
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals("LIL UZI VERT", extractor.getName());
    }

    @Test
    public void testGetDescription() throws Exception {
        assertTrue(extractor.getDescription() != null);
    }

    @Test
    public void testGetAvatarUrl() throws Exception {
        assertTrue(extractor.getAvatarUrl().contains("https://"));
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
        assertTrue("don't have more streams", extractor.hasMoreStreams());
    }

    @Test
    public void testGetSubscriberCount() throws Exception {
        assertTrue("wrong subscriber count", extractor.getSubscriberCount() >= 1000000);
    }

    @Test
    public void testGetNextStreams() throws Exception {
        // Setup the streams
        extractor.getStreams();
        ListExtractor.NextItemsResult nextItemsResult = extractor.getNextStreams();
        assertTrue("extractor didn't have next streams", !nextItemsResult.nextItemsList.isEmpty());
        assertTrue("errors occurred during extraction of the next streams", nextItemsResult.errors.isEmpty());
        assertTrue("extractor didn't have more streams after getNextStreams", extractor.hasMoreStreams());
    }

}
