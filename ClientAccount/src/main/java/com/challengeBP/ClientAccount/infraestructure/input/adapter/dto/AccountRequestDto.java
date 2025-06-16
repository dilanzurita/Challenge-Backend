package com.challengeBP.ClientAccount.infraestructure.input.adapter.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequestDto {
    @NotBlank(message = "Account number is required")
    @Size(min = 10, max = 20, message = "Account number must be between 10 and 20 characters")
    private String number;
    @NotBlank(message = "Account type is required")
    @Pattern(
            regexp = "SAVINGS|CHECKING",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Account type must be either SAVINGS or CHECKING"
    )
    private String type;
    @NotNull(message = "Initial balance is required")
    @Digits(integer = 10, fraction = 2, message = "Initial balance must have up to 2 decimal places")
    private BigDecimal initialBalance;
    @NotNull(message = "Customer ID is required")
    private Long customerId;
}
