package io.github.paulogandolfi.milestone.common.rest;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import io.github.paulogandolfi.milestone.common.error.ConflictException;
import io.github.paulogandolfi.milestone.common.error.DomainException;
import io.github.paulogandolfi.milestone.common.error.ErrorCodes;
import io.github.paulogandolfi.milestone.common.error.ResourceNotFoundException;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void translatesBeanValidationErrorsToBadRequest() throws Exception {
        Method method = ValidationTarget.class.getDeclaredMethod("execute", String.class);
        MethodParameter parameter = new MethodParameter(method, 0);
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "request");
        bindingResult.addError(new FieldError(
                "request",
                "amount",
                "Amount must be greater than zero"));

        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(parameter, bindingResult);

        ResponseEntity<ApiErrorResponse> response = handler.handleValidation(exception);
        ApiErrorResponse body = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
        assertThat(body.code()).isEqualTo(ErrorCodes.VALIDATION_FAILED);
        assertThat(body.fieldErrors())
                .containsExactly(new FieldErrorResponse("amount", "Amount must be greater than zero"));
    }

    @Test
    void translatesDomainErrorsToBadRequest() {
        ResponseEntity<ApiErrorResponse> response = handler.handleDomain(
                new DomainException(ErrorCodes.INVALID_MONEY, "Invalid transaction amount"));
        ApiErrorResponse body = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
        assertThat(body.code()).isEqualTo(ErrorCodes.INVALID_MONEY);
        assertThat(body.message()).isEqualTo("Invalid transaction amount");
        assertThat(body.fieldErrors()).isEmpty();
    }

    @Test
    void translatesMissingResourcesToNotFound() {
        ResponseEntity<ApiErrorResponse> response = handler.handleResourceNotFound(
                new ResourceNotFoundException("Category not found"));
        ApiErrorResponse body = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(body).isNotNull();
        assertThat(body.code()).isEqualTo(ErrorCodes.RESOURCE_NOT_FOUND);
        assertThat(body.message()).isEqualTo("Category not found");
    }

    @Test
    void translatesConflictsToConflict() {
        ResponseEntity<ApiErrorResponse> response = handler.handleConflict(
                new ConflictException("Category already exists"));
        ApiErrorResponse body = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(body).isNotNull();
        assertThat(body.code()).isEqualTo(ErrorCodes.CONFLICT);
    }

    @Test
    void sanitizesUnexpectedRuntimeErrors() {
        ResponseEntity<ApiErrorResponse> response = handler.handleUnexpected(
                new IllegalStateException("database password must not leak"));
        ApiErrorResponse body = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(body).isNotNull();
        assertThat(body.code()).isEqualTo(ErrorCodes.INTERNAL_ERROR);
        assertThat(body.message()).isEqualTo("An unexpected error occurred.");
        assertThat(body.message()).doesNotContain("password");
    }

    private static final class ValidationTarget {
        private void execute(String amount) {
        }
    }
}
