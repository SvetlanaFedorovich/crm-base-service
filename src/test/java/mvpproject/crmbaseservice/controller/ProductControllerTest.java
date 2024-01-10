package mvpproject.crmbaseservice.controller;

import mvpproject.crmbaseservice.TestData;
import mvpproject.crmbaseservice.model.mapper.ProductConverter;
import mvpproject.crmbaseservice.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(ProductController.class)
class ProductControllerTest extends TestData {
    @MockBean
    private ProductService productService;
    @MockBean
    private ProductConverter productConverter;
    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    @BeforeEach
    void init() {
        productConverter = new ProductConverter(MODEL_MAPPER);
    }

    @Autowired
    private MockMvc mockMvc;
    @Test
    void whenGetAllProductsThenAllTheProductsAreReturned() throws Exception {
        when(productService.getAll()).thenReturn(List.of(testProduct()));
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get("/product/list"));
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productName").value("Test"));
    }
}
