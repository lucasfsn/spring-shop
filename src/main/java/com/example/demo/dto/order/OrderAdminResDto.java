package com.example.demo.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class OrderAdminResDto {
    private UUID userId;
    private List<OrderResDto> orders;
}
