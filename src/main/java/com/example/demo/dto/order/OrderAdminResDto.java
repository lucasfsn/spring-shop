package com.example.demo.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class OrderAdminResDto {
    private UUID userId;
    private List<OrderResDto> orders;
}
