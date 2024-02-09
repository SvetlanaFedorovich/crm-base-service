package mvpproject.crmbaseservice.builder;

import jakarta.persistence.EntityNotFoundException;
import mvpproject.crmbaseservice.model.dto.SalesDTO;
import mvpproject.crmbaseservice.model.entity.ClientEntity;
import mvpproject.crmbaseservice.model.entity.ProductEntity;
import mvpproject.crmbaseservice.model.entity.SalesEntity;
import mvpproject.crmbaseservice.repository.ClientRepository;
import mvpproject.crmbaseservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SalesEntityBuilder {
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;

    @Autowired
    public SalesEntityBuilder(ClientRepository clientRepository, ProductRepository productRepository) {
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
    }

    public SalesEntity build(SalesDTO salesDto) {
        ClientEntity clientEntity = clientRepository.findById(salesDto.getClientId())
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));
        ProductEntity productEntity = productRepository.findById(salesDto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        return SalesEntity.builder()
                .client(clientEntity)
                .product(productEntity)
                .quantity(salesDto.getQuantity())
                .salesDate(salesDto.getSalesDate())
                .build();
    }
}

