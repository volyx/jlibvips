package org.jlibvips.operations;

import com.sun.jna.Pointer;
import org.jlibvips.VipsImage;
import org.jlibvips.exceptions.VipsException;
import org.jlibvips.jna.VipsBindings;
import org.jlibvips.util.Varargs;

import java.util.List;

import static org.jlibvips.util.VipsUtils.booleanToInteger;

/**
 * Insert sub into main at position x , y.
 *
 * Normally out shows the whole of main . If expand is TRUE then out is made large enough to hold all of main and sub .
 * Any areas of out not coming from either main or sub are set to background (default 0).
 *
 * If sub overlaps main , sub will appear on top of main.
 *
 * If the number of bands differs, one of the images must have one band. In this case, an n-band image is formed from
 * the one-band image by joining n copies of the one-band image together, and then the two n-band images are operated
 * upon.
 *
 * The two input images are cast up to the smallest common type (see table Smallest common format in arithmetic).
 *
 * @author amp
 * @see <a href="https://jcupitt.github.io/libvips/API/current/libvips-conversion.html#vips-insert">native function</a>
 */
public class VipsInsertOperation {

    private final VipsImage main;
    private final VipsImage sub;

    private int x = 0,y = 0;
    private Boolean expand;
    private Pointer background;

    public VipsInsertOperation(VipsImage main, VipsImage sub) {
        this.main = main;
        this.sub = sub;
    }

    public VipsImage create() {
        var out = new Pointer[1];
        int ret = VipsBindings.INSTANCE.vips_insert(main.getPtr(), sub.getPtr(), out, x, y,
                new Varargs()
                        .add("expand", booleanToInteger(expand))
                        .add("background", background)
                        .toArray());
        if(ret != 0) {
            throw new VipsException("vips_insert", ret);
        }
        return new VipsImage(out[0]);
    }

    public VipsInsertOperation x(int x) {
        this.x = x;
        return this;
    }

    public VipsInsertOperation y(int y) {
        this.y = y;
        return this;
    }

    public VipsInsertOperation expand(boolean expand) {
        this.expand = expand;
        return this;
    }

    public VipsInsertOperation background(List<Float> background) {
        return background(background != null? background.stream().mapToDouble(Float::doubleValue).toArray() : null);
    }

    public VipsInsertOperation background(double[] background) {
        this.background = background != null?
                VipsBindings.INSTANCE.vips_array_double_new(background, background.length) : null;
        return this;
    }
}
