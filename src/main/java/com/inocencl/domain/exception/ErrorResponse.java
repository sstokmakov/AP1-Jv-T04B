package com.inocencl.domain.exception;

import java.time.Instant;

public record ErrorResponse(String message,
                            int status,
                            Instant timestamp){

}
