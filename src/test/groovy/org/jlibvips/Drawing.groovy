package org.jlibvips

import org.jlibvips.jna.VipsBindingsSingleton
import spock.lang.Specification

import java.nio.file.Files

import static org.jlibvips.TestUtils.copyResourceToFS

class Drawing extends Specification {

    def setupSpec() {
        VipsBindingsSingleton.configure("/usr/local/lib/libvips.so")
    }

    def "Draw a colored rect with SVG."() {
        given:
        def svg = """<svg height="100" width="100">
    <rect width="300" height="100" style="fill:rgb(0,0,255);stroke-width:3;stroke:rgb(0,0,0)" />
  </svg>"""
        def svgPath = TestUtils.copyStringToFS svg, ".svg"
        def imagePath = copyResourceToFS "500x500.jpg"
        def image = VipsImage.fromFile imagePath
        when:
        def svgImage = VipsImage.fromFile svgPath
        def newImage = image.insert(svgImage)
            .x(10)
            .y(10)
            .create()
        then:
        newImage != null
        cleanup:
        Files.deleteIfExists svgPath
        Files.deleteIfExists imagePath
    }

    def "Draw a colored pin with SVG on a JPEG."() {
        given:
        def svg = """<svg viewBox="0 0 40 60" height="60">
    <path d="M 2 26 A 19 19 0 1 1 38 26 L 20 60 Z" style="fill:rgb(0,0,255);stroke-width:3;stroke:rgb(0,0,0)"  />
</svg>"""
        def svgPath = TestUtils.copyStringToFS svg, ".svg"

        def imageFile = copyResourceToFS("snippet.jpg")
        def image = VipsImage.fromFile(imageFile)
        when:
        def svgImage = VipsImage.fromFile svgPath
        def x = 156 - (int)(svgImage.width/2), y = 256 - svgImage.height
        def newImage = image.insert(svgImage)
                .x(x)
                .y(y)
                .create()
        then:
        newImage != null
        println newImage.jpeg().save()
        cleanup:
        Files.deleteIfExists svgPath
        Files.deleteIfExists imagePath
    }

    def "Append constannt band to JPEG image."() {
        given: "A JPEG image with 3 bands"
        def imageFile = copyResourceToFS("snippet.png")
        def image = VipsImage.fromFile(imageFile)
        when: "adding a single const band"
        def newImage = image.appendSingleConstantBand(1.0)
        then: "the new image has 4 bands"
        newImage.bands == 4
        cleanup:
        Files.deleteIfExists imageFile
    }

    def "Draw a Pin on a JPEG Snippet via .insert(...)"() {
        given: "A JPEG image and a PNG Pin Icon"
        def imageFile = copyResourceToFS("snippet.jpg")
        def image = VipsImage.fromFile(imageFile).appendSingleConstantBand(1.0)

        def iconFile = copyResourceToFS("pin.png")
        def icon = VipsImage.fromFile(iconFile)

        def x = 156 - (int)(icon.width/2), y = 256 - icon.height
        when: "try to insert the pin to the centre of the image"
        def result = image.insert(icon)
            .x(x)
            .y(y)
            .expand(true)
            .background([0.0f,0.0f,0.0f,0.0f])
            .create()
        then: "the resulting images dimensions should be same to before"
        result.width == image.width
        result.height == image.height
        println result.jpeg().save()
        cleanup:
        Files.deleteIfExists imageFile
        Files.deleteIfExists iconFile
    }

}
