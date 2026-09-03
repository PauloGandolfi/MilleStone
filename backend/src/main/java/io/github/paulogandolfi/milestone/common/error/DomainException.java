package io.github.paulogandolfi.milestone.common.error;

import java.util.Objects;

public class DomainException extends RuntimeException {

    private final String code;

    public DomainException(String message) {
        this(ErrorCodes.DOMAIN_RULE_VIOLATION, message);
    }

    public DomainException(String code, String message) {
        super(requireText(message, "message"));
        this.code = requireText(code, "code");
    }

    public String getCode() {
        return code;
    }

    private static String requireText(String value, String field) {
        Objects.requireNonNull(value, field + " must not be null");
        if (value.isBlank()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }
        return value;
    }
}
