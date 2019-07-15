package com.zavada.ws.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ValidationExceptionResponse {

    private String field;
    private String message;

}
