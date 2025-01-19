package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data@AllArgsConstructor
@Schema(
        name = "Error Response",
        description = "Schema to hold failed response information"
)
public class ErrorResponseDto {
    // endpoint
    @Schema(description = "api path of service")
    private String apiPath;
    @Schema(description = "error code in response")
    private HttpStatus errorCode;
    @Schema(description = "error message  in response")
    private String errorMessage;
    @Schema(description = "date and time of error")
    private LocalDateTime errorTime;
}
