package mvpproject.crmbaseservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import mvpproject.crmbaseservice.model.dto.SalesDTO;
import mvpproject.crmbaseservice.model.mapper.SalesConverter;
import mvpproject.crmbaseservice.service.SalesService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static mvpproject.crmbaseservice.TestData.salesDto;
import static mvpproject.crmbaseservice.TestData.salesDto2;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(SalesController.class)
class SalesControllerTest {

    @MockBean
    private SalesService salesService;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private SalesConverter salesConverter;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCreateSales() throws Exception {
        // Arrange
        SalesDTO salesDto = salesDto();
        when(salesService.create(any(SalesDTO.class))).thenReturn(Optional.of(salesDto));

        // Configure ObjectMapper to support java.time.LocalDate
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        //отключает запись дат как временных меток, что позволяет ObjectMapper корректно сериализовать LocalDate
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Act & Assert
        mockMvc.perform(post("/sales/new").
                contentType(MediaType.APPLICATION_JSON).
                content(mapper.writeValueAsString(salesDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientId", is(salesDto.getClientId().intValue())))
                .andExpect(jsonPath("$.productId", is(salesDto.getProductId().intValue())))
                .andExpect(jsonPath("$.quantity", is(salesDto.getQuantity())));
    }


    @Test
    void testGetAllSales() throws Exception {
        // Arrange
        List<SalesDTO> salesList = asList(salesDto(), salesDto2());
        when(salesService.getAll()).thenReturn(salesList);

        // Act & Assert
        mockMvc.perform(get("/sales/list").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testGetSalesById() throws Exception {
        // Arrange
        SalesDTO salesDto = salesDto();
        when(salesService.getById(anyLong())).thenReturn(Optional.of(salesDto));

        // Act & Assert
        mockMvc.perform(get("/sales/{id}", 1L).
                contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.clientId", is(salesDto.getClientId().intValue())))
                .andExpect(jsonPath("$.productId", is(salesDto.getProductId().intValue())))
                .andExpect(jsonPath("$.quantity", is(salesDto.getQuantity())));
    }

    @Test
    void testUpdateSales() throws Exception {
        // Arrange
        SalesDTO salesDto = salesDto();
        when(salesService.update(anyLong(), any(SalesDTO.class))).thenReturn(Optional.of(salesDto));

        // Configure ObjectMapper to support java.time.LocalDate
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        // отключает запись дат как временных меток, что позволяет ObjectMapper корректно сериализовать LocalDate
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Act & Assert
        mockMvc.perform(put("/sales/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(salesDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientId", is(salesDto.getClientId().intValue())))
                .andExpect(jsonPath("$.productId", is(salesDto.getProductId().intValue())))
                .andExpect(jsonPath("$.quantity", is(salesDto.getQuantity())));
    }

    @Test
    void testGetSalesByDateRange() throws Exception {
        // Arrange
        List<Optional<SalesDTO>> salesList = asList(Optional.of(salesDto()), Optional.of(salesDto2()));
        when(salesService.getSalesByDateRange(anyString())).thenReturn(salesList);

        // Act & Assert
        mockMvc.perform(get("/sales/range/{filter}", "week")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testGetByDateRangeInvalidFilter() throws Exception {
        // Arrange
        String invalidFilter = "invalid";

        // Act & Assert
        mockMvc.perform(get("/sales/range/{filter}", invalidFilter).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

}

