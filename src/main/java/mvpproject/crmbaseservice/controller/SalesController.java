package mvpproject.crmbaseservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mvpproject.crmbaseservice.constant.errorMessage.ErrorMessage;
import mvpproject.crmbaseservice.constant.filter.FilterProduct;
import mvpproject.crmbaseservice.erorr.InvalidFilterException;
import mvpproject.crmbaseservice.erorr.SalesCreateException;
import mvpproject.crmbaseservice.erorr.SalesNotFoundException;
import mvpproject.crmbaseservice.model.dto.SalesDTO;
import mvpproject.crmbaseservice.service.SalesService;
import mvpproject.crmbaseservice.util.NullFieldCheck;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sales")
@Tag(name = "Sales controller", description = "Содержит эндпойнты для работы с продажами")
public class SalesController {

    private final SalesService salesService;

    @Operation(summary = "Добавить продажу", description = "Позволяет добавить новую продажу в базу данных")
    @PostMapping("/new")
    public ResponseEntity<SalesDTO> create(@Parameter(description = "Описание новой продажи") @RequestBody SalesDTO sales) {
        NullFieldCheck.checkFields(sales);
        return ResponseEntity.ok(salesService.create(sales).orElseThrow(()->new SalesCreateException("Failed to create sales", "unknown")));
    }

    @Operation(summary = "Прочитать все продажи",
            description = "Позволяет получить список всех продаж из базы данных")
    @GetMapping("/list")
    public ResponseEntity<List<SalesDTO>> getAll() {
        return ResponseEntity.ok(salesService.getAll());
    }

    @Operation(summary = "Прочитать продажу по id",
            description = "Позволяет получить описание определенной продажи по ее id")
    @GetMapping("{id}")
    public ResponseEntity<SalesDTO> getById(@PathVariable Long id) {
        return salesService.getById(id).map(ResponseEntity::ok)
                .orElseThrow(()->new SalesNotFoundException(ErrorMessage.SALES_NOT_FOUND.getMessage(id)));
    }

    @Operation(summary = "Обновить данные о продаже",
            description = "Позволяет обновить данные определенной продажи в базе данных по ее id")

    @PutMapping("{id}")
    public ResponseEntity<SalesDTO> update(@PathVariable Long id, @RequestBody SalesDTO updateSales) {

        return salesService.updateSales(id, updateSales).map(result-> {
                    if (result.getId() == null) {
                        throw new SalesNotFoundException(ErrorMessage.SALES_NOT_FOUND.getMessage(id));
                    }
                    return ResponseEntity.ok(result);
                })
                .orElseThrow(()->new SalesNotFoundException(ErrorMessage.SALES_NOT_FOUND.getMessage(id)));
    }

    @Operation(summary = "Получить продажи за определенный период",
            description = "Позволяет получить список продаж за определенный период времени")
    @GetMapping("/range/{filter}")
    public ResponseEntity<List<Optional<SalesDTO>>> getByDateRange(@PathVariable String filter) {
        boolean isPresent = Arrays.stream(FilterProduct.values())
                .anyMatch(value->value.name().equalsIgnoreCase(filter));
        if (!isPresent) {
            throw new InvalidFilterException(HttpStatus.BAD_REQUEST, "Invalid Filter " + "\"" + filter + "\", "
                    + " " + "Enter one of the filters: week/month/year", new Throwable(
                    "Invalid filter value"));
        }
        return ResponseEntity.ok(salesService.getSalesByDateRange(filter));
    }
}
