package mvpproject.crmbaseservice.service;

import mvpproject.crmbaseservice.model.dto.ClientDTO;
import mvpproject.crmbaseservice.model.entity.ClientEntity;
import mvpproject.crmbaseservice.model.mapper.ClientConverter;
import mvpproject.crmbaseservice.repository.ClientRepository;
import mvpproject.crmbaseservice.service.util.ClientCreateException;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ClientConverter clientConverter;
    private static final ModelMapper MODEL_MAPPER = new ModelMapper();
    @InjectMocks
    private ClientService clientService;

    @BeforeEach
    void init() {
        clientConverter = new ClientConverter(MODEL_MAPPER);
        clientService = new ClientService(clientRepository, clientConverter);
    }

    @org.junit.jupiter.api.Test
    public void createClientWithEmptyFieldShouldGetException() {
        assertThrows(ClientCreateException.class,
                () -> {
                    clientService.create(clientFromRequestWithEmptyFields);
                });
    }

    @Test
    public void whenGetByIdInvokedThenExpectClientIsReturned() {
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(ivanclient));
        Optional<ClientDTO> clientDto = null;
        assertThrows(ClientCreateException.class,
                () -> {
                    clientService.getById(anyLong());
                });
        Assertions.assertThat(clientDto).isNotEmpty();
        Assertions.assertThat(clientDto.get().getClientName()).isEqualTo(ivanclient.getClientName());
        verify(clientRepository).findById(anyLong());
    }

    private ClientEntity ivanclient = ClientEntity.builder()
            .clientName("Ivan")
            .address("Gomel")
            .payerAccountNumber("1")
            .bankDetails("Bank")
            .build();

    private ClientDTO clientFromRequestWithEmptyFields = ClientDTO.builder()
            .clientName("")
            .address("")
            .payerAccountNumber("")
            .bankDetails("")
            .build();
}
