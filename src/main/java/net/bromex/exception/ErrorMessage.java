package net.bromex.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorMessage {

    private String code;
    private String message;
    private Throwable exception;

}
