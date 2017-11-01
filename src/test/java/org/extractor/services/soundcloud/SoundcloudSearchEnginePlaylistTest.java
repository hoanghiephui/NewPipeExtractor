package org.extractor.services.soundcloud;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.schabi.newpipe.Downloader;
import org.extractor.InfoItem;
import org.extractor.NewPipe;
import org.extractor.search.SearchEngine;
import org.extractor.search.SearchResult;

import static org.junit.Assert.*;
import static org.extractor.ServiceList.SoundCloud;


/*
 * Created by Christian Schabesberger on 29.12.15.
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
 * Test for {@link SearchEngine}
 */
public class SoundcloudSearchEnginePlaylistTest {
    private SearchResult result;

    @Before
    public void setUp() throws Exception {
        NewPipe.init(Downloader.getInstance());
        SearchEngine engine = SoundCloud.getService().getSearchEngine();

        // Search by country not yet implemented
        result = engine.search("parkmemme", 0, "", SearchEngine.Filter.PLAYLIST)
                .getSearchResult();
    }

    @Test
    public void testResultList() {
        assertFalse(result.resultList.isEmpty());
    }

    @Test
    public void testUserItemType() {
        for (InfoItem infoItem : result.resultList) {
            assertEquals(InfoItem.InfoType.PLAYLIST, infoItem.info_type);
        }
    }

    @Test
    public void testResultErrors() {
        if (!result.errors.isEmpty()) for (Throwable error : result.errors) error.printStackTrace();
        assertTrue(result.errors == null || result.errors.isEmpty());
    }

    @Ignore
    @Test
    public void testSuggestion() {
        //todo write a real test
        assertTrue(result.suggestion != null);
    }
}
