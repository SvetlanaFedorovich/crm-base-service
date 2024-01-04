package mvpproject.crmbaseservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mvpproject.crmbaseservice.model.dto.ClientDTO;
import mvpproject.crmbaseservice.service.ClientService;
import mvpproject.crmbaseservice.service.util.ClientCreateException;
import mvpproject.crmbaseservice.service.util.CustomExceptionText;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Client controller", description = "Содержит эндпойнты для работы с клиентами")
@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    @Operation(summary = "Добавить нового клиента",
            description = "Позволяет добавить нового клиента в базу данных")
    @PostMapping("/new")
    public ResponseEntity<ClientDTO> create
            (@Parameter(description = "Описание нового клиента")
             @RequestBody ClientDTO client) throws ClientCreateException {
        return clientService.create(client)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ClientCreateException
                        (String.valueOf(CustomExceptionText.ПОЛЯ_ЗАПРОСА_НЕ_ДОЛЖНЫ_БЫТЬ_ПУСТЫМИ)));
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
    public ResponseEntity<ClientDTO> getClient(@PathVariable Long id) throws ClientCreateException {
        return clientService.getById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ClientCreateException(String.valueOf(CustomExceptionText.КЛИЕНТ_НЕ_НАЙДЕН)));
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
