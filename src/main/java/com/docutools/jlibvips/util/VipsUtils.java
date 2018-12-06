package com.docutools.jlibvips.util;

public class VipsUtils {

    public static Integer booleanToInteger(Boolean b) {
        return b == null? null : (b? 1 : 0);
    }

}
