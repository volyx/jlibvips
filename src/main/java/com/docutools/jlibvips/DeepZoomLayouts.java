package com.docutools.jlibvips;

/**
 * Java representation of the <code>VipsForeignDzLayout</code> enum from libvips.
 *
 * @see <a href="http://libvips.github.io/libvips/API/current/VipsForeignSave.html#VipsForeignDzLayout">VipsForeignDzLayout</a>
 * @author amp
 */
public enum DeepZoomLayouts {
    DeepZoom,
    Zoomify,
    /**
     * In Google layout mode, edge tiles are expanded to tile_size by tile_size pixels.
     */
    Google,
    Last
}
