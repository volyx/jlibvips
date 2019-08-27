package org.jlibvips.jna.glib


import org.jlibvips.VipsImage
import org.jlibvips.VipsInteresting
import org.jlibvips.VipsSize
import spock.lang.Specification

import java.nio.file.Files

import static org.jlibvips.TestUtils.copyResourceToFS

class GLibLogging extends Specification {

    def 'Set GLib Logging Handler'() {
        given:
        def glib = GLibBindingsSingleton.instance()
        def handler = Mock(GLibLogHandler)
        when:
        glib.g_log_set_handler("jlibvips", GLogLevelFlags.G_LOG_LEVEL_DEBUG.getVal(), handler, null)
        glib.g_log("jlibvips", GLogLevelFlags.G_LOG_LEVEL_DEBUG.getVal(), "Hello %s!", "Peter")
        then:
        1 * handler.log(*_)
    }

    def 'Enable Debug Logging for Vips'() {
        given:
        VipsImage.registerLogHandler([GLogLevelFlags.G_LOG_LEVEL_DEBUG,
                                      GLogLevelFlags.G_LOG_LEVEL_ERROR,
                                      GLogLevelFlags.G_LOG_LEVEL_INFO,
                                      GLogLevelFlags.G_LOG_LEVEL_MESSAGE,
                                      GLogLevelFlags.G_LOG_LEVEL_WARNING], { flags, message ->
            println("VIPS[$flags]: $message")
        })
        when:
        def file = copyResourceToFS("500x500.jpg")
        def image = VipsImage.fromFile(file)
        def thumbnail = image.thumbnail(100)
                .autoRotate()
                .size(VipsSize.Nearest)
                .crop(VipsInteresting.Attention)
                .linear()
                .create()
        then: "the resulting image is resized to the specifications"
        thumbnail.width == 100
        thumbnail.height == 100
        cleanup:
        Files.deleteIfExists file
    }

}
