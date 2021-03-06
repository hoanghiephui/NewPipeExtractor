package org.extractor.playlist;

import org.extractor.InfoItemCollector;
import org.extractor.exceptions.ParsingException;

public class PlaylistInfoItemCollector extends InfoItemCollector {
    public PlaylistInfoItemCollector(int serviceId) {
        super(serviceId);
    }

    public PlaylistInfoItem extract(PlaylistInfoItemExtractor extractor) throws ParsingException {
        final PlaylistInfoItem resultItem = new PlaylistInfoItem();

        resultItem.name = extractor.getName();
        resultItem.service_id = getServiceId();
        resultItem.url = extractor.getUrl();

        try {
            resultItem.uploader_name = extractor.getUploaderName();
        } catch (Exception e) {
            addError(e);
        }
        try {
            resultItem.thumbnail_url = extractor.getThumbnailUrl();
        } catch (Exception e) {
            addError(e);
        }
        try {
            resultItem.stream_count = extractor.getStreamCount();
        } catch (Exception e) {
            addError(e);
        }
        return resultItem;
    }

    public void commit(PlaylistInfoItemExtractor extractor) throws ParsingException {
        try {
            addItem(extract(extractor));
        } catch (Exception e) {
            addError(e);
        }
    }
}
