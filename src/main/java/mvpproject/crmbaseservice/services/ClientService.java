package mvpproject.crmbaseservice.services;

import mvpproject.crmbaseservice.entities.Client;
import mvpproject.crmbaseservice.repositories.ClientRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepositoryImpl clientRepositoryImpl;

    public String getClients() {
        return clientRepositoryImpl.readAll();
    }

    public String getClientById(Long id) {
        String response = null;
        Optional<Client> byId = clientRepositoryImpl.findById(id);
        if (byId.isEmpty()) {
            response = "is empty";
        } else {
            response = "The client by id is found.";
        }
        return response;
    }

    public String getClientByName(String clientName) {
        return clientRepositoryImpl.findByName(clientName);
    }

    public String createClient(Client client) {
        return clientRepositoryImpl.create(client);
    }

    public String update(Long id) {
        return clientRepositoryImpl.updateById(id);
    }

    public void deleteById(Long id) {
        clientRepositoryImpl.deleteById(id);
    }
//
//    public static <T> List<T> toList(final Iterable<T> iterable) {
//        return StreamSupport.stream(iterable.spliterator(), false)
//                .toList();
//    }
}
