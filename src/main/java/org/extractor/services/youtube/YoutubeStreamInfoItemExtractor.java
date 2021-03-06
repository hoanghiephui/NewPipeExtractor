package org.extractor.services.youtube;

import org.jsoup.nodes.Element;
import org.extractor.exceptions.ParsingException;
import org.extractor.stream.StreamInfoItemExtractor;
import org.extractor.stream.StreamType;
import org.extractor.utils.Utils;

/*
 * Copyright (C) Christian Schabesberger 2016 <chris.schabesberger@mailbox.org>
 * YoutubeStreamInfoItemExtractor.java is part of NewPipe.
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

public class YoutubeStreamInfoItemExtractor implements StreamInfoItemExtractor {

    private final Element item;
    private final String idChannel;

    public YoutubeStreamInfoItemExtractor(Element item, String idChannel) {
        this.item = item;
        this.idChannel = idChannel;
    }

    @Override
    public StreamType getStreamType() throws ParsingException {
        if (isLiveStream(item)) {
            return StreamType.LIVE_STREAM;
        } else {
            return StreamType.VIDEO_STREAM;
        }
    }

    @Override
    public boolean isAd() throws ParsingException {
        return !item.select("span[class*=\"icon-not-available\"]").isEmpty()
                || !item.select("span[class*=\"yt-badge-ad\"]").isEmpty();
    }

    @Override
    public String getUrl() throws ParsingException {
        try {
            Element el = item.select("div[class*=\"yt-lockup-video\"").first();
            Element dl = el.select("h3").first().select("a").first();
            return dl.attr("abs:href");
        } catch (Exception e) {
            throw new ParsingException("Could not get web page url for the video", e);
        }
    }

    @Override
    public String getName() throws ParsingException {
        try {
            Element el = item.select("div[class*=\"yt-lockup-video\"").first();
            Element dl = el.select("h3").first().select("a").first();
            return dl.text();
        } catch (Exception e) {
            throw new ParsingException("Could not get title", e);
        }
    }

    @Override
    public long getDuration() throws ParsingException {
        try {
            if (getStreamType() == StreamType.LIVE_STREAM) return -1;

            final Element duration = item.select("span[class*=\"video-time\"]").first();
            // apparently on youtube, video-time element will not show up if the video has a duration of 00:00
            // see: https://www.youtube.com/results?sp=EgIQAVAU&q=asdfgf
            return duration == null ? 0 : YoutubeParsingHelper.parseDurationString(duration.text());
        } catch (Exception e) {
            throw new ParsingException("Could not get Duration: " + getUrl(), e);
        }
    }

    @Override
    public String getUploaderName() throws ParsingException {
        try {
            return item.select("div[class=\"yt-lockup-byline\"]").first()
                    .select("a").first()
                    .text();
        } catch (Exception e) {
            throw new ParsingException("Could not get uploader", e);
        }
    }

    @Override
    public String getUploadDate() throws ParsingException {
        try {
            Element meta = item.select("div[class=\"yt-lockup-meta\"]").first();
            if (meta == null) return "";

            return meta.select("li").first().text();
        } catch (Exception e) {
            throw new ParsingException("Could not get upload date", e);
        }
    }

    @Override
    public long getViewCount() throws ParsingException {
        String input;
        try {
            // TODO: Return the actual live stream's watcher count
            // -1 for no view count
            if (getStreamType() == StreamType.LIVE_STREAM) return -1;

            Element meta = item.select("div[class=\"yt-lockup-meta\"]").first();
            if (meta == null) return -1;

            input = meta.select("li").get(1).text();
        } catch (IndexOutOfBoundsException e) {
            throw new ParsingException("Could not parse yt-lockup-meta although available: " + getUrl(), e);
        }

        try {
            return Long.parseLong(Utils.removeNonDigitCharacters(input));
        } catch (NumberFormatException e) {
            // if this happens the video probably has no views
            if (!input.isEmpty()) {
                return 0;
            }

            throw new ParsingException("Could not handle input: " + input, e);
        }
    }

    @Override
    public String getThumbnailUrl() throws ParsingException {
        try {
            String url;
            Element te = item.select("div[class=\"yt-thumb video-thumb\"]").first()
                    .select("img").first();
            url = te.attr("abs:src");
            // Sometimes youtube sends links to gif files which somehow seem to not exist
            // anymore. Items with such gif also offer a secondary image source. So we are going
            // to use that if we've caught such an item.
            if (url.contains(".gif")) {
                url = te.attr("abs:data-thumb");
            }
            return url;
        } catch (Exception e) {
            throw new ParsingException("Could not get thumbnail url", e);
        }
    }

    @Override
    public String getIdChannel() throws ParsingException {
        /*String id;

        try {
            final Element div = item.select("div[class=\"yt-lockup-byline\"]").first()
                    .select("a").first();

            id = div.attr("data-ytid");
        } catch (Exception e) {
            try {
                Element element = item.getElementsByClass("yt-uix-subscription-button").first();
                if (element == null) element = item.getElementsByClass("yt-uix-subscription-preferences-button").first();

                return element.attr("data-channel-external-id");
            } catch (Exception ex) {
                throw new ParsingException("Could not get channel id", ex);
            }
            //throw new ParsingException("Failed to extract playlist uploader", e);
        }*/

        return idChannel;
    }

    @Override
    public boolean isGetChannelId() {
        try {
            final Element div = item.select("div[class=\"yt-lockup-byline\"]").first()
                    .select("a").first();
            return true;
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * Generic method that checks if the element contains any clues that it's a livestream item
     */
    protected static boolean isLiveStream(Element item) {
        return !item.select("span[class*=\"yt-badge-live\"]").isEmpty()
                || !item.select("span[class*=\"video-time-overlay-live\"]").isEmpty();
    }
}
