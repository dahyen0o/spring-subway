package subway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SubwayControllerAdvice {

    @ExceptionHandler(IllegalSectionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse illegalSectionExceptionHandler(IllegalSectionException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(IllegalLineException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse illegalLineExceptionHandler(IllegalLineException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(IllegalStationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse illegalStationExceptionHandler(IllegalStationException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(IllegalStationsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse illegalStationsExceptionHandler(IllegalStationsException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse illegalRequestExceptionHandler(HttpMessageNotReadableException exception) {
        String message = extractMessage(exception);
        return new ErrorResponse(message);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse runtimeExceptionHandler(Exception exception) {
        exception.printStackTrace();
        return new ErrorResponse(exception.getLocalizedMessage());
    }

    private static String extractMessage(final HttpMessageNotReadableException exception) {
        if (exception.getRootCause() != null) {
            return exception.getRootCause().getMessage();
        }
        return "";
    }
}
