package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "Customer",
        description = "Schema to hold customer and account information"
)
public class CustomerDto {
    @Schema(description = "Customer name",example = "myname")
    @NotEmpty(message = "Name can not be null or empty")
    @Size(min = 5, max = 50,message = "length of name should be between 5 and 50")
    private String name;

    @Schema(description = "Customer email",example = "myname@gmail.com")
    @NotEmpty(message = "email can not be null or empty")
    @Email(message = "Email address should be a valid value")
    private String email;

    @Schema(description = "Customer mobile number",example = "987654321")
    @NotEmpty
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be of 10 digits")
    private String mobileNumber;
    @Schema(description = "Customer account details")
    private AccountsDto accountsDto;
}
