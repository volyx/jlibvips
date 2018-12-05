package com.docutools.jlibvips

import spock.lang.Specification

import java.nio.file.Files

import static com.docutools.jlibvips.TestUtils.*

class VipsImageSpec extends Specification {

    def "get resolution of image"() {
        given: "a arbitrary image file"
        def file = copyResourceToFS(resource)
        when: "loading it as VipsImage"
        def image = VipsImage.fromFile(file)
        then: "the width and height should be as original"
        image.width == width
        image.height == height
        cleanup:
        Files.deleteIfExists(file)
        where:
        resource      | width | height
        "500x500.jpg" | 500   | 500
    }

    def "loading vectorised PDFs"() {
        given: "a large vectorised PDF"
        def pdfFile = copyResourceToFS(pdfResource)
        when: "loading it as a vips image"
        def image = VipsImage.fromPdf(pdfFile)
        then: "the height should not exceed the limits imposed by libpoppler (32767)" // TODO documentation link here
        image.width <= VipsImage.POPPLER_LIMIT
        image.height <= VipsImage.POPPLER_LIMIT
        cleanup:
        Files.deleteIfExists(pdfFile)
        where:
        pdfResource << ["1.pdf"]
    }

    def "get bands of image"() {
        given: "a arbitrary image file"
        def file = copyResourceToFS(resource)
        when: "loading it as VipsImage"
        def image = VipsImage.fromFile(file)
        then: "the number of bands is accessible"
        image.bands == bands
        cleanup:
        Files.deleteIfExists(file)
        where:
        resource      | bands
        "500x500.jpg" | 3
    }

    def "creating image pyramids"() {
        given: "a VipsImage"
        def file = copyResourceToFS(resource)
        def image = VipsImage.fromFile(file)
        def outDir = newTempDir()
        when: "calling dzsave"
        image.deepZoom(outDir)
                .layout(layout)
                .background(background)
                .rotate(angle)
                .container(container)
                .tileSize(tileSize)
                .strip()
                .inMemory()
                .save()
        then: "generates an image pyramid on the file system"
        Files.exists outDir.resolve("0/0/0.jpg")
        cleanup:
        Files.deleteIfExists(file)
        outDir.toFile().deleteDir()
        where:
        resource      | layout                 | background         | angle         | container                    | tileSize
        "500x500.jpg" | DeepZoomLayouts.Google | [0.0f, 0.0f, 0.0f] | VipsAngle.D90 | DeepZoomContainer.FileSystem | 256
    }

}
