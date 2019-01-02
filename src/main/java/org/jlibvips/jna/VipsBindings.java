package org.jlibvips.jna;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public interface VipsBindings extends Library {

    VipsBindings INSTANCE = Native.load("libvips.42.dylib", VipsBindings.class);

    Pointer vips_image_new_from_file(String fileName, Object...args);

    int vips_pdfload(String fileName, Pointer[] pointer, Object...args);

    int vips_image_get_width(Pointer pointer);
    int vips_image_get_height(Pointer pointer);

    int vips_image_get_bands(Pointer pointer);

    int vips_dzsave(Pointer pointer, String outDir, Object...args);

    int vips_thumbnail_image(Pointer in, Pointer[] out, int width, Object...args);

    Pointer vips_array_double_new(double[] array, int n);

    int vips_vipssave(Pointer in, String filename, Object...args);
    int vips_jpegsave(Pointer in, String filename, Object...args);
    int vips_webpsave(Pointer in, String filename, Object...args);
}
