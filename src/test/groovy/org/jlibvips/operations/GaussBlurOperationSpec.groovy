package org.jlibvips.operations

import org.jlibvips.VipsImage
import org.jlibvips.jna.VipsBindingsSingleton
import spock.lang.Specification

import java.nio.file.Files

import static org.jlibvips.TestUtils.copyResourceToFS

class GaussBlurOperationSpec extends Specification {

    def setupSpec() {
        VipsBindingsSingleton.configure("/usr/local/lib/libvips.so")
// for MacOS
//        VipsBindingsSingleton.configure("/usr/local/lib/libvips.42.dylib")
    }

    def "Should blur a image"() {
        given:
        def imagePath = copyResourceToFS("900x700.png")
        when:
        def image = VipsImage.fromFile imagePath
        println "GaussBlurOperationSpec"
        image = image.gaussBlur(10).create()
        def res = image.jpeg().save()
        then:
        image != null
        cleanup:
        Files.deleteIfExists imagePath
        Files.deleteIfExists res
    }

}
