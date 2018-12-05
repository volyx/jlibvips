package com.docutools.jlibvips;

public class CouldNotLoadPdfVipsException extends VipsException {

    public CouldNotLoadPdfVipsException(int returnValue) {
        super("pdfload", returnValue);
    }

}
