package com.docutools.jlibvips;

import com.sun.jna.Pointer;

import java.nio.file.Path;
import java.util.List;

public class DeepZoomOperation {

    private final VipsImage image;
    private final Path outDir;

    private DeepZoomLayouts layout;
    private Pointer background;
    private VipsAngle rotate;
    private DeepZoomContainer container;
    private Integer tileSize;
    private Boolean strip;

    DeepZoomOperation(VipsImage image, Path outDir) {
        this.image = image;
        this.outDir = outDir;
    }

    public void save() {
        int ret = VipsBindings.INSTANCE.vips_dzsave(image.getPtr(), outDir.toString(),
                new Varargs()
                        .add("layout", layout.ordinal())
                        .add("background", background)
                        .add("angle", rotate.ordinal())
                        .add("container", container.ordinal())
                        .add("tile_size", tileSize)
                        .add("strip", strip!=null&&strip? 1 : 0)
                        .toArray());
        if(ret != 0) {
            throw new DeepZoomSaveException(ret);
        }
    }

    public DeepZoomOperation layout(DeepZoomLayouts layout) {
        this.layout = layout;
        return this;
    }

    public DeepZoomOperation background(List<Float> background) {
        return background(background != null? background.stream().mapToDouble(Float::doubleValue).toArray() : null);
    }

    public DeepZoomOperation background(double[] background) {
        this.background = background != null?
                VipsBindings.INSTANCE.vips_array_double_new(background, background.length) : null;
        return this;
    }

    public DeepZoomOperation rotate(VipsAngle rotate) {
        this.rotate = rotate;
        return this;
    }

    public DeepZoomOperation container(DeepZoomContainer container) {
        this.container = container;
        return this;
    }

    public DeepZoomOperation tileSize(Integer tileSize) {
        this.tileSize = tileSize;
        return this;
    }

    public DeepZoomOperation strip() {
        this.strip = true;
        return this;
    }
}
