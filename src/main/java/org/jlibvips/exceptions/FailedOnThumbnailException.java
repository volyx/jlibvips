package org.jlibvips.exceptions;

public class FailedOnThumbnailException extends VipsException {

    public FailedOnThumbnailException(int ret) {
        super("vips_thumbnail_image", ret);
    }

}
