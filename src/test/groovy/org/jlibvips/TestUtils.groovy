package org.jlibvips

import java.nio.file.Files
import java.nio.file.Path

class TestUtils {

    static Path copyResourceToFS(String resourceName) {
        def tempFile = Files.createTempFile("jlibvips", "." + resourceName.replaceFirst(~/\.[^\.]+$/, ''))
        tempFile.toFile().withDataOutputStream { DataOutputStream os ->
            TestUtils.class.getResource("/$resourceName").withInputStream { is ->
                os << is
            }
        }
        return tempFile
    }

    static Path newTempDir() {
        return Files.createTempDirectory("jlibvips")
    }

    static Path copyStringToFS(String val, String extension) {
        def tempFile = Files.createTempFile "jlivips", ".$extension"
        Files.writeString tempFile, val
        return tempFile
    }

    private TestUtils() {
    }

}
