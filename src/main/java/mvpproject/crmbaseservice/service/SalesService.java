package mvpproject.crmbaseservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import mvpproject.crmbaseservice.constant.filter.FilterProduct;
import mvpproject.crmbaseservice.erorr.SalesNotFoundException;
import mvpproject.crmbaseservice.model.dto.SalesDTO;
import mvpproject.crmbaseservice.model.entity.ClientEntity;
import mvpproject.crmbaseservice.model.entity.ProductEntity;
import mvpproject.crmbaseservice.model.entity.SalesEntity;
import mvpproject.crmbaseservice.model.mapper.SalesConverter;
import mvpproject.crmbaseservice.repository.ClientRepository;
import mvpproject.crmbaseservice.repository.ProductRepository;
import mvpproject.crmbaseservice.repository.SalesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static mvpproject.crmbaseservice.constant.filter.FilterProduct.getFilterProduct;
import static mvpproject.crmbaseservice.util.SalesCheckFilter.checkFilter;

@Service
@RequiredArgsConstructor
public class SalesService {

    private final SalesRepository salesRepository;
    private final SalesConverter salesConverter;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;

    @Transactional
    public Optional<SalesDTO> create(SalesDTO salesDto) {

        ClientEntity clientEntity = clientRepository.findById(salesDto.getClientId())
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));
        ProductEntity productEntity = productRepository.findById(salesDto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        SalesEntity salesEntity = new SalesEntity();
        salesEntity.setClient(clientEntity);
        salesEntity.setProduct(productEntity);
        salesEntity.setQuantity(salesDto.getQuantity());
        salesEntity.setSalesDate(salesDto.getSalesDate());

        SalesEntity savedSalesEntity = salesRepository.save(salesEntity);
        return Optional.ofNullable(salesConverter.convertFromSalesEntityToDto(savedSalesEntity));
    }


    public List<SalesDTO> getAll() {
        return salesRepository.findAll()
                .stream()
                .map(salesConverter::convertFromSalesEntityToDto)
                .toList();
    }

    public Optional<SalesDTO> getById(Long id) {
        Optional<SalesEntity> sales = salesRepository.findById(id);
        if (sales.isEmpty()) {
            throw new SalesNotFoundException("Продажа с id " + id + " не найдена!");
        }
        return sales.map(salesConverter::convertFromSalesEntityToDto);
    }


    @Transactional
    public Optional<SalesDTO> update(Long id, SalesDTO update) {
        Optional<SalesEntity> existSales = salesRepository.findById(id);
        if (existSales.isPresent()) {
            SalesEntity sales = existSales.get();

            ClientEntity client = clientRepository.findById(update.getClientId())
                    .orElseThrow(()-> new EntityNotFoundException("Client with id: " + update.getClientId() + " not foud"));

            ProductEntity newProduct = productRepository.findById(update.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("Product with id: " + update.getProductId() + " not found"));

            sales.setProduct(newProduct);
            sales.setClient(client);

            sales.setQuantity(update.getQuantity());
            sales.setSalesDate(update.getSalesDate());

            return Optional.of(salesConverter.convertFromSalesEntityToDto(salesRepository.save(sales)));
        }
        return Optional.empty();
    }

    public List<Optional<SalesDTO>> getSalesByDateRange(String filter) {
        FilterProduct filterProduct = getFilterProduct(filter);

        return salesRepository.findBySalesDateAfter(checkFilter(filterProduct))
                .stream()
                .map(salesConverter::convertFromSalesEntityToDto)
                .map(Optional::of)
                .toList();
    }
}