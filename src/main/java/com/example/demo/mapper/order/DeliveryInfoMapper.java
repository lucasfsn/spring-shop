package com.example.demo.mapper.order;

import com.example.demo.dto.order.DeliveryInfoDto;
import com.example.demo.model.order.DeliveryInfo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
  componentModel = "spring",
  unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface DeliveryInfoMapper {
  DeliveryInfo toEntity(DeliveryInfoDto dto);

  DeliveryInfoDto toDto(DeliveryInfo entity);
}
