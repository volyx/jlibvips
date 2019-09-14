package org.jlibvips.jna;

import com.sun.jna.Library;
import com.sun.jna.Pointer;
import org.jlibvips.VipsDirection;

public interface VipsBindings extends Library {

    Pointer vips_image_new_from_file(String fileName, Object...args);
    Pointer vips_image_new_from_buffer(byte[] buf, long length, String optionString, Object...args);

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

    int vips_insert(Pointer main, Pointer sub, Pointer[] out, int x, int y, Object...args);
    int vips_join(Pointer in1, Pointer in2, Pointer[] out, int direction, Object...args);

    int vips_draw_rect1(Pointer image, double ink, int left, int top, int width, int height, Object...args);
    int vips_draw_rect(Pointer image, Pointer ink, int n, int left, int top, int width, int height, Object...args);

    int vips_extract_area(Pointer image, Pointer[] out, int left, int top, int width, int height, Object...args);
    int vips_resize(Pointer in, Pointer[] out, double scale, Object...args);

    int vips_reduce(Pointer in, Pointer[] out, double hshrink, double vshrink, Object...args);
    int vips_embed(Pointer image, Pointer[] out, int x, int y, int width, int height, Object...args);
    int vips_composite2(Pointer base, Pointer overlay, Pointer[] out, int mode, Object...args);
    int vips_merge(Pointer ref, Pointer sec, Pointer[] out, int direction, int dx, int dy, Object...args);
    int vips_rotate(Pointer in, Pointer[] out, double angle, Object...args);
    int vips_similarity(Pointer in, Pointer[] out, Object...args);

    int vips_gaussblur(Pointer in, Pointer[] out, double sigma, Object...args);
}
