package mvpproject.crmbaseservice.service;

import jakarta.persistence.EntityNotFoundException;
import mvpproject.crmbaseservice.builder.SalesEntityBuilder;
import mvpproject.crmbaseservice.erorr.SalesNotFoundException;
import mvpproject.crmbaseservice.model.dto.SalesDTO;
import mvpproject.crmbaseservice.model.entity.SalesEntity;
import mvpproject.crmbaseservice.model.mapper.SalesConverter;
import mvpproject.crmbaseservice.repository.SalesRepository;
import mvpproject.crmbaseservice.kafka.util.SendMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static mvpproject.crmbaseservice.TestData.buildTestSalesDTO;
import static mvpproject.crmbaseservice.TestData.buildTestSalesEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SalesServiceTest {

    @Mock
    private SalesRepository salesRepository;

    @Mock
    private SalesConverter salesConverter;

    @Mock
    private SalesEntityBuilder salesEntityBuilder;

    @Mock
    private SendMessage sendMessage;
    @InjectMocks
    private SalesService salesService;

    @Test
    void testCreate() {
        SalesDTO salesDto = buildTestSalesDTO(1L, 1, 1);
        SalesEntity salesEntity = buildTestSalesEntity(1L, 1, 1);

        when(salesEntityBuilder.build(any(SalesDTO.class))).thenReturn(salesEntity);
        when(salesRepository.save(any(SalesEntity.class))).thenReturn(salesEntity);
        when(salesConverter.convertFromSalesEntityToDto(any(SalesEntity.class))).thenReturn(salesDto);

        Optional<SalesDTO> result = salesService.create(salesDto);

        assertTrue(result.isPresent());
        assertEquals(salesDto, result.get());
    }

    @Test
    void testCreateWhenClientNotFound() {
        SalesDTO salesDto = buildTestSalesDTO(1L, 1, 1);

        when(salesEntityBuilder.build(any(SalesDTO.class))).thenThrow(new EntityNotFoundException("Client not found"));

        assertThrows(EntityNotFoundException.class, ()->salesService.create(salesDto));
    }

    @Test
    void testCreateWhenProductNotFound() {
        SalesDTO salesDto = buildTestSalesDTO(1L, 1, 1);

        when(salesEntityBuilder.build(any(SalesDTO.class))).thenThrow(new EntityNotFoundException("Product not found"));

        assertThrows(EntityNotFoundException.class, ()->salesService.create(salesDto));
    }

    @Test
    void testGetAll() {
        SalesEntity salesEntity1 = buildTestSalesEntity(1L, 1, 1);
        SalesEntity salesEntity2 = buildTestSalesEntity(2L, 2, 15);
        SalesEntity salesEntity3 = buildTestSalesEntity(3L, 3, 100);

        SalesDTO salesDto1 = buildTestSalesDTO(1L, 1, 1);
        SalesDTO salesDto2 = buildTestSalesDTO(2L, 2, 15);
        SalesDTO salesDto3 = buildTestSalesDTO(3L, 3, 100);

        when(salesRepository.findAll()).thenReturn(List.of(salesEntity1, salesEntity2, salesEntity3));
        when(salesConverter.convertFromSalesEntityToDto(salesEntity1)).thenReturn(salesDto1);
        when(salesConverter.convertFromSalesEntityToDto(salesEntity2)).thenReturn(salesDto2);
        when(salesConverter.convertFromSalesEntityToDto(salesEntity3)).thenReturn(salesDto3);

        List<SalesDTO> result = salesService.getAll();

        assertEquals(3, result.size());
        assertEquals(salesDto1, result.get(0));
        assertEquals(salesDto2, result.get(1));
        assertEquals(salesDto3, result.get(2));
    }

    @Test
    void testGetById() {
        SalesEntity salesEntity = buildTestSalesEntity(1L, 1, 1);
        SalesDTO salesDto = buildTestSalesDTO(1L, 1, 1);

        when(salesRepository.findById(1L)).thenReturn(Optional.of(salesEntity));
        when(salesConverter.convertFromSalesEntityToDto(salesEntity)).thenReturn(salesDto);

        Optional<SalesDTO> result = salesService.getById(1L);

        assertTrue(result.isPresent());
        assertEquals(salesDto, result.get());
    }

    @Test
    void testGetByIdNotFound() {
        when(salesRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(SalesNotFoundException.class, ()->salesService.getById(1L));
    }

    @Test
    void testUpdate() {
        SalesEntity existingSalesEntity = buildTestSalesEntity(1L, 1, 1);
        SalesDTO updatedSalesDto = buildTestSalesDTO(1L, 1, 1);

        when(salesRepository.findById(1L)).thenReturn(Optional.of(existingSalesEntity));
        when(salesEntityBuilder.build(updatedSalesDto)).thenReturn(existingSalesEntity);
        when(salesConverter.convertFromSalesEntityToDto(existingSalesEntity)).thenReturn(updatedSalesDto);
        when(salesRepository.save(existingSalesEntity)).thenReturn(existingSalesEntity);

        Optional<SalesDTO> result = salesService.updateSales(1L, updatedSalesDto);

        assertTrue(result.isPresent());
        assertEquals(updatedSalesDto, result.get());

        verify(salesEntityBuilder).build(updatedSalesDto);

        verify(sendMessage).sendMessage(1L);
    }

    @Test
    public void testUpdateSalesWhenSaleNotFound() {
        SalesDTO update = new SalesDTO();
        when(salesRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<SalesDTO> result = salesService.updateSales(1L, update);

        assertTrue(result.isPresent());
        assertEquals(new SalesDTO(), result.get());
    }

    @Test
    void testGetSalesByDateRangeForInvalidFilter() {
        assertThrows(IllegalArgumentException.class, ()->salesService.getSalesByDateRange("INVALID"));
    }

    @Test
    void testGetSalesByDateRangeForWeek() {
        SalesEntity salesEntity1 = buildTestSalesEntity(1L, 1, 1);
        SalesEntity salesEntity2 = buildTestSalesEntity(2L, 2, 15);
        SalesEntity salesEntity3 = buildTestSalesEntity(3L, 3, 100);

        SalesDTO salesDto1 = buildTestSalesDTO(1L, 1, 1);

        when(salesRepository.findBySalesDateAfter(any(LocalDate.class))).thenAnswer(invocation-> {
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

        List<Optional<SalesDTO>> result = salesService.getSalesByDateRange("week");

        assertEquals(1, result.size());
        assertTrue(result.get(0).isPresent());

        assertEquals(salesDto1, result.get(0).get());
    }

    @Test
    void testGetSalesByDateRangeForMonth() {
        SalesEntity salesEntity1 = buildTestSalesEntity(1L, 1, 1);
        SalesEntity salesEntity2 = buildTestSalesEntity(2L, 2, 15);
        SalesEntity salesEntity3 = buildTestSalesEntity(3L, 3, 100);

        SalesDTO salesDto1 = buildTestSalesDTO(1L, 1, 1);
        SalesDTO salesDto2 = buildTestSalesDTO(2L, 2, 15);

        when(salesRepository.findBySalesDateAfter(any(LocalDate.class))).thenAnswer(invocation-> {
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

        List<Optional<SalesDTO>> result = salesService.getSalesByDateRange("month");

        assertEquals(2, result.size());
        assertTrue(result.get(0).isPresent());
        assertTrue(result.get(1).isPresent());

        assertEquals(salesDto1, result.get(0).get());
        assertEquals(salesDto2, result.get(1).get());
    }

    @Test
    void testGetSalesByDateRangeForYear() {
        SalesEntity salesEntity1 = buildTestSalesEntity(1L, 1, 1);
        SalesEntity salesEntity2 = buildTestSalesEntity(2L, 2, 15);
        SalesEntity salesEntity3 = buildTestSalesEntity(3L, 3, 100);

        SalesDTO salesDto1 = buildTestSalesDTO(1L, 1, 1);
        SalesDTO salesDto2 = buildTestSalesDTO(2L, 2, 15);
        SalesDTO salesDto3 = buildTestSalesDTO(3L, 3, 100);

        when(salesRepository.findBySalesDateAfter(any(LocalDate.class))).thenAnswer(invocation-> {
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

        List<Optional<SalesDTO>> result = salesService.getSalesByDateRange("year");

        assertEquals(3, result.size());
        assertTrue(result.get(0).isPresent());
        assertTrue(result.get(1).isPresent());
        assertTrue(result.get(2).isPresent());
        assertEquals(salesDto1, result.get(0).get());
        assertEquals(salesDto2, result.get(1).get());
        assertEquals(salesDto3, result.get(2).get());
    }
}
