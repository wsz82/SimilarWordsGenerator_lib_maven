package io.github.wsz82;

class WorthlessGenerateProcessException extends IllegalStateException {

    public WorthlessGenerateProcessException() {
    }

    public WorthlessGenerateProcessException(String s) {
        super(s);
    }

    public WorthlessGenerateProcessException(String message, Throwable cause) {
        super(message, cause);
    }

    public WorthlessGenerateProcessException(Throwable cause) {
        super(cause);
    }
}
