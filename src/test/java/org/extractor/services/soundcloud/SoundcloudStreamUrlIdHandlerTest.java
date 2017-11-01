package org.extractor.services.soundcloud;

import org.junit.Before;
import org.junit.Test;
import org.schabi.newpipe.Downloader;
import org.extractor.NewPipe;
import org.extractor.exceptions.ParsingException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test for {@link SoundcloudStreamUrlIdHandler}
 */
public class SoundcloudStreamUrlIdHandlerTest {
    private SoundcloudStreamUrlIdHandler urlIdHandler;

    @Before
    public void setUp() throws Exception {
        urlIdHandler = SoundcloudStreamUrlIdHandler.getInstance();
        NewPipe.init(Downloader.getInstance());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getIdWithNullAsUrl() throws ParsingException {
        urlIdHandler.getId(null);
    }

    @Test
    public void getIdForInvalidUrls() {
        List<String> invalidUrls = new ArrayList<>(50);
        invalidUrls.add("https://soundcloud.com/liluzivert/t.e.s.t");
        invalidUrls.add("https://soundcloud.com/liluzivert/tracks");
        invalidUrls.add("https://soundcloud.com/");
        for (String invalidUrl : invalidUrls) {
            Throwable exception = null;
            try {
                urlIdHandler.getId(invalidUrl);
            } catch (ParsingException e) {
                exception = e;
            }
            if (exception == null) {
                fail("Expected ParsingException for url: " + invalidUrl);
            }
        }
    }

    @Test
    public void getId() throws Exception {
        assertEquals("309689103", urlIdHandler.getId("https://soundcloud.com/liluzivert/15-ysl"));
        assertEquals("309689082", urlIdHandler.getId("https://www.soundcloud.com/liluzivert/15-luv-scars-ko"));
        assertEquals("309689035", urlIdHandler.getId("http://soundcloud.com/liluzivert/15-boring-shit"));
        assertEquals("294488599", urlIdHandler.getId("http://www.soundcloud.com/liluzivert/secure-the-bag-produced-by-glohan-beats"));
        assertEquals("294488438", urlIdHandler.getId("HtTpS://sOuNdClOuD.cOm/LiLuZiVeRt/In-O4-pRoDuCeD-bY-dP-bEaTz"));
        assertEquals("294488147", urlIdHandler.getId("https://soundcloud.com/liluzivert/fresh-produced-by-zaytoven#t=69"));
        assertEquals("294487876", urlIdHandler.getId("https://soundcloud.com/liluzivert/threesome-produced-by-zaytoven#t=1:09"));
        assertEquals("294487684", urlIdHandler.getId("https://soundcloud.com/liluzivert/blonde-brigitte-produced-manny-fresh#t=1:9"));
        assertEquals("294487428", urlIdHandler.getId("https://soundcloud.com/liluzivert/today-produced-by-c-note#t=1m9s"));
        assertEquals("294487157", urlIdHandler.getId("https://soundcloud.com/liluzivert/changed-my-phone-produced-by-c-note#t=1m09s"));
    }


    @Test
    public void testAcceptUrl() {
        assertTrue(urlIdHandler.acceptUrl("https://soundcloud.com/liluzivert/15-ysl"));
        assertTrue(urlIdHandler.acceptUrl("https://www.soundcloud.com/liluzivert/15-luv-scars-ko"));
        assertTrue(urlIdHandler.acceptUrl("http://soundcloud.com/liluzivert/15-boring-shit"));
        assertTrue(urlIdHandler.acceptUrl("http://www.soundcloud.com/liluzivert/secure-the-bag-produced-by-glohan-beats"));
        assertTrue(urlIdHandler.acceptUrl("HtTpS://sOuNdClOuD.cOm/LiLuZiVeRt/In-O4-pRoDuCeD-bY-dP-bEaTz"));
        assertTrue(urlIdHandler.acceptUrl("https://soundcloud.com/liluzivert/fresh-produced-by-zaytoven#t=69"));
        assertTrue(urlIdHandler.acceptUrl("https://soundcloud.com/liluzivert/threesome-produced-by-zaytoven#t=1:09"));
        assertTrue(urlIdHandler.acceptUrl("https://soundcloud.com/liluzivert/blonde-brigitte-produced-manny-fresh#t=1:9"));
        assertTrue(urlIdHandler.acceptUrl("https://soundcloud.com/liluzivert/today-produced-by-c-note#t=1m9s"));
        assertTrue(urlIdHandler.acceptUrl("https://soundcloud.com/liluzivert/changed-my-phone-produced-by-c-note#t=1m09s"));
    }
}