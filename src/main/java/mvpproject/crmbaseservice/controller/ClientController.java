package mvpproject.crmbaseservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mvpproject.crmbaseservice.model.dto.ClientDTO;
import mvpproject.crmbaseservice.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
@Tag(name = "Client controller", description = "Содержит эндпойнты для работы с клиентами")
public class ClientController {

    private final ClientService clientService;

    @Operation(summary = "Добавить нового клиента",
            description = "Позволяет добавить нового клиента в базу данных")
    @PostMapping("/new")
    public ResponseEntity<Optional<ClientDTO>> create(@Parameter(description = "Описание нового клиента")
                                                      @RequestBody ClientDTO client) {
        return ResponseEntity.ok(clientService.create(client));
    }

    @Operation(summary = "Получить список всех клиентов",
            description = "Позволяет получить список всех клиентов из базы данных")
    @GetMapping("/list")
    public ResponseEntity<List<ClientDTO>> getAll() {
        return ResponseEntity.ok(clientService.getAll());
    }

    @Operation(summary = "Получить описание клиента по id",
            description = "Позволяет получить описание конкретного клиента по его id из базы данных")
    @GetMapping("{id}")
    public ResponseEntity<ClientDTO> getById(@PathVariable Long id) {
        return clientService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Отредактировать банковские реквизиты клиента",
            description = "Позволяет отредактировать банковские реквизиты клиента в базе данных")
    @PutMapping("{id}")
    public ResponseEntity<ClientDTO> update(@PathVariable Long id, @RequestBody ClientDTO updateClient) {
        return clientService.update(id, updateClient)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
