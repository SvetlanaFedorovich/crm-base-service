package mvpproject.crmbaseservice.service;

import mvpproject.crmbaseservice.entity.Client;
import mvpproject.crmbaseservice.repository.ClientRepositoryImpl;
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
        if (id != 0) {
            Optional<Client> byId = clientRepositoryImpl.findById(id);
            if (byId.isEmpty()) {
                response = "is empty";
            } else {
                response = "The client by id is found.";
            }
            return response;
        } else {
            return "id is 0";
        }
    }

    public String createClient(Client client) {
        return clientRepositoryImpl.create(client);
    }

    public String update(Long id, String newBankDetails) {
        return clientRepositoryImpl.updateById(id, newBankDetails);
    }
}
