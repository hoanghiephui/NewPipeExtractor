package org.extractor.playlist;

import org.extractor.InfoItemExtractor;
import org.extractor.exceptions.ParsingException;

public interface PlaylistInfoItemExtractor extends InfoItemExtractor {
    String getUploaderName() throws ParsingException;
    long getStreamCount() throws ParsingException;
}
