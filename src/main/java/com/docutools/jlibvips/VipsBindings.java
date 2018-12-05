package com.docutools.jlibvips;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public interface VipsBindings extends Library {

    VipsBindings INSTANCE = Native.load("libvips.42.dylib", VipsBindings.class);

    int vips_pdfload(String fileName, Pointer[] pointer, Object...args);

    int vips_image_get_width(Pointer pointer);
    int vips_image_get_height(Pointer pointer);
}
