package com.example.demo.mapper.order;

import com.example.demo.dto.order.DeliveryInfoDto;
import com.example.demo.model.order.DeliveryInfo;
import org.springframework.stereotype.Component;

@Component
public class DeliveryInfoMapper {
    public DeliveryInfo toEntity(DeliveryInfoDto dto) {
        if (dto == null) {
            return null;
        }

        return DeliveryInfo.builder()
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .street(dto.getStreet())
                .houseNumber(dto.getHouseNumber())
                .flatNumber(dto.getFlatNumber())
                .city(dto.getCity())
                .postalCode(dto.getPostalCode())
                .country(dto.getCountry())
                .build();
    }

    public DeliveryInfoDto toDto(DeliveryInfo entity) {
        if (entity == null) {
            return null;
        }

        return DeliveryInfoDto.builder()
                .email(entity.getEmail())
                .phoneNumber(entity.getPhoneNumber())
                .street(entity.getStreet())
                .houseNumber(entity.getHouseNumber())
                .flatNumber(entity.getFlatNumber())
                .city(entity.getCity())
                .postalCode(entity.getPostalCode())
                .country(entity.getCountry())
                .build();
    }
}
