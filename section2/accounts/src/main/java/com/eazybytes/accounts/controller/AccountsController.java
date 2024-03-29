package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.constants.AccountsConstants;
import com.eazybytes.accounts.dto.AccountsDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.dto.ErrorResponseDto;
import com.eazybytes.accounts.dto.ResponseDto;
import com.eazybytes.accounts.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(
        name = "CRUD REST APIs for Accounts in MyBank",
        description = "CRUD REST APIs in MYBank  to  CREATE,UPDATE,FETCH AND DELETE accout details"
)
@RestController
@RequestMapping(path = "/api",produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated
public class AccountsController {
    private IAccountService  iAccountService;
@Operation(
        summary = "Create Account REST API",
        description = "REST API  to create new Customer &  Account in MYBank "
)
@ApiResponses({
        @ApiResponse(
                responseCode="201",
                description = "HTTP Status CREATED"
        ),
        @ApiResponse(
                responseCode="500",
                description = "HTTP Status INTERNAL SERVER ERROR",
                content = @Content(
                        schema = @Schema(implementation = ErrorResponseDto.class)
                )
        )
})
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto){
    iAccountService.createAccount(customerDto);
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new ResponseDto(AccountsConstants.STATUS_201,AccountsConstants.MESSAGE_201));
}
    @Operation(
            summary = "Fetch Account REST API",
            description = "REST API  to get  Account details in MYBank "
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode="200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode="500",
                    description = "HTTP Status INTERNAL SERVER ERROR",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
@GetMapping("/fetch")
public ResponseEntity<CustomerDto> fetchAccount(@RequestParam
                                                @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be of 10 digits")
                                                String mobileNumber){
    CustomerDto customerDto = iAccountService.fetchAccount(mobileNumber);
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(customerDto);
}

    @Operation(
            summary = "Update Account REST API",
            description = "REST API  to update  Account in MYBank "
    )
    @ApiResponses({
            @ApiResponse(
            responseCode="200",
            description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode="417",
                    description = "HTTP Status EXPECTATION_FAILED",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode="500",
                    description = "HTTP Status INTERNAL SERVER ERROR",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
            })
@PutMapping("/update")
public ResponseEntity<ResponseDto> updateAccounts(@Valid @RequestBody CustomerDto customerDto){
    boolean isUpdated = iAccountService.updateAccount(customerDto);
    if (isUpdated) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
    }else{
        return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(new ResponseDto(AccountsConstants.STATUS_417,AccountsConstants.MESSAGE_417_UPDATE));
    }
}

    @Operation(
            summary = "Delete Account REST API",
            description = "REST API  to delete  Account in MYBank "
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode="200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode="417",
                    description = "HTTP Status EXPECTATION_FAILED",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode="500",
                    description = "HTTP Status INTERNAL SERVER ERROR",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
@DeleteMapping("/delete")
public ResponseEntity<ResponseDto> deleteAccount(@RequestParam
                                                 @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be of 10 digits")
                                                 String mobileNumber){
    boolean isDeleted = iAccountService.deleteAccount(mobileNumber);
    if (isDeleted){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
    }else{
        return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(new ResponseDto(AccountsConstants.STATUS_417,AccountsConstants.MESSAGE_417_DELETE));
    }
}
}