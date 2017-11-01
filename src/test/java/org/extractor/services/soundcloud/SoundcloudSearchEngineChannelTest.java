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

/**
 * Test for {@link SearchEngine}
 */
public class SoundcloudSearchEngineChannelTest {
    private SearchResult result;

    @Before
    public void setUp() throws Exception {
        NewPipe.init(Downloader.getInstance());
        SearchEngine engine = SoundCloud.getService().getSearchEngine();

        // SoundCloud will suggest "lil uzi vert" instead of "lill uzi vert"
        // keep in mind that the suggestions can NOT change by country (the parameter "de")
        result = engine.search("lill uzi vert", 0, "de", SearchEngine.Filter.CHANNEL)
                .getSearchResult();
    }

    @Test
    public void testResultList() {
        assertFalse(result.resultList.isEmpty());
    }

    @Test
    public void testResultsItemType() {
        for (InfoItem infoItem : result.resultList) {
            assertEquals(InfoItem.InfoType.CHANNEL, infoItem.info_type);
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
