package mvpproject.crmbaseservice.model.mapper;

import lombok.RequiredArgsConstructor;
import mvpproject.crmbaseservice.model.dto.ClientDto;
import mvpproject.crmbaseservice.model.entity.ClientEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class ClientConverter {

    private final ModelMapper modelMapper;

    public ClientEntity convertClientEntityFromDto(ClientDto clientDto) {
        return modelMapper.map(clientDto, ClientEntity.class);
    }

    public ClientDto convertFromClientEntityToDto(ClientEntity client) {
        return modelMapper.map(client, ClientDto.class);
    }

    public void updateClient(ClientDto clientDto, ClientEntity clientEntity) {
        modelMapper.map(clientDto, clientEntity);
    }
}
