package mvpproject.crmbaseservice.service;

import mvpproject.crmbaseservice.model.dto.ClientDto;
import mvpproject.crmbaseservice.model.entity.ClientEntity;
import mvpproject.crmbaseservice.model.mapper.ClientConverter;
import mvpproject.crmbaseservice.repository.ClientRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

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

    @Test
    void whenGetByIdInvokedThenExpectClientIsReturned() {
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(ivanclient));
        Optional<ClientDto> clientDto = clientService.getById(anyLong());
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
}
