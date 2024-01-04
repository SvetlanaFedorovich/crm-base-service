package mvpproject.crmbaseservice.service;

import lombok.RequiredArgsConstructor;
import mvpproject.crmbaseservice.model.dto.ClientDTO;
import mvpproject.crmbaseservice.model.entity.ClientEntity;
import mvpproject.crmbaseservice.model.mapper.ClientConverter;
import mvpproject.crmbaseservice.repository.ClientRepository;
import mvpproject.crmbaseservice.service.util.ClientCreateException;
import mvpproject.crmbaseservice.service.util.CustomExceptionText;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientConverter clientConverter;

    @Transactional
    public Optional<ClientDTO> create(ClientDTO client) throws ClientCreateException {
        if (client == null || client.getAddress().isEmpty()
                || client.getClientName().isEmpty()
                || client.getPayerAccountNumber().isEmpty()
                || client.getBankDetails().isEmpty()) {
            throw new ClientCreateException(String.valueOf(CustomExceptionText.ПОЛЯ_ЗАПРОСА_НЕ_ДОЛЖНЫ_БЫТЬ_ПУСТЫМИ));
        } else if (checkingTheUniquenessOfBankAccount(client)) {
            throw new ClientCreateException(String.valueOf(CustomExceptionText.ТАКОЙ_КЛИЕНТ_УЖЕ_СУЩЕСТВУЕТ));
        }

        ClientEntity newUser = clientConverter.convertClientEntityFromDto(client);
        return Optional.of(clientConverter.convertFromClientEntityToDto(clientRepository.save(newUser)));
    }

    public List<ClientDTO> getAll() {
        return clientRepository.findAll()
                .stream()
                .map(clientConverter::convertFromClientEntityToDto)
                .toList();
    }

    public Optional<ClientDTO> getById(Long id) throws ClientCreateException {
        if (id == null) {
            throw new ClientCreateException(String.valueOf(CustomExceptionText.ID_НЕ_МОЖЕТ_БЫТЬ_ПУСТЫМ));
        }
        Optional<ClientEntity> user = clientRepository.findById(id);
        return Optional.of(user.map(clientConverter::convertFromClientEntityToDto)
                .orElseThrow(() -> new ClientCreateException(String.valueOf(CustomExceptionText.КЛИЕНТ_НЕ_НАЙДЕН))));
    }

    @Transactional
    public Optional<ClientDTO> update(Long id, ClientDTO update) {
        Optional<ClientEntity> existClient = clientRepository.findById(id);
        if (existClient.isPresent()) {
            ClientEntity client = existClient.get();
            clientConverter.updateClient(update, client);
            return Optional.of(clientConverter.convertFromClientEntityToDto(clientRepository.save(client)));
        }
        return Optional.empty();
    }

    public boolean checkingTheUniquenessOfBankAccount(ClientDTO clientDto) {
        boolean isSuchAnAccountAlreadyExist = false;
        List<ClientDTO> allClients = getAll();
        for (ClientDTO clients : allClients) {
            if (clients.getPayerAccountNumber().equals(clientDto.getPayerAccountNumber())) {
                isSuchAnAccountAlreadyExist = true;
            }
        }
        return isSuchAnAccountAlreadyExist;
    }
}
