package mvpproject.crmbaseservice.service;

import jakarta.persistence.EntityNotFoundException;
import mvpproject.crmbaseservice.erorr.SalesNotFoundException;
import mvpproject.crmbaseservice.model.dto.SalesDTO;
import mvpproject.crmbaseservice.model.entity.ClientEntity;
import mvpproject.crmbaseservice.model.entity.ProductEntity;
import mvpproject.crmbaseservice.model.entity.SalesEntity;
import mvpproject.crmbaseservice.model.mapper.SalesConverter;
import mvpproject.crmbaseservice.repository.ClientRepository;
import mvpproject.crmbaseservice.repository.ProductRepository;
import mvpproject.crmbaseservice.repository.SalesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


import static mvpproject.crmbaseservice.TestData.clientEntity;
import static mvpproject.crmbaseservice.TestData.createClientEntity;
import static mvpproject.crmbaseservice.TestData.createProductEntity;
import static mvpproject.crmbaseservice.TestData.createSalesDto1;
import static mvpproject.crmbaseservice.TestData.createSalesDto2;
import static mvpproject.crmbaseservice.TestData.createSalesDto3;
import static mvpproject.crmbaseservice.TestData.createSalesEntity1;
import static mvpproject.crmbaseservice.TestData.createSalesEntity2;
import static mvpproject.crmbaseservice.TestData.createSalesEntity3;
import static mvpproject.crmbaseservice.TestData.productEntity;
import static mvpproject.crmbaseservice.TestData.salesDto;

