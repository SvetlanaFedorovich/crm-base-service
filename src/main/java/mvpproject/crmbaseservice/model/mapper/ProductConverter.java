package mvpproject.crmbaseservice.model.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mvpproject.crmbaseservice.model.dto.ProductDTO;
import mvpproject.crmbaseservice.model.entity.ProductEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductConverter {

    private final ModelMapper modelMapper;

    public ProductEntity convertProductEntityFromDto(ProductDTO product) {
        return modelMapper.map(product, ProductEntity.class);
    }

    public ProductDTO convertFromProductEntityToDto(ProductEntity product) {
        return modelMapper.map(product, ProductDTO.class);
    }

    public void updateProduct(ProductDTO product, ProductEntity productEntity) {
        modelMapper.map(product, productEntity);
    }
}
