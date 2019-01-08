package org.jlibvips.operations;

import com.sun.jna.Pointer;
import org.jlibvips.VipsDirection;
import org.jlibvips.VipsImage;
import org.jlibvips.exceptions.VipsException;
import org.jlibvips.jna.VipsBindings;
import org.jlibvips.util.Varargs;
import org.jlibvips.util.VipsUtils;

public class VipsJoinOperation {

    private final Pointer in1, in2;
    private VipsDirection direction = VipsDirection.Last;

    private Boolean expand;
    private Integer shim;
    private Pointer background;
    private Integer align;

    public VipsJoinOperation(VipsImage in1, VipsImage in2) {
        this.in1 = in1.getPtr();
        this.in2 = in2.getPtr();
    }

    public VipsImage create() {
        var out = new Pointer[1];
        int ret = VipsBindings.INSTANCE.vips_join(in1, in2, out, direction.ordinal(),
                new Varargs()
                        .add("expand", VipsUtils.booleanToInteger(expand))
                        .add("shim", shim)
                        .add("background", background)
                        .add("align", align).toArray());
        if(ret != 0) {
            throw new VipsException("vips_join", ret);
        }
        return new VipsImage(out[0]);
    }

    public VipsJoinOperation direction(VipsDirection direction) {
        this.direction = direction;
        return this;
    }

    public VipsJoinOperation expand(boolean expand) {
        this.expand = expand;
        return this;
    }

    public VipsJoinOperation shim(int shim) {
        this.shim = shim;
        return this;
    }

    public VipsJoinOperation background(double[] background) {
        this.background = VipsUtils.toPointer(background);
        return this;
    }

    public VipsJoinOperation align(int align) {
        this.align = align;
        return this;
    }

}
