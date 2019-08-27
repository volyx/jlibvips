package org.jlibvips.jna.glib;

import java.util.Arrays;

public enum GLogLevelFlags {
  /* log flags */
  G_LOG_FLAG_RECURSION(1 << 0),
  G_LOG_FLAG_FATAL(1 << 1),
  /* GLib log levels */
  G_LOG_LEVEL_ERROR(1 << 2),       /* always fatal */
  G_LOG_LEVEL_CRITICAL(1 << 3),
  G_LOG_LEVEL_WARNING (1 << 4),
  G_LOG_LEVEL_MESSAGE(1 << 5),
  G_LOG_LEVEL_INFO(1 << 6),
  G_LOG_LEVEL_DEBUG(1 << 7),
  /**
   * Not a valid GLohLevelFlags!
   */
  G_LOG_LEVEL_UNKNOWN(0);

  private final int val;

  GLogLevelFlags(int val) {
    this.val   = val;
  }

  public int getVal() {
    return val;
  }

  public static GLogLevelFlags fromVal(int val) {
    return Arrays.stream(GLogLevelFlags.values())
            .filter(f -> f.getVal() == val)
            .findAny()
            .orElse(G_LOG_LEVEL_UNKNOWN);
  }
}
