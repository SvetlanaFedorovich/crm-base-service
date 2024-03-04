package mvpproject.crmbaseservice.service;

import lombok.RequiredArgsConstructor;
import mvpproject.crmbaseservice.builder.SalesEntityBuilder;
import mvpproject.crmbaseservice.constant.errorMessage.ErrorMessage;
import mvpproject.crmbaseservice.constant.filter.FilterProduct;
import mvpproject.crmbaseservice.erorr.SalesNotFoundException;
import mvpproject.crmbaseservice.model.dto.SalesDTO;
import mvpproject.crmbaseservice.model.entity.SalesEntity;
import mvpproject.crmbaseservice.model.mapper.SalesConverter;
import mvpproject.crmbaseservice.repository.SalesRepository;
import mvpproject.crmbaseservice.kafka.util.SendMessage;
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
    private final SalesEntityBuilder salesEntityBuilder;
    private final SendMessage sendMessage;

    @Transactional
    public Optional<SalesDTO> create(SalesDTO salesDto) {
        SalesEntity salesEntity = salesEntityBuilder.build(salesDto);
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
            throw new SalesNotFoundException(ErrorMessage.SALES_NOT_FOUND.getMessage(id));
        }
        return sales.map(salesConverter::convertFromSalesEntityToDto);
    }


    @Transactional
    public Optional<SalesDTO> updateSales(Long id, SalesDTO update) {
        Optional<SalesEntity> existSales = salesRepository.findById(id);
        if (existSales.isPresent()) {
            SalesEntity sales = existSales.get();

            SalesEntity newSales = salesEntityBuilder.build(update);

            sales.setProduct(newSales.getProduct());
            sales.setClient(newSales.getClient());
            sales.setQuantity(newSales.getQuantity());
            sales.setSalesDate(newSales.getSalesDate());

            SalesEntity savedSales = salesRepository.save(sales);
            Optional<SalesDTO> savedSalesDto = Optional.of(salesConverter.convertFromSalesEntityToDto(savedSales));

            sendMessage.sendMessage(id);

            return savedSalesDto;
        }
        return Optional.ofNullable((SalesDTO.builder().build()));
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
