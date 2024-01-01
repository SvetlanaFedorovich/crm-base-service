package mvpproject.crmbaseservice.service;

import lombok.RequiredArgsConstructor;
import mvpproject.crmbaseservice.model.dto.ClientDTO;
import mvpproject.crmbaseservice.model.entity.ClientEntity;
import mvpproject.crmbaseservice.model.mapper.ClientConverter;
import mvpproject.crmbaseservice.repository.ClientRepository;
import mvpproject.crmbaseservice.service.util.UserNotFoundException;
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
    public Optional<ClientDTO> create(ClientDTO client) {
        ClientEntity newUser = clientConverter.convertClientEntityFromDto(client);
        return Optional.of(clientConverter.convertFromClientEntityToDto(clientRepository.save(newUser)));
    }

    public List<ClientDTO> getAll() {
        return clientRepository.findAll()
                .stream()
                .map(clientConverter::convertFromClientEntityToDto)
                .toList();
    }

    public Optional<ClientDTO> getById(Long id) {
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
}
