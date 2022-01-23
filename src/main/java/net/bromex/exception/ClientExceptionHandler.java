package net.bromex.exception;

import feign.FeignException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Log4j2
@ControllerAdvice
public class ClientExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    @ExceptionHandler(NotImplementedException.class)
    public final ResponseEntity<ErrorMessage> handleException(NotImplementedException e) {
      log.warn(e.getMessage());
      ErrorMessage message = ErrorMessage.builder()
              .code("ERR101")
              .message("Api method is not implemented")
              .exception(e)
              .build();
      return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(message);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(FeignException.NotFound.class)
    public final ResponseEntity<ErrorMessage> handleNotFound(FeignException.NotFound e) {
        ErrorMessage error = ErrorMessage.builder()
                .code("ERR404")
                .message("Resource is not found")
                .exception(e)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
