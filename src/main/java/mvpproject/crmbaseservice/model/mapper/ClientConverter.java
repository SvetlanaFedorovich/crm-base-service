package mvpproject.crmbaseservice.model.mapper;

import lombok.RequiredArgsConstructor;
import mvpproject.crmbaseservice.model.dto.ClientDTO;
import mvpproject.crmbaseservice.model.entity.ClientEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class ClientConverter {

    private final ModelMapper modelMapper;

    public ClientEntity convertClientEntityFromDto(ClientDTO clientDto) {
        return modelMapper.map(clientDto, ClientEntity.class);
    }

    public ClientDTO convertFromClientEntityToDto(ClientEntity client) {
        return modelMapper.map(client, ClientDTO.class);
    }

    public void updateClient(ClientDTO clientDto, ClientEntity clientEntity) {
        modelMapper.map(clientDto, clientEntity);
    }
}
