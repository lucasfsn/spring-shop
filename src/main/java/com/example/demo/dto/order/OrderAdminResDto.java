package com.example.demo.dto.order;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class OrderAdminResDto {
    private UUID userId;
    private List<OrderResDto> orders;
}
