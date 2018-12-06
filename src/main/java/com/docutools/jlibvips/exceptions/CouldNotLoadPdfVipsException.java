package com.docutools.jlibvips.exceptions;

public class CouldNotLoadPdfVipsException extends VipsException {

    public CouldNotLoadPdfVipsException(int returnValue) {
        super("pdfload", returnValue);
    }

}
