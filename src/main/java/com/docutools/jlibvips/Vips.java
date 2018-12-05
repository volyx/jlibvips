package com.docutools.jlibvips;

import com.sun.jna.Pointer;

public class Vips {

    public static Pointer pdfload(String fileName, Object...args) {
        Pointer[] ptr = new Pointer[1];
        var ret = VipsBindings.INSTANCE.vips_pdfload(fileName, ptr, args);
        if(ret != 0) {
            throw new CouldNotLoadPdfVipsException(ret);
        }
        return ptr[0];
    }

}
