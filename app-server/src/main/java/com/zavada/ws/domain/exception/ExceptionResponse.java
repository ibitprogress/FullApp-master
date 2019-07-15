package com.zavada.ws.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor
public class ExceptionResponse {
    private String message;
    private String details;
}
