package org.extractor.services.youtube;

import org.junit.Before;
import org.junit.Test;
import org.schabi.newpipe.Downloader;
import org.extractor.ListExtractor;
import org.extractor.NewPipe;
import org.extractor.channel.ChannelExtractor;

import static org.junit.Assert.*;
import static org.extractor.ServiceList.YouTube;

/*
 * Created by Christian Schabesberger on 12.09.16.
 *
 * Copyright (C) Christian Schabesberger 2015 <chris.schabesberger@mailbox.org>
 * YoutubeSearchEngineStreamTest.java is part of NewPipe.
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
 * Test for {@link ChannelExtractor}
 */
public class YoutubeChannelExtractorTest {

    ChannelExtractor extractor;

    @Before
    public void setUp() throws Exception {
        NewPipe.init(Downloader.getInstance());
        extractor = YouTube.getService()
                .getChannelExtractor("https://www.youtube.com/user/Gronkh");
    }

    @Test
    public void testGetDownloader() throws Exception {
        assertNotNull(NewPipe.getDownloader());
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals(extractor.getName(), "Gronkh");
    }

    @Test
    public void testGetId() throws Exception {
        assertEquals(extractor.getId(), "UCYJ61XIK64sp6ZFFS8sctxw");
    }

    @Test
    public void testGetUrl() throws Exception {
        assertEquals(extractor.getCleanUrl(), "https://www.youtube.com/channel/UCYJ61XIK64sp6ZFFS8sctxw");
    }

    @Test
    public void testGetDescription() throws Exception {
        assertEquals(extractor.getDescription(), "★ ★ ★ KLICK MICH HART, DU SAU! :D ★ ★ ★ Zart im Schmelz und süffig im Abgang. Ungebremster Spieltrieb seit 1896. Tägliche Folgen nonstop seit dem 01.04.2010!...");
    }

    @Test
    public void testGetAvatarUrl() throws Exception {
        assertTrue(extractor.getAvatarUrl(), extractor.getAvatarUrl().contains("yt3"));
    }

    @Test
    public void testGetBannerUrl() throws Exception {
        assertTrue(extractor.getBannerUrl(), extractor.getBannerUrl().contains("yt3"));
    }

    @Test
    public void testGetFeedUrl() throws Exception {
        assertEquals(extractor.getFeedUrl(), "https://www.youtube.com/feeds/videos.xml?channel_id=UCYJ61XIK64sp6ZFFS8sctxw");
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
        assertTrue("wrong subscriber count", extractor.getSubscriberCount() >= 0);
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
