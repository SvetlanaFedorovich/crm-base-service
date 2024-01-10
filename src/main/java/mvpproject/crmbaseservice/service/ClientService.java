package mvpproject.crmbaseservice.service;

import lombok.RequiredArgsConstructor;
import mvpproject.crmbaseservice.error.exception.CustomException;
import mvpproject.crmbaseservice.model.dto.ClientDTO;
import mvpproject.crmbaseservice.model.entity.ClientEntity;
import mvpproject.crmbaseservice.model.mapper.ClientConverter;
import mvpproject.crmbaseservice.repository.ClientRepository;
import mvpproject.crmbaseservice.error.ClientCreateException;
import mvpproject.crmbaseservice.error.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static mvpproject.crmbaseservice.error.exception.CustomException.*;

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
            throw new ClientCreateException(EMPTY_REQUEST_FIELD.toString());
        } else if (checkingTheUniquenessOfBankAccount(client)) {
            throw new ClientCreateException(NOT_A_UNIQUE_BANK_ACCOUNT.toString());
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

    public Optional<ClientDTO> getById(Long id) throws ClientCreateException{
//        if (id == 0) {
////        if (id == 0 || id.equals(null) || Character.getType(Math.toIntExact(id)) != Character.DECIMAL_DIGIT_NUMBER) {
//            throw new ClientCreateException(INVALID_ID_TYPE_ENTERED.toString());
//        }
        Optional<ClientEntity> user = clientRepository.findById(id);
        return Optional.of(user.map(clientConverter::convertFromClientEntityToDto)
                .orElseThrow(UserNotFoundException::new));
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
