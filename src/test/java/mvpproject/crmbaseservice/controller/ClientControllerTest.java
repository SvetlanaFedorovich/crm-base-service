package mvpproject.crmbaseservice.controller;

import mvpproject.crmbaseservice.TestData;
import mvpproject.crmbaseservice.model.mapper.ClientConverter;
import mvpproject.crmbaseservice.service.ClientService;
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
@WebMvcTest(ClientController.class)
class ClientControllerTest extends TestData {

    @Autowired
    private ClientController clientController;
    @MockBean
    private ClientService clientService;
    @MockBean
    private ClientConverter clientConverter;
    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    @BeforeEach
    void init() {
        clientConverter = new ClientConverter(MODEL_MAPPER);
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenGetAllClientsThenAllTheClientsAreReturned() throws Exception {
        when(clientService.getAll()).thenReturn(List.of(testClient()));
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get("/client/list"));
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].clientName").value("Test"));
    }
}
