package org.jlibvips.jna.glib

import org.jlibvips.DeepZoomContainer
import org.jlibvips.DeepZoomLayouts
import org.jlibvips.VipsAngle
import org.jlibvips.VipsExtend
import org.jlibvips.VipsImage
import spock.lang.Specification

import static org.jlibvips.TestUtils.*

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
            println("LIBVIPSLOG: $message")
        })
        when:
        def file = copyResourceToFS("500x500.jpg")
        def image = VipsImage.fromFile(file)
        def outDir = newTempDir()
        image.deepZoom(outDir)
                .layout(DeepZoomLayouts.Google)
                .background([0.0f, 0.0f, 0.0f])
                .rotate(VipsAngle.D90)
                .container(DeepZoomContainer.FileSystem)
                .tileSize(256)
                .centre()
                .strip()
                .suffix(".jpg[Q=85]")
                .save()
        then:
        !outDir.empty
    }

}
