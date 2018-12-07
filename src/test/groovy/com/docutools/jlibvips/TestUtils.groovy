package com.docutools.jlibvips

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

    private TestUtils() {
    }

}
