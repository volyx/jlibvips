package org.jlibvips.util;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Varargs {

    private final Map<String, Object> arguments;

    public Varargs() {
        this.arguments = new HashMap<>();
    }

    public Varargs add(String name, Object value) {
        if(name != null && value != null)
        arguments.put(name, value);
        return this;
    }

    public Object[] toArray() {
        return arguments.keySet().stream()
                .flatMap(key -> Stream.of(key, arguments.get(key)))
                .toArray();
    }
}
