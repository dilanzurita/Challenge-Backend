package com.challengeBP.accountMovements.infraestructure.input.adapter.rest.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovementRequestDto {
    @NotBlank(message = "Movement type is required")
    @Pattern(
            regexp = "DEBIT|CREDIT",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Movement type must be either DEBIT or CREDIT"
    )
    private String type;
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.00", inclusive = false, message = "Amount must be at least 0.00")
    @Digits(integer = 10, fraction = 2, message = "Amount must have up to 2 decimal places")
    private BigDecimal value;
    @NotBlank(message = "Account number is required")
    @Size(min = 5, max = 20, message = "Account number must be between 10 and 20 characters")
    private String accountNumber;
}
