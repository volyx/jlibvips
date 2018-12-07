package com.docutools.jlibvips.operations;

import com.docutools.jlibvips.exceptions.VipsException;

import java.io.IOException;
import java.nio.file.Path;

/**
 * A libvips operation that persists a {@link com.docutools.jlibvips.VipsImage} to the file system.
 *
 * @author amp
 */
public interface SaveOperation {

    /**
     * Saves the {@link com.docutools.jlibvips.VipsImage} to a temporary file and returns the
     * {@link Path} to it.
     *
     * @return {@link Path}
     * @throws IOException when the saving operation failed.
     * @throws VipsException when the libvips operation faied.
     */
    Path save() throws IOException, VipsException;

}
