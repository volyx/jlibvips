package com.docutools.jlibvips;

public class FailedOnThumbnailException extends VipsException {

    public FailedOnThumbnailException(int ret) {
        super("vips_thumbnail_image", ret);
    }

}
