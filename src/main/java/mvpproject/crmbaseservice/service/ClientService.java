package mvpproject.crmbaseservice.service;

import lombok.RequiredArgsConstructor;
import mvpproject.crmbaseservice.model.dto.ClientDto;
import mvpproject.crmbaseservice.model.entity.ClientEntity;
import mvpproject.crmbaseservice.model.mapper.ClientConverter;
import mvpproject.crmbaseservice.repository.ClientRepository;
import mvpproject.crmbaseservice.service.util.ClientCreateException;
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
    public Optional<ClientDto> create(ClientDto client) throws ClientCreateException {
        if (client == null || client.getAddress().isEmpty()
                || client.getClientName().isEmpty()
                || client.getPayerAccountNumber().isEmpty()
                || client.getBankDetails().isEmpty()) {
            throw new ClientCreateException("Поля запроса не должны быть пустыми");
        } else if (checkingTheUniquenessOfBankAccount(client)) {
            throw new ClientCreateException("Такой клиент уже существует!");
        }

        ClientEntity newUser = clientConverter.convertClientEntityFromDto(client);
        return Optional.of(clientConverter.convertFromClientEntityToDto(clientRepository.save(newUser)));
    }

    public List<ClientDto> getAll() {
        return clientRepository.findAll()
                .stream()
                .map(clientConverter::convertFromClientEntityToDto)
                .toList();
    }

    public Optional<ClientDto> getById(Long id) throws ClientCreateException {
        if (id == null) {
            throw new ClientCreateException("Id не может быть пустым. Введите номер id.");
        }
        Optional<ClientEntity> user = clientRepository.findById(id);
        return Optional.of(user.map(clientConverter::convertFromClientEntityToDto)
                .orElseThrow(() -> new ClientCreateException("Клиент не найден")));
    }

    @Transactional
    public Optional<ClientDto> update(Long id, ClientDto update) {
        Optional<ClientEntity> existClient = clientRepository.findById(id);
        if (existClient.isPresent()) {
            ClientEntity client = existClient.get();
            clientConverter.updateClient(update, client);
            return Optional.of(clientConverter.convertFromClientEntityToDto(clientRepository.save(client)));
        }
        return Optional.empty();
    }

    public boolean checkingTheUniquenessOfBankAccount(ClientDto clientDto) {
        boolean isSuchAnAccountAlreadyExist = false;
        List<ClientDto> allClients = getAll();
        for (ClientDto clients : allClients) {
            if (clients.getPayerAccountNumber().equals(clientDto.getPayerAccountNumber())) {
                isSuchAnAccountAlreadyExist = true;
            }
        }
        return isSuchAnAccountAlreadyExist;
    }
}
