package org.extractor.services.youtube;

/*
 * Created by Christian Schabesberger on 18.11.16.
 *
 * Copyright (C) Christian Schabesberger 2016 <chris.schabesberger@mailbox.org>
 * YoutubeSuggestionExtractorTest.java is part of NewPipe.
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

import org.junit.Before;
import org.junit.Test;
import org.schabi.newpipe.Downloader;
import org.extractor.NewPipe;
import org.extractor.SuggestionExtractor;
import org.extractor.exceptions.ExtractionException;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.extractor.ServiceList.YouTube;

/**
 * Test for {@link SuggestionExtractor}
 */
public class YoutubeSuggestionExtractorTest {
    private SuggestionExtractor suggestionExtractor;

    @Before
    public void setUp() throws Exception {
        NewPipe.init(Downloader.getInstance());
        suggestionExtractor = YouTube.getService().getSuggestionExtractor();
    }

    @Test
    public void testIfSuggestions() throws IOException, ExtractionException {
        assertFalse(suggestionExtractor.suggestionList("hello", "de").isEmpty());
    }
}
