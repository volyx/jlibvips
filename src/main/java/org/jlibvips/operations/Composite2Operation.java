package org.jlibvips.operations;

import com.sun.jna.Pointer;
import org.jlibvips.VipsBlendMode;
import org.jlibvips.VipsImage;
import org.jlibvips.VipsInterpretation;
import org.jlibvips.exceptions.VipsException;
import org.jlibvips.jna.VipsBindingsSingleton;
import org.jlibvips.util.Varargs;
import org.jlibvips.util.VipsUtils;

public class Composite2Operation {

    private final Pointer base, overlay;
    private final VipsBlendMode blendMode;

    private VipsInterpretation compositionSpace;
    private Boolean premultiplied;
    private Integer x, y;

    public Composite2Operation(Pointer base, Pointer overlay, VipsBlendMode blendMode) {
        this.base = base;
        this.overlay = overlay;
        this.blendMode = blendMode;
    }

    public VipsImage create() {
        var out = new Pointer[1];
        int ret = VipsBindingsSingleton.instance().vips_composite2(base, overlay, out, VipsUtils.toOrdinal(blendMode),
                new Varargs().add("composition_space", VipsUtils.toOrdinal(compositionSpace))
                        .add("premultiplied", VipsUtils.booleanToInteger(premultiplied))
                        .add("x", x)
                        .add("y", y).toArray());
        if (ret != 0) {
            throw new VipsException("vips_composite2", ret);
        }
        return new VipsImage(out[0]);
    }

    public Composite2Operation compositionSpace(VipsInterpretation space) {
        this.compositionSpace = space;
        return this;
    }

    public Composite2Operation premultiplied(Boolean premultiplied) {
        this.premultiplied = premultiplied;
        return this;
    }

    public Composite2Operation x(Integer x) {
        this.x = x;
        return this;
    }

    public Composite2Operation y(Integer y) {
        this.y = y;
        return this;
    }

}
