package org.jlibvips.jna.glib;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;

public interface GLibLogHandler extends Callback {

  void log(String log_domain, int flags, String message, Pointer pointer);

}