import static mvpproject.crmbaseservice.TestData.salesEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SalesServiceTest {

    @Mock
    private SalesRepository salesRepository;

    @Mock
    private SalesConverter salesConverter;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private SalesService salesService;

    @Test
    void testCreate() {
        // Arrange
        SalesDTO salesDto = salesDto();

        ClientEntity clientEntity = clientEntity();
        ProductEntity productEntity = productEntity();
        SalesEntity salesEntity = salesEntity();

        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(clientEntity));
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(productEntity));
        when(salesRepository.save(any(SalesEntity.class))).thenReturn(salesEntity);
        when(salesConverter.convertFromSalesEntityToDto(any(SalesEntity.class))).thenReturn(salesDto);

        // Act
        Optional<SalesDTO> result = salesService.create(salesDto);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(salesDto, result.get());
    }

    @Test
    void testCreateWhenClientNotFound() {
        // Arrange
        SalesDTO salesDto = salesDto();

        when(clientRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> salesService.create(salesDto));
    }

    @Test
    void testCreateWhenProductNotFound() {
        // Arrange
        SalesDTO salesDto = salesDto();

        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(new ClientEntity()));
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> salesService.create(salesDto));
    }

    @Test
    void testGetAll() {
        // Arrange
        SalesEntity salesEntity1 = createSalesEntity1();
        SalesEntity salesEntity2 = createSalesEntity2();
        SalesEntity salesEntity3 = createSalesEntity3();

        SalesDTO salesDto1 = createSalesDto1();
        SalesDTO salesDto2 = createSalesDto2();
        SalesDTO salesDto3 = createSalesDto3();

        when(salesRepository.findAll()).thenReturn(List.of(salesEntity1, salesEntity2, salesEntity3));
        when(salesConverter.convertFromSalesEntityToDto(salesEntity1)).thenReturn(salesDto1);
        when(salesConverter.convertFromSalesEntityToDto(salesEntity2)).thenReturn(salesDto2);
        when(salesConverter.convertFromSalesEntityToDto(salesEntity3)).thenReturn(salesDto3);

        // Act
        List<SalesDTO> result = salesService.getAll();

        // Assert
        assertEquals(3, result.size());
        assertEquals(salesDto1, result.get(0));
        assertEquals(salesDto2, result.get(1));
        assertEquals(salesDto3, result.get(2));
    }

    @Test
    void testGetById() {
        // Arrange
        Long id = 1L;

        SalesEntity salesEntity = createSalesEntity1();
        SalesDTO salesDto = createSalesDto1();

        when(salesRepository.findById(id)).thenReturn(Optional.of(salesEntity));
        when(salesConverter.convertFromSalesEntityToDto(salesEntity)).thenReturn(salesDto);

        // Act
        Optional<SalesDTO> result = salesService.getById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(salesDto, result.get());
    }

    @Test
    void testGetByIdNotFound() {
        // Arrange
        Long id = 1L;

        when(salesRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(SalesNotFoundException.class, () -> salesService.getById(id));
    }


    @Test
    void testUpdate() {
        // Arrange
        Long id = 1L;

        SalesEntity salesEntity = createSalesEntity1();
        SalesDTO salesDto = createSalesDto1();
        ProductEntity productEntity = createProductEntity();
        ClientEntity clientEntity = createClientEntity();

        when(salesRepository.findById(id)).thenReturn(Optional.of(salesEntity));
        when(productRepository.findById(salesDto.getProductId())).thenReturn(Optional.of(productEntity));
        when(clientRepository.findById(salesDto.getClientId())).thenReturn(Optional.of(clientEntity));
        when(salesConverter.convertFromSalesEntityToDto(salesEntity)).thenReturn(salesDto);
        when(salesRepository.save(salesEntity)).thenReturn(salesEntity);

        // Act
        Optional<SalesDTO> result = salesService.update(id, salesDto);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(salesDto, result.get());
    }

    @Test
    void testUpdateNotFound() {
        // Arrange
        Long id = 1L;
        SalesDTO salesDto = createSalesDto1();

        when(salesRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<SalesDTO> result = salesService.update(id, salesDto);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testGetSalesByDateRangeForInvalidFilter() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> salesService.getSalesByDateRange("INVALID"));
    }

    @Test
    void testGetSalesByDateRangeForWeek() {
        // Arrange
        SalesEntity salesEntity1 = createSalesEntity1();
        SalesEntity salesEntity2 = createSalesEntity2();
        SalesEntity salesEntity3 = createSalesEntity3();

        SalesDTO salesDto1 = createSalesDto1();

        when(salesRepository.findBySalesDateAfter(any(LocalDate.class))).thenAnswer(invocation -> {
            LocalDate date = invocation.getArgument(0);
            if (date.isAfter(salesEntity1.getSalesDate())) {
                return List.of();
            } else if (date.isAfter(salesEntity2.getSalesDate())) {
                return List.of(salesEntity1);
            } else if (date.isAfter(salesEntity3.getSalesDate())) {
                return List.of(salesEntity1, salesEntity2);
            } else {
                return List.of(salesEntity1, salesEntity2, salesEntity3);
            }
        });

        when(salesConverter.convertFromSalesEntityToDto(salesEntity1)).thenReturn(salesDto1);

        // Act
        List<Optional<SalesDTO>> result = salesService.getSalesByDateRange("week");

        // Assert
        assertEquals(1, result.size());
        assertTrue(result.get(0).isPresent());

        assertEquals(salesDto1, result.get(0).get());
    }

    @Test
    void testGetSalesByDateRangeForMonth() {
        // Arrange
        SalesEntity salesEntity1 = createSalesEntity1();
        SalesEntity salesEntity2 = createSalesEntity2();
        SalesEntity salesEntity3 = createSalesEntity3();

        SalesDTO salesDto1 = createSalesDto1();
        SalesDTO salesDto2 = createSalesDto2();

        when(salesRepository.findBySalesDateAfter(any(LocalDate.class))).thenAnswer(invocation -> {
            LocalDate date = invocation.getArgument(0);
            if (date.isAfter(salesEntity1.getSalesDate())) {
                return List.of();
            } else if (date.isAfter(salesEntity2.getSalesDate())) {
                return List.of(salesEntity1);
            } else if (date.isAfter(salesEntity3.getSalesDate())) {
                return List.of(salesEntity1, salesEntity2);
            } else {
                return List.of(salesEntity1, salesEntity2, salesEntity3);
            }
        });

        when(salesConverter.convertFromSalesEntityToDto(salesEntity1)).thenReturn(salesDto1);
        when(salesConverter.convertFromSalesEntityToDto(salesEntity2)).thenReturn(salesDto2);

        // Act
        List<Optional<SalesDTO>> result = salesService.getSalesByDateRange("month");

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.get(0).isPresent());
        assertTrue(result.get(1).isPresent());

        assertEquals(salesDto1, result.get(0).get());
        assertEquals(salesDto2, result.get(1).get());
    }

    @Test
    void testGetSalesByDateRangeForYear() {
        // Arrange
        SalesEntity salesEntity1 = createSalesEntity1();
        SalesEntity salesEntity2 = createSalesEntity2();
        SalesEntity salesEntity3 = createSalesEntity3();

        SalesDTO salesDto1 = createSalesDto1();
        SalesDTO salesDto2 = createSalesDto2();
        SalesDTO salesDto3 = createSalesDto3();

        when(salesRepository.findBySalesDateAfter(any(LocalDate.class))).thenAnswer(invocation -> {
            LocalDate date = invocation.getArgument(0);
            if (date.isAfter(salesEntity1.getSalesDate())) {
                return List.of();
            } else if (date.isAfter(salesEntity2.getSalesDate())) {
                return List.of(salesEntity1);
            } else if (date.isAfter(salesEntity3.getSalesDate())) {
                return List.of(salesEntity1, salesEntity2);
            } else {
                return List.of(salesEntity1, salesEntity2, salesEntity3);
            }
        });

        when(salesConverter.convertFromSalesEntityToDto(salesEntity1)).thenReturn(salesDto1);
        when(salesConverter.convertFromSalesEntityToDto(salesEntity2)).thenReturn(salesDto2);
        when(salesConverter.convertFromSalesEntityToDto(salesEntity3)).thenReturn(salesDto3);

        // Act
        List<Optional<SalesDTO>> result = salesService.getSalesByDateRange("year");

        // Assert
        assertEquals(3, result.size());
        assertTrue(result.get(0).isPresent());
        assertTrue(result.get(1).isPresent());
        assertTrue(result.get(2).isPresent());
        assertEquals(salesDto1, result.get(0).get());
        assertEquals(salesDto2, result.get(1).get());
        assertEquals(salesDto3, result.get(2).get());
    }
}
