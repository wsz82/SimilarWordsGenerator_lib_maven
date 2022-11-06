package io.github.wsz82;

class InvalidWordException extends IllegalStateException {

    InvalidWordException() {
    }

    InvalidWordException(String s) {
        super(s);
    }

    InvalidWordException(String message, Throwable cause) {
        super(message, cause);
    }

    InvalidWordException(Throwable cause) {
        super(cause);
    }
}
