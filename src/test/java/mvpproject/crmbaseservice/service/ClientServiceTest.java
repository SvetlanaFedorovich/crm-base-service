package mvpproject.crmbaseservice.service;

import mvpproject.crmbaseservice.TestData;
import mvpproject.crmbaseservice.model.dto.ClientDTO;
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

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest extends TestData {

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
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(ivanClient()));
        Optional<ClientDTO> clientDto = clientService.getById(anyLong());
        Assertions.assertThat(clientDto).isNotNull();
        Assertions.assertThat(clientDto.get().getClientName()).isEqualTo(ivanClient().getClientName());
        verify(clientRepository).findById(anyLong());
    }


    @Test
    void whenGetAllInvokedThenAllTheClientsAreReturned() {
        when(clientRepository.findAll()).thenReturn(List.of(kiraClient(), ivanClient()));
        clientService.getAll().forEach(ClientDTO::getClientName);
        String[] expected = new String[]{kiraClient().getClientName(), ivanClient().getClientName()
        };
        Assertions.assertThat(expected).containsExactlyInAnyOrder(kiraClient().getClientName(), ivanClient().getClientName());
    }

    @Test
    void whenCreatedInvokedWithClientThenClientIsSaved() {
        when(clientRepository.findAll()).thenReturn(List.of(clientConverter.convertClientEntityFromDto(testClient())));
        when(clientRepository.save(clientConverter.convertClientEntityFromDto(testClient())))
                .thenReturn(clientConverter.convertClientEntityFromDto(testClient()));

        clientService.create(testClient());
        List<String> allUserEmail = clientRepository.findAll().stream()
                .map(ClientEntity::getClientName)
                .toList();
        Assertions.assertThat(allUserEmail).contains(testClient().getClientName());
    }

    @Test
    void whenUpdateByIdThenSavedClientUpdate() {
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(ivanClient()));
        when(clientRepository.save(ivanClient())).thenReturn(ivanClient());

        clientService.update(anyLong(), clientConverter.convertFromClientEntityToDto(ivanClient()));
        Assertions.assertThat(ivanClient().getClientName()).contains(ivanClient().getClientName());
    }
}
