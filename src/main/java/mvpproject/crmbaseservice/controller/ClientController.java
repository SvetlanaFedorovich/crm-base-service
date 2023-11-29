package mvpproject.crmbaseservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import mvpproject.crmbaseservice.entity.Client;
import mvpproject.crmbaseservice.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Client controller", description = "Содержит эндпойнты для работы с клиентами")
@RestController
@AllArgsConstructor
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Operation(
            summary = "Получить список всех клиентов",
            description = "Позволяет получить список всех клиентов из базы данных"
    )
    @GetMapping("/client")
    public String getClients() {
        return clientService.getClients();
    }

    @Operation(
            summary = "Получить описание клиента по id",
            description = "Позволяет получить описание конкретного клиента по его id из базы данных"
    )
    @GetMapping("/client/{clientId}")
    public String getClient(@PathVariable("clientId") @Parameter(description = "Идентификатор клиента") Long clientId) {
        return clientService.getClientById(clientId);
    }

    @Operation(
            summary = "Добавить нового клиента",
            description = "Позволяет добавить нового клиента в базу данных"
    )
    @PostMapping("/client")
    public String create(@RequestBody @Parameter(description = "Описание нового клиента") Client client) {
        return clientService.createClient(client);
    }

    @Operation(
            summary = "Отредактировать банковские реквизиты клиента",
            description = "Позволяет отредактировать банковские реквизиты клиента в базе данных"
    )
    @PutMapping("/client/{clientId}/{newBankDetails}")
    public String update(@PathVariable @Parameter(description = "Идентификатор клиента") Long clientId, @PathVariable @Parameter(description = "новые банковские реквизиты") String newBankDetails) {
        return clientService.update(clientId, newBankDetails);
    }
}
