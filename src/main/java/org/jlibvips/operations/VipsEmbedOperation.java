package org.jlibvips.operations;

import com.sun.jna.Pointer;
import org.jlibvips.VipsExtend;
import org.jlibvips.VipsImage;
import org.jlibvips.exceptions.VipsException;
import org.jlibvips.jna.VipsBindings;
import org.jlibvips.jna.VipsBindingsSingleton;
import org.jlibvips.util.Varargs;
import org.jlibvips.util.VipsUtils;

import java.util.List;

import static org.jlibvips.util.VipsUtils.toOrdinal;

public class VipsEmbedOperation {

    private final Pointer in;
    private final int x,y,width,height;

    private VipsExtend extend;
    private Pointer background;

    public VipsEmbedOperation(Pointer in, int x, int y, int width, int height) {
        this.in = in;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public VipsImage create() {
        Pointer[] out = new Pointer[1];
        int ret = VipsBindingsSingleton.instance().vips_embed(this.in, out, x, y, width, height,
                new Varargs()
                        .add("extend", toOrdinal(extend))
                        .add("background", background)
                        .toArray());
        if(ret != 0) {
            throw new VipsException("vips_embed", ret);
        }
        return new VipsImage(out[0]);
    }

    public VipsEmbedOperation extend(VipsExtend extend) {
        this.extend = extend;
        return this;
    }

    /**
     * Normally edge tiles are filled with white, but you can set another colour with background.
     *
     * @param background background color
     * @return this
     */
    public VipsEmbedOperation background(List<Float> background) {
        return background(background != null? background.stream().mapToDouble(Float::doubleValue).toArray() : null);
    }

    /**
     * Normally edge tiles are filled with white, but you can set another colour with background.
     *
     * @param background background color
     * @return this
     */
    public VipsEmbedOperation background(double[] background) {
        this.background = background != null?
                 VipsUtils.toPointer(background) : null;
        return this;
    }
}
