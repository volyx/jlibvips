package org.jlibvips.operations;

import com.sun.jna.Pointer;
import org.jlibvips.VipsAngle;
import org.jlibvips.VipsImage;
import org.jlibvips.exceptions.VipsException;
import org.jlibvips.jna.VipsBindingsSingleton;
import org.jlibvips.util.Varargs;

public class SimilarityOperation {

    private final VipsImage image;

    private VipsAngle angle;

    public SimilarityOperation(VipsImage image) {
        this.image = image;
    }

    public VipsImage create() {
        try {
            Pointer[] out = new Pointer[1];
            int ret = VipsBindingsSingleton.instance().vips_similarity(image.getPtr(), out,
                    new Varargs().add("angle", angle != null ? angle.toDouble() : null).toArray());
            if (ret == 0) {
                return new VipsImage(out[0]);
            } else
                throw new VipsException("vips_similarity", ret);
        } catch (Throwable t) {
            throw new VipsException("vips_similarity", t);
        }
    }

    public SimilarityOperation angle(VipsAngle angle) {
        this.angle = angle;
        return this;
    }

}
