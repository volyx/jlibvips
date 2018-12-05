package com.docutools.jlibvips;

public class DeepZoomSaveException extends VipsException {
    public DeepZoomSaveException(int returnValue) {
        super("dzsave", returnValue);
    }
}
