package com.docutools.jlibvips;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.docutools.jlibvips.VipsUtils.booleanToInteger;

public class WebpSaveOperation {

    private final VipsImage image;

    private Integer quality;
    private Boolean lossless;
    private Boolean smartSubsample;
    private Integer alphaQuality;
    private Boolean strip;

    WebpSaveOperation(VipsImage image) {
        this.image = image;
    }

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
            throw new VipsException("vips_webpsave", ret);
        }
        return path;
    }

    public WebpSaveOperation quality(int q) {
        this.quality = q;
        return this;
    }

    public WebpSaveOperation alphaQuality(int q) {
        this.alphaQuality = q;
        return this;
    }

    public WebpSaveOperation lossless() {
        this.lossless = true;
        return this;
    }

    public WebpSaveOperation smartSubsample() {
        this.smartSubsample = true;
        return this;
    }

    public WebpSaveOperation strip() {
        this.strip = true;
        return this;
    }

}
