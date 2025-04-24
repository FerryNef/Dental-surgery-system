package com.ads.dentalapp.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BillResponseDTO(
        Long billId,
        LocalDate billDate,
        BigDecimal amount,
        String status
) {
}
