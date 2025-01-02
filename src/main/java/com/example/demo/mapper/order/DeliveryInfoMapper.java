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

        DeliveryInfo deliveryInfo = new DeliveryInfo();
        deliveryInfo.setEmail(dto.getEmail());
        deliveryInfo.setPhoneNumber(dto.getPhoneNumber());
        deliveryInfo.setStreet(dto.getStreet());
        deliveryInfo.setHouseNumber(dto.getHouseNumber());
        deliveryInfo.setFlatNumber(dto.getFlatNumber());
        deliveryInfo.setCity(dto.getCity());
        deliveryInfo.setPostalCode(dto.getPostalCode());
        deliveryInfo.setCountry(dto.getCountry());

        return deliveryInfo;
    }

    public DeliveryInfoDto toDto(DeliveryInfo entity) {
        if (entity == null) {
            return null;
        }

        DeliveryInfoDto dto = new DeliveryInfoDto();
        dto.setEmail(entity.getEmail());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setStreet(entity.getStreet());
        dto.setHouseNumber(entity.getHouseNumber());
        dto.setFlatNumber(entity.getFlatNumber());
        dto.setCity(entity.getCity());
        dto.setPostalCode(entity.getPostalCode());
        dto.setCountry(entity.getCountry());

        return dto;
    }
}
