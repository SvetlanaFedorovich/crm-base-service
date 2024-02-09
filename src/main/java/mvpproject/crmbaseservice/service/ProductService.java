package mvpproject.crmbaseservice.service;

import lombok.RequiredArgsConstructor;
import mvpproject.crmbaseservice.model.dto.ProductDTO;
import mvpproject.crmbaseservice.model.entity.ProductEntity;
import mvpproject.crmbaseservice.model.mapper.ProductConverter;
import mvpproject.crmbaseservice.repository.ProductRepository;
import mvpproject.crmbaseservice.erorr.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductConverter productConverter;

    @Transactional
    public Optional<ProductDTO> create(ProductDTO product) {
        ProductEntity newProduct = productConverter.convertProductEntityFromDto(product);
        return Optional.of(productConverter.convertFromProductEntityToDto(productRepository
                .save(newProduct)));
    }

    public List<ProductDTO> getAll() {
        return productRepository.findAll()
                .stream()
                .map(productConverter::convertFromProductEntityToDto)
                .toList();
    }

    public Optional<ProductDTO> getById(Long id) {
        Optional<ProductEntity> user = productRepository.findById(id);
        return Optional.of(user.map(productConverter::convertFromProductEntityToDto)
                .orElseThrow(UserNotFoundException::new));
    }

    @Transactional
    public Optional<ProductDTO> update(Long id, ProductDTO update) {
        Optional<ProductEntity> existProduct = productRepository.findById(id);
        if (existProduct.isPresent()) {
            ProductEntity product = existProduct.get();
            productConverter.updateProduct(update, product);
            return Optional.of(productConverter.convertFromProductEntityToDto(productRepository
                    .save(product)));
        }
        return Optional.empty();
    }

}
