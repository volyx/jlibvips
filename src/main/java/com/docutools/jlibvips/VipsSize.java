package com.docutools.jlibvips;

/**
 * Java representation of <code>VipsSize</code> enum from libvips.
 *
 * @see <a href="http://libvips.github.io/libvips/API/current/libvips-resample.html#VipsSize">VipsSize</a>
 * @author amp
 */
public enum VipsSize {
    Nearest,
    Linear,
    Cubic,
    Mitchell,
    LANCZOS2,
    LANCZOS3,
    Last
}
