package org.jlibvips

import org.jlibvips.jna.VipsBindingsSingleton
import spock.lang.Specification

class LibraryDetection extends Specification {

    def setupSpec() {
        VipsBindingsSingleton.configure("/usr/local/lib/libvips.so")
    }

    def "Detect the vips library from the current system."() {
        when:
        def instance = VipsBindingsSingleton.instance()
        then:
        instance != null
    }

}
