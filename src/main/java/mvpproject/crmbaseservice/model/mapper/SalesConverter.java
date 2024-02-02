package mvpproject.crmbaseservice.model.mapper;

import lombok.RequiredArgsConstructor;
import mvpproject.crmbaseservice.model.dto.SalesDTO;
import mvpproject.crmbaseservice.model.entity.SalesEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SalesConverter {
    private final ModelMapper modelMapper;

    public SalesEntity convertSalesEntityFromDto(SalesDTO salesDto) {
        return modelMapper.map(salesDto, SalesEntity.class);
    }

    public SalesDTO convertFromSalesEntityToDto(SalesEntity sales) {
        return modelMapper.map(sales, SalesDTO.class);
    }

    public void updateSales(SalesDTO salesDto, SalesEntity salesEntity) {
        modelMapper.map(salesDto, salesEntity);
    }
}
