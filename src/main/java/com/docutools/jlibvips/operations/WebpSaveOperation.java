package com.docutools.jlibvips.operations;

import com.docutools.jlibvips.jna.VipsBindings;
import com.docutools.jlibvips.VipsImage;
import com.docutools.jlibvips.exceptions.WebpConversionException;
import com.docutools.jlibvips.util.Varargs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.docutools.jlibvips.util.VipsUtils.booleanToInteger;

/**
 * Write an image to a file in WebP format.
 *
 * @author amp
 * @see <a href="https://en.wikipedia.org/wiki/WebP">WebP</a>
 * @see <a href="http://libvips.github.io/libvips/API/current/VipsForeignSave.html#vips-webpsave">vips_webpsave()</a>
 */
public class WebpSaveOperation implements SaveOperation {

    private final VipsImage image;

    private Integer quality;
    private Boolean lossless;
    private Boolean smartSubsample;
    private Integer alphaQuality;
    private Boolean strip;

    public WebpSaveOperation(VipsImage image) {
        this.image = image;
    }

    @Override
    public Path save() throws IOException {
        var path = Files.createTempFile("jlibvips", "webp");
        int ret = VipsBindings.INSTANCE.vips_webpsave(image.getPtr(), path.toString(),
                new Varargs()
                        .add("Q", quality)
                        .add("lossless", booleanToInteger(lossless))
                        .add("smart_subsample", booleanToInteger(smartSubsample))
                        .add("alpha_q", alphaQuality)
                        .add("strip", booleanToInteger(strip))
                        .toArray());
        if(ret != 0) {
            throw new WebpConversionException(ret);
        }
        return path;
    }

    /**
     * Set the output quality factor.
     *
     * @param q 0-100
     * @return this
     */
    public WebpSaveOperation quality(int q) {
        this.quality = q;
        return this;
    }

    /**
     * Set the output alpha quality in lossless mode.
     *
     * @param q 0-100
     * @return this
     */
    public WebpSaveOperation alphaQuality(int q) {
        this.alphaQuality = q;
        return this;
    }

    /**
     * Enables lossless compression.
     *
     * @return this
     */
    public WebpSaveOperation lossless() {
        this.lossless = true;
        return this;
    }

    /**
     * Enables high quality chroma subsampling.
     *
     * @return this
     */
    public WebpSaveOperation smartSubsample() {
        this.smartSubsample = true;
        return this;
    }

    /**
     * Remove all metadata from the image.
     *
     * @return this
     */
    public WebpSaveOperation strip() {
        this.strip = true;
        return this;
    }

}
