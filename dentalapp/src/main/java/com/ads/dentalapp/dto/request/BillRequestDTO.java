package com.ads.dentalapp.dto.request;

import java.math.BigDecimal;

public record BillRequestDTO(
        BigDecimal amount,
        String status,
        String description
) {
}
