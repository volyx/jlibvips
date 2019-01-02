package org.jlibvips.exceptions;

public class WebpConversionException extends VipsException {

    public WebpConversionException(int ret) {
        super("vips_webpsave", ret);
    }

}
