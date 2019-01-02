package org.jlibvips.exceptions;

public class VipsException extends RuntimeException {

    private final String operation;
    private final int returnValue;

    public VipsException(String operation, int returnValue) {
        super(String.format("VipsBindings operation %s returned error code %d.", operation, returnValue));
        this.operation = operation;
        this.returnValue = returnValue;
    }

    public String getOperation() {
        return operation;
    }

    public int getReturnValue() {
        return returnValue;
    }
}
