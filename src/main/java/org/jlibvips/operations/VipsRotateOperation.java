package org.jlibvips.operations;

import com.sun.jna.Pointer;
import org.jlibvips.VipsAngle;
import org.jlibvips.VipsImage;
import org.jlibvips.exceptions.VipsException;
import org.jlibvips.jna.VipsBindingsSingleton;
import org.jlibvips.util.Varargs;

import java.util.List;

/**
 * Rotates the given {@link org.jlibvips.VipsImage} by the {@link org.jlibvips.VipsAngle}.
 *
 * @author codecitizen
 */
public class VipsRotateOperation {

    private final VipsImage image;
    private final VipsAngle angle;

    private Pointer background;
    private Double idx,idy,odx,ody;

    public VipsRotateOperation(VipsImage image, VipsAngle angle) {
        this.image = image;
        this.angle = angle;
    }

    public VipsImage create() {
        Pointer[] out = new Pointer[1];
        int ret = VipsBindingsSingleton.instance().vips_rotate(image.getPtr(), out, angle.toDouble(),
                new Varargs()
                        .add("background", background)
                        .add("idx", idx)
                        .add("idy", idy)
                        .add("odx", odx)
                        .add("ody", ody).toArray());
        if(ret != 0) {
            throw new VipsException("vips_rotate", ret);
        }
        return new VipsImage(out[0]);
    }

    public VipsRotateOperation withIdx(double idx) {
        this.idx = idx;
        return this;
    }

    public VipsRotateOperation withIdy(double idy) {
        this.idy = idy;
        return this;
    }

    public VipsRotateOperation withOdx(double odx) {
        this.odx = odx;
        return this;
    }

    public VipsRotateOperation withOdy(double ody) {
        this.ody = ody;
        return this;
    }

    public VipsRotateOperation background(List<Float> background) {
        return background(background != null? background.stream().mapToDouble(Float::doubleValue).toArray() : null);
    }

    public VipsRotateOperation background(double[] background) {
        this.background = background != null?
                VipsBindingsSingleton.instance().vips_array_double_new(background, background.length) : null;
        return this;
    }
}
