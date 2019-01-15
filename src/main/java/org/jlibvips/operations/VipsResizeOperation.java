package org.jlibvips.operations;

import com.sun.jna.Pointer;
import org.jlibvips.VipsImage;
import org.jlibvips.VipsKernel;
import org.jlibvips.exceptions.VipsException;
import org.jlibvips.jna.VipsBindings;
import org.jlibvips.util.Varargs;

import static org.jlibvips.util.VipsUtils.toOrdinal;

public class VipsResizeOperation {

    private final Pointer in;
    private final double scale;

    private Double verticalScale;
    private VipsKernel kernel;

    public VipsResizeOperation(Pointer in, double scale) {
        this.in = in;
        this.scale = scale;
    }

    public VipsImage create() {
        var out = new Pointer[1];
        int ret = VipsBindings.INSTANCE.vips_resize(this.in, out, scale,
                new Varargs()
                        .add("vscale", verticalScale)
                        .add("kernel", toOrdinal(kernel))
                        .toArray());
        if(ret != 0) {
            throw new VipsException("vips_resize", ret);
        }
        return new VipsImage(out[0]);
    }

    public VipsResizeOperation verticalScale(double verticalScale) {
        this.verticalScale = verticalScale;
        return this;
    }

    public VipsResizeOperation kernel(VipsKernel kernel) {
        this.kernel = kernel;
        return this;
    }
}
