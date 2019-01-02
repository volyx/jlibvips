package org.jlibvips.util;

public class VipsUtils {

    public static Integer booleanToInteger(Boolean b) {
        return b == null? null : (b? 1 : 0);
    }

    public static Integer toOrdinal(Enum v) {
        return v != null? v.ordinal() : null;
    }

}
