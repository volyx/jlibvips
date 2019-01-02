package org.jlibvips.exceptions;

public class DeepZoomSaveException extends VipsException {
    public DeepZoomSaveException(int returnValue) {
        super("dzsave", returnValue);
    }
}
