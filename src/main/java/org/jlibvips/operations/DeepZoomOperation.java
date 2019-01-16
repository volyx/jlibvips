package org.jlibvips.operations;

import org.jlibvips.DeepZoomContainer;
import org.jlibvips.DeepZoomLayouts;
import org.jlibvips.VipsAngle;
import org.jlibvips.VipsImage;
import org.jlibvips.exceptions.DeepZoomSaveException;
import org.jlibvips.jna.VipsBindings;
import org.jlibvips.jna.VipsBindingsSingleton;
import org.jlibvips.util.Varargs;
import com.sun.jna.Pointer;

import java.nio.file.Path;
import java.util.List;

import static org.jlibvips.util.VipsUtils.*;

/**
 * Saves an image as a set of tiles at various resolutions. By default {@link DeepZoomOperation} uses the
 * {@link DeepZoomLayouts#DeepZoom} layout. Call {@link DeepZoomOperation#layout(DeepZoomLayouts)} to change that.
 *
 * <a href="http://libvips.github.io/libvips/API/current/VipsForeignSave.html#vips-dzsave">Native Function</a>
 *
 * @author amp
 */
public class DeepZoomOperation {

    private final VipsImage image;
    private final Path outDir;

    private String baseName;
    private DeepZoomLayouts layout;
    private Pointer background;
    private VipsAngle rotate;
    private DeepZoomContainer container;
    private Integer tileSize;
    private boolean centre;
    private Boolean strip;
    private String suffix;

    public DeepZoomOperation(VipsImage image, Path outDir) {
        this.image = image;
        this.outDir = outDir;
    }

    public void save() {
        int ret = VipsBindingsSingleton.instance().vips_dzsave(image.getPtr(), outDir.toString(),
                new Varargs()
                        .add("basename", baseName)
                        .add("layout", toOrdinal(layout))
                        .add("background", background)
                        .add("angle", toOrdinal(rotate))
                        .add("container", toOrdinal(container))
                        .add("tile_size", tileSize)
                        .add("centre", booleanToInteger(centre))
                        .add("strip", booleanToInteger(strip))
                        .add("suffix", suffix)
                        .toArray());
        if(ret != 0) {
            throw new DeepZoomSaveException(ret);
        }
    }

    /**
     * Use basename to set the name of the directory tree we are creating. The default value is set from name.
     *
     * @param n basename value
     * @return this
     */
    public DeepZoomOperation baseName(String n) {
        this.baseName = n;
        return this;
    }

    /**
     * Sets the tile layout.
     *
     * @param layout {@link DeepZoomOperation}
     * @return this
     */
    public DeepZoomOperation layout(DeepZoomLayouts layout) {
        this.layout = layout;
        return this;
    }

    /**
     * Normally edge tiles are filled with white, but you can set another colour with background.
     *
     * @param background background color
     * @return this
     */
    public DeepZoomOperation background(List<Float> background) {
        return background(background != null? background.stream().mapToDouble(Float::doubleValue).toArray() : null);
    }

    /**
     * Normally edge tiles are filled with white, but you can set another colour with background.
     *
     * @param background background color
     * @return this
     */
    public DeepZoomOperation background(double[] background) {
        this.background = background != null?
                VipsBindingsSingleton.instance().vips_array_double_new(background, background.length) : null;
        return this;
    }

    /**
     * Sets the rotation angle, before tiling the picture.
     *
     * @param rotate {@link VipsAngle}
     * @return this
     */
    public DeepZoomOperation rotate(VipsAngle rotate) {
        this.rotate = rotate;
        return this;
    }

    /**
     * Defines the output format of this operation.
     *
     * @param container {@link DeepZoomContainer}
     * @return this
     */
    public DeepZoomOperation container(DeepZoomContainer container) {
        this.container = container;
        return this;
    }

    /**
     * Width/height of the generated tiles.
     *
     * @param tileSize tile size pixel
     * @return this
     */
    public DeepZoomOperation tileSize(Integer tileSize) {
        this.tileSize = tileSize;
        return this;
    }

    /**
     * When called edge tiles will be centred instead of being put top-left.
     *
     * @return this
     */
    public DeepZoomOperation centre() {
        this.centre = true;
        return this;
    }

    /**
     * Strips the metadata from the tiles.
     *
     * @return this
     */
    public DeepZoomOperation strip() {
        this.strip = true;
        return this;
    }

    /**
     * You can set suffix to something like <code>".jpg[Q=85]"</code> to control the tile write options.
     *
     * @param suffix Tile name suffix.
     * @return this
     */
    public DeepZoomOperation suffix(String suffix) {
        this.suffix = suffix;
        return this;
    }
}
