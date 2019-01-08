package org.jlibvips.operations;

import org.jlibvips.VipsImage;
import org.jlibvips.exceptions.VipsException;
import org.jlibvips.jna.VipsBindings;
import org.jlibvips.util.Varargs;

import java.util.List;

import static org.jlibvips.util.VipsUtils.booleanToInteger;
import static org.jlibvips.util.VipsUtils.toPointer;

public class DrawRectOperation {

    private final VipsImage image;

    private int width = 0, height = 0;
    private int top = 0, left = 0;
    private double[] ink;

    private Boolean fill;

    public DrawRectOperation(VipsImage image) {
        this.image = image;
    }

    public void draw() {
        if(ink == null || ink.length == 0)
            ink = new double[]{0.0};
        var args = new Varargs()
                .add("fill", booleanToInteger(fill))
                .toArray();
        int ret;
        if(ink.length == 1) {
            ret = VipsBindings.INSTANCE.vips_draw_rect1(image.getPtr(), ink[0], left, top, width, height, args);
        } else {
            ret = VipsBindings.INSTANCE.vips_draw_rect(image.getPtr(), toPointer(ink), ink.length, left, top, width, height, args);
        }
        if(ret != 0) {
            throw new VipsException("vips_draw_rect(1)", ret);
        }
    }

    public DrawRectOperation width(int width) {
        this.width = width;
        return this;
    }

    public DrawRectOperation height(int height) {
        this.height = height;
        return this;
    }

    public DrawRectOperation top(int top) {
        this.top = top;
        return this;
    }

    public DrawRectOperation left(int left) {
        this.left = left;
        return this;
    }

    public DrawRectOperation ink(List<Float> ink) {
        return ink(ink.stream().mapToDouble(Float::doubleValue).toArray());
    }

    public DrawRectOperation ink(double ink) {
        return ink(new double[]{ink});
    }

    public DrawRectOperation ink(double[] ink) {
        this.ink = ink;
        return this;
    }

    public DrawRectOperation fill(boolean fill) {
        this.fill = fill;
        return this;
    }
}
