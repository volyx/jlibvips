package org.jlibvips.operations;

import org.jlibvips.VipsImage;
import org.jlibvips.exceptions.VipsException;
import org.jlibvips.jna.VipsBindings;
import org.jlibvips.jna.VipsBindingsSingleton;
import org.jlibvips.util.Varargs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class VipsSaveOperation implements SaveOperation {

    private final VipsImage image;

    public VipsSaveOperation(VipsImage image) {
        this.image = image;
    }

    @Override
    public Path save() throws IOException, VipsException {
        Path path = Files.createTempFile("jlibvips", ".v");
        int ret = VipsBindingsSingleton.instance().vips_vipssave(image.getPtr(), path.toString(), Varargs.empty());
        if(ret != 0) {
            throw new VipsException("vips_vipssave", ret);
        }
        return path;
    }

}
