package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Schema(
        name = "Accounts",
        description = "Schema to hold account information"
)
@Data
public class AccountsDto {
    @Schema(description = "account number")
    @NotEmpty(message = "AccountNumber can not be a null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})",message = "accountNumber must be of 10 digits")
    private Long accountNumber;
    @Schema(description = "account type information", example = "savings")
    @NotEmpty(message = "accountType can not be null or empty")
    private String accountType;
    @Schema(description = "branch address of MyBank")
    @NotEmpty(message = "branchAddress can not be null or empty")
    private String branchAddress;
}
