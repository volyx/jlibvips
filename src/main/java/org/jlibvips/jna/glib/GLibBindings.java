package org.jlibvips.jna.glib;

import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Pointer;

public interface GLibBindings extends Library {

  /**
   * Sets the log handler for a domain and a set of log levels. To handle fatal and recursive messages the log_levels
   * parameter must be combined with the G_LOG_FLAG_FATAL and G_LOG_FLAG_RECURSION bit flags.
   * <a href="https://developer.gnome.org/glib/stable/glib-Message-Logging.html#g-log-set-handler">GLib Reference Manual Docs.</a>
   *
   * @param log_domain the log domain, or NULL for the default "" application domain.
   * @param log_levels the log levels to apply the log handler for. To handle fatal and recursive messages as well,
   *                   combine the log levels with the G_LOG_FLAG_FATAL and G_LOG_FLAG_RECURSION bit flags.
   * @param log_func the log handler function
   * @param user_data data passed to the log handler
   * @return the id of the new handler
   */
  int g_log_set_handler(String log_domain, int log_levels, Callback log_func, Pointer user_data);

  void g_log(String log_domain, int log_levels, String format, Object...args);

}
