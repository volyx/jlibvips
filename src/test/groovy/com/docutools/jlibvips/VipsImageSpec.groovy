package com.docutools.jlibvips

import spock.lang.Specification

import java.nio.file.Files

import static com.docutools.jlibvips.TestUtils.*

class VipsImageSpec extends Specification {

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

}
