package org.extractor.services.youtube;

/*
 * Created by Christian Schabesberger on 12.08.17.
 *
 * Copyright (C) Christian Schabesberger 2017 <chris.schabesberger@mailbox.org>
 * YoutubeTreindingKioskInfoTest.java is part of NewPipe.
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
import org.extractor.StreamingService;
import org.extractor.UrlIdHandler;
import org.extractor.kiosk.KioskInfo;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.extractor.ServiceList.YouTube;

/**
 * Test for {@link KioskInfo}
 */
public class YoutubeTreindingKioskInfoTest {
    KioskInfo kioskInfo;

    @Before
    public void setUp()
            throws Exception {
        NewPipe.init(Downloader.getInstance());
        StreamingService service = YouTube.getService();
        UrlIdHandler urlIdHandler = service.getKioskList().getUrlIdHandlerByType("Trending");

        kioskInfo = KioskInfo.getInfo(YouTube.getService(), urlIdHandler.getUrl("Trending"), null);
    }

    @Test
    public void getStreams() {
        assertFalse(kioskInfo.related_streams.isEmpty());
    }

    @Test
    public void getId() {
        assertEquals(kioskInfo.id, "Trending");
    }

    @Test
    public void getName() {
        assertFalse(kioskInfo.name.isEmpty());
    }
}
