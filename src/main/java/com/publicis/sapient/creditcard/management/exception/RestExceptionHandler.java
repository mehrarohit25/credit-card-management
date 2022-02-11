package com.publicis.sapient.creditcard.management.exception;

import com.publicis.sapient.creditcard.management.model.ErrorDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.publicis.sapient.creditcard.management.util.Constants.CARD_DATA_ERROR;
import static com.publicis.sapient.creditcard.management.util.Constants.CONSTRAINT_ERROR;
import static com.publicis.sapient.creditcard.management.util.Constants.HYPHEN;
import static com.publicis.sapient.creditcard.management.util.Constants.INVALID_ERROR;
import static com.publicis.sapient.creditcard.management.util.Constants.SYSTEM_ADMINISTRATOR;
import static com.publicis.sapient.creditcard.management.util.Constants.TECHNICAL_ERROR;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;


@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(RestExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        LOG.error("Schema failure exception : " + ex.getMessage());
        List<ErrorDetails> errorDetailsList = ex.getBindingResult().getFieldErrors().stream().map(fieldError -> {
            String errorMessage = MessageFormat.format("{0} {1}", INVALID_ERROR + HYPHEN + fieldError.getField(), fieldError.getDefaultMessage());
            return createErrorMessage(INVALID_ERROR, errorMessage);
        }).collect(Collectors.toList());
        return new ResponseEntity<>(errorDetailsList, BAD_REQUEST);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        LOG.error("Constraint validation exception : " + ex.getMessage());
        List<ErrorDetails> errorDetailsList = ex.getConstraintViolations().stream().map(constraintViolation -> {
            String errorMessage = MessageFormat.format("{0} {1}", CONSTRAINT_ERROR + HYPHEN + constraintViolation.getPropertyPath(), constraintViolation.getMessage());
            return createErrorMessage(CONSTRAINT_ERROR, errorMessage);
        }).collect(Collectors.toList());
        return new ResponseEntity<>(errorDetailsList, BAD_REQUEST);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException exception) {
        LOG.error("Data integrity validation exception : " + exception.getMessage());
        ErrorDetails errorDetails = createErrorMessage(INVALID_ERROR, INVALID_ERROR + HYPHEN + CARD_DATA_ERROR);
        return new ResponseEntity<>(errorDetails, BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleException(Exception exception) {
        LOG.error("Technical exception occurred  : " + exception);
        ErrorDetails errorDetails = createErrorMessage(TECHNICAL_ERROR, SYSTEM_ADMINISTRATOR);
        return new ResponseEntity<>(errorDetails, INTERNAL_SERVER_ERROR);
    }

    public static ErrorDetails createErrorMessage(String message, String details) {
        return ErrorDetails.builder().timestamp(LocalDateTime.now()).details(details).message(message).build();
    }

}
