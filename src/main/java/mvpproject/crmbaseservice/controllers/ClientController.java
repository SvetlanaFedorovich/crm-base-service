package mvpproject.crmbaseservice.controllers;

import mvpproject.crmbaseservice.entities.Client;
import mvpproject.crmbaseservice.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClientController {

    @Autowired
    final private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/client")
    public String getClients() {
        return clientService.getClients();
    }

    @GetMapping("/client/{clientId}")
    public String getClient(@PathVariable("clientId") Long clientId) {
        return clientService.getClientById(clientId);
    }

    @GetMapping("/client/'{clientName}'")
    public String getClient(@PathVariable("clientName") String clientName) {
        return clientService.getClientByName(clientName);
    }

    @PostMapping("/client")
    public String create(@RequestBody Client client) {
        return clientService.createClient(client);
    }

    @PutMapping("/client/{clientId}/{newBankDetails}")
    public String update(@PathVariable Long clientId, @PathVariable String newBankDetails) {
        return clientService.update(clientId);
    }

    @DeleteMapping("/client/{clientId}")
    public void deleteById(@PathVariable Long clientId) {
        clientService.deleteById(clientId);
    }
}
