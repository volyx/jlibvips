package org.jlibvips.operations;

import org.jlibvips.exceptions.VipsException;
import org.jlibvips.VipsImage;

import java.io.IOException;
import java.nio.file.Path;

/**
 * A libvips operation that persists a {@link VipsImage} to the file system.
 *
 * @author amp
 */
public interface SaveOperation {

    /**
     * Saves the {@link VipsImage} to a temporary file and returns the
     * {@link Path} to it.
     *
     * @return {@link Path}
     * @throws IOException when the saving operation failed.
     * @throws VipsException when the libvips operation faied.
     */
    Path save() throws IOException, VipsException;

}
