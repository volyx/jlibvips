package com.docutools.jlibvips;

import com.sun.jna.Pointer;

public class ThumbnailOperation {

    private final VipsImage image;
    private final int width;

    private Boolean autoRotate;
    private VipsSize size;
    private VipsInteresting crop;
    private Boolean linear;


    ThumbnailOperation(VipsImage image, int width) {
        this.image = image;
        this.width = width;
    }

    public VipsImage create() {
        var pointers = new Pointer[1];
        int ret = VipsBindings.INSTANCE.vips_thumbnail_image(image.getPtr(), pointers, width,
                new Varargs().add("auto_rotate", autoRotate)
                        .add("size", size.ordinal())
                        .add("crop", crop.ordinal())
                        .add("linear", linear)
                        .toArray());
        if(ret != 0) {
            throw new FailedOnThumbnailException(ret);
        }
        return new VipsImage(pointers[0]);
    }

    public ThumbnailOperation autoRotate() {
        this.autoRotate = true;
        return this;
    }

    public ThumbnailOperation size(VipsSize size) {
        this.size = size;
        return this;
    }

    public ThumbnailOperation crop(VipsInteresting crop) {
        this.crop = crop;
        return this;
    }

    public ThumbnailOperation linear() {
        this.linear = true;
        return this;
    }
}
